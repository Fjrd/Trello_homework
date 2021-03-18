package org.levelup.trello.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionService {

    Connection openConnection() throws SQLException;

}
