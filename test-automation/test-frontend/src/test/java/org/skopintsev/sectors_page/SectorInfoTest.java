package org.skopintsev.sectors_page;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.skopintsev.assertions.common.elements.ButtonElementAssertions;
import org.skopintsev.assertions.sector.SectorCardAssertions;
import org.skopintsev.assertions.sector.SectorsPageAssertions;
import org.skopintsev.models.api.sector.SubSector;
import org.skopintsev.transport.PostApiResponseHelper;

import java.util.Collections;

public class SectorInfoTest extends BaseSectorTest {

    @Test
    @Tag("smoke")
    @Description("Test checks the display of Sectors page information.")
    @Severity(SeverityLevel.NORMAL)
    public void sectorsPageInfoTest() {
        SectorsPageAssertions.checkPageTitleText("Economic Sub-Sectors");
        SectorsPageAssertions.checkSubSectorsCountLabelText("TOTAL SUB-SECTORS: ");
        SectorsPageAssertions.checkSubSectorsCountValueText(BASE_SUB_SECTORS_LIST.size());
        SectorsPageAssertions.checkSectorsCountLabelText("TOTAL SECTORS: ");
        SectorsPageAssertions.checkSectorsCountValueText(BASE_SECTORS_LIST.size());
        ButtonElementAssertions.checkRetryBtnExists(false);
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the display of single sector card information.")
    @Severity(SeverityLevel.NORMAL)
    public void sectorCardInfoTest() {
        SubSector subSector = BASE_SUB_SECTORS_LIST.get(0);

        SectorCardAssertions.checkSubSectorName(subSector.getSubSectorName());
        SectorCardAssertions.checkSubSectorCodeLabelText("SUB-SECTOR ID: ");
        SectorCardAssertions.checkSubSectorCodeValueText(subSector.getSubSectorCode());
        SectorCardAssertions.checkSectorNameLabelText("SECTOR: ");
        SectorCardAssertions.checkSectorNameValueText(getSectorName(subSector.getSectorCode()));
        SectorCardAssertions.checkInfoBtnText("INFO");
    }

    @Test
    @Tag("regression")
    @Description("Test checks the display of the Sectors page in case of empty response list.")
    @Severity(SeverityLevel.NORMAL)
    public void sectorsEmptyResponseTest() {
        PostApiResponseHelper.stubGetSectors(Collections.emptyList());
        PostApiResponseHelper.stubGetSubSectors(Collections.emptyList());
        Selenide.refresh();

        SectorsPageAssertions.checkPageTitleText("No Sectors Found");
        ButtonElementAssertions.checkRetryBtnExists(false);
    }

    @Test
    @Tag("regression")
    @Description("Test checks the display of the Sectors page when no sectors were found.")
    @Severity(SeverityLevel.NORMAL)
    public void sectorsNotFoundTest() {
        PostApiResponseHelper.stubGetSectorsNotFound(Collections.emptyList());
        PostApiResponseHelper.stubGetSubSectorsNotFound(Collections.emptyList());
        Selenide.refresh();

        SectorsPageAssertions.checkPageTitleText("Failed to Load Sectors");
        ButtonElementAssertions.checkRetryBtnExists(true);
        ButtonElementAssertions.checkRetryBtnIsVisible();
        ButtonElementAssertions.checkRetryBtnEnabled(true);
    }

    @Test
    @Tag("regression")
    @Description("Test checks the display of the Sectors page in case of server error response.")
    @Severity(SeverityLevel.NORMAL)
    public void sectorsServerErrorTest() {
        PostApiResponseHelper.stubGetSectorsServerError(Collections.emptyList());
        PostApiResponseHelper.stubGetSubSectorsServerError(Collections.emptyList());
        Selenide.refresh();

        SectorsPageAssertions.checkPageTitleText("Failed to Load Sectors");
        ButtonElementAssertions.checkRetryBtnExists(true);
        ButtonElementAssertions.checkRetryBtnIsVisible();
        ButtonElementAssertions.checkRetryBtnEnabled(true);
    }
}
