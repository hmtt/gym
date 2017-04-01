package uk.co.hmtt.cukes.core.entities;

import org.springframework.stereotype.Component;
import uk.co.hmtt.cukes.core.annotations.Bind;
import uk.co.hmtt.cukes.core.annotations.Unique;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import static java.util.Collections.singletonList;

@Component
public class UserActivityEntity implements Entity<UserActivityEntity> {

    @Bind
    @Unique
    private int userId;
    @Bind
    @Unique
    private int activityId;
    @Bind
    private Date lastBooked;

    public static final String USER_ID = "userId";
    public static final String ACTIVITY_ID = "activityId";
    public static final String LAST_BOOKED = "lastBooked";

    private static EntityDao entityDao;

    public static void setEntityDao(EntityDao dao) {
        entityDao = dao;
    }

    @Override
    public UserActivityEntity mapRow(ResultSet resultSet, int i) throws SQLException {
        return UserActivityEntity
                .buildActivityEntity(resultSet.getInt(USER_ID), resultSet.getInt(ACTIVITY_ID))
                .withLastUpdated(resultSet.getTimestamp(LAST_BOOKED));
    }

    @Override
    public String getTableName() {
        return EntityDao.TABLE_NAME.USER_ACTIVITY.toString();
    }

    @Override
    public UserActivityEntity persist() {
        entityDao.insert(UserActivityEntity.class, singletonList(this));
        return this;
    }

    public static UserActivityEntity buildActivityEntity(int userId, int activityId) {
        return new UserActivityEntity().withUserId(userId).withActivityId(activityId);
    }

    public int getUserId() {
        return userId;
    }

    public UserActivityEntity withUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getActivityId() {
        return activityId;
    }

    public UserActivityEntity withActivityId(int activityId) {
        this.activityId = activityId;
        return this;
    }

    public UserActivityEntity withLastUpdated(Date lastBooked) {
        this.lastBooked = lastBooked;
        return this;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public Date getLastBooked() {
        return lastBooked;
    }

    public void setLastBooked(Date lastBooked) {
        this.lastBooked = lastBooked;
    }
}
