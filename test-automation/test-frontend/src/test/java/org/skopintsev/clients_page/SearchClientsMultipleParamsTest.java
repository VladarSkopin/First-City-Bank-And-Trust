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
import org.skopintsev.steps.common.elements.LoadingSpinnerSteps;
import org.skopintsev.transport.PostApiResponseHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchClientsMultipleParamsTest extends BaseClientTest {

    Client client = BASE_CLIENTS_LIST.stream()
                .filter(c -> c.getIsBlocked() == false)
                .findFirst()
                .orElse(null);

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
        client.setIsBlocked(true);
        Map<String, String> params = new HashMap<>();
        params.put("nameOrTitle", client.getNameOrTitle());
        params.put("socialRankCode", client.getSocialRankCode());
        params.put("clientTypeCode", client.getClientTypeCode());
        params.put("subSectorCode", client.getSubSectorCode());
        params.put("districtCode", client.getDistrictCode());
        params.put("isBlocked", String.valueOf(client.getIsBlocked()));
        PostApiResponseHelper.stubGetSearchClientsWithParams(params, List.of(client));
        Selenide.refresh();

        ClientsPageAssertions.checkTotalClientsCountValueText(BASE_CLIENTS_LIST.size());

        ClientSearchPanelSteps.typeClientName(client.getNameOrTitle());
        ClientSearchPanelSteps.selectOptionSocialRankByValue(client.getSocialRankCode());
        ClientSearchPanelSteps.selectOptionClientTypeByValue(client.getClientTypeCode());
        ClientSearchPanelSteps.selectOptionSubSectorByValue(client.getSubSectorCode());
        ClientSearchPanelSteps.selectOptionDistrictByValue(client.getDistrictCode());
        ClientSearchPanelSteps.clickIsBlockedCheckbox();
        ClientSearchPanelSteps.clickSearchClientsBtn();

        ClientsPageAssertions.checkTotalClientsCountValueText(1);

        // Clicking RESET button
        ClientSearchPanelSteps.clickResetBtn();
        LoadingSpinnerSteps.waitingForPageLoading();

        ClientsPageAssertions.checkTotalClientsCountValueText(BASE_CLIENTS_LIST.size());
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
