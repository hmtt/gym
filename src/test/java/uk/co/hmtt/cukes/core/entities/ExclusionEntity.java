package uk.co.hmtt.cukes.core.entities;

import uk.co.hmtt.cukes.core.annotations.Bind;
import uk.co.hmtt.cukes.core.annotations.Unique;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import static java.util.Collections.singletonList;

public class ExclusionEntity implements Entity<ExclusionEntity> {

    public static final String USER_ACTIVITY_ACTIVITY_ID = "userActivity_activityId";
    public static final String USER_ACTIVITY_USER_ID = "userActivity_userId";
    public static final String EXCLUSION_DATE = "exclusionDate";
    @Bind
    @Unique
    private int userActivity_userId;
    @Bind
    @Unique
    private int userActivity_activityId;
    @Bind
    @Unique
    private Date exclusionDate;

    private static EntityDao entityDao;

    @Override
    public ExclusionEntity mapRow(ResultSet resultSet, int i) throws SQLException {
        return buildExclusionEntity()
                .withActivityId(resultSet.getInt(USER_ACTIVITY_ACTIVITY_ID))
                .withUserId(resultSet.getInt(USER_ACTIVITY_USER_ID))
                .withExclusionDate(resultSet.getDate(EXCLUSION_DATE));
    }

    @Override
    public String getTableName() {
        return EntityDao.TABLE_NAME.EXCLUSION.toString();
    }

    @Override
    public ExclusionEntity persist() {
        entityDao.insert(ExclusionEntity.class, singletonList(this));
        return this;
    }

    public static void setEntityDao(EntityDao dao) {
        entityDao = dao;
    }

    public static ExclusionEntity buildExclusionEntity() {
        return new ExclusionEntity();
    }

    public ExclusionEntity withUserId(int userId) {
        this.userActivity_userId = userId;
        return this;
    }

    public ExclusionEntity withActivityId(int activityId) {
        this.userActivity_activityId = activityId;
        return this;
    }

    public ExclusionEntity withExclusionDate(Date exclusionDate) {
        this.exclusionDate = exclusionDate;
        return this;
    }

    public Date getExclusionDate() {
        return exclusionDate;
    }

    public int getUserActivity_userId() {
        return userActivity_userId;
    }

    public int getUserActivity_activityId() {
        return userActivity_activityId;
    }


}
