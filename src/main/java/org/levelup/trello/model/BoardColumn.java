package org.levelup.trello.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "columns")
public class BoardColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "column_order")
    private Integer order;

}
