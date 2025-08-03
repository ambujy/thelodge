package com.thelodge.repository;

import com.thelodge.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    // You can add custom queries here if needed
}
