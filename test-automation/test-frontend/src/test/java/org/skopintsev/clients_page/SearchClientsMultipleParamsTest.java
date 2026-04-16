package org.skopintsev.clients_page;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.skopintsev.assertions.client.ClientsPageAssertions;
import org.skopintsev.assertions.client.ClientsSearchAssertions;
import org.skopintsev.steps.client.ClientSearchPanelSteps;
import org.skopintsev.steps.common.elements.LoadingSpinnerSteps;

public class SearchClientsMultipleParamsTest extends BaseClientTest {

    @Test
    @Tag("smoke")
    @Description("""
            Test checks the search functionality by multiple parameters:.
            1) by client name,
            2) by social rank,
            3) by client type,
            4) by sub-sector,
            5) by district,
            6) by isBlocked status.
            After performing the search, test checks the "RESET" button functionality.
            """)
    @Severity(SeverityLevel.CRITICAL)
    public void clientsSearchByMultipleParametersTest() {
        // client name XXX AAA AAB AAD AAE AAF
        // social rank R1 R2 R2 R2 R2 R2
        // client types PART PART GOVT GOVT GOVT GOVT
        // sub-sector S1 S1 S1 S2 S2 S2
        // district D1 D1 D1 D1 D2 D2
        // isBlocked false true true true true false
        // search: name = AA, rank = R2, type = GOVT, S2, D2, isBlocked = false
        ClientSearchPanelSteps.typeClientName(BASE_CLIENTS_LIST.get(0).getNameOrTitle());
        ClientSearchPanelSteps.selectOptionSocialRankByVisibleText(BASE_SOCIAL_RANKS_LIST.get(0).getRankName());
        ClientSearchPanelSteps.selectOptionClientTypeByVisibleText(BASE_CLIENT_TYPES_LIST.get(0).getClientTypeName());
        ClientSearchPanelSteps.selectOptionSubSectorByVisibleText(BASE_SUB_SECTORS_LIST.get(0).getSubSectorName());
        ClientSearchPanelSteps.selectOptionDistrictByVisibleText(BASE_DISTRICTS_LIST.get(0).getDistrictName());
        ClientSearchPanelSteps.clickIsBlockedCheckbox();
        ClientSearchPanelSteps.clickSearchClientsBtn();

        ClientsPageAssertions.checkTotalClientsCountValueText(1);




        ClientSearchPanelSteps.clickResetBtn();
        LoadingSpinnerSteps.waitingForPageLoading();

        ClientsPageAssertions.checkTotalClientsCountValueText(10000);
        ClientsSearchAssertions.checkInputClientNameText("");
        ClientsSearchAssertions.checkSocialRanksSelectionText("All Social Ranks");
        ClientsSearchAssertions.checkClientTypesSelectionText("All Client Types");
        ClientsSearchAssertions.checkSubSectorsSelectionText("All Sub‑Sectors");
        ClientsSearchAssertions.checkDistrictSelectionText("All Districts");
        ClientsSearchAssertions.checkIsBlockedLabelText("Blocked only");
        ClientsSearchAssertions.checkIsBlockedCheckboxChecked(false);
        ClientsSearchAssertions.checkSearchBtnEnabled(true);
        ClientsSearchAssertions.checkResetBtnEnabled(true);
    }
}
