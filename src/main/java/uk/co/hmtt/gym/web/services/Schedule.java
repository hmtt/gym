package uk.co.hmtt.gym.web.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.co.hmtt.gym.provider.thepeak.AvailableClasses;
import uk.co.hmtt.gym.provider.thepeak.Booker;

@Controller
@RequestMapping(value = {"/service"})
public class Schedule {

    private static final Logger LOGGER = LoggerFactory.getLogger(Schedule.class);

    @Autowired
    private AvailableClasses availableClasses;

    @Autowired
    private Booker booker;

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = {"/execute"})
    public void execute(@RequestParam(value = "type", required = true) String type) {

        LOGGER.error("executing {}", type);

        if ("available".equals(type)) {
            availableClasses.find();
        } else if ("booker".equals(type)) {
            booker.book();
        }
    }

}
