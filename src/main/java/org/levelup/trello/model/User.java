package org.levelup.trello.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class User {
    private Integer id;
    private String login;
    private String email;
    private String name;
}
