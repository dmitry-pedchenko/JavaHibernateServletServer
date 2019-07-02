package dbService.dao;

import dbService.dataSets.UsersDataSets;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.*;

public class UsersDAO {
    private Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }



    public long getUserId(String name) throws HibernateException {
        Criteria criteria = session.createCriteria(UsersDataSets.class);
        return ((UsersDataSets) criteria.add(Restrictions.eq("user_names", name)).uniqueResult()).getId();
    }

    public UsersDataSets getUserById(long id) throws HibernateException {
        return (UsersDataSets) session.get(UsersDataSets.class, id);
    }

    public long insertUser(String name) throws HibernateException {
        return (Long) session.save(new UsersDataSets(name));
    }

    public long insertUser(String name, String password) throws HibernateException {
        return (Long) session.save(new UsersDataSets(name, password));
    }

    public void deleteUser(String name) throws HibernateException {
        session.delete(getUserById(getUserId(name)));
    }

    public void updateUser(String name, String password) throws HibernateException{
        session.merge(new UsersDataSets(getUserId(name), name, password));
//        session.saveOrUpdate(new UsersDataSets(getUserId(name), name, password));
    }

    public List<UsersDataSets> getAllUsers() throws HibernateException {
        Criteria criteria = session.createCriteria(UsersDataSets.class);
        List<UsersDataSets> allUsers = null;
        List<UsersDataSets> allUsersFinal = new ArrayList<UsersDataSets>();
        try {
            allUsers = criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        for (UsersDataSets usersDataSets : allUsers) {
            allUsersFinal.add(new UsersDataSets(usersDataSets.getUser_names(), usersDataSets.getPassword()));
        }

        return allUsersFinal;
    }
}