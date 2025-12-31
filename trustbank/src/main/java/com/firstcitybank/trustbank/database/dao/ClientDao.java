package com.firstcitybank.trustbank.database.dao;

import com.firstcitybank.trustbank.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientDao {
    List<Client> selectClients();
    int insertClient(Client client);
    int deleteClient(String clientCode);
    Optional<Client> selectClientByCode(String clientCode);

    // Search operations
    List<Client> findClientsBySocialRank(String socialRankCode);
    List<Client> findClientsByClientType(String clientTypeCode);
    List<Client> findClientsBySubSector(String subSectorCode);
    List<Client> findClientsBySector(String sectorCode);
    List<Client> findClientsByDistrict(String districtCode);

    // Advanced search with multiple criteria
    List<Client> searchClients(
            String socialRankCode,
            String clientTypeCode,
            String subSectorCode,
            String sectorCode,
            String districtCode,
            Boolean isBlocked
    );

    // Count operations
    long countAllClients();
    long countActiveClients();
    long countBlockedClients();
    long countClientsBySocialRank(String socialRankCode);
    long countClientsByClientType(String clientTypeCode);
    long countClientsBySector(String sectorCode);

    // Existence checks
    boolean existsByName(String nameOrTitle);
    boolean existsByCode(String clientCode);
}
