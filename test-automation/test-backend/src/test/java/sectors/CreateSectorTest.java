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
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.assertions.db.sectors.SectorDbAssertions;
import org.skopintsev.database.sectors.SectorDb;
import org.skopintsev.database.sectors.SectorDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.model.Sector;
import org.skopintsev.transport.PostApiReqHelper;

import java.util.stream.Stream;

import static org.skopintsev.constants.Constants.SC_OK;
import static org.skopintsev.constants.Constants.SC_SERVER_ERROR;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateSectorTest extends BaseSectorTest {

    final String SECTOR_CODE = GeneratorBuilder.generateTestCode();
    final String SECTOR_NAME = GeneratorBuilder.generateString(10);

    @Test
    @Tag("regression")
    @Description("Test uses API to post a Sector that is already present in the Database.")
    @Severity(SeverityLevel.CRITICAL)
    public void createSectorAlreadyExistsTest() {
        Sector sectorApi = Sector.builder()
                .sectorCode(SECTOR_CODE)
                .sectorName(SECTOR_NAME)
                .build();
        PostApiReqHelper.saveSectorAndValidate(sectorApi, SC_OK);
        int sectorsCountOld = SectorDbHelper.getSectorsCount();

        PostApiReqHelper.saveSectorAndValidate(sectorApi, SC_SERVER_ERROR);
        int sectorsCountNew = SectorDbHelper.getSectorsCount();
        CommonDbAssertions.checkCounts(sectorsCountNew, sectorsCountOld);
    }

    @ParameterizedTest(name = "[{index}] sectorCode = {0}")
    @ValueSource(strings = {"", " "})
    @NullSource
    @Tag("regression")
    @Description(
            """
            Test uses API to post a Sector:
            1) with code = null,
            2) with code = empty string,
            3) with code = whitespace.
            """)
    @Severity(SeverityLevel.CRITICAL)
    public void createSectorWithInvalidCodeTest(String sectorCode) {
        int sectorsCountOld = SectorDbHelper.getSectorsCount();

        Sector sectorApi = Sector.builder()
                .sectorCode(sectorCode)
                .sectorName(SECTOR_NAME)
                .build();
        PostApiReqHelper.saveSectorAndValidate(sectorApi, SC_SERVER_ERROR);

        int sectorsCountNew = SectorDbHelper.getSectorsCount();
        CommonDbAssertions.checkCounts(sectorsCountNew, sectorsCountOld);
    }

    @ParameterizedTest(name = "[{index}] sectorCode = {0}")
    @MethodSource("sectorCodeRequest")
    @Tag("regression")
    @Description(
            """
            Test uses API to post a Sector:
            1) with sector code that needs to be trimmed,
            2) with sector code that should be modified to upper case.
            """)
    @Severity(SeverityLevel.CRITICAL)
    public void createSectorCodeTrimUppercaseTest(String sectorCode) {
        int sectorsCountOld = SectorDbHelper.getSectorsCount();
        String sectorCodeTrimmedUppercase = sectorCode.trim().toUpperCase();

        Sector sectorApi = Sector.builder()
                .sectorCode(sectorCode)
                .sectorName(SECTOR_NAME)
                .build();
        PostApiReqHelper.saveSectorAndValidate(sectorApi, SC_OK);

        SectorDb sectorDb = SectorDbHelper.selectSectorByCode(sectorCodeTrimmedUppercase);
        SectorDbAssertions.checkSectorPresence(sectorDb, true);

        int sectorsCountNew = SectorDbHelper.getSectorsCount();
        CommonDbAssertions.checkCounts(sectorsCountNew - 1, sectorsCountOld);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to post a Sector with sector name already present in the Database.")
    @Severity(SeverityLevel.CRITICAL)
    public void createSectorNameAlreadyExistsTest() {
        SectorDb sectorDb = SectorDb.builder()
                .sectorCode(SECTOR_CODE)
                .sectorName(SECTOR_NAME)
                .build();
        int rowsInserted = SectorDbHelper.insertSector(sectorDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        int sectorsCountOld = SectorDbHelper.getSectorsCount();

        Sector sectorApiSameName = Sector.builder()
                .sectorCode(GeneratorBuilder.generateTestCode())
                .sectorName(SECTOR_NAME)
                .build();
        PostApiReqHelper.saveSectorAndValidate(sectorApiSameName, SC_SERVER_ERROR);

        int sectorsCountNew = SectorDbHelper.getSectorsCount();
        CommonDbAssertions.checkCounts(sectorsCountNew, sectorsCountOld);
    }

    @ParameterizedTest(name = "[{index}] sectorName = {0}")
    @ValueSource(strings = {"", " "})
    @NullSource
    @Tag("regression")
    @Description(
            """
            Test uses API to post a Sector:
            1) with sector name = null,
            2) with sector name = empty string,
            3) with sector name = whitespace.
            """)
    @Severity(SeverityLevel.CRITICAL)
    public void createSectorWithInvalidNameTest(String sectorName) {
        int sectorsCountOld = SectorDbHelper.getSectorsCount();

        Sector sectorApi = Sector.builder()
                .sectorCode(SECTOR_CODE)
                .sectorName(sectorName)
                .build();
        PostApiReqHelper.saveSectorAndValidate(sectorApi, SC_SERVER_ERROR);

        int sectorsCountNew = SectorDbHelper.getSectorsCount();
        CommonDbAssertions.checkCounts(sectorsCountNew, sectorsCountOld);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to post a Sector with sector name that needs to be trimmed.")
    @Severity(SeverityLevel.CRITICAL)
    public void createSectorNameTrimTest() {
        int sectorsCountOld = SectorDbHelper.getSectorsCount();

        String sectorNameToTrim = " " + GeneratorBuilder.generateString(10) + " ";
        String sectorNameTrimmed = sectorNameToTrim.trim();

        Sector sectorApi = Sector.builder()
                .sectorCode(SECTOR_CODE)
                .sectorName(sectorNameToTrim)
                .build();
        PostApiReqHelper.saveSectorAndValidate(sectorApi, SC_OK);

        SectorDb sectorDb = SectorDbHelper.selectSectorByCode(SECTOR_CODE);
        SectorDbAssertions.checkSectorPresence(sectorDb, true);
        SectorDbAssertions.checkSectorField("sectorName", sectorDb.getSectorName(), sectorNameTrimmed);

        int sectorsCountNew = SectorDbHelper.getSectorsCount();
        CommonDbAssertions.checkCounts(sectorsCountNew - 1, sectorsCountOld);
    }

    private static Stream<Arguments> sectorCodeRequest() {
        return Stream.of(
                Arguments.of(" " + GeneratorBuilder.generateTestCode() + " "),
                Arguments.of(GeneratorBuilder.generateTestCode().toLowerCase())
        );
    }
}
