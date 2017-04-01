package uk.co.hmtt.gym.app.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class SpringSchedular {

    public static final int TEN_MINUTES = 1000 * 60 * 10;
    
    @Autowired
    private BookingJob bookingJob;

    @Autowired
    private AvailableClassesJob availableClassesJob;

    @Scheduled(fixedRate = TEN_MINUTES)
    public void schduleBookingJob() {
        bookingJob.execute();
    }

    @Scheduled(fixedRate = TEN_MINUTES)
    public void scheduleAvailableClassesJob() {
        availableClassesJob.execute();
    }

}
