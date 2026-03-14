package org.skopintsev.districts_page;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.skopintsev.assertions.common.elements.ButtonElementAssertions;
import org.skopintsev.assertions.district.DistrictCardAssertions;
import org.skopintsev.assertions.district.DistrictsPageAssertions;
import org.skopintsev.models.api.District;
import org.skopintsev.transport.PostApiResponseHelper;

import java.util.Collections;

public class DistrictInfoTest extends BaseDistrictTest {

    @Test
    @Tag("smoke")
    @Description("Test checks the display of Districts page information.")
    @Severity(SeverityLevel.NORMAL)
    public void districtsPageInfoTest() {
        DistrictsPageAssertions.checkPageTitleText("City Districts");
        DistrictsPageAssertions.checkDistrictsCountLabel("TOTAL DISTRICTS: ");
        DistrictsPageAssertions.checkDistrictsCountValue(BASE_DISTRICTS_LIST.size());
        ButtonElementAssertions.checkRetryBtnExists(false);
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the display of single district card information.")
    @Severity(SeverityLevel.NORMAL)
    public void districtCardInfoTest() {
        District district = BASE_DISTRICTS_LIST.get(0);

        DistrictCardAssertions.checkDistrictName(district.getDistrictName());
        DistrictCardAssertions.checkDistrictCode(district.getDistrictCode());
        DistrictCardAssertions.checkDetailedMapBtnEnabled();
        DistrictCardAssertions.checkDetailedMapBtnText("DETAILED MAP");
    }

    @Test
    @Tag("regression")
    @Description("Test checks the display of the Districts page in case of empty response list.")
    @Severity(SeverityLevel.NORMAL)
    public void districtsEmptyResponseTest() {
        PostApiResponseHelper.stubGetDistricts(Collections.emptyList());
        Selenide.refresh();

        DistrictsPageAssertions.checkPageTitleText("No Districts Found");
        ButtonElementAssertions.checkRetryBtnExists(false);
    }

    @Test
    @Tag("regression")
    @Description("Test checks the display of the Districts page when no districts were found.")
    @Severity(SeverityLevel.NORMAL)
    public void districtsNotFoundTest() {
        PostApiResponseHelper.stubGetDistrictsNotFound(Collections.emptyList());
        Selenide.refresh();

        DistrictsPageAssertions.checkPageTitleText("Failed to Load Districts");
        ButtonElementAssertions.checkRetryBtnExists(true);
        ButtonElementAssertions.checkRetryBtnIsVisible();
        ButtonElementAssertions.checkRetryBtnEnabled(true);
    }

    @Test
    @Tag("regression")
    @Description("Test checks the display of the Districts page in case of server error response.")
    @Severity(SeverityLevel.NORMAL)
    public void districtsServerErrorTest() {
        PostApiResponseHelper.stubGetDistrictsServerError(Collections.emptyList());
        Selenide.refresh();

        DistrictsPageAssertions.checkPageTitleText("Failed to Load Districts");
        ButtonElementAssertions.checkRetryBtnExists(true);
        ButtonElementAssertions.checkRetryBtnIsVisible();
        ButtonElementAssertions.checkRetryBtnEnabled(true);
    }
}
