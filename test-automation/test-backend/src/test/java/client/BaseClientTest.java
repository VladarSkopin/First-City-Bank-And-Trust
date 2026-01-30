package client;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.*;
import org.skopintsev.database.client_types.ClientTypeDb;
import org.skopintsev.database.client_types.ClientTypeDbHelper;
import org.skopintsev.database.clients.ClientDbHelper;
import org.skopintsev.database.districts.DistrictDb;
import org.skopintsev.database.districts.DistrictDbHelper;
import org.skopintsev.database.sectors.SectorDb;
import org.skopintsev.database.sectors.SectorDbHelper;
import org.skopintsev.database.sectors.subsectors.SubSectorDb;
import org.skopintsev.database.sectors.subsectors.SubSectorDbHelper;
import org.skopintsev.database.social_ranks.SocialRankDb;
import org.skopintsev.database.social_ranks.SocialRankDbHelper;
import org.skopintsev.helper.GeneratorBuilder;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FieldDefaults(level = AccessLevel.PROTECTED)
public class BaseClientTest {

    final String BASE_CLIENT_TYPE_CODE = GeneratorBuilder.generateTestCode();
    final String BASE_SOCIAL_RANK_CODE = GeneratorBuilder.generateTestCode();
    final String BASE_DISTRICT_CODE = GeneratorBuilder.generateTestCode();
    final String BASE_SECTOR_CODE = GeneratorBuilder.generateTestCode();
    final String BASE_SUB_SECTOR_CODE = GeneratorBuilder.generateTestCode();

    @BeforeAll
    public void beforeAll() {
        ClientTypeDb newClientTypeDb = ClientTypeDb.builder()
                .clientTypeCode(BASE_CLIENT_TYPE_CODE)
                .clientTypeName(GeneratorBuilder.generateString(20))
                .build();
        ClientTypeDbHelper.insertClientType(newClientTypeDb);

        SocialRankDb newSocialRankDb = SocialRankDb.builder()
                .rankCode(BASE_SOCIAL_RANK_CODE)
                .rankName(GeneratorBuilder.generateString(10))
                .build();
        SocialRankDbHelper.insertSocialRank(newSocialRankDb);

        DistrictDb newDistrictDb = DistrictDb.builder()
                .districtCode(BASE_DISTRICT_CODE)
                .districtName(GeneratorBuilder.generateString(20))
                .build();
        DistrictDbHelper.insertDistrict(newDistrictDb);

        SectorDb newSectorDb = SectorDb.builder()
                .sectorCode(BASE_SECTOR_CODE)
                .sectorName(GeneratorBuilder.generateString(30))
                .build();
        SectorDbHelper.insertSector(newSectorDb);

        SubSectorDb subSectorDb = SubSectorDb.builder()
                .sectorCode(BASE_SECTOR_CODE)
                .subSectorCode(BASE_SUB_SECTOR_CODE)
                .subSectorName(GeneratorBuilder.generateString(40))
                .build();
        SubSectorDbHelper.insertSubSector(subSectorDb);
    }

    @AfterAll
    public void afterAll() {
        ClientTypeDbHelper.deleteClientType(BASE_CLIENT_TYPE_CODE);
        SocialRankDbHelper.deleteSocialRank(BASE_SOCIAL_RANK_CODE);
        DistrictDbHelper.deleteDistrict(BASE_DISTRICT_CODE);
        SubSectorDbHelper.deleteSubSector(BASE_SUB_SECTOR_CODE);
        SectorDbHelper.deleteSector(BASE_SECTOR_CODE);
    }

    @BeforeEach
    public void setUp() {
        ClientDbHelper.deleteAllTestClients();
    }

    @AfterEach
    public void tearDown() {
        ClientDbHelper.deleteAllTestClients();
    }
}
