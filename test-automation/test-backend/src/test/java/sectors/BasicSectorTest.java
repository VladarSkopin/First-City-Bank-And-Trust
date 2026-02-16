package sectors;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.skopintsev.assertions.api.sectors.SectorApiAssertions;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.assertions.db.sectors.SectorDbAssertions;
import org.skopintsev.database.sectors.SectorDb;
import org.skopintsev.database.sectors.SectorDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.model.sectors.Sector;
import org.skopintsev.transport.GetApiReqHelper;
import org.skopintsev.transport.PostApiReqHelper;

import java.util.List;

import static org.skopintsev.constants.Constants.SC_OK;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BasicSectorTest extends BaseSectorTest {

    final String SECTOR_CODE = GeneratorBuilder.generateTestCode();
    final String SECTOR_NAME = GeneratorBuilder.generateString(10);
    final String DESCRIPTION = GeneratorBuilder.generateString(1000);

    @Test
    @Tag("smoke")
    @Description("Test inserts a new Sector object into the Database and checks API for the new added sector.")
    @Severity(SeverityLevel.BLOCKER)
    public void createSectorDbTest() {
        SectorDb newSectorDb = SectorDb.builder()
                .sectorCode(SECTOR_CODE)
                .sectorName(SECTOR_NAME)
                .description(DESCRIPTION)
                .build();
        int rowsInserted = SectorDbHelper.insertSector(newSectorDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        List<Sector> sectors = GetApiReqHelper.getSectorsAndValidate(SC_OK);
        SectorApiAssertions.checkNotNullSectors(sectors);

        Sector newAddedSectorApi = sectors
                .stream()
                .filter(s -> s.getSectorCode().equals(SECTOR_CODE))
                .findFirst()
                .orElse(null);
        SectorDbAssertions.checkSectorField("sectorName", newAddedSectorApi.getSectorName(), SECTOR_NAME);
        SectorDbAssertions.checkSectorField("description", newAddedSectorApi.getDescription(), DESCRIPTION);
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to post a new Sector object and checks Database for the new added sector.")
    @Severity(SeverityLevel.BLOCKER)
    public void createSectorApiTest() {
        Sector newAddedSectorApi = Sector.builder()
                .sectorCode(SECTOR_CODE)
                .sectorName(SECTOR_NAME)
                .description(DESCRIPTION)
                .build();
        PostApiReqHelper.saveSectorAndValidate(newAddedSectorApi, SC_OK);

        List<Sector> sectors = GetApiReqHelper.getSectorsAndValidate(SC_OK);
        SectorApiAssertions.checkNotNullSectors(sectors);

        SectorDb newAddedSectorDb = SectorDbHelper.selectSectorByCode(SECTOR_CODE);
        SectorDbAssertions.checkSectorPresence(newAddedSectorDb, true);
        SectorDbAssertions.checkSectorField("sectorName", newAddedSectorApi.getSectorName(), SECTOR_NAME);
        SectorDbAssertions.checkSectorField("description", newAddedSectorApi.getDescription(), DESCRIPTION);
    }
}
