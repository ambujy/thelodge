package com.thelodge.enums;

public enum PaymentStatusType {
    SUCCESS,    // Payment was completed successfully.
    PENDING,    // Payment is pending and not yet completed.
    FAILED,     // Payment attempt failed.
    CANCELLED,  // Payment was cancelled by the user or system.
    REFUNDED    // Payment was refunded to the user.
}
