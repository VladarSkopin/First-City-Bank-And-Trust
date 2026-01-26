package district;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.assertions.db.DistrictDbAssertions;
import org.skopintsev.database.districts.DistrictDb;
import org.skopintsev.database.districts.DistrictDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.transport.DeleteApiReqHelper;

import java.util.stream.Stream;

import static org.skopintsev.constants.Constants.SC_NOT_FOUND;
import static org.skopintsev.constants.Constants.SC_OK;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeleteDistrictTest extends BaseDistrictTest {

    private final String DISTRICT_CODE = GeneratorBuilder.generateTestCode();

    @Test
    @Tag("regression")
    @Description("Test creates a new district in the Database and uses API to delete it.")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteDistrict() {
        int districtsCountOld = DistrictDbHelper.getDistrictsCount();

        DistrictDb districtDb = DistrictDb.builder()
                .districtCode(DISTRICT_CODE)
                .districtName(GeneratorBuilder.generateString(10))
                .build();
        int rowsInserted = DistrictDbHelper.insertDistrict(districtDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        DeleteApiReqHelper.deleteDistrictAndValidate(DISTRICT_CODE, SC_OK);

        districtDb = DistrictDbHelper.selectDistrictByCode(DISTRICT_CODE);
        DistrictDbAssertions.checkDistrictPresence(districtDb, false);

        int districtsCountNew = DistrictDbHelper.getDistrictsCount();
        CommonDbAssertions.checkCounts(districtsCountNew, districtsCountOld);
    }

    @ParameterizedTest(name = "[{index}] districtCode = {0}")
    @MethodSource("districtCodeRequest")
    @Tag("regression")
    @Description(
        """
        Test uses API to delete a district:
        1) with code = null,
        2) with code = empty string,
        3) a district that is absent in the Database.
        """)
    @Severity(SeverityLevel.CRITICAL)
    public void deleteDistrictNegativeTest(String districtCode) {
        int districtsCountOld = DistrictDbHelper.getDistrictsCount();

        DeleteApiReqHelper.deleteDistrictAndValidate(districtCode, SC_NOT_FOUND);

        int districtsCountNew = DistrictDbHelper.getDistrictsCount();
        CommonDbAssertions.checkCounts(districtsCountNew, districtsCountOld);
    }

    private static Stream<Arguments> districtCodeRequest() {
        return Stream.of(
                Arguments.of((String) null),
                Arguments.of(""),
                Arguments.of(GeneratorBuilder.generateTestCode())
        );
    }
}
