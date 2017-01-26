package com.tooling.schedular;

import com.tooling.schedular.model.BookingRequest;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Application {

    private Calendar calendar;

    /**
     * Constructs the booking system with office start and end time.
     */
    public Application(LocalTime officeStartTime, LocalTime officeEndTime) {
        // Create calendar with office/room working hours
        calendar = Calendar.with(officeStartTime, officeEndTime);
    }

    public static void main(String[] args) {
        List<BookingRequest> bookingRequests = Arrays.asList(
                BookingRequest.of("2015-08-17 10:17:06 EMP001", "2015-08-21 09:00 2"),
                BookingRequest.of("2015-08-16 12:34:56 EMP002", "2015-08-21 09:00 2"),
                BookingRequest.of("2015-08-16 09:28:23 EMP003", "2015-08-22 14:00 2"),
                BookingRequest.of("2015-08-17 11:23:45 EMP004", "2015-08-22 16:00 1"),
                BookingRequest.of("2015-08-15 17:29:12 EMP005", "2015-08-21 16:00 3"));

        // Process batch booking
        new Application(LocalTime.of(9, 0), LocalTime.of(17, 30))
                .processBookings(bookingRequests);
    }

    public void processBookings(List<BookingRequest> bookingRequests) {
        Objects.requireNonNull(bookingRequests);

        // Sort and book the requests
        bookingRequests
                .stream()
                .sorted(Comparator.comparing(BookingRequest::getSubmissionTime))
                .forEach(bookingRequest -> calendar.book(bookingRequest));


        // Display as expected output format
        calendar.display();
    }
}