package org.skopintsev.clients_page;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.skopintsev.assertions.client.ClientsPageAssertions;
import org.skopintsev.assertions.common.elements.ButtonElementAssertions;
import org.skopintsev.transport.PostApiResponseHelper;

import java.util.Collections;

public class ClientInfoTest extends BaseClientTest {

    @Test
    @Tag("smoke")
    @Description("Test checks the display of Clients page information.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientsPageInfoTest() {
        ClientsPageAssertions.checkPageTitleText("Banking Clients");

        ButtonElementAssertions.checkRetryBtnExists(false);
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the display of single client card information.")
    @Severity(SeverityLevel.CRITICAL)
    public void clientCardInfoTest() {


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
}
