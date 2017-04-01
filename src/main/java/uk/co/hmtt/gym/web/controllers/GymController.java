package uk.co.hmtt.gym.web.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.co.hmtt.gym.config.web.GymUser;

@RequestMapping(value = {"/gym"})
public class GymController {

    protected uk.co.hmtt.gym.app.model.User getUser() {
        return ((GymUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getGymUser();
    }

}
