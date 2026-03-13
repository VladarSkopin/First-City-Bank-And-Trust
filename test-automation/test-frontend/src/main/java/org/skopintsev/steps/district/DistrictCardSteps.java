package org.skopintsev.steps.district;

import io.qameta.allure.Step;
import org.skopintsev.models.gui.districts.DistrictCard;

public class DistrictCardSteps {

    @Step("Click 'DETAILED MAP' button.")
    public static void clickDetailedMapBtn() {
        DistrictCard.getDetailedMapBtn().click();
    }

}
