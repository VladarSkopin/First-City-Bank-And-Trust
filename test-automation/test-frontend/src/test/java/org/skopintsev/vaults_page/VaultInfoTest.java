package org.skopintsev.vaults_page;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.skopintsev.assertions.common.elements.ButtonElementAssertions;
import org.skopintsev.transport.PostApiResponseHelper;

import java.util.Collections;

public class VaultInfoTest extends BaseVaultTest {

    @Test
    @Tag("smoke")
    @Description("Test checks the display of Vaults page information.")
    @Severity(SeverityLevel.BLOCKER)
    public void vaultsPageInfoTest() {

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
    @Severity(SeverityLevel.BLOCKER)
    public void vaultCardInfoTest(boolean isBlockedClient) {


    }

    @Test
    @Tag("regression")
    @Description("Test checks the display of the Vaults page in case of empty response list.")
    @Severity(SeverityLevel.BLOCKER)
    public void vaultsEmptyResponseTest() {
        PostApiResponseHelper.stubGetVaults(Collections.emptyList());
        Selenide.refresh();


        ButtonElementAssertions.checkRetryBtnExists(false);
    }

    @Test
    @Tag("regression")
    @Description("Test checks the display of the Vaults page when no vaults were found.")
    @Severity(SeverityLevel.BLOCKER)
    public void vaultsNotFoundTest() {
        PostApiResponseHelper.stubGetVaults(Collections.emptyList());
        Selenide.refresh();


        ButtonElementAssertions.checkRetryBtnExists(true);
        ButtonElementAssertions.checkRetryBtnIsVisible();
        ButtonElementAssertions.checkRetryBtnEnabled(true);
    }

    @Test
    @Tag("regression")
    @Description("Test checks the display of the Vaults page in case of server error response.")
    @Severity(SeverityLevel.BLOCKER)
    public void vaultsServerErrorTest() {
        PostApiResponseHelper.stubGetVaults(Collections.emptyList());
        Selenide.refresh();


        ButtonElementAssertions.checkRetryBtnExists(true);
        ButtonElementAssertions.checkRetryBtnIsVisible();
        ButtonElementAssertions.checkRetryBtnEnabled(true);
    }
}
