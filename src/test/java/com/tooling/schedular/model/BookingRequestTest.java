package com.tooling.schedular.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BookingRequestTest {

    @Before
    public void setup() {
        // TODO : Remove all duplicate setup code in all unit tests.
    }

    @Test
    public void given_text_input_should_create_bookingrequest_object() {
        BookingRequest bookingRequest = BookingRequest.of("2015-08-17 10:17:06 EMP001", "2015-08-21 09:00 2");

        assertEquals(bookingRequest.getDuration(), 2);
        assertEquals(bookingRequest.getEmployeeId(), "EMP001");
    }


    @Test
    public void office_starts_and_ends_12am_to_12am_should_behave_as_expected() {
        LocalDateTime requestedSubmitted = LocalDateTime.of(2016, 12, 28, 9, 0);
        LocalDateTime eventRequest = LocalDateTime.of(2016, 12, 29, 20, 0);
        BookingRequest bookingRequest = new BookingRequest(requestedSubmitted, "Emp001", eventRequest, 2);

        boolean requestFallWithInOfficeHours = bookingRequest.isRequestFallWithInOfficeHours(LocalTime.of(0, 0), LocalTime.of(23, 0));
        assertTrue(requestFallWithInOfficeHours);
    }

    @Test
    public void return_true_if_meeting_request_within_office_hours() {
        LocalDateTime requestedSubmitted = LocalDateTime.of(2016, 12, 28, 9, 0);
        LocalDateTime eventRequest = LocalDateTime.of(2016, 12, 29, 13, 0);
        BookingRequest bookingRequest = new BookingRequest(requestedSubmitted, "Emp001", eventRequest, 2);

        boolean requestFallWithInOfficeHours = bookingRequest.isRequestFallWithInOfficeHours(LocalTime.of(8, 0), LocalTime.of(17, 0));
        assertTrue(requestFallWithInOfficeHours);
    }

    @Test
    public void return_true_even_if_meeting_request_ends_exactly_at_office_end_time() {
        LocalDateTime requestedSubmitted = LocalDateTime.of(2016, 12, 28, 9, 0);
        LocalDateTime eventRequest = LocalDateTime.of(2016, 12, 29, 15, 0);
        BookingRequest bookingRequest = new BookingRequest(requestedSubmitted, "Emp001", eventRequest, 2);

        boolean requestFallWithInOfficeHours = bookingRequest.isRequestFallWithInOfficeHours(LocalTime.of(8, 0), LocalTime.of(17, 0));
        assertTrue(requestFallWithInOfficeHours);
    }

    @Test
    public void return_false_if_meeting_request_is_after_office_hours() {
        LocalDateTime requestedSubmitted = LocalDateTime.of(2016, 12, 28, 9, 0);
        LocalDateTime eventRequest = LocalDateTime.of(2016, 12, 29, 16, 0);
        BookingRequest bookingRequest = new BookingRequest(requestedSubmitted, "Emp001", eventRequest, 2);

        boolean requestFallWithInOfficeHours = bookingRequest.isRequestFallWithInOfficeHours(LocalTime.of(8, 0), LocalTime.of(17, 0));
        assertFalse(requestFallWithInOfficeHours);
    }
}
