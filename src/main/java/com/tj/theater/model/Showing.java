package com.tj.theater.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.tj.theater.util.AppConstants.MOVIE_CODE_SPECIAL;
import static com.tj.theater.util.DiscountCode.*;

public class Showing {
    private final Movie movie;
    private final int sequenceOfTheDay;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private final LocalDateTime showStartTime;

    public Showing(Movie movie, int sequenceOfTheDay, LocalDateTime showStartTime) {
        this.movie = movie;
        this.sequenceOfTheDay = sequenceOfTheDay;
        this.showStartTime = showStartTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDateTime getShowStartTime() {
        return showStartTime;
    }

    public double getMovieFee() {
        return movie.getTicketPrice() - calculateDiscount();
    }

    public int getSequenceOfTheDay() {
        return sequenceOfTheDay;
    }

    private double calculateDiscount() {
        double percentDiscount = 0;
        if (showStartTime.getHour() >= 11 && showStartTime.isBefore(toLocalDateTimeAt4Pm())) {
            percentDiscount = movie.getTicketPrice() * MORNING_SHOW.value;  // 25% discount for shows starting between 11 AM - 4 PM
        } else if (MOVIE_CODE_SPECIAL == movie.getSpecialCode()) {
            percentDiscount = movie.getTicketPrice() * SPECIAL_SHOW.value;  // 20% discount for special movie
        }

        //Return if percent discount is greater than max cash discount possible
        if (percentDiscount > FIRST_SHOW.value) {
            return percentDiscount;
        }
        double cashDiscount = 0;
        if (sequenceOfTheDay == 1) {
            cashDiscount = FIRST_SHOW.value; // $3 discount for 1st show
        } else if (sequenceOfTheDay == 2) {
            cashDiscount = SECOND_SHOW.value; // $2 discount for 2nd show
        } else if (showStartTime.getDayOfMonth() == 7) {
            cashDiscount = DAY7_SHOW.value; // $1 discount for 7th day of the month
        }

        // biggest discount wins
        return Math.max(percentDiscount, cashDiscount);
    }

    private LocalDateTime toLocalDateTimeAt4Pm() {
        return showStartTime.toLocalDate().atTime(16, 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Showing showing = (Showing) o;
        return sequenceOfTheDay == showing.sequenceOfTheDay
                && Objects.equals(movie, showing.movie)
                && Objects.equals(showStartTime, showing.showStartTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, sequenceOfTheDay, showStartTime);
    }
}
