package org.skopintsev;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.skopintsev.assertions.UrlAssertions;
import org.skopintsev.assertions.common.HeaderPanelAssertions;
import org.skopintsev.models.gui.common.HeaderPanel;
import org.skopintsev.steps.common.HeaderPanelSteps;
import org.skopintsev.transport.PostApiResponseHelper;

import java.util.Collections;

import static com.codeborne.selenide.WebDriverRunner.url;

public class HeaderPanelTest extends BaseTest {

    @Test
    @Tag("smoke")
    @Description("Test checks the display of all the header panel tabs.")
    @Severity(SeverityLevel.BLOCKER)
    public void headerPanelTabsTest() {
        //  todo: PostApiResponseHelper.stubGetVaults(Collections.emptyList());
        //  todo: PostApiResponseHelper.stubGetClients(Collections.emptyList());
        PostApiResponseHelper.stubGetCurrencies(Collections.emptyList());
        PostApiResponseHelper.stubGetSocialRanks(Collections.emptyList());
        PostApiResponseHelper.stubGetDistricts(Collections.emptyList());
        PostApiResponseHelper.stubGetSectors(Collections.emptyList());
        PostApiResponseHelper.stubGetSubSectors(Collections.emptyList());

        // Clients page tab
        HeaderPanelSteps.clickClientsPageBtn();
        UrlAssertions.checkClientsUrl(url());
        HeaderPanelAssertions.checkHeaderPanelTabActive(HeaderPanel.getClientsPageBtn());
        HeaderPanelAssertions.verifyAllOtherTabsInactive(HeaderPanel.getClientsPageBtn());

        // Social Ranks page tab
        HeaderPanelSteps.clickSocialRanksPageBtn();
        UrlAssertions.checkSocialRanksUrl(url());
        HeaderPanelAssertions.checkHeaderPanelTabActive(HeaderPanel.getSocialRanksPageBtn());
        HeaderPanelAssertions.verifyAllOtherTabsInactive(HeaderPanel.getSocialRanksPageBtn());

        // Districts page tab
        HeaderPanelSteps.clickDistrictsPageBtn();
        UrlAssertions.checkDistrictsUrl(url());
        HeaderPanelAssertions.checkHeaderPanelTabActive(HeaderPanel.getDistrictsPageBtn());
        HeaderPanelAssertions.verifyAllOtherTabsInactive(HeaderPanel.getDistrictsPageBtn());

        // Currencies page tab
        HeaderPanelSteps.clickCurrenciesPageBtn();
        UrlAssertions.checkCurrenciesUrl(url());
        HeaderPanelAssertions.checkHeaderPanelTabActive(HeaderPanel.getCurrenciesPageBtn());
        HeaderPanelAssertions.verifyAllOtherTabsInactive(HeaderPanel.getCurrenciesPageBtn());

        // Sectors page tab
        HeaderPanelSteps.clickSectorsPageBtn();
        UrlAssertions.checkSectorsUrl(url());
        HeaderPanelAssertions.checkHeaderPanelTabActive(HeaderPanel.getSectorsPageBtn());
        HeaderPanelAssertions.verifyAllOtherTabsInactive(HeaderPanel.getSectorsPageBtn());

        // Vaults page tab
        HeaderPanelSteps.clickVaultsPageBtn();
        UrlAssertions.checkVaultsUrl(url());
        HeaderPanelAssertions.checkHeaderPanelTabActive(HeaderPanel.getVaultsPageBtn());
        HeaderPanelAssertions.verifyAllOtherTabsInactive(HeaderPanel.getVaultsPageBtn());
    }
}
