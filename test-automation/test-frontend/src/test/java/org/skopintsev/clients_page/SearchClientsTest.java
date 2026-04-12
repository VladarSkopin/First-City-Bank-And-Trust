package org.skopintsev.clients_page;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.skopintsev.assertions.client.ClientsSearchAssertions;
import org.skopintsev.steps.client.ClientSearchPanelSteps;

public class SearchClientsTest extends BaseClientTest {

    // todo: initial values of inputs
    @Test
    @Tag("smoke")
    @Description("Test checks the initial values of the clients search panel.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientsSearchInitialValuesTest() {
        ClientsSearchAssertions.checkSearchBtnEnabled(true);
        ClientsSearchAssertions.checkResetBtnEnabled(true);
    }

    // todo: search by client name
    @Test
    @Tag("smoke")
    @Description("Test checks the search functionality by client name.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientsSearchByNameTest() {
        ClientSearchPanelSteps.clickSearchClientsBtn();
    }

    // todo: search by social rank
    @Test
    @Tag("smoke")
    @Description("Test checks the search functionality by social rank.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientsSearchBySocialRankTest() {
        ClientSearchPanelSteps.clickSearchClientsBtn();
    }

    // todo: search by client type
    @Test
    @Tag("smoke")
    @Description("Test checks the search functionality by client type.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientsSearchByClientTypeTest() {
        ClientSearchPanelSteps.clickSearchClientsBtn();
    }

    // todo: search by sub-sector
    @Test
    @Tag("smoke")
    @Description("Test checks the search functionality by sub-sector.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientsSearchBySubSectorTest() {
        ClientSearchPanelSteps.clickSearchClientsBtn();
    }

    // todo: search by district
    @Test
    @Tag("smoke")
    @Description("Test checks the search functionality by district.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientsSearchByDistrictTest() {
        ClientSearchPanelSteps.clickSearchClientsBtn();
    }

    // todo: search by isBlocked
    @Test
    @Tag("smoke")
    @Description("Test checks the search functionality by isBlocked status.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientsSearchByIsBlockedTest() {
        ClientSearchPanelSteps.clickSearchClientsBtn();
    }

    // todo: search by name + rank + type + sub-sector + district + isBlocked + reset btn
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
        ClientSearchPanelSteps.clickSearchClientsBtn();
    }

}
