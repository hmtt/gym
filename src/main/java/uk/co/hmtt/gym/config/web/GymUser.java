package uk.co.hmtt.gym.config.web;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;

public class GymUser extends User {

    private static final long serialVersionUID = 1L;
    private final uk.co.hmtt.gym.app.model.User modelGymUser;

    public GymUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, uk.co.hmtt.gym.app.model.User gymUser) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.modelGymUser = gymUser;
    }

    public uk.co.hmtt.gym.app.model.User getGymUser() {
        return modelGymUser;
    }

    @Override
    public boolean equals(Object obj) {
        return reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
