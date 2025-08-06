package com.thelodge.repository;

import com.thelodge.entity.TravelMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelModeRepository extends JpaRepository<TravelMode, Integer> {
    boolean existsByNameIgnoreCase(String name);
}

