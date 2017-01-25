package com.tooling.schedular.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertTrue;

public class EventTest {

    @Before
    public void setup() {
        // TODO : Remove all duplicate setup code in all unit tests.
    }

    @Test
    public void events_are_overlap_if_both_have_same_start_time() {
        Event event1 = Event.of(LocalTime.of(11, 0), LocalTime.of(13, 0), "001");
        Event event2 = Event.of(LocalTime.of(11, 0), LocalTime.of(12, 0), "001");

        assertTrue(event1.isOverlap(event2));
    }

    @Test
    public void events_are_overlap_if_both_have_same_end_time() {
        Event event1 = Event.of(LocalTime.of(11, 0), LocalTime.of(13, 0), "001");
        Event event2 = Event.of(LocalTime.of(10, 0), LocalTime.of(13, 0), "001");

        assertTrue(event1.isOverlap(event2));
    }

    @Test
    public void events_are_overlap_if_given_event_start_in_range() {
        Event event1 = Event.of(LocalTime.of(11, 0), LocalTime.of(14, 0), "001");
        Event event2 = Event.of(LocalTime.of(12, 0), LocalTime.of(13, 0), "001");

        assertTrue(event1.isOverlap(event2));
    }
}
