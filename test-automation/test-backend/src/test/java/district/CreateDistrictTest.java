package district;

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
import org.skopintsev.assertions.db.DistrictDbAssertions;
import org.skopintsev.database.districts.DistrictDb;
import org.skopintsev.database.districts.DistrictDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.model.District;
import org.skopintsev.transport.PostApiReqHelper;


import java.util.stream.Stream;

import static org.skopintsev.constants.Constants.SC_OK;
import static org.skopintsev.constants.Constants.SC_SERVER_ERROR;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateDistrictTest extends BaseDistrictTest {

    static final String DISTRICT_CODE = GeneratorBuilder.generateTestCode();
    final String DISTRICT_NAME = GeneratorBuilder.generateString(10);

    @Test
    @Tag("regression")
    @Description("Test uses API to post a District that is already present in the Database.")
    @Severity(SeverityLevel.CRITICAL)
    public void createDistrictAlreadyExistsTest() {
        District districtApi = District.builder()
                .districtCode(DISTRICT_CODE)
                .districtName(DISTRICT_NAME)
                .build();
        PostApiReqHelper.saveDistrictAndValidate(districtApi, SC_OK);
        int districtsCountOld = DistrictDbHelper.getDistrictsCount();

        PostApiReqHelper.saveDistrictAndValidate(districtApi, SC_SERVER_ERROR);
        int districtsCountNew = DistrictDbHelper.getDistrictsCount();
        CommonDbAssertions.checkCounts(districtsCountNew, districtsCountOld);
    }

    @ParameterizedTest(name = "[{index}] districtCode = {0}")
    @ValueSource(strings = {"", " "})
    @NullSource
    @Tag("regression")
    @Description(
        """
        Test uses API to post a District:
        1) with district code = null,
        2) with district code = empty string,
        3) with district code = whitespace.
        """)
    @Severity(SeverityLevel.CRITICAL)
    public void createDistrictWithInvalidCodeTest(String districtCode) {
        int districtsCountOld = DistrictDbHelper.getDistrictsCount();

        District districtApi = District.builder()
                .districtCode(districtCode)
                .districtName(DISTRICT_NAME)
                .build();
        PostApiReqHelper.saveDistrictAndValidate(districtApi, SC_SERVER_ERROR);

        int districtsCountNew = DistrictDbHelper.getDistrictsCount();
        CommonDbAssertions.checkCounts(districtsCountNew, districtsCountOld);
    }

    @ParameterizedTest(name = "[{index}] districtCode = {0}")
    @MethodSource("districtCodeRequest")
    @Tag("regression")
    @Description(
        """
        Test uses API to post a District:
        1) with district code that needs to be trimmed,
        2) with district code that should be modified to upper case,
        3) with district code in mixed case.
        """)
    @Severity(SeverityLevel.CRITICAL)
    public void createDistrictCodeTrimUppercaseTest(String districtCode) {
        int districtsCountOld = DistrictDbHelper.getDistrictsCount();
        String districtCodeTrimmedUppercase = districtCode.trim().toUpperCase();

        District districtApi = District.builder()
                .districtCode(districtCode)
                .districtName(DISTRICT_NAME)
                .build();
        PostApiReqHelper.saveDistrictAndValidate(districtApi, SC_OK);

        DistrictDb districtDb = DistrictDbHelper.selectDistrictByCode(districtCodeTrimmedUppercase);
        DistrictDbAssertions.checkDistrictPresence(districtDb, true);

        int districtsCountNew = DistrictDbHelper.getDistrictsCount();
        CommonDbAssertions.checkCounts(districtsCountNew, districtsCountOld + 1);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to post a District with district name already present in the Database.")
    @Severity(SeverityLevel.CRITICAL)
    public void createDistrictNameAlreadyExistsTest() {
        DistrictDb districtDb = DistrictDb.builder()
                .districtCode(DISTRICT_CODE)
                .districtName(DISTRICT_NAME)
                .build();
        int rowsInserted = DistrictDbHelper.insertDistrict(districtDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        int districtsCountOld = DistrictDbHelper.getDistrictsCount();

        District districtApiSameName = District.builder()
                .districtCode(GeneratorBuilder.generateTestCode())
                .districtName(DISTRICT_NAME)
                .build();
        PostApiReqHelper.saveDistrictAndValidate(districtApiSameName, SC_SERVER_ERROR);

        int districtsCountNew = DistrictDbHelper.getDistrictsCount();
        CommonDbAssertions.checkCounts(districtsCountNew, districtsCountOld);
    }

    @ParameterizedTest(name = "[{index}] districtName = {0}")
    @ValueSource(strings = {"", " "})
    @NullSource
    @Tag("regression")
    @Description(
        """
        Test uses API to post a District:
        1) with district name = null,
        2) with district name = empty string,
        3) with district name = whitespace.
        """)
    @Severity(SeverityLevel.CRITICAL)
    public void createDistrictWithInvalidNameTest(String districtName) {
        int districtsCountOld = DistrictDbHelper.getDistrictsCount();

        District districtApi = District.builder()
                .districtCode(DISTRICT_CODE)
                .districtName(districtName)
                .build();
        PostApiReqHelper.saveDistrictAndValidate(districtApi, SC_SERVER_ERROR);

        int districtsCountNew = DistrictDbHelper.getDistrictsCount();
        CommonDbAssertions.checkCounts(districtsCountNew, districtsCountOld);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to post a District with district name that needs to be trimmed.")
    @Severity(SeverityLevel.CRITICAL)
    public void createDistrictNameTrimTest() {
        int districtsCountOld = DistrictDbHelper.getDistrictsCount();

        String districtNameToTrim = " " + GeneratorBuilder.generateString(10) + " ";
        String districtNameTrimmed = districtNameToTrim.trim();

        District districtApi = District.builder()
                .districtCode(DISTRICT_CODE)
                .districtName(districtNameToTrim)
                .build();
        PostApiReqHelper.saveDistrictAndValidate(districtApi, SC_OK);

        DistrictDb districtDb = DistrictDbHelper.selectDistrictByCode(DISTRICT_CODE);
        DistrictDbAssertions.checkDistrictPresence(districtDb, true);
        DistrictDbAssertions.checkDistrictName(districtDb.getDistrictName(), districtNameTrimmed);

        int districtsCountNew = DistrictDbHelper.getDistrictsCount();
        CommonDbAssertions.checkCounts(districtsCountNew, districtsCountOld + 1);
    }

    private static Stream<Arguments> districtCodeRequest() {
        return Stream.of(
                Arguments.of(" " + DISTRICT_CODE + " "),
                Arguments.of(GeneratorBuilder.generateTestCode().toLowerCase()),
                Arguments.of(DISTRICT_CODE.charAt(0) + DISTRICT_CODE.substring(1).toLowerCase())
        );
    }
}
