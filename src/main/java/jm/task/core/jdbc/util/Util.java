package jm.task.core.jdbc.util;
import com.mysql.cj.Session;
import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.mapping.MetadataSource;
import org.hibernate.metamodel.Metadata;
import org.hibernate.metamodel.MetadataSources;
import org.hibernate.service.Service;
import org.hibernate.service.ServiceRegistry;
import javax.xml.stream.events.StartDocument;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class Util {

    private static StandardServiceRegistry standardServiceRegistry;
    private static SessionFactory sessionFactory;

        static {
           try {
               StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

               Map<String, String> dbSettings = new HashMap<>();
               dbSettings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
               dbSettings.put(Environment.URL, "jdbc:mysql://localhost:3306/task?useSSL=false&serverTimeZone=UTC");
               dbSettings.put(Environment.USER, "Root1993");
               dbSettings.put(Environment.PASS, "root1993");
               dbSettings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
               dbSettings.put(Environment.SHOW_SQL, "true");
               dbSettings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
               registryBuilder.applySettings(dbSettings);
               standardServiceRegistry = registryBuilder.build();
               MetadataSources source = new MetadataSources(standardServiceRegistry);
               Metadata metadata = source.getMetadataBuilder().build();
               sessionFactory = metadata.getSessionFactoryBuilder().build();
           } catch (HibernateException exception) {
               System.out.println("Problem creating session factory");
               exception.printStackTrace();
           }
       }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
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
