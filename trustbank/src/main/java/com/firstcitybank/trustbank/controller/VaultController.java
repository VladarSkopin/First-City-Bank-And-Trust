package com.firstcitybank.trustbank.controller;

import com.firstcitybank.trustbank.model.Vault;
import com.firstcitybank.trustbank.model.VaultOperationRequest;
import com.firstcitybank.trustbank.service.VaultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.firstcitybank.trustbank.helper.Paths.CODE_PATH;
import static com.firstcitybank.trustbank.helper.Paths.VAULTS_PATH;

@RestController
@RequestMapping(path = VAULTS_PATH)
public class VaultController {

    private final VaultService vaultService;

    public VaultController(VaultService vaultService) {
        this.vaultService = vaultService;
    }

    @GetMapping
    public List<Vault> getAllVaults() {
        return vaultService.getVaults();
    }

    @PostMapping
    public void addVault(@RequestBody Vault vault) {
        vaultService.addNewVault(vault);
    }

    @DeleteMapping(CODE_PATH)
    public void deleteVault(@PathVariable("code") String code) {
        vaultService.deleteVault(code);
    }

    @PostMapping("/operations")
    public ResponseEntity<Vault> executeVaultOperation(@RequestBody VaultOperationRequest request) {
        Vault updatedVault = vaultService.executeVaultOperation(request);
        return ResponseEntity.ok(updatedVault);
    }

}
