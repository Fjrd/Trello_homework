package org.levelup.trello.service;

import org.levelup.trello.model.Board;

public interface BoardRepository {

    Board createBoard(Integer userId, String name, boolean favourite);

}
