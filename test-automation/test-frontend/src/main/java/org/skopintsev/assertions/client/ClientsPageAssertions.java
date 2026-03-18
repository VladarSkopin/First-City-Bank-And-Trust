package org.skopintsev.assertions.client;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.skopintsev.models.gui.clients.ClientsPage;

public class ClientsPageAssertions {

    @Step("Check 'Clients' page title is displayed with text '{0}'.")
    public static void checkPageTitleText(String titleText) {
        ClientsPage.getPageTitle().shouldBe(Condition.visible).shouldHave(Condition.text(titleText));
    }

    @Step("Check total clients count label is displayed with text '{0}'.")
    public static void checkTotalClientsCountLabelText(String countLabel) {
        ClientsPage.getCountLabel().shouldBe(Condition.visible).shouldHave(Condition.text(countLabel));
    }

    @Step("Check total clients count value is displayed with text '{0}'.")
    public static void checkTotalClientsCountValueText(int countValue) {
        ClientsPage.getCountValue().shouldBe(Condition.visible).shouldHave(
                Condition.text(String.valueOf(countValue)));
    }

    @Step("Check active clients count label is displayed with text '{0}'.")
    public static void checkActiveClientsCountLabelText(String countLabel) {
        ClientsPage.getCountLabelActive().shouldBe(Condition.visible).shouldHave(Condition.text(countLabel));
    }

    @Step("Check active clients count value is displayed with text '{0}'.")
    public static void checkActiveClientsCountValueText(int countValue) {
        ClientsPage.getCountValueActive().shouldBe(Condition.visible).shouldHave(
                Condition.text(String.valueOf(countValue)));
    }

    @Step("Check blocked clients count label is displayed with text '{0}'.")
    public static void checkBlockedClientsCountLabelText(String countLabel) {
        ClientsPage.getCountLabelBlocked().shouldBe(Condition.visible).shouldHave(Condition.text(countLabel));
    }

    @Step("Check blocked clients count value is displayed with text '{0}'.")
    public static void checkBlockedClientsCountValueText(int countValue) {
        ClientsPage.getCountValueBlocked().shouldBe(Condition.visible).shouldHave(
                Condition.text(String.valueOf(countValue)));
    }

    @Step("Check footer warning note is displayed with text '{0}'.")
    public static void checkFooterNoteText(String footerText) {
        ClientsPage.getWarningNote().shouldBe(Condition.visible).shouldHave(Condition.text(footerText));
    }

}
