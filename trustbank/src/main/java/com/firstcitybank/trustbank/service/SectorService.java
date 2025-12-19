package com.firstcitybank.trustbank.service;

import com.firstcitybank.trustbank.database.dao.SectorDao;
import com.firstcitybank.trustbank.exception.NotFoundException;
import com.firstcitybank.trustbank.model.Sector;
import com.firstcitybank.trustbank.model.SocialRank;
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
        // todo: insert Sector
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
