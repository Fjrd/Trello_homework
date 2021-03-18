package org.levelup.trello.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Setter
@Getter
@ToString(exclude = "users")
@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToMany(mappedBy = "teams", cascade = CascadeType.ALL)
    private Collection<User> users;

}
