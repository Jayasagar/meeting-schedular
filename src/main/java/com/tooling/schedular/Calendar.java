package com.tooling.schedular;

import com.tooling.schedular.model.BookingRequest;
import com.tooling.schedular.model.Event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents the calendar with scheduled events. Responsible for processing the booking request.
 * Note: Right now, this class only write the results to the console.
 */
public final class Calendar {
    private LocalTime officeStartTime;
    private LocalTime officeEndTime;
    private Map<LocalDate, List<Event>> eventsByDate;

    public static Calendar with(LocalTime officeStartTime, LocalTime officeEndTime) {
        Objects.requireNonNull(officeStartTime, "Office start time mandatory to instantiate the calendar");
        Objects.requireNonNull(officeEndTime, "Office end time mandatory to instantiate the calendar");

        Calendar calendar = new Calendar();
        calendar.officeStartTime = officeStartTime;
        calendar.officeEndTime = officeEndTime;
        calendar.eventsByDate = new HashMap<>();
        return calendar;
    }

    private Calendar() {}

    /**
     * Returns true if booking is successful.
     *
     * It ignore booking silently and returns false if any of the given system constraints not met!
     * @param bookingRequest
     */
    public boolean book(BookingRequest bookingRequest) {
        Objects.requireNonNull(bookingRequest);

        // Validate is this booking fall outside office hours
        if (!bookingRequest.isRequestFallWithInOfficeHours(officeStartTime, officeEndTime)) {
            return false;
        }

        // Validate is event overlap with existing events on the given day
        Event event = Event.of(bookingRequest);
        if (isEventOverlapping(bookingRequest.startTimeToLocalDate(), event)) {
            return false;
        }

        // Add event to calendar
        LocalDate eventDate = bookingRequest.startTimeToLocalDate();
        addEvent(eventDate, event);

        return true;
    }

    /**
     * TODO: display could move to separate interface and inject into this class so that we can support multiple output writer.
     */
    public void display() {
        eventsByDate
                .entrySet()
                .stream()
                // sort by event date
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(entry -> {
                    // Display event day, example: 2015-08-21
                    System.out.println(entry.getKey());

                    // Display day events
                    // example: 09:00 11:00 EMP002
                    entry.getValue()
                            .stream()
                            // Sort by time
                            .sorted(Comparator.comparing(Event::getStartTime))
                            .map(event -> String.format("%s %s %s", event.getStartTime(), event.getEndTime(), event.getOrganizer()))
                            .forEach(formattedEvent -> {
                                System.out.println(formattedEvent);
                            });

                });
    }

    private boolean isEventOverlapping(LocalDate day, Event event) {
        // Validate only if calendar has events with the given day, else no chance of overlapping.
        if (eventsByDate.containsKey(day)) {
            List<Event> dayEvents = eventsByDate.get(day);

            return dayEvents
                    .stream()
                    // Validate event is overlapping with existing events
                    .anyMatch(existingEvent -> existingEvent.isOverlap(event));
        }

        return false;
    }

    private void addEvent(LocalDate eventDate, Event event) {
        List<Event> dayEvents = eventsByDate.get(eventDate);
        if (dayEvents == null) {
            List<Event> events = new ArrayList<Event>();
            events.add(event);
            eventsByDate.put(eventDate, events);
        } else {
            dayEvents.add(event);
            eventsByDate.put(eventDate, dayEvents);
        }
    }

}
