package org.skopintsev.sectors_page;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.skopintsev.assertions.common.elements.ButtonElementAssertions;
import org.skopintsev.assertions.common.windows.ModalWindowAssertions;
import org.skopintsev.assertions.sector.SectorDescriptionWindowAssertions;
import org.skopintsev.models.api.sector.SubSector;
import org.skopintsev.steps.sector.SectorCardSteps;

public class SectorDescriptionTest extends BaseSectorTest {

    @Test
    @Tag("smoke")
    @Description("Test checks the display of social rank regulations.")
    @Severity(SeverityLevel.NORMAL)
    public void regulationsModalWindowTest() {
        SubSector subSector = BASE_SUB_SECTORS_LIST.get(0);

        SectorCardSteps.clickInfoBtn();

        ModalWindowAssertions.checkHeaderText(subSector.getSubSectorName() + " Description");
        ModalWindowAssertions.checkModalBodyMinHeight(300);
        ModalWindowAssertions.checkModalBodyMinWidth(600);
        SectorDescriptionWindowAssertions.checkSubSectorCodeText("CODE: " + subSector.getSubSectorCode());
        SectorDescriptionWindowAssertions.checkSectorName("SECTOR: " + getSectorName(subSector.getSectorCode()));
        SectorDescriptionWindowAssertions.checkSubSectorDescription(subSector.getDescription());
        ButtonElementAssertions.checkOkBtnIsVisible();
        ButtonElementAssertions.checkOkBtnEnabled(true);
        ButtonElementAssertions.checkCrossCloseBtnIsVisible();
        ButtonElementAssertions.checkCrossCloseBtnEnabled(true);
    }

}
