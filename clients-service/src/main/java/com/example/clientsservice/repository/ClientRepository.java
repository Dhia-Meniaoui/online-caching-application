package com.example.clientsservice.repository;

import com.example.clientsservice.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends MongoRepository<Client,String> {
    Boolean existsByClientId(String clientId);

    Optional<Client> searchFirstByClientId(String clientId);

    Optional<Client> findByClientId(String clientId);

    List<Client> findAllByUserId(long parseLong);
}
