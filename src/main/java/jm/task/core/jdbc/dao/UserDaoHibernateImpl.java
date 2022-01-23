package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    public final SessionFactory sessionFactory = Util.getSessionFactory();


    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS User( id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                    " name VARCHAR(255), lastName VARCHAR(255), age INT ); ");
            query.executeUpdate();
            transaction.commit();
        }catch(HibernateException ex){
            if(transaction != null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("DROP TABLE IF EXISTS USER");
            query.executeUpdate();
            transaction.commit();
        }catch(HibernateException ex){
            if(transaction != null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }


    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("DELETE FROM User WHERE id = " + id).executeUpdate();
            transaction.commit();
        }catch(HibernateException ex){
            if(transaction != null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<User> userList = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            userList = session.createQuery("FROM User").list();
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }finally {
            if (session != null)
                session.close();
        }
        return userList;
    }


    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("TRUNCATE User");
            query.executeUpdate();
            transaction.commit();
        }catch(HibernateException ex){
            if(transaction != null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }finally {
            session.close();
        }
    }
}
