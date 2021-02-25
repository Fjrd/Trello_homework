package org.levelup.trello.service;

import org.levelup.trello.model.Board;

import java.util.ArrayList;

public interface BoardService {
    ArrayList<Board> showUserBoards(Integer userId);
    void deleteBoard(Integer id);
    Board editBoard(Integer id);
    Board addNewBoard(String name, Boolean favourite, Integer userId);
}
