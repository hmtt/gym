package uk.co.hmtt.gym.app.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.hmtt.gym.provider.thepeak.Booker;

@Component
public class BookingJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingJob.class);

    @Autowired
    private Booker booker;

    public void execute() {
        try {
            booker.book();
        } catch (Exception e) {
            LOGGER.error("Catch error, but don't let it interrupt schedule", e);
        }
    }

}
