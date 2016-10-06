package com.theironyard.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by davehochstrasser on 10/6/16.
 */
@Entity
public class Invitee {

    @Id
    private Long id;

    @ManyToOne
    private User invitee;

    private String emailTo;

    public Invitee(Long id, User invitee, String emailTo) {
        this.id = id;
        this.invitee = invitee;
        this.emailTo = emailTo;
    }

    public Invitee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getInvitee() {
        return invitee;
    }

    public void setInvitee(User invitee) {
        this.invitee = invitee;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }
}
