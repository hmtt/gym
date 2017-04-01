package uk.co.hmtt.gym.repository.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.co.hmtt.gym.app.model.Activity;
import uk.co.hmtt.gym.app.model.Exclusion;
import uk.co.hmtt.gym.app.model.User;
import uk.co.hmtt.gym.repository.UserActivityDao;

import java.util.Collections;
import java.util.List;

@Repository
public class GymUserActivityDao implements UserActivityDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<uk.co.hmtt.gym.app.model.UserActivity> getAllScheduledBookingRequests() {
        return sessionFactory.getCurrentSession().createQuery("from UserActivity").list();
    }

    @Override
    public uk.co.hmtt.gym.app.model.UserActivity getScheduledBookingRequest(User user, Activity activity) {
        final List<?> activities = sessionFactory.getCurrentSession().createQuery("from UserActivity where userId=? and activityId=?").setParameter(0, user.getId()).setParameter(1, activity.getId()).list();
        return (activities.size() == 1) ? (uk.co.hmtt.gym.app.model.UserActivity) activities.get(0) : null;
    }

    @Override
    public List<uk.co.hmtt.gym.app.model.UserActivity> getScheduledBookingRequests(User user) {
        return sessionFactory.getCurrentSession().createQuery("from UserActivity where userId=?").setParameter(0, user.getId()).list();
    }

    @Override
    public void addScheduledBookingRequest(uk.co.hmtt.gym.app.model.UserActivity userActivity) {
        sessionFactory.getCurrentSession().saveOrUpdate(userActivity);
    }

    @Override
    public void updateScheduledBookingRequest(uk.co.hmtt.gym.app.model.UserActivity userActivity) {
        sessionFactory.getCurrentSession().saveOrUpdate(userActivity);
    }

    @Override
    public List<Exclusion> getExclusions(final uk.co.hmtt.gym.app.model.UserActivity userActivity) {

        if (userActivity == null || userActivity.getUser().getId() == null || userActivity.getActivity().getId() == null) {
            return Collections.emptyList();
        }

        return sessionFactory.getCurrentSession().
                createQuery("from Exclusion where userActivity_userId= :userId and userActivity_activityId= :activityId").
                setInteger("userId", userActivity.getUser().getId()).
                setInteger("activityId", userActivity.getActivity().getId()).
                list();

    }

    @Override
    public void addExclusion(Exclusion exclusion) {
        sessionFactory.getCurrentSession().saveOrUpdate(exclusion);
    }

    @Override
    public void deleteExclusion(Exclusion exclusion) {
        sessionFactory.getCurrentSession().delete(exclusion);
    }

    @Override
    public void deleteScheduledBookingRequest(uk.co.hmtt.gym.app.model.UserActivity userActivity) {
        sessionFactory.getCurrentSession().delete(userActivity);
    }

}
