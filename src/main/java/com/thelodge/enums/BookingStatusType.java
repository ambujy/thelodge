package com.thelodge.enums;

public enum BookingStatusType {
    PENDING,      // Booking is pending and awaiting confirmation.
    BOOKED,       // Booking has been made and is confirmed.
    CHECKED_IN,   // Guest has checked in.
    CHECKED_OUT,  // Guest has checked out.
    CANCELLED,    // Booking has been cancelled by the user or the system.
    NO_SHOW,      // The guest didn’t show up and didn’t cancel in time.
    COMPLETED,    // The full lifecycle of the booking (check-in to check-out) is completed.
    FAILED        // Payment failed or booking couldn’t be processed.
}
