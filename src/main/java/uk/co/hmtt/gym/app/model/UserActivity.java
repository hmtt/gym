package uk.co.hmtt.gym.app.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

@Entity
@Table(name = "User_Activity", schema = "booker")
@AssociationOverrides({
        @AssociationOverride(name = "pk.user",
                joinColumns = {@JoinColumn(name = "userId")}),
        @AssociationOverride(name = "pk.activity",
                joinColumns = {@JoinColumn(name = "activityId")})})
public class UserActivity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private UserActivityId pk = new UserActivityId();
    private Set<Exclusion> exclusions = new HashSet<>(0);
    private Date lastBooked;

    @EmbeddedId
    public UserActivityId getPk() {
        return pk;
    }

    public void setPk(UserActivityId userActivityId) {
        this.pk = userActivityId;
    }

    @Transient
    public User getUser() {
        return pk.getUser();
    }

    public void setUser(User user) {
        getPk().setUser(user);
    }

    @Transient
    public Activity getActivity() {
        return pk.getActivity();
    }

    public void setActivity(Activity activity) {
        getPk().setActivity(activity);
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastBooked", nullable = true)
    public Date getLastBooked() {
        return (lastBooked != null) ? (Date) lastBooked.clone() : null;
    }

    public void setLastBooked(Date lastBooked) {
        if (lastBooked != null) {
            this.lastBooked = (Date) lastBooked.clone();
        }
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.userActivity")
    public Set<Exclusion> getExclusions() {
        return this.exclusions;
    }

    public void setExclusions(Set<Exclusion> exclusions) {
        this.exclusions = exclusions;
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
