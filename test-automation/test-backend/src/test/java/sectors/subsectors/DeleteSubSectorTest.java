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
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.assertions.db.sectors.SubSectorDbAssertions;
import org.skopintsev.database.sectors.subsectors.SubSectorDb;
import org.skopintsev.database.sectors.subsectors.SubSectorDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.transport.DeleteApiReqHelper;

import java.util.stream.Stream;

import static org.skopintsev.constants.Constants.SC_NOT_FOUND;
import static org.skopintsev.constants.Constants.SC_OK;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeleteSubSectorTest extends BaseSubSectorTest {

    final String SUB_SECTOR_CODE = GeneratorBuilder.generateTestCode();
    final String SUB_SECTOR_NAME = GeneratorBuilder.generateString(10);

    @Test
    @Tag("regression")
    @Description("Test creates a new sub-sector in the Database and uses API to delete it.")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteSubSectorTest() {
        int subSectorsCountOld = SubSectorDbHelper.getSubSectorsCount();

        SubSectorDb subSectorDb = SubSectorDb.builder()
                .sectorCode(BASE_SECTOR_CODE)
                .subSectorCode(SUB_SECTOR_CODE)
                .subSectorName(SUB_SECTOR_NAME)
                .build();
        int rowsInserted = SubSectorDbHelper.insertSubSector(subSectorDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        DeleteApiReqHelper.deleteSubSectorAndValidate(SUB_SECTOR_CODE, SC_OK);

        subSectorDb = SubSectorDbHelper.selectSubSectorByCode(SUB_SECTOR_CODE);
        SubSectorDbAssertions.checkSubSectorPresence(subSectorDb, false);

        int subSectorsCountNew = SubSectorDbHelper.getSubSectorsCount();
        CommonDbAssertions.checkCounts(subSectorsCountNew, subSectorsCountOld);
    }

    @ParameterizedTest(name = "[{index}] subSectorCode = {0}")
    @MethodSource("subSectorCodeRequest")
    @Tag("regression")
    @Description(
        """
        Test uses API to delete a sub-sector:
        1) with code = null,
        2) with code = empty string,
        3) a sub-sector that is absent in the Database.
        """)
    @Severity(SeverityLevel.CRITICAL)
    public void deleteSubSectorNegativeTest(String subSectorCode) {
        int subSectorsCountOld = SubSectorDbHelper.getSubSectorsCount();

        DeleteApiReqHelper.deleteSubSectorAndValidate(subSectorCode, SC_NOT_FOUND);

        int subSectorsCountNew = SubSectorDbHelper.getSubSectorsCount();
        CommonDbAssertions.checkCounts(subSectorsCountNew, subSectorsCountOld);
    }

    private static Stream<Arguments> subSectorCodeRequest() {
        return Stream.of(
                Arguments.of((String) null),
                Arguments.of(""),
                Arguments.of(GeneratorBuilder.generateTestCode())
        );
    }
}
