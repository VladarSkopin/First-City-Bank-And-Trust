package com.firstcitybank.trustbank.controller;

import com.firstcitybank.trustbank.model.vault.VaultStatsRequest;
import com.firstcitybank.trustbank.model.vault.VaultStatsResponse;
import com.firstcitybank.trustbank.service.VaultStatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.firstcitybank.trustbank.helper.Paths.VAULT_STATS;

@RestController
@RequestMapping(VAULT_STATS)
public class VaultStatsController {

    private final VaultStatsService vaultStatsService;

    public VaultStatsController(VaultStatsService vaultStatsService) {
        this.vaultStatsService = vaultStatsService;
    }

    @PostMapping
    public ResponseEntity<VaultStatsResponse> getVaultStats(@RequestBody VaultStatsRequest request) {
        VaultStatsResponse response = vaultStatsService.getVaultStats(request);
        return ResponseEntity.ok(response);
    }
}
