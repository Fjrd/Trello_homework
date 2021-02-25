package org.levelup.trello.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Board {
    private Integer id;
    private String name;
    private boolean favourite;
    private Integer ownerId;
}
