package com.firstcitybank.trustbank.service;

import com.firstcitybank.trustbank.database.dao.VaultDao;
import com.firstcitybank.trustbank.model.vault.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VaultStatsService {

    private final VaultDao vaultDao;

    public VaultStatsService(VaultDao vaultDao) {
        this.vaultDao = vaultDao;
    }

    public VaultStatsResponse getVaultStats(VaultStatsRequest request) {
        // Validate request
        if (request.limit() <= 0) throw new IllegalArgumentException("Limit must be positive");
        if (!"CoreBank".equals(request.systemName())) throw new IllegalArgumentException("Invalid systemName, expected 'CoreBank'");

        SearchParams params = request.searchParams();
        if (params == null || params.searchBy() == null || params.searchString() == null) {
            throw new IllegalArgumentException("searchParams with searchBy and searchString are required");
        }

        String searchBy = params.searchBy().toUpperCase();
        String searchString = params.searchString();
        int limit = request.limit();

        List<Vault> vaults = switch (searchBy) {
            case "SECTOR" -> vaultDao.selectVaultsBySectorWithLimit(searchString, limit);
            case "SUBSECTOR" -> vaultDao.selectVaultsBySubSectorWithLimit(searchString, limit);
            default -> throw new IllegalArgumentException("searchBy must be 'SECTOR' or 'SUBSECTOR'");
        };

        List<VaultSummary> summaries = vaults.stream()
                .map(v -> new VaultSummary(v.vaultCode(), v.currencyCode(), v.amount()))
                .collect(Collectors.toList());

        return new VaultStatsResponse(summaries.size(), summaries, LocalDateTime.now(), "Vaults");
    }
}
