package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.boot.MetadataSources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/* class LazySingleton {
    private static LazySingleton Instance = null;
    private LazySingleton() {}
    public static LazySingleton getInstance() {
        if (Instance == null) {
            synchronized(LazySingleton.class) {
                Instance = new LazySingleton();
            }
        }
        return Instance;
    }
} */


public class Util {
    private static Util instance;
    private static final String userName = "root";
    private static final String password = "neh,ektynyjcnm_7621*";
    private static final String connectionUrl = "jdbc:mysql://localhost:3306/base?serverTimezone=UTC";
    private static SessionFactory sessionFactory;

    Connection connection = null;

    public Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection(connectionUrl, userName, password);
            System.out.println("Подключение установлено");
        } catch (SQLException e) {
            System.out.println("Подключение отсутствует");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
    public static Util getInstance() throws SQLException {
        if (instance == null) {
            instance = new Util();
        } else if (instance.getConnection().isClosed()) {
            instance = new Util();
        }
        return instance;
    }


    public static SessionFactory getSessionFactory() {
        try {
            if (sessionFactory == null) {
                StandardServiceRegistryBuilder registryBuilder =
                        new StandardServiceRegistryBuilder();
                Map<String, String> settings = new HashMap<>();
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/base?serverTimezone=UTC");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "neh,ektynyjcnm_7621*");
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                registryBuilder.applySettings(settings);
                StandardServiceRegistry registry = registryBuilder.build();
                MetadataSources metadataSources = new MetadataSources(registry)
                        .addAnnotatedClass(User.class);
                sessionFactory = metadataSources.buildMetadata().buildSessionFactory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }
}