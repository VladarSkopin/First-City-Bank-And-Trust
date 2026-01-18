package district;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.skopintsev.assertions.api.DistrictApiAssertions;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.assertions.db.DistrictDbAssertions;
import org.skopintsev.database.district.DistrictDb;
import org.skopintsev.database.district.DistrictDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.model.District;
import org.skopintsev.transport.GetApiReqHelper;
import org.skopintsev.transport.PostApiReqHelper;

import java.util.List;

import static org.skopintsev.constants.Constants.SC_OK;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BasicDistrictTest extends BaseDistrictTest {

    String districtCode = GeneratorBuilder.generateTestCode();
    String districtName = GeneratorBuilder.generateString(3);

    @Test
    @Tag("smoke")
    @Description("Test inserts a new District object into the Database and checks API for the new added district.")
    @Severity(SeverityLevel.BLOCKER)
    public void createDistrictDbTest() {
        DistrictDb newDistrictDb = DistrictDb.builder()
                .districtCode(districtCode)
                .districtName(districtName)
                .build();
        int rowsInserted = DistrictDbHelper.insertDistrict(newDistrictDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        List<District> districts = GetApiReqHelper.getDistrictsAndValidate(SC_OK);
        DistrictApiAssertions.checkNotNullDistricts(districts);

        District newAddedDistrictApi = districts
                .stream()
                .filter(d -> d.getDistrictCode().equals(districtCode))
                .findFirst()
                .orElse(null);
        DistrictDbAssertions.checkDistrictName(newAddedDistrictApi.getDistrictName(), districtName);
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to post a new District object and checks Database for the new added district.")
    @Severity(SeverityLevel.BLOCKER)
    public void createDistrictApiTest() {
        District newAddedDistrictApi = District.builder()
                .districtCode(districtCode)
                .districtName(districtName)
                .build();
        PostApiReqHelper.saveDistrictAndValidate(newAddedDistrictApi, SC_OK);

        List<District> districts = GetApiReqHelper.getDistrictsAndValidate(SC_OK);
        DistrictApiAssertions.checkNotNullDistricts(districts);

        DistrictDb newAddedDistrictDb = DistrictDbHelper.selectDistrictByCode(districtCode);
        DistrictDbAssertions.checkDistrictName(newAddedDistrictDb.getDistrictName(), districtName);
    }
}
