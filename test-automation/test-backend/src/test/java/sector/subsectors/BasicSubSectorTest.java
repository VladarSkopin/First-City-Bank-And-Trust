package sector.subsectors;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.skopintsev.assertions.api.sectors.SubSectorApiAssertions;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.assertions.db.sectors.SubSectorDbAssertions;
import org.skopintsev.database.sectors.subsectors.SubSectorDb;
import org.skopintsev.database.sectors.subsectors.SubSectorDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.model.sectors.SubSector;
import org.skopintsev.transport.api.SectorsApiClient;

import java.util.List;

import static org.skopintsev.constants.Constants.SC_OK;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BasicSubSectorTest extends BaseSubSectorTest {

    final String SUB_SECTOR_CODE = GeneratorBuilder.generateTestCode();
    final String SUB_SECTOR_NAME = GeneratorBuilder.generateString(10);
    final String SUB_SECTOR_DESCRIPTION = GeneratorBuilder.generateString(100);

    @Test
    @Tag("smoke")
    @Description("Test inserts a new SubSector object into the Database and checks API for the new added sub-sector.")
    @Severity(SeverityLevel.BLOCKER)
    public void createSubSectorDbTest() {
        SubSectorDb subSectorDb = SubSectorDb.builder()
                .sectorCode(BASE_SECTOR_CODE)
                .subSectorCode(SUB_SECTOR_CODE)
                .subSectorName(SUB_SECTOR_NAME)
                .description(SUB_SECTOR_DESCRIPTION)
                .build();
        int rowsInserted = SubSectorDbHelper.insertSubSector(subSectorDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        List<SubSector> subSectors = SectorsApiClient.getSubSectorsAndValidate(SC_OK);
        SubSectorApiAssertions.checkNotNullSubSectors(subSectors);

        SubSector newAddedSubSector = subSectors
                .stream()
                .filter(s -> s.getSubSectorCode().equals(SUB_SECTOR_CODE))
                .findFirst()
                .orElse(null);
        SubSectorDbAssertions.checkSubSectorField("subSectorName", newAddedSubSector.getSubSectorName(),
                SUB_SECTOR_NAME);
        SubSectorDbAssertions.checkSubSectorField("description", newAddedSubSector.getDescription(),
                SUB_SECTOR_DESCRIPTION);
        SubSectorDbAssertions.checkSubSectorField("sectorCode", newAddedSubSector.getSectorCode(),
                BASE_SECTOR_CODE);
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to post a new SubSector object and checks Database for the new added sub-sector.")
    @Severity(SeverityLevel.BLOCKER)
    public void createSubSectorApiTest() {
        SubSector subSector = SubSector.builder()
                .sectorCode(BASE_SECTOR_CODE)
                .subSectorCode(SUB_SECTOR_CODE)
                .subSectorName(SUB_SECTOR_NAME)
                .description(SUB_SECTOR_DESCRIPTION)
                .build();
        SectorsApiClient.saveSubSectorAndValidate(subSector, SC_OK);

        List<SubSector> subSectors = SectorsApiClient.getSubSectorsAndValidate(SC_OK);
        SubSectorApiAssertions.checkNotNullSubSectors(subSectors);

        SubSectorDb subSectorDb = SubSectorDbHelper.selectSubSectorByCode(SUB_SECTOR_CODE);
        SubSectorDbAssertions.checkSubSectorPresence(subSectorDb, true);
        SubSectorDbAssertions.checkSubSectorField("subSectorName", subSectorDb.getSubSectorName(),
                SUB_SECTOR_NAME);
        SubSectorDbAssertions.checkSubSectorField("description", subSectorDb.getDescription(),
                SUB_SECTOR_DESCRIPTION);
        SubSectorDbAssertions.checkSubSectorField("sectorCode", subSectorDb.getSectorCode(),
                BASE_SECTOR_CODE);
    }
}
