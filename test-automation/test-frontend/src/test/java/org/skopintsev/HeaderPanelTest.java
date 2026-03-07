package org.skopintsev;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.skopintsev.steps.common.HeaderPanelSteps;

public class HeaderPanelTest extends BaseTest {

    @Test
    @Tag("smoke")
    @Description("Test checks the display of all the header panel tabs.")
    @Severity(SeverityLevel.BLOCKER)
    public void headerPanelTabsTest() {

        // todo: !!! mock empty stubs for all the requests !!!
        //  PostApiResponseHelper.stubGetVaults(Collections.emptyList());
        //  PostApiResponseHelper.stubGetClients(Collections.emptyList());
        //  PostApiResponseHelper.stubGetCurrencies(Collections.emptyList());
        //  PostApiResponseHelper.stubGetSocialRanks(Collections.emptyList());
        //  PostApiResponseHelper.stubGetDistricts(Collections.emptyList());
        //  PostApiResponseHelper.stubGetSectors(Collections.emptyList());
        //  PostApiResponseHelper.stubGetSubSectors(Collections.emptyList());

        // Clients page tab
        HeaderPanelSteps.clickClientsPageBtn();
        // todo: UrlAssertions.???
        // todo: HeaderPanelAssertions.???

        // Social Ranks page tab
        HeaderPanelSteps.clickSocialRanksPageBtn();
        // todo: UrlAssertions.???
        // todo: HeaderPanelAssertions.???

        // Districts page tab
        HeaderPanelSteps.clickDistrictsPageBtn();
        // todo: UrlAssertions.???
        // todo: HeaderPanelAssertions.???

        // Currencies page tab
        HeaderPanelSteps.clickCurrenciesPageBtn();
        // todo: UrlAssertions.???
        // todo: HeaderPanelAssertions.???

        // Sectors page tab
        HeaderPanelSteps.clickSectorsPageBtn();
        // todo: UrlAssertions.???
        // todo: HeaderPanelAssertions.???

        // Vaults page tab
        HeaderPanelSteps.clickVaultsPageBtn();
        // todo: UrlAssertions.???
        // todo: HeaderPanelAssertions.???
    }

    // todo: method to check that all other tabs are inactive except = SelenideElement ===> reuse it !!!

}
