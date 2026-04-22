package vault.vault_pool;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.*;
import org.skopintsev.database.client_types.ClientTypeDb;
import org.skopintsev.database.client_types.ClientTypeDbHelper;
import org.skopintsev.database.clients.ClientDb;
import org.skopintsev.database.clients.ClientDbHelper;
import org.skopintsev.database.currencies.CurrencyDb;
import org.skopintsev.database.currencies.CurrencyDbHelper;
import org.skopintsev.database.districts.DistrictDb;
import org.skopintsev.database.districts.DistrictDbHelper;
import org.skopintsev.database.sectors.SectorDb;
import org.skopintsev.database.sectors.SectorDbHelper;
import org.skopintsev.database.sectors.subsectors.SubSectorDb;
import org.skopintsev.database.sectors.subsectors.SubSectorDbHelper;
import org.skopintsev.database.social_ranks.SocialRankDb;
import org.skopintsev.database.social_ranks.SocialRankDbHelper;
import org.skopintsev.database.vaults.VaultDb;
import org.skopintsev.database.vaults.VaultDbHelper;
import org.skopintsev.database.vaults.VaultPoolsDbHelper;
import org.skopintsev.helper.GeneratorBuilder;

import java.math.BigInteger;
import java.time.LocalDateTime;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class BaseVaultPoolTest {

    String BASE_CLIENT_TYPE_CODE = GeneratorBuilder.generateTestCode();
    String BASE_SOCIAL_RANK_CODE = GeneratorBuilder.generateTestCode();
    String BASE_DISTRICT_CODE = GeneratorBuilder.generateTestCode();
    String BASE_SECTOR_CODE_FIRST = GeneratorBuilder.generateTestCode();
    String BASE_SECTOR_CODE_SECOND = GeneratorBuilder.generateTestCode();
    String BASE_SUB_SECTOR_CODE_FIRST = GeneratorBuilder.generateTestCode();
    String BASE_SUB_SECTOR_CODE_SECOND = GeneratorBuilder.generateTestCode();
    String BASE_CLIENT_CODE_FIRST = GeneratorBuilder.generateTestCode();
    String BASE_CLIENT_CODE_SECOND = GeneratorBuilder.generateTestCode();
    String BASE_CURRENCY_CODE_FIRST = GeneratorBuilder.generateTestCode();
    String BASE_CURRENCY_CODE_SECOND = GeneratorBuilder.generateTestCode();

    String BASE_VAULT_CODE_OLD = GeneratorBuilder.generateTestCode();
    String BASE_VAULT_CODE_ARCHIVED = GeneratorBuilder.generateTestCode();
    String BASE_VAULT_CODE_0_BALANCE = GeneratorBuilder.generateTestCode();
    String BASE_VAULT_CODE_100_BALANCE = GeneratorBuilder.generateTestCode();
    String BASE_VAULT_CODE_99_BALANCE = GeneratorBuilder.generateTestCode();
    String BASE_VAULT_CODE_101_BALANCE = GeneratorBuilder.generateTestCode();
    String BASE_VAULT_CODE_2nd_CURRENCY = GeneratorBuilder.generateTestCode();
    String BASE_VAULT_CODE_2nd_SECTOR = GeneratorBuilder.generateTestCode();

    ClientTypeDb newClientTypeDb = ClientTypeDb.builder()
            .clientTypeCode(BASE_CLIENT_TYPE_CODE)
            .clientTypeName(GeneratorBuilder.generateString(20))
            .build();
    SocialRankDb newSocialRankDb = SocialRankDb.builder()
            .rankCode(BASE_SOCIAL_RANK_CODE)
            .rankName(GeneratorBuilder.generateString(10))
            .build();
    DistrictDb newDistrictDb = DistrictDb.builder()
            .districtCode(BASE_DISTRICT_CODE)
            .districtName(GeneratorBuilder.generateString(20))
            .build();
    SectorDb newSectorDbFirst = SectorDb.builder()
            .sectorCode(BASE_SECTOR_CODE_FIRST)
            .sectorName(GeneratorBuilder.generateString(30))
            .build();
    SectorDb newSectorDbSecond = SectorDb.builder()
            .sectorCode(BASE_SECTOR_CODE_SECOND)
            .sectorName(GeneratorBuilder.generateString(30))
            .build();
    SubSectorDb subSectorDbFirst = SubSectorDb.builder()
            .sectorCode(BASE_SECTOR_CODE_FIRST)
            .subSectorCode(BASE_SUB_SECTOR_CODE_FIRST)
            .subSectorName(GeneratorBuilder.generateString(40))
            .build();
    SubSectorDb subSectorDbSecond = SubSectorDb.builder()
            .sectorCode(BASE_SECTOR_CODE_SECOND)
            .subSectorCode(BASE_SUB_SECTOR_CODE_SECOND)
            .subSectorName(GeneratorBuilder.generateString(40))
            .build();
    ClientDb clientDbFirst = ClientDb.builder()
            .clientCode(BASE_CLIENT_CODE_FIRST)
            .clientTypeCode(BASE_CLIENT_TYPE_CODE)
            .nameOrTitle(GeneratorBuilder.generateString(10))
            .socialRankCode(BASE_SOCIAL_RANK_CODE)
            .subSectorCode(BASE_SUB_SECTOR_CODE_FIRST)
            .build();
    ClientDb clientDbSecond = ClientDb.builder()
            .clientCode(BASE_CLIENT_CODE_SECOND)
            .clientTypeCode(BASE_CLIENT_TYPE_CODE)
            .nameOrTitle(GeneratorBuilder.generateString(10))
            .socialRankCode(BASE_SOCIAL_RANK_CODE)
            .subSectorCode(BASE_SUB_SECTOR_CODE_SECOND)
            .build();
    CurrencyDb currencyDbFirst = CurrencyDb.builder()
            .currencyCode(BASE_CURRENCY_CODE_FIRST)
            .currencyName(GeneratorBuilder.generateString(5))
            .currencySymbol("$")
            .build();
    CurrencyDb currencyDbSecond = CurrencyDb.builder()
            .currencyCode(BASE_CURRENCY_CODE_SECOND)
            .currencyName(GeneratorBuilder.generateString(5))
            .currencySymbol("$")
            .build();

     VaultDb vaultDbOld = VaultDb.builder()
            .vaultCode(BASE_VAULT_CODE_OLD)
            .clientCode(BASE_CLIENT_CODE_FIRST)
            .createdAt(LocalDateTime.now().minusYears(20))  // old
            .modifiedAt(LocalDateTime.now().minusDays(1))
            .amount(BigInteger.valueOf(900))
            .currencyCode(BASE_CURRENCY_CODE_FIRST)
            .isArchived(false)
            .build();
    VaultDb vaultDbArchived = VaultDb.builder()
            .vaultCode(BASE_VAULT_CODE_ARCHIVED)
            .clientCode(BASE_CLIENT_CODE_FIRST)
            .createdAt(LocalDateTime.now().minusYears(1))
            .modifiedAt(LocalDateTime.now().minusDays(1))
            .amount(BigInteger.valueOf(900))
            .currencyCode(BASE_CURRENCY_CODE_FIRST)
            .isArchived(true)  // archived
            .build();
    VaultDb vaultDb0Balance = VaultDb.builder()
            .vaultCode(BASE_VAULT_CODE_0_BALANCE)
            .clientCode(BASE_CLIENT_CODE_FIRST)
            .createdAt(LocalDateTime.now().minusYears(1))
            .modifiedAt(LocalDateTime.now().minusDays(1))
            .amount(BigInteger.valueOf(0))  // balance = 0
            .currencyCode(BASE_CURRENCY_CODE_FIRST)
            .isArchived(false)
            .build();
    VaultDb vaultDb100Balance = VaultDb.builder()
            .vaultCode(BASE_VAULT_CODE_100_BALANCE)
            .clientCode(BASE_CLIENT_CODE_FIRST)
            .createdAt(LocalDateTime.now().minusYears(1))
            .modifiedAt(LocalDateTime.now().minusDays(1))
            .amount(BigInteger.valueOf(100))  // balance = 100
            .currencyCode(BASE_CURRENCY_CODE_FIRST)
            .isArchived(false)
            .build();
    VaultDb vaultDb99Balance = VaultDb.builder()
            .vaultCode(BASE_VAULT_CODE_99_BALANCE)
            .clientCode(BASE_CLIENT_CODE_FIRST)
            .createdAt(LocalDateTime.now().minusYears(1))
            .modifiedAt(LocalDateTime.now().minusDays(1))
            .amount(BigInteger.valueOf(99))  // balance = 99
            .currencyCode(BASE_CURRENCY_CODE_FIRST)
            .isArchived(false)
            .build();
    VaultDb vaultDb101Balance = VaultDb.builder()
            .vaultCode(BASE_VAULT_CODE_101_BALANCE)
            .clientCode(BASE_CLIENT_CODE_FIRST)
            .createdAt(LocalDateTime.now().minusYears(1))
            .modifiedAt(LocalDateTime.now().minusDays(1))
            .amount(BigInteger.valueOf(101))  // balance = 101
            .currencyCode(BASE_CURRENCY_CODE_FIRST)
            .isArchived(false)
            .build();
    VaultDb vaultDb2ndCurrency = VaultDb.builder()
            .vaultCode(BASE_VAULT_CODE_2nd_CURRENCY)
            .clientCode(BASE_CLIENT_CODE_FIRST)
            .createdAt(LocalDateTime.now().minusYears(1))
            .modifiedAt(LocalDateTime.now().minusDays(1))
            .amount(BigInteger.valueOf(900))
            .currencyCode(BASE_CURRENCY_CODE_SECOND)  // 2nd currency
            .isArchived(false)
            .build();
    VaultDb vaultDb2ndSector = VaultDb.builder()
            .vaultCode(BASE_VAULT_CODE_2nd_SECTOR)
            .clientCode(BASE_CLIENT_CODE_SECOND)  // 2nd client -> 2nd sub-sector -> 2nd sector
            .createdAt(LocalDateTime.now().minusYears(1))
            .modifiedAt(LocalDateTime.now().minusDays(1))
            .amount(BigInteger.valueOf(900))
            .currencyCode(BASE_CURRENCY_CODE_FIRST)
            .isArchived(false)
            .build();

    @BeforeAll
    public void beforeAll() {
        ClientTypeDbHelper.insertClientType(newClientTypeDb);
        SocialRankDbHelper.insertSocialRank(newSocialRankDb);
        DistrictDbHelper.insertDistrict(newDistrictDb);
        SectorDbHelper.insertSector(newSectorDbFirst);
        SectorDbHelper.insertSector(newSectorDbSecond);
        SubSectorDbHelper.insertSubSector(subSectorDbFirst);
        SubSectorDbHelper.insertSubSector(subSectorDbSecond);
        ClientDbHelper.insertClient(clientDbFirst);
        ClientDbHelper.insertClient(clientDbSecond);
        CurrencyDbHelper.insertCurrency(currencyDbFirst);
        CurrencyDbHelper.insertCurrency(currencyDbSecond);

        VaultDbHelper.insertVault(vaultDbOld);
        VaultDbHelper.insertVault(vaultDbArchived);
        VaultDbHelper.insertVault(vaultDb0Balance);
        VaultDbHelper.insertVault(vaultDb100Balance);
        VaultDbHelper.insertVault(vaultDb99Balance);
        VaultDbHelper.insertVault(vaultDb101Balance);
        VaultDbHelper.insertVault(vaultDb2ndCurrency);
        VaultDbHelper.insertVault(vaultDb2ndSector);
    }

    @AfterAll
    public void afterAll() {
        VaultDbHelper.deleteVault(BASE_VAULT_CODE_OLD);
        VaultDbHelper.deleteVault(BASE_VAULT_CODE_ARCHIVED);
        VaultDbHelper.deleteVault(BASE_VAULT_CODE_0_BALANCE);
        VaultDbHelper.deleteVault(BASE_VAULT_CODE_100_BALANCE);
        VaultDbHelper.deleteVault(BASE_VAULT_CODE_99_BALANCE);
        VaultDbHelper.deleteVault(BASE_VAULT_CODE_101_BALANCE);
        VaultDbHelper.deleteVault(BASE_VAULT_CODE_2nd_CURRENCY);
        VaultDbHelper.deleteVault(BASE_VAULT_CODE_2nd_SECTOR);

        CurrencyDbHelper.deleteCurrency(BASE_CURRENCY_CODE_FIRST);
        CurrencyDbHelper.deleteCurrency(BASE_CURRENCY_CODE_SECOND);
        ClientDbHelper.deleteClient(BASE_CLIENT_CODE_FIRST);
        ClientDbHelper.deleteClient(BASE_CLIENT_CODE_SECOND);
        ClientTypeDbHelper.deleteClientType(BASE_CLIENT_TYPE_CODE);
        SocialRankDbHelper.deleteSocialRank(BASE_SOCIAL_RANK_CODE);
        DistrictDbHelper.deleteDistrict(BASE_DISTRICT_CODE);
        SubSectorDbHelper.deleteSubSector(BASE_SUB_SECTOR_CODE_FIRST);
        SubSectorDbHelper.deleteSubSector(BASE_SUB_SECTOR_CODE_SECOND);
        SectorDbHelper.deleteSector(BASE_SECTOR_CODE_FIRST);
        SectorDbHelper.deleteSector(BASE_SECTOR_CODE_SECOND);
    }

    @BeforeEach
    public void setUp() {
        VaultPoolsDbHelper.deleteAllTestVaultPools();
    }

    @AfterEach
    public void tearDown() {
        VaultPoolsDbHelper.deleteAllTestVaultPools();
    }
}
