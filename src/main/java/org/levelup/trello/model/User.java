package org.levelup.trello.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

//@Data
@Setter
@Getter
@Entity
@Table(name = "users")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class User {

    @Id // primary key из таблицы users
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "login")
    private String login;
    private String email;
    @OneToMany(mappedBy = "owner") // mappedBy прописывается только в случае bidirectional связи
    private Collection<Board> boards;
    @OneToOne
    @MapsId
    private UserCredentials credentials;
    @ManyToMany
    @JoinTable(
            name = "team_member",
            joinColumns = @JoinColumn(name = "user_id"), // колонка из таблицы team_member, которая ссылается на Id User
            inverseJoinColumns = @JoinColumn(name = "team_id") // колонка из таблицы team_member, которая ссылается на Id Team
    )
    private Collection<Team> teams;

    public User(Integer id, String name, String login, String email) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.email = email;
        this.boards = new ArrayList<>();
        this.teams = new ArrayList<>();
    }

}
