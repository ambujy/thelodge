package com.thelodge.repository;

import com.thelodge.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    // Additional query methods can be defined here if needed
}
