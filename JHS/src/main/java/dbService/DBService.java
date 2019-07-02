package dbService;

import dbService.dao.UsersDAO;
import dbService.dataSets.UsersDataSets;
import org.eclipse.jetty.server.Authentication;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class DBService {
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "create";
    private final SessionFactory sessionFactory;

    public DBService() {
        Configuration configuration = getMySqlConfiguration();
        this.sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private Configuration getMySqlConfiguration() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UsersDataSets.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/educ?serverTimezone=UTC");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "P@$$w0rd");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);

        return configuration;
    }

    public long addUser(String name, String password) {
        Session session = sessionFactory.openSession();

        try {
            Transaction transaction = session.beginTransaction();
            UsersDAO dao = new UsersDAO(session);
            long id = dao.insertUser(name, password);
            transaction.commit();
            return id;
        } catch (HibernateException e) {
            try {
                throw new DBException(e);
            } catch (Exception e1) {
                System.out.println(e1);
            }
            return 0;
        } finally {
            session.close();
        }
    }

    public long getUserId(String name) {
        Session session = sessionFactory.openSession();

        try {
            UsersDAO dao = new UsersDAO(session);
            long id = dao.getUserId(name);
            return id;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        } finally {
            session.close();
        }
    }

    public UsersDataSets getUser(long id) {
        Session session = sessionFactory.openSession();
        try {
            UsersDAO dao = new UsersDAO(session);
            UsersDataSets usersDataSets = dao.getUserById(id);
            return usersDataSets;
        } catch (HibernateException e) {
            try {
                throw new DBException(e);

            } catch (Exception e1) {
                System.out.println(e1);
            }
            return new UsersDataSets("dummy","dummy");
        } finally {
            session.close();
        }
    }

    public void deleteUser(String name) {
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();
            UsersDAO dao = new UsersDAO(session);
            dao.deleteUser(name);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
    }

    public void updateUser(String name, String password) {
        Session session = sessionFactory.openSession();

        try {
            Transaction transaction = session.beginTransaction();
            UsersDAO dao = new UsersDAO(session);
            dao.updateUser(name, password);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
    }

    public List<UsersDataSets> getAllUsers() {
        Session session = sessionFactory.openSession();
        List<UsersDataSets> allUsers = new ArrayList<UsersDataSets>();
        try {
            UsersDAO dao = new UsersDAO(session);
            allUsers = dao.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return allUsers;
    }

}