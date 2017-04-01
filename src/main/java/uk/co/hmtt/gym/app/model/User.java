package uk.co.hmtt.gym.app.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

@Entity
@Table(name = "User", schema = "booker", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")})
public class User implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String email;
    private String passcode;
    private Date lastLoggedIn;
    private int failedToLoginCount;
    private boolean enabled;
    private Set<UserActivity> userActivity = new HashSet<>(0);

    public User() {
        // Explicit declaration of default constructor
    }

    public User(String email, String passcode) {
        this.email = email;
        this.passcode = passcode;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "email", nullable = false, length = 150)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "passcode", nullable = false, length = 4)
    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastLoggedIn", nullable = false)
    public Date getLastLoggedIn() {
        return (lastLoggedIn != null) ? (Date) lastLoggedIn.clone() : null;
    }

    public void setLastLoggedIn(Date lastLoggedIn) {
        if (lastLoggedIn != null) {
            this.lastLoggedIn = (Date) lastLoggedIn.clone();
        }
    }

    @Column(name = "failedToLoginCount", nullable = false)
    public int getFailedToLoginCount() {
        return failedToLoginCount;
    }

    public void setFailedToLoginCount(int failedToLoginCount) {
        this.failedToLoginCount = failedToLoginCount;
    }

    @Column(name = "enabled", nullable = false)
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.user", cascade = CascadeType.ALL)
    public Set<UserActivity> getUserActivity() {
        return this.userActivity;
    }

    public void setUserActivity(Set<UserActivity> userActivity) {
        this.userActivity = userActivity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }

}
