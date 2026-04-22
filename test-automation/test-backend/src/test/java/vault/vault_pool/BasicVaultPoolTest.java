package vault.vault_pool;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.skopintsev.assertions.api.vaults.vault_pools.VaultPoolsApiAssertions;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.assertions.db.vaults.vault_pools.VaultPoolsDbAssertions;
import org.skopintsev.database.factory.VaultPoolDbFactory;
import org.skopintsev.database.vaults.VaultPoolsDb;
import org.skopintsev.database.vaults.VaultPoolsDbHelper;
import org.skopintsev.model.factory.VaultPoolApiFactory;
import org.skopintsev.model.vaults.vault_pools.CreateVaultPoolRequest;
import org.skopintsev.model.vaults.vault_pools.VaultPool;
import org.skopintsev.model.vaults.vault_pools.VaultPoolResponse;
import org.skopintsev.transport.api.VaultPoolsApiClient;

import java.time.LocalDateTime;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BasicVaultPoolTest extends BaseVaultPoolTest {

    @Test
    @Tag("smoke")
    @Description("Test inserts a new VaultPool object into the Database and checks API for the new added vault pool.")
    @Severity(SeverityLevel.BLOCKER)
    public void createVaultDbTest() {
        VaultPoolsDb vaultPoolsDb = VaultPoolDbFactory.generateDefaultVaultPoolsDb(
                BASE_CURRENCY_CODE_FIRST, BASE_SECTOR_CODE_FIRST);

        int rowsInserted = VaultPoolsDbHelper.insertVaultPool(vaultPoolsDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        List<VaultPoolResponse> vaultPoolResponseList = VaultPoolsApiClient.getVaultPoolsAndValidate(200)
                .stream()
                .filter(vp -> vp.getVaultPoolName().startsWith("TEST"))
                .toList();
        List<VaultPoolResponse> vaultPoolResponseListExpected = List.of(
                VaultPoolResponse.builder()
                        .vaultPoolName(vaultPoolsDb.getVaultPoolName())
                        .isArchived(vaultPoolsDb.getIsArchived())
                        .currencyCode(BASE_CURRENCY_CODE_FIRST)
                        .sectorCode(BASE_SECTOR_CODE_FIRST)
                        .amountFrom(vaultPoolsDb.getAmountFrom())
                        .amountTo(vaultPoolsDb.getAmountTo())
                        .createdFrom(vaultPoolsDb.getCreatedFrom())
                        .createdTo(vaultPoolsDb.getCreatedTo())
                        .build()
        );

        VaultPoolsApiAssertions.checkVaultPoolsResponseMatchesExpected(
                vaultPoolResponseList, vaultPoolResponseListExpected);
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to post a new VaultPool object and checks Database for the new added vault pool.")
    @Severity(SeverityLevel.BLOCKER)
    public void createVaultApiTest() {
        CreateVaultPoolRequest createVaultPoolRequest = VaultPoolApiFactory.generateDefaultCreateVaultPoolRequest(
                BASE_CURRENCY_CODE_FIRST, BASE_SECTOR_CODE_FIRST);
        VaultPool vaultPool = VaultPoolsApiClient.saveVaultPoolAndValidate(createVaultPoolRequest, 201);

        String poolName = createVaultPoolRequest.getVaultPoolName();
        boolean isArchived = createVaultPoolRequest.getIsArchived();
        long amountFrom = createVaultPoolRequest.getAmountFrom();
        long amountTo = createVaultPoolRequest.getAmountTo();
        LocalDateTime createdFrom = createVaultPoolRequest.getCreatedFrom();
        LocalDateTime createdTo = createVaultPoolRequest.getCreatedTo();

        VaultPool vaultPoolExpected = VaultPool.builder()
                .vaultPoolName(poolName)
                .isArchived(isArchived)
                .currencyCode(BASE_CURRENCY_CODE_FIRST)
                .sectorCode(BASE_SECTOR_CODE_FIRST)
                .amountFrom(amountFrom)
                .amountTo(amountTo)
                .createdFrom(createdFrom)
                .createdTo(createdTo)
                .build();
        VaultPoolsApiAssertions.checkVaultPoolMatchesExpected(vaultPool, vaultPoolExpected);

        VaultPoolsDb vaultPoolsDb = VaultPoolsDbHelper.selectVaultPoolByName(createVaultPoolRequest.getVaultPoolName());
        VaultPoolsDb vaultPoolsDbExpected = VaultPoolsDb.builder()
                .vaultPoolName(poolName)
                .isArchived(isArchived)
                .currencyCode(BASE_CURRENCY_CODE_FIRST)
                .sectorCode(BASE_SECTOR_CODE_FIRST)
                .amountFrom(amountFrom)
                .amountTo(amountTo)
                .createdFrom(createdFrom)
                .createdTo(createdTo)
                .build();
        VaultPoolsDbAssertions.checkVaultPoolDbMatchesExpected(vaultPoolsDb, vaultPoolsDbExpected);

    }
}
