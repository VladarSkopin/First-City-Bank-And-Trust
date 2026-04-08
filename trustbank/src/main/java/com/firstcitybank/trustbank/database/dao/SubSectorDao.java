package com.firstcitybank.trustbank.database.dao;

import com.firstcitybank.trustbank.model.sector.SubSector;

import java.util.List;
import java.util.Optional;

public interface SubSectorDao {
    List<SubSector> selectSubSectors();
    int insertSubSector(SubSector subSector);
    boolean existsByName(String subSectorName);
    boolean existsByCode(String subSectorCode);
    int deleteSubSector(String subSectorCode);
    Optional<SubSector> selectSubSectorByCode(String subSectorCode);
}
