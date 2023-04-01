package com.tj.theater;

import com.tj.theater.model.Theater;
import com.tj.theater.util.LocalDateProvider;

public class TheaterApplication {
    public static void main(String[] args) {
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printSchedule();
        theater.printScheduleJson();
    }
}
