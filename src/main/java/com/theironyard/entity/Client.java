package com.theironyard.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by jeffreydorney on 10/18/16.
 */
@Entity
public class Client {

    @Id
    @NotBlank
    private int id;

    @NotBlank
    private String clientId;

    @NotBlank
    private String clientSecret;

    public Client(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public Client() {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
