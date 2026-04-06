package com.firstcitybank.trustbank.model.vault;

import java.time.LocalDateTime;
import java.util.List;

public record VaultStatsResponse(int totalCount, List<VaultSummary> vaults, LocalDateTime dateReceived, String systemName) {
//    public VaultStatsResponse {
//        systemName = "Vaults"; // normalise in compact constructor
//    }
}
