package com.tj.theater.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LocalDateProviderTests {
    @Test
    void shouldReturnValidCurrentDate() {
        assertNotNull(LocalDateProvider.singleton().currentDate());
    }

    @Test
    void shouldReturnValidCurrentDateTime() {
        assertNotNull(LocalDateProvider.singleton().currentDateTimeAt(0, 0));
    }

    @Test
    void shouldMatchHoursMinsWhenCurrentDateTime() {
        var currentDateTime = LocalDateProvider.singleton().currentDateTimeAt(16, 59);
        assertEquals(16, currentDateTime.getHour());
        assertEquals(59, currentDateTime.getMinute());
    }
}
