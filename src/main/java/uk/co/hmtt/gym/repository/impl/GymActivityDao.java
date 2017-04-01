package uk.co.hmtt.gym.repository.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.co.hmtt.gym.app.model.Activity;
import uk.co.hmtt.gym.repository.ActivityDao;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

@Repository
public class GymActivityDao implements ActivityDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int addActivity(String className, String classDate) {
        Activity activity = getActivity(className, classDate);

        if (activity == null) {
            activity = createActivity(className, classDate);
        }

        activity.setLastChecked(Calendar.getInstance(Locale.UK).getTime());
        sessionFactory.getCurrentSession().saveOrUpdate(activity);
        return activity.getId();
    }

    @Override
    public Activity getActivity(String className, String classDate) {
        final List<?> activity = sessionFactory.getCurrentSession().
                createQuery("from Activity where className = :className and classDate = :classDate").
                setString("className", className).setString("classDate", classDate).list();
        return getFirstResult(activity);
    }

    @Override
    public Activity getActivity(int id) {
        final List<?> activity = sessionFactory.getCurrentSession().
                createQuery("from Activity where id = :id").setString("id", Integer.toString(id)).list();
        return getFirstResult(activity);
    }

    @Override
    public List<Activity> getAllActivities() {
        return sessionFactory.getCurrentSession().createQuery("from Activity order by className").list();
    }

    @Override
    public List<Activity> getTimesForActivity(String className) {
        return sessionFactory.getCurrentSession().
                createQuery("from Activity where className = :className order by className, classDate").
                setString("className", className).list();
    }

    private Activity createActivity(String className, String classDate) {
        Activity activity;
        activity = new Activity();
        activity.setClassName(className);
        activity.setClassDate(classDate);
        return activity;
    }

    private Activity getFirstResult(List<?> activity) {
        return isNotEmpty(activity) ? (Activity) activity.get(0) : null;
    }

}
