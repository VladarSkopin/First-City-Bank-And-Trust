package org.skopintsev;

import com.codeborne.selenide.logevents.SelenideLogger;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.skopintsev.transport.PostApiResponseHelper;
import org.skopintsev.util.OpenUrl;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@FieldDefaults(level = AccessLevel.PROTECTED)
public class BaseTest {

    static WireMockServer wireMockServer;

    @BeforeAll
    public static void init() {
        // Choose a port that doesn't conflict with your real backend (e.g., 8089)
        wireMockServer = new WireMockServer(options().port(8080));
        wireMockServer.start();
        WireMock.configureFor("localhost", 8080);

        SelenideLogger.addListener(
                "AllureSelenide",
                new AllureSelenide().screenshots(true)
                        .includeSelenideSteps(false)
                        .savePageSource(false));
        Configuration.timeout = 10_000;
        Configuration.headless = false;
    }

    @BeforeEach
    public void openFirstCityBankUrl() {
        PostApiResponseHelper.deleteMappingsJournal();
        PostApiResponseHelper.deleteWebRequestsJournal();
        PostApiResponseHelper.postResetMock();
        PostApiResponseHelper.getMappingsMock();
        //todo: PostApiResponseHelper.postGetUserInfoResponse(); -> user role and permissions
        OpenUrl.openFirstCityBank();
        //todo: LoginPage.loginBankManager(); -> user login and password
    }

    @AfterEach
    public void closeBrowser() {
        Selenide.closeWebDriver();
    }

    @AfterAll
    public static void stopWireMock() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

}
