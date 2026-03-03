package org.skopintsev;

import com.codeborne.selenide.logevents.SelenideLogger;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.skopintsev.transport.PostApiResponseHelper;
import org.skopintsev.util.OpenUrl;

public class BaseTest {

    @BeforeAll
    public static void init() {
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

}
