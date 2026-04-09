package com.firstcitybank.trustbank.service;

import com.firstcitybank.trustbank.database.dao.ClientDao;
import com.firstcitybank.trustbank.model.client.Client;
import org.springframework.stereotype.Service;
import com.firstcitybank.trustbank.helper.ClientValidator;

import java.util.List;

@Service
public class SearchClientsService {

    private final ClientDao clientDao;

    public SearchClientsService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }


    // Single criteria searches

    public List<Client> getClientsBySocialRank(String socialRankCode) {
        ClientValidator.validateSocialRankCode(socialRankCode);
        return clientDao.findClientsBySocialRank(socialRankCode);
    }

    public List<Client> getClientsByClientType(String clientTypeCode) {
        ClientValidator.validateClientTypeCode(clientTypeCode);
        return clientDao.findClientsByClientType(clientTypeCode);
    }

    public List<Client> getClientsBySubSector(String subSectorCode) {
        ClientValidator.validateSubSectorCode(subSectorCode);
        return clientDao.findClientsBySubSector(subSectorCode);
    }

    public List<Client> getClientsBySector(String sectorCode) {
        ClientValidator.validateSectorCode(sectorCode);
        return clientDao.findClientsBySector(sectorCode);
    }

    public List<Client> getClientsByDistrict(String districtCode) {
        ClientValidator.validateDistrictCode(districtCode);
        return clientDao.findClientsByDistrict(districtCode);
    }


    // Advanced search with multiple criteria

    public List<Client> searchClients(
            String nameOrTitle,
            String socialRankCode,
            String clientTypeCode,
            String subSectorCode,
            String sectorCode,
            String districtCode,
            Boolean isBlocked) {

        // Validate all provided parameters
        if (nameOrTitle != null) ClientValidator.validateClientNameOrTitle(nameOrTitle);
        if (socialRankCode != null) ClientValidator.validateSocialRankCode(socialRankCode);
        if (clientTypeCode != null) ClientValidator.validateClientTypeCode(clientTypeCode);
        if (subSectorCode != null) ClientValidator.validateSubSectorCode(subSectorCode);
        if (sectorCode != null) ClientValidator.validateSectorCode(sectorCode);
        if (districtCode != null) ClientValidator.validateDistrictCode(districtCode);

        return clientDao.searchClients(
                nameOrTitle, socialRankCode, clientTypeCode, subSectorCode,
                sectorCode, districtCode, isBlocked
        );
    }


    // Statistics and counts

    public int countClientsBySocialRank(String socialRankCode) {
        ClientValidator.validateSocialRankCode(socialRankCode);
        return clientDao.countClientsBySocialRank(socialRankCode);
    }

    public int countClientsByClientType(String clientTypeCode) {
        ClientValidator.validateClientTypeCode(clientTypeCode);
        return clientDao.countClientsByClientType(clientTypeCode);
    }

    public int countClientsBySector(String sectorCode) {
        ClientValidator.validateSectorCode(sectorCode);
        return clientDao.countClientsBySector(sectorCode);
    }


    public int countClients() {
        return clientDao.countAllClients();
    }

    public int countClientsActive() {
        return clientDao.countActiveClients();
    }

    public int countClientsBlocked() {
        return clientDao.countBlockedClients();
    }



}
