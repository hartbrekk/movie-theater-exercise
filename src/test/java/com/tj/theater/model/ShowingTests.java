package com.tj.theater.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.tj.theater.util.AppConstants.BATMAN;
import static com.tj.theater.util.AppConstants.SPIDERMAN;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShowingTests {

    private final Movie specialMovie = new Movie(SPIDERMAN, Duration.ofMinutes(90), 12.5, 1);

    private final Movie movie = new Movie(BATMAN, Duration.ofMinutes(90), 10.99, 0);

    private final LocalDate day7 = LocalDate.of(2023, 1, 7);

    @Test
    @DisplayName("When Movie is regular and no discounts eligible then should discount to 0")
    void shouldZeroWhenRegularMovieWithNoEligibleDiscounts() {
        Showing showing = new Showing(movie, 5, LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 0)));
        assertEquals(10.99, showing.getMovieFee());
    }

    @Test
    @DisplayName("When Movie is special then should discount to 20%")
    void shouldDiscountTo20PercentWhenSpecialMovie() {
        Showing showing = new Showing(specialMovie, 5, LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 0)));
        assertEquals(10, showing.getMovieFee());
    }

    @Test
    @DisplayName("When Movie is special and show on 7th then should discount to 20%")
    void shouldDiscountTo20PercentWhenSpecialMovieOn7th() {
        Showing showing = new Showing(specialMovie, 5, LocalDateTime.of(day7, LocalTime.of(18, 0)));
        assertEquals(10, showing.getMovieFee());
    }

    @Test
    @DisplayName("When Movie is special and in the morning between 11 AM to 4PM then should discount to 25%")
    void shouldDiscountTo25PercentWhenSpecialMovieInTheMorning() {
        Showing showing = new Showing(specialMovie, 5, LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)));
        assertEquals(9.375, showing.getMovieFee());
    }

    @Test
    @DisplayName("When Movie is special and in the morning between 11 AM to 4PM and on 7th then should discount to 25%")
    void shouldDiscountTo25PercentWhenSpecialMovieInTheMorningOn7th() {
        Showing showing = new Showing(specialMovie, 5, LocalDateTime.of(day7, LocalTime.of(12, 0)));
        assertEquals(9.375, showing.getMovieFee());
    }

    @Test
    @DisplayName("Given lower ticketprice when Movie is special and first show then max discount should be $3")
    void shouldDiscountTo$3WhenSpecialMovieAndFirstShow() {
        Showing showing = new Showing(specialMovie, 1, LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 0)));
        assertEquals(9.5, showing.getMovieFee());
    }


    @Test
    @DisplayName("Given lower ticketprice when Movie is special and in the morning between 11 AM to 4PM then max discount should be 25%")
    void shouldDiscountTo25PercentWhenSpecialMovieAndFirstShowInTheMorning() {
        Showing showing = new Showing(specialMovie, 1, LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)));
        assertEquals(9.375, showing.getMovieFee());
    }

    @Test
    @DisplayName("Given higher ticketprice when Movie is special and first show then max discount should be 20%")
    void shouldDiscountTo20PercentWhenSpecialMovieAndFirstShowGivenHigherTicketPrice() {
        var anotherSpecialMovie = new Movie(SPIDERMAN, Duration.ofMinutes(90), 22.5, 1);
        Showing showing = new Showing(anotherSpecialMovie, 1, LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 0)));
        assertEquals(18, showing.getMovieFee());
    }

    @Test
    @DisplayName("Given higher ticketprice when Movie is special and first show then max discount should be 25%")
    void shouldDiscountTo25PercentWhenSpecialMovieAndFirstShowInTheMorningGivenHigherTicketPrice() {
        var anotherSpecialMovie = new Movie(SPIDERMAN, Duration.ofMinutes(90), 22.5, 1);
        Showing showing = new Showing(anotherSpecialMovie, 1, LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 0)));
        assertEquals(16.875, showing.getMovieFee());
    }

    @Test
    @DisplayName("When Movie is regular and first show then should discount to $3")
    void shouldDiscountTo$3WhenFirstShow() {
        Showing showing = new Showing(movie, 1, LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 0)));
        assertEquals(7.99, showing.getMovieFee());
    }

    @Test
    @DisplayName("When Movie is regular and first show on 7th then should discount to $3")
    void shouldDiscountTo$3WhenFirstShowOn7th() {
        Showing showing = new Showing(movie, 1, LocalDateTime.of(day7, LocalTime.of(17, 0)));
        assertEquals(7.99, showing.getMovieFee());
    }

    @Test
    @DisplayName("When Movie is regular and second show then should discount to $2")
    void shouldDiscountTo$2WhenSecondShow() {
        Showing showing = new Showing(movie, 2, LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 0)));
        assertEquals(8.99, showing.getMovieFee());
    }

    @Test
    @DisplayName("When Movie is regular and second show on 7th then should discount to $2")
    void shouldDiscountTo$2WhenSecondShowOn7th() {
        Showing showing = new Showing(movie, 2, LocalDateTime.of(day7, LocalTime.of(17, 0)));
        assertEquals(8.99, showing.getMovieFee());
    }

    @Test
    @DisplayName("When Movie is regular and show on 7th then should discount to $1")
    void shouldDiscountTo$1WhenShowOn7th() {
        Showing showing = new Showing(movie, 3, LocalDateTime.of(day7, LocalTime.of(17, 0)));
        assertEquals(9.99, showing.getMovieFee());
    }

    @Test
    @DisplayName("When show in the morning between 11 AM to 4PM then should discount to 25%")
    void shouldDiscountTo25PercentWhenShowInTheMorning() {
        Showing showing = new Showing(movie, 5, LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)));
        assertEquals(8.2425, showing.getMovieFee());
    }

    //Edge cases outside 11 AM-4PM window
    @Test
    @DisplayName("When show in the morning before 11 AM then should discount to 0")
    void shouldZeroDiscounttWhenShowInTheMorning() {
        Showing showing = new Showing(movie, 3, LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 59)));
        assertEquals(10.99, showing.getMovieFee());
    }

    @Test
    @DisplayName("When show in the evening right after 4 PM then should discount to 0")
    void shouldZeroDiscounttWhenShowInTheEveningPast4PM() {
        var showing = new Showing(movie, 3, LocalDateTime.of(LocalDate.now(), LocalTime.of(16, 1)));
        assertEquals(10.99, showing.getMovieFee());
    }

}
