package com.firstcitybank.trustbank.service;

import com.firstcitybank.trustbank.database.dao.DistrictDao;
import com.firstcitybank.trustbank.exception.NotFoundException;
import com.firstcitybank.trustbank.model.District;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DistrictService {

    private final DistrictDao districtDao;

    public DistrictService(DistrictDao districtDao) {
        this.districtDao = districtDao;
    }

    public List<District> getDistricts() {
        return districtDao.selectDistricts();
    }

    public void addNewDistrict(District district) {
        // 1. Validate input
        if (district == null) {
            throw new IllegalArgumentException("District data cannot be null");
        }

        if (district.districtCode() == null || district.districtCode().trim().isEmpty()) {
            throw new IllegalArgumentException("District code is required");
        }

        if (district.districtName() == null || district.districtName().trim().isEmpty()) {
            throw new IllegalArgumentException("District name is required");
        }

        // 2. Check if district exists
        boolean districtExists = districtDao.existsByName(district.districtName());
        if (districtExists) {
            throw new IllegalStateException("District with name '" + district.districtName() + "' already exists");
        }

        // 3. Insert new district
        Integer rowsAffected = districtDao.insertDistrict(district);

        // 4. Check if insertion was successful
        if (rowsAffected == null || rowsAffected <= 0) {
            throw new IllegalStateException("Failed to insert District");
        }
    }

    public void deleteDistrict(String districtCode) {
        if (districtCode == null || districtCode.trim().isEmpty()) {
            throw new IllegalArgumentException("District code cannot be null or empty");
        }

        String normalizedCode = districtCode.trim().toUpperCase();

        Optional<District> districts = districtDao.selectDistrictByCode(normalizedCode);
        districts.ifPresentOrElse(district -> {
            int result = districtDao.deleteDistrict(normalizedCode);
            if (result != 1) {
                throw new IllegalStateException("Oops cannot delete District");
            }
        }, () -> {
            throw new NotFoundException(String.format("District with code %s not found", normalizedCode));
        });
    }
}
