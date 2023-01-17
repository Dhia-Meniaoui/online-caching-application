package com.example.clientsservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Client {
    @Id @JsonIgnore
    private ObjectId id;
    private String clientId;
    private String secretKey;
    private String applicationName;
    private String applicationUrl;
    private String applicationDescription;
    private ECurrency currency;
    @JsonIgnore
    private Long userId;
    public Client() {

    }



}
