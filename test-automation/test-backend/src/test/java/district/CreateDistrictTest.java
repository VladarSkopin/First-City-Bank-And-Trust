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
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.assertions.db.DistrictDbAssertions;
import org.skopintsev.database.district.DistrictDb;
import org.skopintsev.database.district.DistrictDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.model.District;
import org.skopintsev.transport.PostApiReqHelper;


import java.util.stream.Stream;

import static org.skopintsev.constants.Constants.SC_OK;
import static org.skopintsev.constants.Constants.SC_SERVER_ERROR;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateDistrictTest extends BaseDistrictTest {

    String districtCode = GeneratorBuilder.generateTestCode();
    String districtName = GeneratorBuilder.generateString(10);

    @Test
    @Tag("regression")
    @Description("Test uses API to post a District that is already present in the Database.")
    @Severity(SeverityLevel.CRITICAL)
    public void createDistrictAlreadyExists() {
        District districtApi = District.builder()
                .districtCode(districtCode)
                .districtName(districtName)
                .build();
        PostApiReqHelper.saveDistrictAndValidate(districtApi, SC_OK);
        int districtsCountOld = DistrictDbHelper.getDistrictsCount();

        PostApiReqHelper.saveDistrictAndValidate(districtApi, SC_SERVER_ERROR);
        int districtsCountNew = DistrictDbHelper.getDistrictsCount();
        CommonDbAssertions.checkCounts(districtsCountNew, districtsCountOld);
    }

    @ParameterizedTest(name = "[{index}] districtCode = {0}")
    @ValueSource(strings = {""})
    @NullSource
    @Tag("regression")
    @Description("""
        Test uses API to post a District:
        1) with district code = null,
        2) with district code = empty string.
        """)
    @Severity(SeverityLevel.CRITICAL)
    public void createDistrictWithInvalidCode(String districtCode) {
        int districtsCountOld = DistrictDbHelper.getDistrictsCount();

        District districtApi = District.builder()
                .districtCode(districtCode)
                .districtName(districtName)
                .build();
        PostApiReqHelper.saveDistrictAndValidate(districtApi, SC_SERVER_ERROR);

        int districtsCountNew = DistrictDbHelper.getDistrictsCount();
        CommonDbAssertions.checkCounts(districtsCountNew, districtsCountOld);
    }

    @ParameterizedTest(name = "[{index}] districtCode = {0}")
    @MethodSource("districtCodeRequest")
    @Tag("regression")
    @Description("""
        Test uses API to post a District:
        1) with district code that needs to be trimmed,
        2) with district code that should be modified to upper case.
        """)
    @Severity(SeverityLevel.CRITICAL)
    public void createDistrictCodeTrimUppercase(String districtCode) {
        String districtCodeTrimmedUppercase = districtCode.trim().toUpperCase();

        District districtApi = District.builder()
                .districtCode(districtCode)
                .districtName(districtName)
                .build();
        PostApiReqHelper.saveDistrictAndValidate(districtApi, SC_OK);

        DistrictDb districtDb = DistrictDbHelper.selectDistrictByCode(districtCodeTrimmedUppercase);
        DistrictDbAssertions.checkDistrictPresence(districtDb, true);
        DistrictDbAssertions.checkDistrictCode(districtDb.getDistrictCode(), districtCodeTrimmedUppercase);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to post a District with district name already present in the Database.")
    @Severity(SeverityLevel.CRITICAL)
    public void createDistrictNameAlreadyExists() {
        District districtApi = District.builder()
                .districtCode(districtCode)
                .districtName(districtName)
                .build();
        PostApiReqHelper.saveDistrictAndValidate(districtApi, SC_OK);

        int districtsCountOld = DistrictDbHelper.getDistrictsCount();

        District districtApiSameName = District.builder()
                .districtCode(GeneratorBuilder.generateTestCode())
                .districtName(districtName)
                .build();
        PostApiReqHelper.saveDistrictAndValidate(districtApiSameName, SC_SERVER_ERROR);

        int districtsCountNew = DistrictDbHelper.getDistrictsCount();
        CommonDbAssertions.checkCounts(districtsCountNew, districtsCountOld);
    }

    @ParameterizedTest(name = "[{index}] districtName = {0}")
    @ValueSource(strings = {""})
    @NullSource
    @Tag("regression")
    @Description("""
        Test uses API to post a District:
        1) with district name = null,
        2) with district name = empty string.
        """)
    @Severity(SeverityLevel.CRITICAL)
    public void createDistrictWithInvalidName(String districtName) {
        int districtsCountOld = DistrictDbHelper.getDistrictsCount();

        District districtApi = District.builder()
                .districtCode(districtCode)
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
    public void createDistrictNameTrim() {
        String districtNameToTrim = " " + GeneratorBuilder.generateString(10) + " ";
        String districtNameTrimmed = districtNameToTrim.trim();

        District districtApi = District.builder()
                .districtCode(districtCode)
                .districtName(districtNameToTrim)
                .build();
        PostApiReqHelper.saveDistrictAndValidate(districtApi, SC_OK);

        DistrictDb districtDb = DistrictDbHelper.selectDistrictByCode(districtCode);
        DistrictDbAssertions.checkDistrictPresence(districtDb, true);
        DistrictDbAssertions.checkDistrictName(districtDb.getDistrictName(), districtNameTrimmed);
    }

    private static Stream<Arguments> districtCodeRequest() {
        return Stream.of(
                Arguments.of(" " + GeneratorBuilder.generateTestCode() + " "),
                Arguments.of(GeneratorBuilder.generateTestCode().toLowerCase())
        );
    }
}
