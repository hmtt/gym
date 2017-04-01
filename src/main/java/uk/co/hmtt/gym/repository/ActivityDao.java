package uk.co.hmtt.gym.repository;

import uk.co.hmtt.gym.app.model.Activity;

import java.util.List;

public interface ActivityDao {

    int addActivity(String className, String classDate);

    Activity getActivity(String className, String classDate);

    Activity getActivity(int id);

    List<Activity> getAllActivities();

    List<Activity> getTimesForActivity(String className);

}
