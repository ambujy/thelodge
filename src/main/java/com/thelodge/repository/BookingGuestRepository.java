package com.thelodge.repository;

import com.thelodge.entity.BookingGuest;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingGuestRepository extends JpaRepository<BookingGuest, Integer> {
    List<BookingGuest> findByBookingId(Integer bookingId);  
}
