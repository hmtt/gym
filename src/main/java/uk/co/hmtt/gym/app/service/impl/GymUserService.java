package uk.co.hmtt.gym.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.hmtt.gym.app.model.User;
import uk.co.hmtt.gym.app.service.UserService;
import uk.co.hmtt.gym.repository.UserDao;

import java.util.Calendar;
import java.util.Locale;
import java.util.Set;

@Service
public class GymUserService implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public User getUser(String email) {
        return userDao.findUser(email);
    }

    @Override
    @Transactional
    public Set<User> getUsers() {
        return userDao.findUsers();
    }

    @Override
    @Transactional
    public void updateUserForSuccessfulLogon(User user) {
        user.setLastLoggedIn(Calendar.getInstance(Locale.UK).getTime());
        user.setFailedToLoginCount(0);
        user.setEnabled(true);
        userDao.updateUser(user);
    }

    @Override
    @Transactional
    public void updateUserForFailedLogon(User user) {
        user.setLastLoggedIn(Calendar.getInstance(Locale.UK).getTime());
        user.setFailedToLoginCount(user.getFailedToLoginCount() + 1);
        userDao.updateUser(user);
    }
}
