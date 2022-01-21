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
            String sql = "CREATE TABLE IF NOT EXISTS User( id INT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                    " name VARCHAR(255), lastName VARCHAR(255), age INT ); ";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        }catch(HibernateException ex){
            if(transaction != null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS User";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        }catch(HibernateException ex){
            if(transaction != null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            User us = new User(name, lastName, age);
            session.save(us);
            session.getTransaction().commit();
        }catch(HibernateException ex){
            if(transaction != null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        }catch(HibernateException ex){
            if(transaction != null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<User> userList = null;
        try {
            transaction = session.beginTransaction();
            String sql = "SELECT * FROM User";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            userList = query.list();
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        session.close();
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String sql = "TRUNCATE User";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        }catch(HibernateException ex){
            if(transaction != null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        session.close();
    }
}
