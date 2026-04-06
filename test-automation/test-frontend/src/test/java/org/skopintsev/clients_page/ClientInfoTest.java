package org.skopintsev.clients_page;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.skopintsev.assertions.client.ClientsCardAssertions;
import org.skopintsev.assertions.client.ClientsPageAssertions;
import org.skopintsev.assertions.common.elements.ButtonElementAssertions;
import org.skopintsev.models.api.*;
import org.skopintsev.models.api.factory.ClientFactory;
import org.skopintsev.models.api.sector.SubSector;
import org.skopintsev.transport.CheckApiRequestHelper;
import org.skopintsev.transport.PostApiResponseHelper;

import java.util.Collections;
import java.util.List;

import static org.skopintsev.constants.Api.CLIENTS;

public class ClientInfoTest extends BaseClientTest {

    @Test
    @Tag("smoke")
    @Description("Test checks the display of Clients page information.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientsPageInfoTest() {
        ClientsPageAssertions.checkPageTitleText("Banking Clients");
        ClientsPageAssertions.checkTotalClientsCountLabelText("TOTAL CLIENTS: ");
        ClientsPageAssertions.checkTotalClientsCountValueText(BASE_CLIENTS_LIST.size());
        ClientsPageAssertions.checkActiveClientsCountLabelText("ACTIVE: ");
        ClientsPageAssertions.checkActiveClientsCountValueText(
                BASE_CLIENTS_LIST.stream().filter(c -> c.getIsBlocked() == true).count());
        ClientsPageAssertions.checkBlockedClientsCountLabelText("BLOCKED: ");
        ClientsPageAssertions.checkBlockedClientsCountValueText(
                BASE_CLIENTS_LIST.stream().filter(c -> c.getIsBlocked() == false).count());
        ClientsPageAssertions.checkFooterNoteText(
                "Client information is confidential. Unauthorized access is prohibited.");
        ButtonElementAssertions.checkRetryBtnExists(false);
    }

    @ParameterizedTest(name = "[{index}] boolean isBlockedClient = {0}")
    @ValueSource(booleans = {true, false})
    @Tag("smoke")
    @Description(
        """
        Test checks the display of a single client card information:
        "1) when client is blocked,
        "2) when client is active.
        """)
    @Severity(SeverityLevel.CRITICAL)
    public void clientCardInfoTest(boolean isBlockedClient) {
        Client client = isBlockedClient ?
                ClientFactory.generateClient(
                BASE_CLIENT_TYPES_LIST.get(0).getClientTypeCode(),
                null,
                null,
                null,
                true) :
                ClientFactory.generateClient(
                        BASE_CLIENT_TYPES_LIST.get(1).getClientTypeCode(),
                        BASE_SOCIAL_RANKS_LIST.get(0).getRankCode(),
                        BASE_DISTRICTS_LIST.get(0).getDistrictCode(),
                        BASE_SUB_SECTORS_LIST.get(0).getSubSectorCode(),
                        false);
        List<Client> clientsList = List.of(client);
        PostApiResponseHelper.stubGetClients(clientsList);
        Selenide.refresh();

        ClientsCardAssertions.checkAvatar(Character.toUpperCase(client.getNameOrTitle().charAt(0)));
        ClientsCardAssertions.checkClientNameOrTitle(client.getNameOrTitle());
        ClientsCardAssertions.checkRankName(
                isBlockedClient ? "" : getSocialRankName(client.getSocialRankCode()));
        if (isBlockedClient) {
            ClientsCardAssertions.checkBlockedBannerExist(true);
            ClientsCardAssertions.checkBlockedBannerText("BLOCKED");
        } else {
            ClientsCardAssertions.checkBlockedBannerExist(false);
        }
        ClientsCardAssertions.checkClientCodeLabelText("CLIENT ID:");
        ClientsCardAssertions.checkClientCodeValueText(client.getClientCode());
        ClientsCardAssertions.checkClientTypeLabelText("CLIENT TYPE:");
        ClientsCardAssertions.checkClientTypeValueText(
                isBlockedClient ? "Unknown" : getClientTypeName(client.getClientTypeCode()));
        ClientsCardAssertions.checkDistrictLabelText("DISTRICT:");
        ClientsCardAssertions.checkDistrictValueText(
                isBlockedClient ? "Unknown District" : getDistrictName(client.getDistrictCode()));
        ClientsCardAssertions.checkStatusLabelText("STATUS:");
        ClientsCardAssertions.checkStatusValueText(
                isBlockedClient ? "BLOCKED 🔒" : "ACTIVE ✅"
        );
        ClientsCardAssertions.checkSectorNameLabelText("SECTOR:");
        ClientsCardAssertions.checkSectorNameValueText(
                isBlockedClient ? "Unknown Sector" : getSubSectorName(client.getSubSectorCode()));
    }

    @Test
    @Tag("regression")
    @Description("Test checks the display of the Clients page in case of empty response list.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientsEmptyResponseTest() {
        PostApiResponseHelper.stubGetClients(Collections.emptyList());
        Selenide.refresh();

        ClientsPageAssertions.checkPageTitleText("No Clients Found");
        ButtonElementAssertions.checkRetryBtnExists(false);
    }

    @Test
    @Tag("regression")
    @Description("Test checks the display of the Clients page when no sectors were found.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientsNotFoundTest() {
        PostApiResponseHelper.stubGetClientsNotFound(Collections.emptyList());
        Selenide.refresh();

        ClientsPageAssertions.checkPageTitleText("Failed to Load Clients");
        ButtonElementAssertions.checkRetryBtnExists(true);
        ButtonElementAssertions.checkRetryBtnIsVisible();
        ButtonElementAssertions.checkRetryBtnEnabled(true);
    }

    @Test
    @Tag("regression")
    @Description("Test checks the display of the Clients page in case of server error response.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientsServerErrorTest() {
        PostApiResponseHelper.stubGetClientsServerError(Collections.emptyList());
        Selenide.refresh();

        ClientsPageAssertions.checkPageTitleText("Failed to Load Clients");
        ButtonElementAssertions.checkRetryBtnExists(true);
        ButtonElementAssertions.checkRetryBtnIsVisible();
        ButtonElementAssertions.checkRetryBtnEnabled(true);
    }


    public String getSubSectorName(String subSectorCode) {
        SubSector subSector = BASE_SUB_SECTORS_LIST
                .stream()
                .filter(s -> s.getSubSectorCode().equals(subSectorCode))
                .findFirst()
                .orElse(null);
        return subSector.getSubSectorName();
    }

    public String getSocialRankName(String rankCode) {
        SocialRank socialRank = BASE_SOCIAL_RANKS_LIST
                .stream()
                .filter(s -> s.getRankCode().equals(rankCode))
                .findFirst()
                .orElse(null);
        return socialRank.getRankName();
    }

    public String getClientTypeName(String clientTypeCode) {
        ClientType clientType = BASE_CLIENT_TYPES_LIST
                .stream()
                .filter(c -> c.getClientTypeCode().equals(clientTypeCode))
                .findFirst()
                .orElse(null);
        return clientType.getClientTypeName();
    }

    public String getDistrictName(String districtCode) {
        District district = BASE_DISTRICTS_LIST
                .stream()
                .filter(d -> d.getDistrictCode().equals(districtCode))
                .findFirst()
                .orElse(null);
        return district.getDistrictName();
    }
}
