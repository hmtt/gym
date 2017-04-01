package uk.co.hmtt.gym.app.service;

import uk.co.hmtt.gym.app.model.Activity;

import java.util.List;

public interface ActivityService {

    void addActivity(String className, String classDate);

    Activity getActivity(int id);

    List<Activity> getAllActivities();

    List<Activity> getTimesForActivity(String className);

}
