package uk.co.hmtt.gym.repository;

import uk.co.hmtt.gym.app.model.User;

import java.util.Set;

public interface UserDao {

    User findUser(String email);

    Set<User> findUsers();

    void updateUser(User user);

}
