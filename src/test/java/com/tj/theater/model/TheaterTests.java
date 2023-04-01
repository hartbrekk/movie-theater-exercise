package com.tj.theater.model;

import com.tj.theater.util.LocalDateProvider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TheaterTests {
    private final Theater theater = new Theater(LocalDateProvider.singleton());

    @Test()
    void shouldThrowExceptionMatchTotalFeeShowingAndCustomer() {
        var ex = assertThrows(IllegalStateException.class,
                () -> theater.reserve(new Customer("John Doe"), 0, 4));
        assertEquals("Invalid sequence requested from the system:0 for Customer:name: John Doe", ex.getMessage());
    }

    @Test
    void shouldMatchTotalFeeShowingAndCustomer() {
        Customer john = new Customer("John Doe");
        Reservation reservation = theater.reserve(john, 2, 4);
        assertEquals(37.5, reservation.totalFee());
        assertEquals(john, reservation.getCustomer());
        assertEquals(theater.getSchedule().size(), 9);
        assertEquals(theater.getSchedule().get(1), reservation.getShowing());
    }

    @Test
    void shouldMatchScheduleJson() {
        assertEquals(testJson, theater.getScheduleAsJson());
    }

    private static final String testJson = "[{\"movie\":{\"title\":\"Turning Red\",\"runningTime\":\"PT1H25M\"," +
            "\"ticketPrice\":11.0,\"specialCode\":0},\"sequenceOfTheDay\":1,\"showStartTime\":\"2023-04-01T09:00\"," +
            "\"movieFee\":8.0},{\"movie\":{\"title\":\"Spider-Man: No Way Home\",\"runningTime\":\"PT1H30M\"," +
            "\"ticketPrice\":12.5,\"specialCode\":1},\"sequenceOfTheDay\":2,\"showStartTime\":\"2023-04-01T11:00\"," +
            "\"movieFee\":9.375},{\"movie\":{\"title\":\"The Batman\",\"runningTime\":\"PT1H35M\",\"ticketPrice\":9.0," +
            "\"specialCode\":0},\"sequenceOfTheDay\":3,\"showStartTime\":\"2023-04-01T12:50\",\"movieFee\":6.75}," +
            "{\"movie\":{\"title\":\"Turning Red\",\"runningTime\":\"PT1H25M\",\"ticketPrice\":11.0,\"specialCode\":0}," +
            "\"sequenceOfTheDay\":4,\"showStartTime\":\"2023-04-01T14:30\",\"movieFee\":8.25},{\"movie\":{\"title\":" +
            "\"Spider-Man: No Way Home\",\"runningTime\":\"PT1H30M\",\"ticketPrice\":12.5,\"specialCode\":1}," +
            "\"sequenceOfTheDay\":5,\"showStartTime\":\"2023-04-01T16:10\",\"movieFee\":10.0},{\"movie\":{\"title\":" +
            "\"The Batman\",\"runningTime\":\"PT1H35M\",\"ticketPrice\":9.0,\"specialCode\":0},\"sequenceOfTheDay\":6," +
            "\"showStartTime\":\"2023-04-01T17:50\",\"movieFee\":9.0},{\"movie\":{\"title\":\"Turning Red\"," +
            "\"runningTime\":\"PT1H25M\",\"ticketPrice\":11.0,\"specialCode\":0},\"sequenceOfTheDay\":7," +
            "\"showStartTime\":\"2023-04-01T19:30\",\"movieFee\":11.0},{\"movie\":{\"title\":\"Spider-Man: No Way Home\"" +
            ",\"runningTime\":\"PT1H30M\",\"ticketPrice\":12.5,\"specialCode\":1},\"sequenceOfTheDay\":8,\"showStartTime\"" +
            ":\"2023-04-01T21:10\",\"movieFee\":10.0},{\"movie\":{\"title\":\"The Batman\",\"runningTime\":\"PT1H35M\"," +
            "\"ticketPrice\":9.0,\"specialCode\":0},\"sequenceOfTheDay\":9,\"showStartTime\":\"2023-04-01T23:00\"," +
            "\"movieFee\":9.0}]";
}
