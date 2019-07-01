package dbService.dao;

import dbService.dataSets.UsersDataSets;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

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

    public void updateUser(String name) throws HibernateException{
        session.update(getUserById(getUserId(name)));
    }


}
