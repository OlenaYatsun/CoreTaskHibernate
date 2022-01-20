package jm.task.core.jdbc;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Main {

    public static void main(String[] args)  {
        UserServiceImpl user = new UserServiceImpl();
        user.createUsersTable();
        user.saveUser("Ivan","Pupkin", (byte) 30);
        user.saveUser("Vasilisa", "Sokolova", (byte) 29);
        user.saveUser("Bueon", "Hoon", (byte) 31);
        user.saveUser("Irina", "Pupkina", (byte) 28);
        user.getAllUsers();
        user.cleanUsersTable();
        user.dropUsersTable();

    }
}
