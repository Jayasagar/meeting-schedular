package com.tooling.schedular.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Setter @Getter
@EqualsAndHashCode(of = {"startTime", "endTime"})
public class Event {
    private LocalTime startTime;
    private LocalTime endTime;
    private String organizer;

    public boolean isOverlap(Event event) {
        if (Objects.isNull(event)) {
            return false;
        }

        LocalTime givenStartTime = event.getStartTime();
        LocalTime givenEndTime = event.getEndTime();

        // Two cases
        // Case 1: it would be overlap if start or end time of one event equal to other event
        // Case 2: it would be overlap if given event start time is in b/w this event start and end time
        return (this.startTime.equals(givenStartTime) || this.endTime.equals(givenEndTime)) ||
                (givenStartTime.isAfter(this.startTime) && givenStartTime.isBefore(this.endTime));
    }

    public static Event of(BookingRequest bookingRequest) {
        LocalDateTime eventStartDateTime = bookingRequest.getStartTime();
        LocalTime startTime = LocalTime.of(eventStartDateTime.getHour(), eventStartDateTime.getMinute());
        LocalTime endTime = startTime.plusHours(bookingRequest.getDuration());

        return Event.of(startTime, endTime, bookingRequest.getEmployeeId());
    }

    public static Event of(LocalTime startTime, LocalTime endTime, String organizer) {
        Objects.requireNonNull(startTime);
        Objects.requireNonNull(endTime);
        Objects.requireNonNull(organizer);

        Event event = new Event();
        event.startTime = startTime;
        event.endTime = endTime;
        event.organizer = organizer;

        return event;
    }
}
