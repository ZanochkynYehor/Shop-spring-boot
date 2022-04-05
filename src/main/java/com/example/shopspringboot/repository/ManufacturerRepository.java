package com.example.shopspringboot.repository;

import com.example.shopspringboot.domain.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {
    
}