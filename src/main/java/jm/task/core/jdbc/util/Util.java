package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import java.sql.*;

import java.util.Properties;


public class Util {
    public static SessionFactory getSessionFactory()
    {
        Properties prop = new Properties();
        prop.setProperty("connection.driver_class", "com.mysql.cj.jdbc.Driver");
        prop.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/task?useSSL=false&serverTimeZone=UTC");
        prop.setProperty("hibernate.connection.username", "Root1993");
        prop.setProperty("hibernate.connection.password", "root1993");
        prop.setProperty("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
        prop.setProperty("hibernate.use_sql_comments", "true");
        prop.setProperty("hibernate.show_sql", "true");

        Configuration cfg = new Configuration();
        cfg.addAnnotatedClass(User.class);
        cfg.setProperties(prop);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(cfg.getProperties()).build();

        return cfg.buildSessionFactory(serviceRegistry);
    }

    final static String URL = "jdbc:mysql://localhost:3306/task?useSSL=false&serverTimeZone=UTC";
    final static String USERNAME = "Root1993";
    final static String PASSWORD = "root1993";
    final static String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            System.out.println(e);
        } catch(ClassNotFoundException a){
            a.printStackTrace();
        }
        return connection;
    }
    public static void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }
}
