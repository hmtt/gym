package uk.co.hmtt.gym.app.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Exclusion", schema = "booker")
public class Exclusion implements Serializable {

    private static final long serialVersionUID = 1L;
    private ExclusionId pk = new ExclusionId();
    private UserActivity userActivity;

    @EmbeddedId
    public ExclusionId getPk() {
        return pk;
    }

    public void setPk(ExclusionId pk) {
        this.pk = pk;
    }

    @Transient
    public UserActivity getUserActivity() {
        return userActivity;
    }

//    public void setUserActivity(UserActivity userActivity) {
//        this.userActivity = userActivity;
//    }

    @Temporal(value = TemporalType.DATE)
    @Column(name = "exclusionDate", nullable = false, insertable = false, updatable = false)
    public Date getExclusionDate() {
        return this.getPk().getExclusionDate();
    }

    public void setExclusionDate(Date exclusionDate) {
        this.getPk().setExclusionDate(exclusionDate);
    }

}
