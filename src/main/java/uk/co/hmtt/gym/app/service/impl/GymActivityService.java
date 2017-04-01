package uk.co.hmtt.gym.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.hmtt.gym.app.model.Activity;
import uk.co.hmtt.gym.app.service.ActivityService;
import uk.co.hmtt.gym.repository.ActivityDao;

import java.util.List;

@Service
public class GymActivityService implements ActivityService {

    @Autowired
    private ActivityDao activityDao;

    @Override
    @Transactional
    public void addActivity(String className, String classDate) {
        activityDao.addActivity(className, classDate);
    }

    @Override
    @Transactional
    public Activity getActivity(int id) {
        return activityDao.getActivity(id);
    }

    @Override
    @Transactional
    public List<Activity> getAllActivities() {
        return activityDao.getAllActivities();
    }

    @Override
    @Transactional
    public List<Activity> getTimesForActivity(String className) {
        return activityDao.getTimesForActivity(className);
    }

}
