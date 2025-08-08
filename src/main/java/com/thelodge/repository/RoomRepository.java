package com.thelodge.repository;

import com.thelodge.entity.Guest;
import com.thelodge.entity.Room;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    Optional<Guest> findByRoomNumber(Integer roomNumber);
    // Additional query methods can be defined here if needed
}
