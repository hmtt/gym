package uk.co.hmtt.gym.app.service;

import uk.co.hmtt.gym.app.model.User;

import java.util.Set;

public interface UserService {

    User getUser(String email);

    Set<User> getUsers();

    void updateUserForSuccessfulLogon(User user);

    void updateUserForFailedLogon(User user);

}
