package org.levelup.trello;

import org.levelup.trello.jdbc.JdbcBoardService;
import org.levelup.trello.jdbc.JdbcUserService;
import org.levelup.trello.service.TerminalService;


import java.io.IOException;
import java.sql.SQLException;

public class TrelloApplication {

    public static void main(String[] args) throws IOException, SQLException {
        TerminalService terminalService = new TerminalService(new JdbcUserService(), new JdbcBoardService());
        terminalService.startUp();
    }


}
