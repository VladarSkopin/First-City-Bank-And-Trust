package com.firstcitybank.trustbank.service;

import com.firstcitybank.trustbank.database.dao.ClientDao;
import com.firstcitybank.trustbank.model.Client;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchClientsService {

    private final ClientDao clientDao;

    public SearchClientsService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }


    // Single criteria searches

    public List<Client> getClientsBySocialRank(String socialRankCode) {
        validateSocialRankCode(socialRankCode);
        return clientDao.findClientsBySocialRank(socialRankCode);
    }

    public List<Client> getClientsByClientType(String clientTypeCode) {
        validateClientTypeCode(clientTypeCode);
        return clientDao.findClientsByClientType(clientTypeCode);
    }

    public List<Client> getClientsBySubSector(String subSectorCode) {
        validateSubSectorCode(subSectorCode);
        return clientDao.findClientsBySubSector(subSectorCode);
    }

    public List<Client> getClientsBySector(String sectorCode) {
        validateSectorCode(sectorCode);
        return clientDao.findClientsBySector(sectorCode);
    }

    public List<Client> getClientsByDistrict(String districtCode) {
        validateDistrictCode(districtCode);
        return clientDao.findClientsByDistrict(districtCode);
    }


    // Advanced search with multiple criteria

    public List<Client> searchClients(
            String socialRankCode,
            String clientTypeCode,
            String subSectorCode,
            String sectorCode,
            String districtCode,
            Boolean isBlocked) {

        // Validate all provided parameters
        if (socialRankCode != null) validateSocialRankCode(socialRankCode);
        if (clientTypeCode != null) validateClientTypeCode(clientTypeCode);
        if (subSectorCode != null) validateSubSectorCode(subSectorCode);
        if (sectorCode != null) validateSectorCode(sectorCode);
        if (districtCode != null) validateDistrictCode(districtCode);

        return clientDao.searchClients(
                socialRankCode, clientTypeCode, subSectorCode,
                sectorCode, districtCode, isBlocked
        );
    }


    // Statistics and counts

    public long countClientsBySocialRank(String socialRankCode) {
        validateSocialRankCode(socialRankCode);
        return clientDao.countClientsBySocialRank(socialRankCode);
    }

    public long countClientsByClientType(String clientTypeCode) {
        validateClientTypeCode(clientTypeCode);
        return clientDao.countClientsByClientType(clientTypeCode);
    }

    public long countClientsBySector(String sectorCode) {
        validateSectorCode(sectorCode);
        return clientDao.countClientsBySector(sectorCode);
    }


    // Validation methods

    private void validateSocialRankCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Social rank code cannot be null or empty");
        }
    }

    private void validateClientTypeCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Client type code cannot be null or empty");
        }
    }

    private void validateSubSectorCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Sub-sector code cannot be null or empty");
        }
    }

    private void validateSectorCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Sector code cannot be null or empty");
        }
    }

    private void validateDistrictCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("District code cannot be null or empty");
        }
    }

}
