package sectors;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.assertions.db.sectors.SectorDbAssertions;
import org.skopintsev.database.sectors.SectorDb;
import org.skopintsev.database.sectors.SectorDbHelper;
import org.skopintsev.database.sectors.subsectors.SubSectorDb;
import org.skopintsev.database.sectors.subsectors.SubSectorDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.transport.DeleteApiReqHelper;

import java.util.stream.Stream;

import static org.skopintsev.constants.Constants.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeleteSectorTest extends BaseSectorTest {

    final String SECTOR_CODE = GeneratorBuilder.generateTestCode();
    final String SECTOR_NAME = GeneratorBuilder.generateString(10);

    @Test
    @Tag("regression")
    @Description("Test creates a new sector in the Database and uses API to delete it.")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteSectorTest() {
        int sectorsCountOld = SectorDbHelper.getSectorsCount();

        SectorDb sectorDb = SectorDb.builder()
                .sectorCode(SECTOR_CODE)
                .sectorName(SECTOR_NAME)
                .build();
        int rowsInserted = SectorDbHelper.insertSector(sectorDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        DeleteApiReqHelper.deleteSectorAndValidate(SECTOR_CODE, SC_OK);

        sectorDb = SectorDbHelper.selectSectorByCode(SECTOR_CODE);
        SectorDbAssertions.checkSectorPresence(sectorDb, false);

        int sectorsCountNew = SectorDbHelper.getSectorsCount();
        CommonDbAssertions.checkCounts(sectorsCountNew, sectorsCountOld);
    }

    @ParameterizedTest(name = "[{index}] sectorCode = {0}")
    @MethodSource("sectorCodeRequest")
    @Tag("regression")
    @Description(
            """
            Test uses API to delete a sector:
            1) with code = null,
            2) with code = empty string,
            3) a sector that is absent in the Database.
            """)
    @Severity(SeverityLevel.CRITICAL)
    public void deleteSectorNegativeTest(String sectorCode) {
        int sectorsCountOld = SectorDbHelper.getSectorsCount();

        DeleteApiReqHelper.deleteSectorAndValidate(sectorCode, SC_NOT_FOUND);

        int sectorsCountNew = SectorDbHelper.getSectorsCount();
        CommonDbAssertions.checkCounts(sectorsCountNew, sectorsCountOld);
    }

    @Test
    @Tag("regression")
    @Description("Test tries to delete a sector that has a sub-sector linked to it in the Database.")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteSectorWithSubSectorTest() {
        int sectorsCountOld = SectorDbHelper.getSectorsCount();

        SectorDb sectorDb = SectorDb.builder()
                .sectorCode(SECTOR_CODE)
                .sectorName(SECTOR_NAME)
                .build();
        int rowsInserted = SectorDbHelper.insertSector(sectorDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        SubSectorDb subSectorDb = SubSectorDb.builder()
                .sectorCode(SECTOR_CODE)
                .subSectorCode(GeneratorBuilder.generateTestCode())
                .subSectorName(GeneratorBuilder.generateString(10))
                .build();
        rowsInserted = SubSectorDbHelper.insertSubSector(subSectorDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        DeleteApiReqHelper.deleteSectorAndValidate(SECTOR_CODE, SC_SERVER_ERROR);

        int sectorsCountNew = SectorDbHelper.getSectorsCount();
        CommonDbAssertions.checkCounts(sectorsCountNew - 1, sectorsCountOld);

        SubSectorDbHelper.deleteSubSector(subSectorDb.getSubSectorCode());
    }

    private static Stream<Arguments> sectorCodeRequest() {
        return Stream.of(
                Arguments.of((String) null),
                Arguments.of(""),
                Arguments.of(GeneratorBuilder.generateTestCode())
        );
    }
}
