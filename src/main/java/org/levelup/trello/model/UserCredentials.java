package org.levelup.trello.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@ToString
@Entity
@Table(name = "user_credentials")
public class UserCredentials {

    @Id
    @Column(name = "user_id")
    private Integer userId;
    private String password;

}
