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
@Table(name = "Activity", schema = "booker", uniqueConstraints = {
        @UniqueConstraint(columnNames = "className"),
        @UniqueConstraint(columnNames = "classDate")})
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String className;
    private String classDate;
    private Date lastChecked;
    private Set<UserActivity> userActivities = new HashSet<>(0);

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "className", nullable = false, length = 10)
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Column(name = "classDate", nullable = false, length = 10)
    public String getClassDate() {
        return classDate;
    }

    public void setClassDate(String classDate) {
        this.classDate = classDate;
    }

    @Column(name = "lastChecked", nullable = false)
    public Date getLastChecked() {
        return (lastChecked != null) ? (Date) lastChecked.clone() : null;
    }

    public void setLastChecked(Date lastChecked) {
        if (lastChecked != null) {
            this.lastChecked = (Date) lastChecked.clone();
        }
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.activity")
    public Set<UserActivity> getUserActivities() {
        return userActivities;
    }

    public void setUserActivities(Set<UserActivity> userActivities) {
        this.userActivities = userActivities;
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
