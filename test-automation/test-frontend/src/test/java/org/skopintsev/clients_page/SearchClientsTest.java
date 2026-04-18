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

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchClientsTest extends BaseClientTest {

    Client client = BASE_CLIENTS_LIST.stream()
            .filter(c -> c.getIsBlocked() == false)
            .findFirst()
            .orElse(null);

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
        ClientsSearchAssertions.checkSearchBtnText("SEARCH CLIENTS");
        ClientsSearchAssertions.checkResetBtnText("RESET");
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the search functionality by client name.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientsSearchByNameTest() {
        String clientName = client.getNameOrTitle();

        Map<String, String> params = new HashMap<>();
        params.put("nameOrTitle", clientName);
        PostApiResponseHelper.stubGetSearchClientsWithParams(params, List.of(client));
        Selenide.refresh();

        ClientSearchPanelSteps.typeClientName(clientName);
        ClientSearchPanelSteps.clickSearchClientsBtn();

        ClientsPageAssertions.checkTotalClientsCountValueText(1);
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the search functionality by social rank.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientsSearchBySocialRankTest() {
        String socialRankCode = client.getSocialRankCode();

        Map<String, String> params = new HashMap<>();
        params.put("socialRankCode", socialRankCode);
        PostApiResponseHelper.stubGetSearchClientsWithParams(params, List.of(client));
        Selenide.refresh();

        String socialRank = BASE_SOCIAL_RANKS_LIST
                .stream()
                .filter(s -> s.getRankCode().equals(socialRankCode))
                .findFirst()
                .orElse(null).getRankName();

        ClientSearchPanelSteps.selectOptionSocialRankByVisibleText(socialRank);
        ClientSearchPanelSteps.clickSearchClientsBtn();

        ClientsPageAssertions.checkTotalClientsCountValueText(1);
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the search functionality by client type.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientsSearchByClientTypeTest() {
        String clientTypeCode = client.getClientTypeCode();

        Map<String, String> params = new HashMap<>();
        params.put("clientTypeCode", clientTypeCode);
        PostApiResponseHelper.stubGetSearchClientsWithParams(params, List.of(client));
        Selenide.refresh();

        String clientType = BASE_CLIENT_TYPES_LIST
                .stream()
                .filter(c -> c.getClientTypeCode().equals(clientTypeCode))
                .findFirst()
                .orElse(null).getClientTypeName();

        ClientSearchPanelSteps.selectOptionClientTypeByVisibleText(clientType);
        ClientSearchPanelSteps.clickSearchClientsBtn();

        ClientsPageAssertions.checkTotalClientsCountValueText(1);
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the search functionality by sub-sector.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientsSearchBySubSectorTest() {
        String subSectorCode = client.getSubSectorCode();

        Map<String, String> params = new HashMap<>();
        params.put("subSectorCode", subSectorCode);
        PostApiResponseHelper.stubGetSearchClientsWithParams(params, List.of(client));
        Selenide.refresh();

        String subSector = BASE_SUB_SECTORS_LIST
                .stream()
                .filter(s -> s.getSubSectorCode().equals(subSectorCode))
                .findFirst()
                .orElse(null).getSubSectorName();

        ClientSearchPanelSteps.selectOptionSubSectorByVisibleText(subSector);
        ClientSearchPanelSteps.clickSearchClientsBtn();

        ClientsPageAssertions.checkTotalClientsCountValueText(1);
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the search functionality by district.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientsSearchByDistrictTest() {
        String districtCode = client.getDistrictCode();

        Map<String, String> params = new HashMap<>();
        params.put("districtCode", districtCode);
        PostApiResponseHelper.stubGetSearchClientsWithParams(params, List.of(client));
        Selenide.refresh();

        String district = BASE_DISTRICTS_LIST
                .stream()
                .filter(d -> d.getDistrictCode().equals(districtCode))
                .findFirst()
                .orElse(null).getDistrictName();

        ClientSearchPanelSteps.selectOptionDistrictByVisibleText(district);
        ClientSearchPanelSteps.clickSearchClientsBtn();

        ClientsPageAssertions.checkTotalClientsCountValueText(1);
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the search functionality by isBlocked status.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientsSearchByIsBlockedTest() {
        client.setIsBlocked(true);

        Map<String, String> params = new HashMap<>();
        params.put("isBlocked", String.valueOf(client.getIsBlocked()));
        PostApiResponseHelper.stubGetSearchClientsWithParams(params, List.of(client));
        Selenide.refresh();

        ClientSearchPanelSteps.clickIsBlockedCheckbox();
        ClientSearchPanelSteps.clickSearchClientsBtn();

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

        ClientsPageAssertions.checkPageTitleText("No Clients Found");
    }
}
