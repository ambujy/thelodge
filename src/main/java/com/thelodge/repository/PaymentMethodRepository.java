package com.thelodge.repository;

import com.thelodge.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {
    boolean existsByNameIgnoreCase(String name);
}
