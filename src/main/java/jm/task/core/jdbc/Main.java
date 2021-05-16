package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Util util = new Util();
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Alla", "Pugacheva", (byte) 72);
        userService.saveUser("Maksim", "Galkin", (byte) 44);
        userService.saveUser("Vladimir", "Kuzmin", (byte) 65);
        userService.saveUser("Filipp", "Kirkorov", (byte) 54);
        userService.getAllUsers();
        userService.removeUserById(3);

    }
}
