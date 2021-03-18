package org.levelup.trello.profiling;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
public class ConnectionAliveTimeService {

    // private long openConnectionTime;

    private static ConnectionAliveTimeService instance = new ConnectionAliveTimeService();
    private Map<Connection, Long> connectionOpenTimes;

    private ConnectionAliveTimeService() {
        this.connectionOpenTimes = new HashMap<>();
    }

    // ConnectionAliveTimeService.getInstance();
    public static ConnectionAliveTimeService getInstance() {
        return instance;
    }

    public void writeTimeWhenConnectionWasOpened(Connection connection) {
        connectionOpenTimes.put(connection, System.currentTimeMillis()); // записали когда открылось соединение
    }

    public void printConnectionAliveTime(Connection connection) {
        Long openTime = connectionOpenTimes.remove(connection);
        if (openTime != null) {
            System.out.println("Connection alive time: " + (System.currentTimeMillis() - openTime) + "ms");
        } else {
            System.out.println("No open connection time for such connection...");
        }
    }

}
