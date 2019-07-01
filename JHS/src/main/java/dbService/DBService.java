package dbService;

import dbService.dao.UsersDAO;
import dbService.dataSets.UsersDataSets;
import org.eclipse.jetty.server.Authentication;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


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
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            UsersDAO dao = new UsersDAO(session);
            long id = dao.insertUser(name, password);
            transaction.commit();
            session.close();
            return id;
        } catch (HibernateException e) {
            try {
                throw new DBException(e);
            } catch (Exception e1) {
                System.out.println(e1);
            }
            return 0;
        }
    }

    public long getUserId(String name) {
        try {
            Session session = sessionFactory.openSession();
            UsersDAO dao = new UsersDAO(session);
            long id = dao.getUserId(name);
            session.close();
            return id;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
    }

    public UsersDataSets getUser(long id) {
        try {
            Session session = sessionFactory.openSession();
            UsersDAO dao = new UsersDAO(session);
            UsersDataSets usersDataSets = dao.getUserById(id);
            session.close();
            return usersDataSets;
        } catch (HibernateException e) {
            try {
                throw new DBException(e);

            } catch (Exception e1) {
                System.out.println(e1);
            }
            return new UsersDataSets("dummy","dummy");
        }
    }

    public void deleteUser(String name) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            UsersDAO dao = new UsersDAO(session);
            dao.deleteUser(name);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateUser(String name) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            UsersDAO dao = new UsersDAO(session);
            dao.updateUser(name);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}