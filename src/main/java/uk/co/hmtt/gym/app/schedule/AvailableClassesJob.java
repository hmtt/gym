package uk.co.hmtt.gym.app.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.hmtt.gym.provider.thepeak.AvailableClasses;

@Component
public class AvailableClassesJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(AvailableClassesJob.class);

    @Autowired
    private AvailableClasses availableClasses;

    public void execute() {
        try {
            availableClasses.find();
        } catch (Exception e) {
            LOGGER.error("Catch error, but don't let it interrupt schedule", e);
        }
    }

}
