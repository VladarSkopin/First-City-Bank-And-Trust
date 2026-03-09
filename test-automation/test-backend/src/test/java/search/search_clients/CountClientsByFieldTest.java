package search.search_clients;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.*;
import org.skopintsev.assertions.api.clients.SearchClientsApiAssertions;
import org.skopintsev.database.client_types.ClientTypeDb;
import org.skopintsev.database.client_types.ClientTypeDbHelper;
import org.skopintsev.database.clients.ClientDb;
import org.skopintsev.database.clients.ClientDbHelper;
import org.skopintsev.database.factory.ClientDbFactory;
import org.skopintsev.database.sectors.SectorDb;
import org.skopintsev.database.sectors.SectorDbHelper;
import org.skopintsev.database.sectors.subsectors.SubSectorDb;
import org.skopintsev.database.sectors.subsectors.SubSectorDbHelper;
import org.skopintsev.database.social_ranks.SocialRankDb;
import org.skopintsev.database.social_ranks.SocialRankDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.transport.GetApiReqHelper;

import java.util.ArrayList;
import java.util.List;

import static org.skopintsev.constants.Constants.SC_OK;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CountClientsByFieldTest extends BaseSearchClientsTest {

    // Store created entities for cleanup
    List<String> createdClientCodes;
    List<String> createdSocialRankCodes;
    List<String> createdClientTypeCodes;
    List<String> createdSectorCodes;
    List<String> createdSubSectorCodes;

    @BeforeEach
    public void setUp() {
        createdClientCodes = new ArrayList<>();
        createdSocialRankCodes = new ArrayList<>();
        createdClientTypeCodes = new ArrayList<>();
        createdSectorCodes = new ArrayList<>();
        createdSubSectorCodes = new ArrayList<>();
    }

    @AfterEach
    public void cleanup() {
        // Delete in reverse order of dependencies
        createdClientCodes.forEach(ClientDbHelper::deleteClient);
        createdSubSectorCodes.forEach(SubSectorDbHelper::deleteSubSector);
        createdSectorCodes.forEach(SectorDbHelper::deleteSector);
        createdSocialRankCodes.forEach(SocialRankDbHelper::deleteSocialRank);
        createdClientTypeCodes.forEach(ClientTypeDbHelper::deleteClientType);
    }


    @Test
    @Tag("smoke")
    @Description("Test uses API to count clients by social rank.")
    @Severity(SeverityLevel.CRITICAL)
    public void countClientsBySocialRankTest() {
        SocialRankDb socialRankDb = SocialRankDb.builder()
                .rankCode(GeneratorBuilder.generateTestCode())
                .rankName(GeneratorBuilder.generateString(12))
                .build();
        SocialRankDbHelper.insertSocialRank(socialRankDb);
        String generatedRankCode = socialRankDb.getRankCode();
        createdSocialRankCodes.add(generatedRankCode);

        ClientDb clientDb = ClientDbFactory.defaultClientDbRequest(
                BASE_CLIENT_TYPE_CODE,
                generatedRankCode,
                BASE_DISTRICT_CODE,
                BASE_SUB_SECTOR_CODE
        );
        ClientDbHelper.insertClient(clientDb);
        createdClientCodes.add(clientDb.getClientCode());

        int clientsDbCount = ClientDbHelper.getClientsCountByRank(generatedRankCode);

        int clientsCount = GetApiReqHelper.countClientsByRankAndValidate(generatedRankCode, SC_OK);
        SearchClientsApiAssertions.checkClientsCount(clientsCount, clientsDbCount);
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to count clients by client type.")
    @Severity(SeverityLevel.CRITICAL)
    public void countClientsByClientTypeTest() {
        ClientTypeDb clientTypeDb = ClientTypeDb.builder()
                .clientTypeCode(GeneratorBuilder.generateTestCode())
                .clientTypeName(GeneratorBuilder.generateString(10))
                .build();
        ClientTypeDbHelper.insertClientType(clientTypeDb);
        String generatedClientTypeCode = clientTypeDb.getClientTypeCode();
        createdClientTypeCodes.add(generatedClientTypeCode);

        ClientDb clientDb = ClientDbFactory.defaultClientDbRequest(
                generatedClientTypeCode,
                BASE_SOCIAL_RANK_CODE,
                BASE_DISTRICT_CODE,
                BASE_SUB_SECTOR_CODE
        );
        ClientDbHelper.insertClient(clientDb);
        createdClientCodes.add(clientDb.getClientCode());

        int clientsDbCount = ClientDbHelper.getClientsCountByClientType(generatedClientTypeCode);

        int clientsCount = GetApiReqHelper.countClientsByClientTypeAndValidate(generatedClientTypeCode, SC_OK);
        SearchClientsApiAssertions.checkClientsCount(clientsCount, clientsDbCount);
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to count clients by sector.")
    @Severity(SeverityLevel.CRITICAL)
    public void countClientsBySectorTest() {
        SectorDb sectorDb = SectorDb.builder()
                .sectorCode("SECTOR-" + GeneratorBuilder.generateTestCode())
                .sectorName(GeneratorBuilder.generateString(15))
                .build();
        SectorDbHelper.insertSector(sectorDb);
        String generatedSectorCode = sectorDb.getSectorCode();
        createdSectorCodes.add(generatedSectorCode);

        SubSectorDb subSectorDb = SubSectorDb.builder()
                .subSectorCode("SUB_SECTOR-" + GeneratorBuilder.generateTestCode())
                .subSectorName(GeneratorBuilder.generateString(20))
                .sectorCode(generatedSectorCode)
                .build();
        SubSectorDbHelper.insertSubSector(subSectorDb);
        String generatedSubSectorCode = subSectorDb.getSubSectorCode();
        createdSubSectorCodes.add(generatedSubSectorCode);

        ClientDb clientDb = ClientDbFactory.defaultClientDbRequest(
                BASE_CLIENT_TYPE_CODE,
                BASE_SOCIAL_RANK_CODE,
                BASE_DISTRICT_CODE,
                generatedSubSectorCode
        );
        ClientDbHelper.insertClient(clientDb);
        createdClientCodes.add(clientDb.getClientCode());

        int clientsDbCount = ClientDbHelper.getClientsCountBySector(generatedSectorCode);

        int clientsCount = GetApiReqHelper.countClientsBySectorAndValidate(generatedSectorCode, SC_OK);
        SearchClientsApiAssertions.checkClientsCount(clientsCount, clientsDbCount);
    }
}
