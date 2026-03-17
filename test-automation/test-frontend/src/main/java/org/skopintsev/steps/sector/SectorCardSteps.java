package org.skopintsev.steps.sector;

import io.qameta.allure.Step;
import org.skopintsev.models.gui.sector.SectorCard;

public class SectorCardSteps {

    @Step("Click 'INFO' button.")
    public static void clickInfoBtn() {
        SectorCard.getViewDescriptionBtn().click();
    }

}
