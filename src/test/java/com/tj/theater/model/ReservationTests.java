package com.tj.theater.model;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.tj.theater.util.AppConstants.SPIDERMAN;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationTests {
    private final Movie movie = new Movie(SPIDERMAN, Duration.ofMinutes(90), 12.5, 1);

    private final LocalDateTime now = LocalDateTime.now();

    @Test
    void shouldCalculateTotalFee() {
        var customer = new Customer("John Doe");
        var showing = new Showing(movie, 1, now);
        var reservation = new Reservation(customer, showing, 3);
        assertEquals(28.5, reservation.totalFee());
        assertEquals(customer, reservation.getCustomer());
        assertEquals(3, reservation.getAudienceCount());
    }

    @Test
    void shouldReturnZeroWhenZeroAudience() {
        var customer = new Customer("John Doe");
        var showing = new Showing(movie, 1, now);
        var reservation = new Reservation(customer, showing, 0);
        assertEquals(0d, reservation.totalFee());
    }

}
