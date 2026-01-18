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
}
