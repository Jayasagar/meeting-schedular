package com.tooling.schedular.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter @EqualsAndHashCode(of = {"submissionTime"})
public class BookingRequest {
    private LocalDateTime submissionTime;
    private String employeeId;
    private LocalDateTime startTime;
    private int duration;

    /**
     * Construct {@link BookingRequest} from the raw input.
     * For example: tuple 1: 2015-08-17 10:17:06 EMP001
     * tuple 2: 2015-08-21 09:00 2
     * @param tuple1 should be of format [request submission time, in the format YYYY-MM-DD HH:MM:SS] [ARCH:employee id]
     * @param tuple2 should be of format [meeting start time, in the format YYYY-MM-DD HH:MM] [ARCH:meeting duration in hours]
     * @return {@link BookingRequest}
     */
    public static BookingRequest of(String tuple1, String tuple2) {
        Objects.requireNonNull(tuple1);
        Objects.requireNonNull(tuple2);

        // TODO: validate input and handle exception
        int index = tuple1.lastIndexOf(' ');
        String submissionTime = tuple1.substring(0, index);
        String employeeId = tuple1.substring(index).trim();

        index = tuple2.lastIndexOf(' ');
        String startTime = tuple2.substring(0, index);
        String duration = tuple2.substring(index).trim();

        BookingRequest bookingRequest =
                new BookingRequest(
                    parseDateToSeconds(submissionTime),
                    employeeId,
                    parseDateToMinutes(startTime),
                    Integer.parseInt(duration));

        return bookingRequest;
    }

    public BookingRequest(LocalDateTime submissionTime, String employeeId, LocalDateTime startTime, int duration) {
        this.submissionTime = submissionTime;
        this.employeeId = employeeId;
        this.startTime = startTime;
        this.duration = duration;
    }

    public boolean isRequestFallWithInOfficeHours(LocalTime officeStartTime, LocalTime officeEndTime) {
        // is event started after OfficeStartTime && is event completes before OfficeEndTime;
        LocalTime eventStartTime = LocalTime.of(startTime.getHour(), startTime.getMinute());
        return  (eventStartTime.equals(officeStartTime) || eventStartTime.isAfter(officeStartTime)) &&
                (eventStartTime.plusHours(duration).equals(officeEndTime) || eventStartTime.plusHours(duration).isBefore(officeEndTime));
    }

    public LocalDate startTimeToLocalDate() {
        return startTime.toLocalDate();
    }
    private static LocalDateTime parseDateToSeconds(String date) {
        // TODO: extract constant to common place and reuse the base format
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private static LocalDateTime parseDateToMinutes(String date) {
        // TODO: extract constant to common place
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
