package com.example.usersservice.services;

import com.example.usersservice.exception.CustomException;
import com.example.usersservice.models.CustomUser;
import com.example.usersservice.models.ECurrency;
import com.example.usersservice.models.ERole;
import com.example.usersservice.models.Role;
import com.example.usersservice.payload.reponse.JwtResponse;
import com.example.usersservice.payload.reponse.MessageResponse;
import com.example.usersservice.payload.request.LoginRequest;
import com.example.usersservice.payload.request.SignupRequest;
import com.example.usersservice.repositories.CustomUserRepository;
import com.example.usersservice.repositories.RoleRepository;
import com.example.usersservice.security.jwt.JwtUtils;
import com.example.usersservice.security.verficationKey.VerificationToken;
import com.example.usersservice.security.verficationKey.VerificationTokenRepository;
import com.example.usersservice.security.verficationKey.VerificationTokenService;
import com.kastourik12.amqp.RabbitMQMessageProducer;
import com.kastourik12.clients.notification.NotificationEmail;
import com.kastourik12.clients.users.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final RabbitMQMessageProducer rabbitMQMessageProducer;
    private final VerificationTokenService verificationTokenService;
    private final CustomUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final VerificationTokenRepository verificationTokenRepository;


    private final PasswordEncoder encoder;
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        CustomUser user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + loginRequest.getUsername()));
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                            loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            JwtResponse response = JwtResponse.builder()
                    .token(jwt)
                    .username(user.getUsername())
                    .expiresAt(Date.from(Instant.now().plusMillis(jwtUtils.getJwtExpirationInMillis())))
                    .balance(user.getCredit())
                    .build();
            return ResponseEntity.ok(response);
    }
    public ResponseEntity<?> saveUser(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        if(userRepository.existsByPhone(signupRequest.getPhoneNumber())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Phone is already in use!"));
        }
        CustomUser user = CustomUser.builder()
                .username(signupRequest.getUsername())
                .password(encoder.encode(signupRequest.getPassword()))
                .email(signupRequest.getEmail())
                .firstName(signupRequest.getFirstName())
                .lastName(signupRequest.getLastName())
                .phone(signupRequest.getPhoneNumber())
                .enabled(false)
                .defaultCurrency(ECurrency.USD)
                .credit(0.0)
                .roles(new HashSet<>())
                .build();
            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new CustomException("Error: Role is not found."));
            roles.add(userRole);
            user.setRoles(roles);
        userRepository.save(user);
        String token =verificationTokenService.generateVerificationToken(user.getUsername());
        NotificationEmail notificationEmail = NotificationEmail.builder()
                .subject("Verify your account")
                .recipient(user.getEmail())
                .body("To verify your account, please click on the link below:\n" +
                        "http://localhost:8081/api/auth/accountVerification/"+ token)
                .build();
        rabbitMQMessageProducer.publish(notificationEmail, "notification.exchange","internal.email.routing-key");
        return ResponseEntity.ok(new MessageResponse("User registered successfully! you need to activate your account ! check your email"));
    }

    public ResponseEntity<?> verifyUser(String verificationToken) {
        VerificationToken token = verificationTokenRepository.findByToken(verificationToken).orElseThrow(()-> new CustomException("Invalid Token"));
        String username = token.getUsername();
        CustomUser user = userRepository.findByUsername(username).orElseThrow(() -> new CustomException("User not found with name - " + username));
        user.setEnabled(true);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User verified successfully!"));
    }




    public ResponseEntity<UserDTO> validateToken(String token) {
        if(jwtUtils.validateJwtToken(token)) {
            String username = jwtUtils.getUsernameFromJwtToken(token);
            Optional<CustomUser> user = userRepository.findByUsername(username);
            UserDTO userDTO = new UserDTO(user.get().getId(), user.get().getUsername());
            return ResponseEntity.ok(userDTO);
        }
        throw new CustomException("Invalid Token");
    }

    public String getUsernameFromToken(String token) {
        return jwtUtils.getUsernameFromJwtToken(token);
    }
}


