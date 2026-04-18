package com.firstcitybank.trustbank.controller;

import com.firstcitybank.trustbank.model.vault.vault_pools.CreateVaultPoolRequest;
import com.firstcitybank.trustbank.model.vault.vault_pools.DeleteVaultPoolRequest;
import com.firstcitybank.trustbank.model.vault.vault_pools.VaultPool;
import com.firstcitybank.trustbank.service.VaultPoolsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.firstcitybank.trustbank.helper.Paths.VAULT_POOLS;


@RestController
@RequestMapping(path = VAULT_POOLS)
public class VaultPoolsController {

    private final VaultPoolsService vaultPoolsService;

    public VaultPoolsController(VaultPoolsService vaultPoolsService) {
        this.vaultPoolsService = vaultPoolsService;
    }

    @PostMapping
    public ResponseEntity<VaultPool> createVaultPool(@RequestBody CreateVaultPoolRequest request) {
        VaultPool created = vaultPoolsService.createVaultPool(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteVaultPool(@RequestBody DeleteVaultPoolRequest request) {
        vaultPoolsService.deleteVaultPool(request.vaultPoolName());
        return ResponseEntity.noContent().build();
    }
}
