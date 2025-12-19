package com.firstcitybank.trustbank.service;

import com.firstcitybank.trustbank.database.dao.SubSectorDao;
import com.firstcitybank.trustbank.exception.NotFoundException;
import com.firstcitybank.trustbank.model.SocialRank;
import com.firstcitybank.trustbank.model.SubSector;
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
        // todo: insert Sector
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
