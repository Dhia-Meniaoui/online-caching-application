package com.example.usersservice.repositories;

import com.example.usersservice.models.CustomUser;
import com.example.usersservice.models.ECurrency;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class CustomUserRepositoryTest {
    @Autowired
    private CustomUserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void findByUsername() {
        //given
        CustomUser user = CustomUser.builder()
                .username("test")
                .password("test")
                .email("aaaa@test.com")
                .firstName("aaa")
                .lastName("aaa")
                .phone("aaa")
                .credit(0.0)
                .imageURL("aaa")
                .defaultCurrency(ECurrency.USD)
                .enabled(true)
                .build();
        underTest.save(user);
        //when
        CustomUser foundUser = underTest.findByUsername("test").orElse(null);
        //then
        assertThat(foundUser.getUsername()).isEqualTo(user.getUsername());
    }
}