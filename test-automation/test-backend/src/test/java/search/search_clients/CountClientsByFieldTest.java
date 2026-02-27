package search.search_clients;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
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

import static org.skopintsev.constants.Constants.SC_OK;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CountClientsByFieldTest extends BaseSearchClientsTest {

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

        ClientDb clientDb = ClientDbFactory.defaultClientDbRequest(
                BASE_CLIENT_TYPE_CODE,
                generatedRankCode,
                BASE_DISTRICT_CODE,
                BASE_SUB_SECTOR_CODE
        );
        ClientDbHelper.insertClient(clientDb);

        int clientsDbCount = ClientDbHelper.getClientsCountByRank(generatedRankCode);

        int clientsCount = GetApiReqHelper.countClientsByRankAndValidate(generatedRankCode, SC_OK);
        SearchClientsApiAssertions.checkClientsCount(clientsCount, clientsDbCount);

        // cleanup
        ClientDbHelper.deleteClient(clientDb.getClientCode());
        SocialRankDbHelper.deleteSocialRank(generatedRankCode);
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

        ClientDb clientDb = ClientDbFactory.defaultClientDbRequest(
                generatedClientTypeCode,
                BASE_SOCIAL_RANK_CODE,
                BASE_DISTRICT_CODE,
                BASE_SUB_SECTOR_CODE
        );
        ClientDbHelper.insertClient(clientDb);

        int clientsDbCount = ClientDbHelper.getClientsCountByClientType(generatedClientTypeCode);

        int clientsCount = GetApiReqHelper.countClientsByClientTypeAndValidate(generatedClientTypeCode, SC_OK);
        SearchClientsApiAssertions.checkClientsCount(clientsCount, clientsDbCount);

        // cleanup
        ClientDbHelper.deleteClient(clientDb.getClientCode());
        ClientTypeDbHelper.deleteClientType(generatedClientTypeCode);
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

        SubSectorDb subSectorDb = SubSectorDb.builder()
                .subSectorCode("SUB_SECTOR-" + GeneratorBuilder.generateTestCode())
                .subSectorName(GeneratorBuilder.generateString(20))
                .sectorCode(generatedSectorCode)
                .build();
        SubSectorDbHelper.insertSubSector(subSectorDb);
        String generatedSubSectorCode = subSectorDb.getSubSectorCode();

        ClientDb clientDb = ClientDbFactory.defaultClientDbRequest(
                BASE_CLIENT_TYPE_CODE,
                BASE_SOCIAL_RANK_CODE,
                BASE_DISTRICT_CODE,
                generatedSubSectorCode
        );
        ClientDbHelper.insertClient(clientDb);


        int clientsDbCount = ClientDbHelper.getClientsCountBySector(generatedSectorCode);

        int clientsCount = GetApiReqHelper.countClientsBySectorAndValidate(generatedSectorCode, SC_OK);
        SearchClientsApiAssertions.checkClientsCount(clientsCount, clientsDbCount);

        // cleanup
        ClientDbHelper.deleteClient(clientDb.getClientCode());
        SubSectorDbHelper.deleteSubSector(generatedSubSectorCode);
        SectorDbHelper.deleteSector(generatedSectorCode);
    }
}
