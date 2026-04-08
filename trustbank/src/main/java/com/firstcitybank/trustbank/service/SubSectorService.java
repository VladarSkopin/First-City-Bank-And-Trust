package com.firstcitybank.trustbank.service;

import com.firstcitybank.trustbank.database.dao.SubSectorDao;
import com.firstcitybank.trustbank.exception.NotFoundException;
import com.firstcitybank.trustbank.model.sector.SubSector;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubSectorService {

    private final SubSectorDao subSectorDao;

    public SubSectorService(SubSectorDao subSectorDao) {
        this.subSectorDao = subSectorDao;
    }

    public List<SubSector> getSubSectors() {
        return subSectorDao.selectSubSectors();
    }

    public void addNewSubSector(SubSector subSector) {
        // 1. Validate input
        if (subSector == null) {
            throw new IllegalArgumentException("Sub-Sector data cannot be null");
        }

        if (subSector.subSectorCode() == null || subSector.subSectorCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Sub-Sector code is required");
        }

        if (subSector.subSectorName() == null || subSector.subSectorName().trim().isEmpty()) {
            throw new IllegalArgumentException("Sub-Sector name is required");
        }

        if (subSector.sectorCode() == null || subSector.sectorCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Sector code is required");
        }

        // 2. Check if sub-sector exists
        boolean rankExists = subSectorDao.existsByName(subSector.subSectorName());
        if (rankExists) {
            throw new IllegalStateException("Sub-Sector with name '" + subSector.subSectorName() + "' already exists");
        }

        // 3. Insert new sub-sector
        Integer rowsAffected = subSectorDao.insertSubSector(subSector);

        // 4. Check if insertion was successful
        if (rowsAffected == null || rowsAffected <= 0) {
            throw new IllegalStateException("Failed to insert Sub-Sector");
        }
    }

    public void deleteSubSector(String subSectorCode) {
        if (subSectorCode == null || subSectorCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Sub-Sector code cannot be null or empty");
        }

        String normalizedCode = subSectorCode.trim().toUpperCase();

        Optional<SubSector> subSectors = subSectorDao.selectSubSectorByCode(normalizedCode);
        subSectors.ifPresentOrElse(subSector -> {
            int result = subSectorDao.deleteSubSector(normalizedCode);
            if (result != 1) {
                throw new IllegalStateException("Oops cannot delete Sub-Sector");
            }
        }, () -> {
            throw new NotFoundException(String.format("Sub-Sector with code %s not found", normalizedCode));
        });
    }
}
