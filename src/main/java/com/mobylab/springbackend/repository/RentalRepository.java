package com.mobylab.springbackend.repository;

import com.mobylab.springbackend.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}
