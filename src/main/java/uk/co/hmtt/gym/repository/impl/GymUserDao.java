package uk.co.hmtt.gym.repository.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.co.hmtt.gym.app.model.User;
import uk.co.hmtt.gym.repository.UserDao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

@Repository
public class GymUserDao implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User findUser(String email) {
        final List<User> users = sessionFactory.getCurrentSession().createQuery("from User where email=?").setParameter(0, email).list();
        return isNotEmpty(users) ? users.get(0) : null;
    }

    @Override
    public Set<User> findUsers() {
        return new HashSet<>(sessionFactory.getCurrentSession().createQuery("from User").list());
    }

    @Override
    public void updateUser(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }

}
