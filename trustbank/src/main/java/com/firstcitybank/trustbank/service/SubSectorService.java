package com.firstcitybank.trustbank.service;

import com.firstcitybank.trustbank.database.dao.SubSectorDao;
import com.firstcitybank.trustbank.model.SubSector;
import org.springframework.stereotype.Service;

import java.util.List;

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
        // todo: insert Sector
    }

    public void deleteSubSector(String subSectorCode) {
        // todo: delete Sector
    }
}
