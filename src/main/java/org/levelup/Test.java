package org.levelup;

public class Test {

    public static void main(String[] args) {
        String login = "l";
        String name = "n";
        String password = "p";

        String s = "insert into users (login, name, email) values (" + login + ", " + name + ", " + password + ")";
        String c = "insert into users (login, name, email) values (l, n, p)";
        String c1 = "insert into users (login, name, email) values ('l', 'n', 'p')";

        // connection.createStatement();
        // stmt.executeUpdate(s); - insert/update/delete commands
    }

}
