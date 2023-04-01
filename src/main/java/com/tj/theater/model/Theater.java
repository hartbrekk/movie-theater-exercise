package com.tj.theater.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tj.theater.util.LocalDateProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.tj.theater.util.AppConstants.*;
import static com.tj.theater.util.NumberUtil.roundToTwoDecimals;

public class Theater {

    private final LocalDateProvider provider;
    private final List<Showing> schedule;

    private static final Logger logger = LoggerFactory.getLogger(Theater.class);

    public Theater(LocalDateProvider provider) {
        this.provider = provider;
        schedule = loadSchedule();
        if (schedule.isEmpty()) {
            throw new IllegalStateException("No showings found in the system");
        }
    }

    private List<Showing> loadSchedule() {
        Movie spiderMan = new Movie(SPIDERMAN, Duration.ofMinutes(90), 12.5, 1);
        Movie turningRed = new Movie(TURNING_RED, Duration.ofMinutes(85), 11, 0);
        Movie theBatMan = new Movie(BATMAN, Duration.ofMinutes(95), 9, 0);
        return List.of(
                new Showing(turningRed, 1, provider.currentDateTimeAt(9, 0)),
                new Showing(spiderMan, 2, provider.currentDateTimeAt(11, 0)),
                new Showing(theBatMan, 3, provider.currentDateTimeAt(12, 50)),
                new Showing(turningRed, 4, provider.currentDateTimeAt(14, 30)),
                new Showing(spiderMan, 5, provider.currentDateTimeAt(16, 10)),
                new Showing(theBatMan, 6, provider.currentDateTimeAt(17, 50)),
                new Showing(turningRed, 7, provider.currentDateTimeAt(19, 30)),
                new Showing(spiderMan, 8, provider.currentDateTimeAt(21, 10)),
                new Showing(theBatMan, 9, provider.currentDateTimeAt(23, 0))
        );
    }

    public List<Showing> getSchedule() {
        return schedule;
    }

    public Reservation reserve(Customer customer, int sequence, int howManyTickets) {
        try {
            if (sequence < 1) {
                throw new IllegalStateException("Invalid sequence requested from the system:" + sequence + " for Customer:" + customer);
            }
            Showing showing = schedule.get(sequence - 1);
            return new Reservation(customer, showing, howManyTickets);
        } catch (RuntimeException ex) {
            logger.error("reserve() encountered exception:", ex);
            throw ex;
        }
    }

    public void printSchedule() {
        logger.info("{}", provider.currentDate());
        logger.info("===================================================");
        schedule.forEach(s ->
                logger.info("{}: {} {} {} ${}", s.getSequenceOfTheDay(), s.getShowStartTime(), s.getMovie().getTitle(), humanReadableFormat(s.getMovie().getRunningTime()), roundToTwoDecimals(s.getMovieFee()))
        );
        logger.info("===================================================");
    }

    private String humanReadableFormat(Duration duration) {
        long hour = duration.toHours();
        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

        return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
    }

    // (s) postfix should be added to handle plural correctly
    private String handlePlural(long value) {
        if (value > 1) {
            return "s";
        }
        return "";
    }

    public void printScheduleJson() {
        logger.info(getScheduleAsJson());
    }

    public String getScheduleAsJson() {
        var mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        try {
            return mapper.writeValueAsString(schedule);
        } catch (JsonProcessingException jpEx) {
            logger.error("getScheduleAsJson() encountered exception:", jpEx);
            throw new RuntimeException(jpEx);
        }
    }

}
