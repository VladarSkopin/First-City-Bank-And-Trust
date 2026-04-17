package org.skopintsev.clients_page;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.skopintsev.assertions.client.ClientsPageAssertions;
import org.skopintsev.assertions.client.ClientsSearchAssertions;
import org.skopintsev.models.api.client.Client;
import org.skopintsev.steps.client.ClientSearchPanelSteps;
import org.skopintsev.transport.PostApiResponseHelper;
import org.skopintsev.util.GeneratorBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SearchClientsTest extends BaseClientTest {

    List<Client> CLIENTS = List.of(BASE_CLIENTS_LIST.get(0));

    @Test
    @Tag("smoke")
    @Description("Test checks the initial values of the clients search panel.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientsSearchInitialValuesTest() {
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

    @Test
    @Tag("smoke")
    @Description("Test checks the search functionality by client name.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientsSearchByNameTest() {
        String randomName = GeneratorBuilder.generateString(10);
        Map<String, String> params = new HashMap<>();
        params.put("nameOrTitle", randomName);
        PostApiResponseHelper.stubGetSearchClientsWithParams(params, CLIENTS);
        Selenide.refresh();

        ClientSearchPanelSteps.typeClientName(BASE_CLIENTS_LIST.get(0).getNameOrTitle());
        ClientSearchPanelSteps.clickSearchClientsBtn();
        // todo: check request was sent

        ClientsPageAssertions.checkTotalClientsCountValueText(1);
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the search functionality by social rank.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientsSearchBySocialRankTest() {
        String randomName = GeneratorBuilder.generateString(10);
        Map<String, String> params = new HashMap<>();
        params.put("nameOrTitle", randomName);
        PostApiResponseHelper.stubGetSearchClientsWithParams(params, CLIENTS);
        Selenide.refresh();

        ClientSearchPanelSteps.selectOptionSocialRankByVisibleText(BASE_SOCIAL_RANKS_LIST.get(0).getRankName());
        ClientSearchPanelSteps.clickSearchClientsBtn();
        // todo: check request was sent

        ClientsPageAssertions.checkTotalClientsCountValueText(1);
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the search functionality by client type.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientsSearchByClientTypeTest() {
        String randomName = GeneratorBuilder.generateString(10);
        Map<String, String> params = new HashMap<>();
        params.put("nameOrTitle", randomName);
        PostApiResponseHelper.stubGetSearchClientsWithParams(params, CLIENTS);
        Selenide.refresh();

        ClientSearchPanelSteps.selectOptionClientTypeByVisibleText(BASE_CLIENT_TYPES_LIST.get(0).getClientTypeName());
        ClientSearchPanelSteps.clickSearchClientsBtn();
        // todo: check request was sent

        ClientsPageAssertions.checkTotalClientsCountValueText(1);
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the search functionality by sub-sector.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientsSearchBySubSectorTest() {
        String randomName = GeneratorBuilder.generateString(10);
        Map<String, String> params = new HashMap<>();
        params.put("nameOrTitle", randomName);
        PostApiResponseHelper.stubGetSearchClientsWithParams(params, CLIENTS);
        Selenide.refresh();

        ClientSearchPanelSteps.selectOptionSubSectorByVisibleText(BASE_SUB_SECTORS_LIST.get(0).getSubSectorName());
        ClientSearchPanelSteps.clickSearchClientsBtn();
        // todo: check request was sent

        ClientsPageAssertions.checkTotalClientsCountValueText(1);
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the search functionality by district.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientsSearchByDistrictTest() {
        String randomName = GeneratorBuilder.generateString(10);
        Map<String, String> params = new HashMap<>();
        params.put("nameOrTitle", randomName);
        PostApiResponseHelper.stubGetSearchClientsWithParams(params, CLIENTS);
        Selenide.refresh();

        ClientSearchPanelSteps.selectOptionDistrictByVisibleText(BASE_DISTRICTS_LIST.get(0).getDistrictName());
        ClientSearchPanelSteps.clickSearchClientsBtn();
        // todo: check request was sent

        ClientsPageAssertions.checkTotalClientsCountValueText(1);
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the search functionality by isBlocked status.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientsSearchByIsBlockedTest() {
        String randomName = GeneratorBuilder.generateString(10);
        Map<String, String> params = new HashMap<>();
        params.put("nameOrTitle", randomName);
        PostApiResponseHelper.stubGetSearchClientsWithParams(params, CLIENTS);
        Selenide.refresh();

        ClientSearchPanelSteps.clickIsBlockedCheckbox();
        ClientSearchPanelSteps.clickSearchClientsBtn();
        // todo: check request was sent

        ClientsPageAssertions.checkTotalClientsCountValueText(1);
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the search functionality when no clients satisfied the search parameters.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientsSearchNotFoundTest() {
        String randomName = GeneratorBuilder.generateString(10);
        Map<String, String> params = new HashMap<>();
        params.put("nameOrTitle", randomName);
        PostApiResponseHelper.stubGetSearchClientsWithParams(params, Collections.emptyList());
        Selenide.refresh();

        ClientSearchPanelSteps.typeClientName(randomName);
        ClientSearchPanelSteps.clickSearchClientsBtn();
        // todo: check request was sent

        ClientsPageAssertions.checkPageTitleText("No Clients Found");
    }
}
