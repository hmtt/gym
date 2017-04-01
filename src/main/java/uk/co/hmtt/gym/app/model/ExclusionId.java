package uk.co.hmtt.gym.app.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

@Embeddable
public class ExclusionId implements Serializable {

    private static final long serialVersionUID = 1L;
    private UserActivity userActivity;
    private Date exclusionDate;

    @ManyToOne
    public UserActivity getUserActivity() {
        return userActivity;
    }

    public void setUserActivity(UserActivity userActivity) {
        this.userActivity = userActivity;
    }

    public Date getExclusionDate() {
        return (exclusionDate != null) ? (Date) exclusionDate.clone() : null;
    }

    public void setExclusionDate(Date exclusionDate) {
        if (exclusionDate != null) {
            this.exclusionDate = (Date) exclusionDate.clone();
        }
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
