package com.tooling.schedular;

import com.tooling.schedular.model.BookingRequest;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CalendarTest {

    @Test
    public void booking_should_be_ignored_if_meeting_duration_not_fall_with_in_office_hours() {
        LocalDateTime requestedSubmitted = LocalDateTime.of(2016, 12, 28, 9, 0);
        LocalDateTime eventRequest = LocalDateTime.of(2016, 12, 29, 16, 0);
        BookingRequest bookingRequest = new BookingRequest(requestedSubmitted, "Emp001", eventRequest, 2);

        Calendar calendar = Calendar.with(LocalTime.of(8, 0), LocalTime.of(17, 0));

        assertFalse(calendar.book(bookingRequest));
    }

    @Test
    public void booking_should_be_successfull_on_valid_event_request() {
        LocalDateTime requestedSubmitted = LocalDateTime.of(2016, 12, 28, 9, 0);
        LocalDateTime eventRequest = LocalDateTime.of(2016, 12, 29, 14, 0);
        BookingRequest bookingRequest = new BookingRequest(requestedSubmitted, "Emp001", eventRequest, 2);

        Calendar calendar = Calendar.with(LocalTime.of(8, 0), LocalTime.of(17, 0));

        assertTrue(calendar.book(bookingRequest));
    }

    @Test
    public void booking_should_be_ignored_if_event_slot_is_already_reserved() {
        LocalDateTime requestedSubmitted = LocalDateTime.of(2016, 12, 28, 9, 0);
        LocalDateTime eventRequest = LocalDateTime.of(2016, 12, 29, 14, 0);
        BookingRequest bookingRequest = new BookingRequest(requestedSubmitted, "Emp001", eventRequest, 2);

        Calendar calendar = Calendar.with(LocalTime.of(8, 0), LocalTime.of(17, 0));

        assertTrue(calendar.book(bookingRequest));
        assertFalse(calendar.book(bookingRequest));
    }

    @Test
    public void display_calendar_events_format_should_be_as_expected() {
        LocalDateTime requestedSubmitted = LocalDateTime.of(2016, 12, 28, 9, 0);
        LocalDateTime eventRequest = LocalDateTime.of(2016, 12, 29, 14, 0);

        Calendar calendar = Calendar.with(LocalTime.of(8, 0), LocalTime.of(17, 0));

        calendar.book(new BookingRequest(requestedSubmitted, "Emp001", eventRequest, 2));
        calendar.book(new BookingRequest(requestedSubmitted.minusDays(1), "Emp001", eventRequest.minusHours(2), 2));

        calendar.display();
    }

    @Test
    public void display_calendar_events_should_show_in_chronologically_by_day() {

    }

    @Test
    public void display_day_events_should_show_in_chronologically() {

    }

}
