package org.levelup.trello.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "boards")
@Setter
@Getter
@ToString(exclude = "owner")
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Integer id;
    private String name;
    private Boolean favourite;
    @ManyToOne // by default, owner_id
    @JoinColumn(name = "owner_id") // связь с таблицей users происходит по колонке owner_id
    private User owner;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id", nullable = false) // колонка в таблице columns, которая является foreign key на таблицу boards
    private Collection<BoardColumn> columns;

}
