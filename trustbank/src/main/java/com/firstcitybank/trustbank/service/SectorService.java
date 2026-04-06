package com.firstcitybank.trustbank.service;

import com.firstcitybank.trustbank.database.dao.SectorDao;
import com.firstcitybank.trustbank.exception.NotFoundException;
import com.firstcitybank.trustbank.model.sector.Sector;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectorService {

    private final SectorDao sectorDao;

    public SectorService(SectorDao sectorDao) {
        this.sectorDao = sectorDao;
    }

    public List<Sector> getSectors() {
        return sectorDao.selectSectors();
    }

    public void addNewSector(Sector sector) {
        // 1. Validate input
        if (sector == null) {
            throw new IllegalArgumentException("Sector data cannot be null");
        }

        if (sector.sectorCode() == null || sector.sectorCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Sector code is required");
        }

        if (sector.sectorName() == null || sector.sectorName().trim().isEmpty()) {
            throw new IllegalArgumentException("Sector name is required");
        }

        // 2. Check if sector exists
        boolean rankExists = sectorDao.existsByName(sector.sectorName());
        if (rankExists) {
            throw new IllegalStateException("Sector with name '" + sector.sectorName() + "' already exists");
        }

        // 3. Insert new sector
        Integer rowsAffected = sectorDao.insertSector(sector);

        // 4. Check if insertion was successful
        if (rowsAffected == null || rowsAffected <= 0) {
            throw new IllegalStateException("Failed to insert Sector");
        }
    }

    public void deleteSector(String sectorCode) {
        if (sectorCode == null || sectorCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Sector code cannot be null or empty");
        }

        String normalizedCode = sectorCode.trim().toUpperCase();

        Optional<Sector> sectors = sectorDao.selectSectorByCode(normalizedCode);
        sectors.ifPresentOrElse(sector -> {
            int result = sectorDao.deleteSector(normalizedCode);
            if (result != 1) {
                throw new IllegalStateException("Oops cannot delete Sector");
            }
        }, () -> {
            throw new NotFoundException(String.format("Sector with code %s not found", normalizedCode));
        });
    }
}
