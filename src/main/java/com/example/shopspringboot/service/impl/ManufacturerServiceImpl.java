package com.example.shopspringboot.service.impl;

import com.example.shopspringboot.domain.Manufacturer;
import com.example.shopspringboot.repository.ManufacturerRepository;
import com.example.shopspringboot.service.ManufacturerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(
            ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public List<Manufacturer> getManufacturers() {
        return manufacturerRepository.findAll();
    }
}