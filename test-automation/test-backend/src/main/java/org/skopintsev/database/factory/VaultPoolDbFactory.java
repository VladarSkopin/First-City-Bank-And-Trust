package org.skopintsev.database.factory;


import org.skopintsev.database.vaults.VaultPoolsDb;
import org.skopintsev.helper.GeneratorBuilder;

import java.time.LocalDateTime;

public class VaultPoolDbFactory {

    public static VaultPoolsDb generateDefaultVaultPoolsDb(
            String currencyCode, String sectorCode) {

        return VaultPoolsDb.builder()
                .vaultPoolName(GeneratorBuilder.generateTestName())
                .isArchived(false)
                .currencyCode(currencyCode)
                .sectorCode(sectorCode)
                .amountFrom(0L)
                .amountTo(100L)
                .createdFrom(LocalDateTime.now().minusYears(1))
                .createdTo(LocalDateTime.now())
                .build();
    }
}
