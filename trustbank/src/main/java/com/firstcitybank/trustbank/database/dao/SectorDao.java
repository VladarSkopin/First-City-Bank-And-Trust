package com.firstcitybank.trustbank.database.dao;

import com.firstcitybank.trustbank.model.sector.Sector;

import java.util.List;
import java.util.Optional;

public interface SectorDao {
    List<Sector> selectSectors();
    int insertSector(Sector sector);
    boolean existsByName(String sectorName);
    boolean existsByCode(String sectorCode);
    int deleteSector(String sectorCode);
    Optional<Sector> selectSectorByCode(String sectorCode);
}
