package org.skopintsev.districts_page;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.skopintsev.assertions.common.elements.ButtonElementAssertions;
import org.skopintsev.assertions.common.windows.ModalWindowAssertions;
import org.skopintsev.assertions.district.DistrictMapWindowAssertions;
import org.skopintsev.models.api.District;
import org.skopintsev.steps.district.DistrictCardSteps;
import org.skopintsev.transport.PostApiResponseHelper;
import org.skopintsev.util.GeneratorBuilder;

import java.util.List;

public class DistrictMapTest extends BaseDistrictTest {

    @Test
    @Tag("smoke")
    @Description("Test checks the display of district map when it was found.")
    @Severity(SeverityLevel.NORMAL)
    public void districtMapFoundTest() {
        final List<District> districts = List.of(
                District.builder()
                        .districtCode("D-1")
                        .districtName(GeneratorBuilder.generateString(10))
                        .build()
        );
        PostApiResponseHelper.stubGetDistricts(districts);
        Selenide.refresh();

        DistrictCardSteps.clickDetailedMapBtn();

        ModalWindowAssertions.checkHeaderText(districts.get(0).getDistrictName() + " - Detailed Map");
        ModalWindowAssertions.checkModalBodyMinHeight(400);
        ModalWindowAssertions.checkModalBodyMinWidth(600);
        DistrictMapWindowAssertions.checkDistrictMapImageIsVisible(true);
        ButtonElementAssertions.checkOkBtnIsVisible();
        ButtonElementAssertions.checkOkBtnEnabled(true);
        ButtonElementAssertions.checkCrossCloseBtnIsVisible();
        ButtonElementAssertions.checkCrossCloseBtnEnabled(true);
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the display of district map when it was NOT found.")
    @Severity(SeverityLevel.NORMAL)
    public void districtMapNotFoundTest() {
        DistrictCardSteps.clickDetailedMapBtn();

        String districtName = BASE_DISTRICTS_LIST.get(0).getDistrictName();

        ModalWindowAssertions.checkHeaderText(districtName + " - Detailed Map");
        ModalWindowAssertions.checkModalBodyMinHeight(400);
        ModalWindowAssertions.checkModalBodyMinWidth(600);
        DistrictMapWindowAssertions.checkDistrictMapImageIsVisible(false);
        DistrictMapWindowAssertions.checkDistrictMapBannerText("MAP UNAVAILABLE");
        DistrictMapWindowAssertions.checkDistrictMapDescriptionText("Cartographic data for "
                + districtName + " is currently classified");
        ButtonElementAssertions.checkOkBtnIsVisible();
        ButtonElementAssertions.checkOkBtnEnabled(true);
        ButtonElementAssertions.checkCrossCloseBtnIsVisible();
        ButtonElementAssertions.checkCrossCloseBtnEnabled(true);
    }
}
