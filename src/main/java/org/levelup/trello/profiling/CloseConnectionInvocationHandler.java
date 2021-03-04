package org.levelup.trello.profiling;

import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;

@RequiredArgsConstructor
public class CloseConnectionInvocationHandler implements InvocationHandler {

    private final Connection connection;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("close")) {
                ConnectionAliveTimeService.getInstance()
                    .printConnectionAliveTime(connection);
        }
        return method.invoke(connection, args);
    }

}
