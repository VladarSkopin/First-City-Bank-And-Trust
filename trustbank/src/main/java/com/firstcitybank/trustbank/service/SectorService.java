package com.firstcitybank.trustbank.service;

import com.firstcitybank.trustbank.database.dao.SectorDao;
import com.firstcitybank.trustbank.model.Sector;
import org.springframework.stereotype.Service;

import java.util.List;

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
        // todo: delete Sector
    }
}
