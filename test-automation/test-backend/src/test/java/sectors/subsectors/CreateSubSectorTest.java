package sectors.subsectors;

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
import org.skopintsev.assertions.db.sectors.SubSectorDbAssertions;
import org.skopintsev.database.sectors.SectorDb;
import org.skopintsev.database.sectors.SectorDbHelper;
import org.skopintsev.database.sectors.subsectors.SubSectorDb;
import org.skopintsev.database.sectors.subsectors.SubSectorDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.model.Sector;
import org.skopintsev.model.SubSector;
import org.skopintsev.transport.PostApiReqHelper;

import java.util.stream.Stream;

import static org.skopintsev.constants.Constants.SC_OK;
import static org.skopintsev.constants.Constants.SC_SERVER_ERROR;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateSubSectorTest extends BaseSubSectorTest {

    final String SUB_SECTOR_CODE = GeneratorBuilder.generateTestCode();
    final String SUB_SECTOR_NAME = GeneratorBuilder.generateString(10);

    @Test
    @Tag("regression")
    @Description("Test uses API to post a sub-sector that is already present in the Database.")
    @Severity(SeverityLevel.CRITICAL)
    public void createSubSectorAlreadyExistsTest() {
        SubSector subSector = SubSector.builder()
                .sectorCode(BASE_SECTOR_CODE)
                .subSectorCode(SUB_SECTOR_CODE)
                .subSectorName(SUB_SECTOR_NAME)
                .build();
        PostApiReqHelper.saveSubSectorAndValidate(subSector, SC_OK);
        int subSectorsCountOld = SubSectorDbHelper.getSubSectorsCount();

        PostApiReqHelper.saveSubSectorAndValidate(subSector, SC_SERVER_ERROR);
        int subSectorsCountNew = SubSectorDbHelper.getSubSectorsCount();
        CommonDbAssertions.checkCounts(subSectorsCountNew, subSectorsCountOld);
    }

    @ParameterizedTest(name = "[{index}] subSectorCode = {0}")
    @ValueSource(strings = {""})
    @NullSource
    @Tag("regression")
    @Description(
            """
            Test uses API to post a sub-sector:
            1) with sub-sector code = null,
            2) with sub-sector code = empty string.
            """)
    @Severity(SeverityLevel.CRITICAL)
    public void createSubSectorWithInvalidCodeTest(String subSectorCode) {
        int subSectorsCountOld = SubSectorDbHelper.getSubSectorsCount();

        SubSector subSector = SubSector.builder()
                .sectorCode(BASE_SECTOR_CODE)
                .subSectorCode(subSectorCode)
                .subSectorName(SUB_SECTOR_NAME)
                .build();
        PostApiReqHelper.saveSubSectorAndValidate(subSector, SC_SERVER_ERROR);

        int subSectorsCountNew = SubSectorDbHelper.getSubSectorsCount();
        CommonDbAssertions.checkCounts(subSectorsCountNew, subSectorsCountOld);
    }

    @ParameterizedTest(name = "[{index}] subSectorCode = {0}")
    @MethodSource("subSectorCodeRequest")
    @Tag("regression")
    @Description(
            """
            Test uses API to post a sub-sector:
            1) with sub-sector code that needs to be trimmed,
            2) with sub-sector code that should be modified to upper case.
            """)
    @Severity(SeverityLevel.CRITICAL)
    public void createSubSectorCodeTrimUppercaseTest(String subSectorCode) {
        int subSectorsCountOld = SubSectorDbHelper.getSubSectorsCount();
        String subSectorCodeTrimmedUppercase = subSectorCode.trim().toUpperCase();

        SubSector subSector = SubSector.builder()
                .sectorCode(BASE_SECTOR_CODE)
                .subSectorCode(subSectorCode)
                .subSectorName(SUB_SECTOR_NAME)
                .build();
        PostApiReqHelper.saveSubSectorAndValidate(subSector, SC_OK);

        SubSectorDb subSectorDb = SubSectorDbHelper.selectSubSectorByCode(subSectorCodeTrimmedUppercase);
        SubSectorDbAssertions.checkSubSectorPresence(subSectorDb, true);

        int subSectorsCountNew = SubSectorDbHelper.getSubSectorsCount();
        CommonDbAssertions.checkCounts(subSectorsCountNew - 1, subSectorsCountOld);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to post a sub-sector with name already present in the Database.")
    @Severity(SeverityLevel.CRITICAL)
    public void createSubSectorNameAlreadyExistsTest() {
        SubSectorDb subSectorDb = SubSectorDb.builder()
                .sectorCode(BASE_SECTOR_CODE)
                .subSectorCode(SUB_SECTOR_CODE)
                .subSectorName(SUB_SECTOR_NAME)
                .build();
        int rowsInserted = SubSectorDbHelper.insertSubSector(subSectorDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        int subSectorsCountOld = SubSectorDbHelper.getSubSectorsCount();

        SubSector subSectorApiSameName = SubSector.builder()
                .sectorCode(BASE_SECTOR_CODE)
                .subSectorCode(GeneratorBuilder.generateTestCode())
                .subSectorName(SUB_SECTOR_NAME)
                .build();
        PostApiReqHelper.saveSubSectorAndValidate(subSectorApiSameName, SC_SERVER_ERROR);

        int subSectorsCountNew = SubSectorDbHelper.getSubSectorsCount();
        CommonDbAssertions.checkCounts(subSectorsCountNew, subSectorsCountOld);
    }

    @ParameterizedTest(name = "[{index}] subSectorName = {0}")
    @ValueSource(strings = {""})
    @NullSource
    @Tag("regression")
    @Description(
            """
            Test uses API to post a sub-sector:
            1) with sub-sector name = null,
            2) with sub-sector name = empty string.
            """)
    @Severity(SeverityLevel.CRITICAL)
    public void createSubSectorWithInvalidNameTest(String subSectorName) {
        int subSectorsCountOld = SubSectorDbHelper.getSubSectorsCount();

        SubSector subSectorApi = SubSector.builder()
                .sectorCode(BASE_SECTOR_CODE)
                .subSectorCode(SUB_SECTOR_CODE)
                .subSectorName(subSectorName)
                .build();
        PostApiReqHelper.saveSubSectorAndValidate(subSectorApi, SC_SERVER_ERROR);

        int subSectorsCountNew = SubSectorDbHelper.getSubSectorsCount();
        CommonDbAssertions.checkCounts(subSectorsCountNew, subSectorsCountOld);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to post a sub-sector with sub-sector name that needs to be trimmed.")
    @Severity(SeverityLevel.CRITICAL)
    public void createSubSectorNameTrimTest() {
        int subSectorsCountOld = SubSectorDbHelper.getSubSectorsCount();

        String subSectorNameToTrim = " " + GeneratorBuilder.generateString(10) + " ";
        String subSectorNameTrimmed = subSectorNameToTrim.trim();

        SubSector subSectorApi = SubSector.builder()
                .sectorCode(BASE_SECTOR_CODE)
                .subSectorCode(SUB_SECTOR_CODE)
                .subSectorName(subSectorNameToTrim)
                .build();
        PostApiReqHelper.saveSubSectorAndValidate(subSectorApi, SC_OK);

        SubSectorDb subSectorDb = SubSectorDbHelper.selectSubSectorByCode(SUB_SECTOR_CODE);
        SubSectorDbAssertions.checkSubSectorPresence(subSectorDb, true);
        SubSectorDbAssertions.checkSubSectorField("subSectorName", subSectorDb.getSubSectorName(),
                subSectorNameTrimmed);

        int subSectorsCountNew = SubSectorDbHelper.getSubSectorsCount();
        CommonDbAssertions.checkCounts(subSectorsCountNew - 1, subSectorsCountOld);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to post a sub-sector with sector that is absent in the Database.")
    @Severity(SeverityLevel.CRITICAL)
    public void createSubSectorWithAbsentSectorTest() {
        int subSectorsCountOld = SubSectorDbHelper.getSubSectorsCount();

        SubSector subSectorApi = SubSector.builder()
                .sectorCode(GeneratorBuilder.generateTestCode())
                .subSectorCode(SUB_SECTOR_CODE)
                .subSectorName(SUB_SECTOR_NAME)
                .build();
        PostApiReqHelper.saveSubSectorAndValidate(subSectorApi, SC_SERVER_ERROR);

        SubSectorDb subSectorDb = SubSectorDbHelper.selectSubSectorByCode(SUB_SECTOR_CODE);
        SubSectorDbAssertions.checkSubSectorPresence(subSectorDb, false);

        int subSectorsCountNew = SubSectorDbHelper.getSubSectorsCount();
        CommonDbAssertions.checkCounts(subSectorsCountNew, subSectorsCountOld);
    }

    private static Stream<Arguments> subSectorCodeRequest() {
        return Stream.of(
                Arguments.of(" " + GeneratorBuilder.generateTestCode() + " "),
                Arguments.of(GeneratorBuilder.generateTestCode().toLowerCase())
        );
    }
}
