package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    private String tableName = "user";
    Util util = new Util();

    public void createUsersTable() {
        Statement statement = null;
        String createUsersTable = "CREATE TABLE IF NOT EXISTS " +
                tableName +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY , " +
                "name VARCHAR(45) NOT NULL, " +
                "lastName VARCHAR(45) NOT NULL, " +
                "age MEDIUMINT NOT NULL)";
        try(Connection connection = util.getInstance().getConnection()) {
            statement = connection.createStatement();
            statement.execute(createUsersTable);
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        Statement statement = null;
        String dropUsersTable = "DROP TABLE IF EXISTS " +
                tableName;
        try(Connection connection = util.getConnection()) {
            statement = connection.createStatement();
            statement.execute(dropUsersTable);
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement statement = null;
        String saveUser = "INSERT INTO " +
                tableName +
                "( name , lastName, age ) " +
                " values ('" + name + "', '" + lastName + "', '" + age + "')";
        try(Connection connection = util.getConnection()) {
            statement = connection.prepareStatement(saveUser);
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("User с именем " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        PreparedStatement statement = null;
        String removeUserById = "DELETE FROM " +
                tableName +
                " WHERE id = " + id;
        try(Connection connection = util.getConnection()) {
            statement = connection.prepareStatement(removeUserById);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        Statement statement = null;
        List<User> users = new ArrayList<>();
        try(Connection connection = util.getConnection()) {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT  * FROM " + tableName);
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
    Statement statement = null;
    String cleanUsersTable = "TRUNCATE TABLE " +
                tableName;
        try(Connection connection = util.getConnection()) {
            statement = connection.createStatement();
            statement.executeUpdate(cleanUsersTable);
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

