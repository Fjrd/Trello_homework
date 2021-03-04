package org.levelup.trello.profiling;

import lombok.RequiredArgsConstructor;
import org.levelup.trello.jdbc.JdbcConnectionService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.Arrays;

@RequiredArgsConstructor
public class OpenConnectionServiceInvocationHandler implements InvocationHandler {

    private final JdbcConnectionService jdbcConnectionService;
    //

    // Method - метод интерфейса
    // Object[] args - значения аргуметов
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // Method - какой-то метод из интерфейса ConnectionService.
        Class<?> clazz = jdbcConnectionService.getClass();
        Method classMethod = getOverriddenMethod(clazz, method, args);

        // ConnectionService#openConnection() -> Method@4435234 - не содержит информации об аннотации
        // JdbcConnectionService#openConnection() -> Method@4o35934

        if (classMethod.getAnnotation(AliveTime.class) != null) {
            long start = System.nanoTime();

            Object result = method.invoke(jdbcConnectionService, args); // jdbcConnectionService.method(args) -> jdbcConnectionService.openConnection()

            long end = System.nanoTime();
            System.out.println("Time to acquiring connection: " + (end - start) + " ns");

            ConnectionAliveTimeService.getInstance()
                    .writeTimeWhenConnectionWasOpened((Connection) result); // записали время открытия соединения

            return createConnectionProxy((Connection) result);
        }

        return method.invoke(jdbcConnectionService, args);
    }

    private Object createConnectionProxy(Connection connection) {
        return Proxy.newProxyInstance(
                connection.getClass().getClassLoader(),
                connection.getClass().getInterfaces(),
                new CloseConnectionInvocationHandler(connection)
        );
    }

    private Method findMethod(Class<?> clazz, Method interfaceMethod) {
        Method[] classMethods = clazz.getMethods(); // getMethods() - возвращает все публичные методы
        for (Method classMethod : classMethods) {
            // TODO: Сделать сравнение массивов типов из classMethod и interfaceMethod
            if (classMethod.getName().equals(interfaceMethod.getName())) {
                return classMethod;
            }
        }

        throw new AbstractMethodError("Couldn't find method in class but it defined in interface");
    }

    private Method getOverriddenMethod(Class<?> clazz, Method interfaceMethod, Object[] args) {
        // Object[] args -> Class[] argTypes
        Class<?>[] argTypes = args == null ? null :
                (Class<?>[]) Arrays.stream(args)
                        .map(arg -> arg.getClass())
                        .toArray();
        try {
            return clazz.getMethod(interfaceMethod.getName(), argTypes);
        } catch (NoSuchMethodException exc) {
            throw new AbstractMethodError("Couldn't find method in class but it defined in interface");
        }
    }

}
