package org.skopintsev.assertions.client;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.skopintsev.models.gui.clients.ClientCard;

public class ClientsCardAssertions {

    @Step("Check client avatar is displayed with text '{0}'.")
    public static void checkAvatar(char avatarText) {
        ClientCard.getAvatar().shouldBe(Condition.visible).shouldHave(
                Condition.text(String.valueOf(avatarText)));
    }

    @Step("Check client name or title is displayed with text '{0}'.")
    public static void checkClientNameOrTitle(String clientName) {
        ClientCard.getNameOrTitle().shouldBe(Condition.visible).shouldHave(Condition.text(clientName));
    }

    @Step("Check rank name is displayed with text '{0}'.")
    public static void checkRankName(String rankName) {
        ClientCard.getRankName().shouldBe(Condition.visible).shouldHave(Condition.exactText(rankName));
    }

    @Step("Check blocked banner should exist = '{0}'.")
    public static void checkBlockedBannerExist(boolean shouldExist) {
        ClientCard.getBlockedBanner().should(shouldExist ? Condition.exist : Condition.not(Condition.exist));
    }

    @Step("Check blocked banner is displayed with text '{0}'.")
    public static void checkBlockedBannerText(String blockedBanner) {
        ClientCard.getBlockedBanner().shouldBe(Condition.visible).shouldHave(Condition.text(blockedBanner));
    }

    @Step("Check client code label is displayed with text '{0}'.")
    public static void checkClientCodeLabelText(String clientCodeLabel) {
        ClientCard.getClientCodeLabel().shouldBe(Condition.visible).shouldHave(Condition.text(clientCodeLabel));
    }

    @Step("Check client code value is displayed with text '{0}'.")
    public static void checkClientCodeValueText(String clientCodeValue) {
        ClientCard.getClientCodeValue().shouldBe(Condition.visible).shouldHave(Condition.text(clientCodeValue));
    }

    @Step("Check client type label is displayed with text '{0}'.")
    public static void checkClientTypeLabelText(String clientTypeLabel) {
        ClientCard.getClientTypeLabel().shouldBe(Condition.visible).shouldHave(Condition.text(clientTypeLabel));
    }

    @Step("Check client type value is displayed with text '{0}'.")
    public static void checkClientTypeValueText(String clientTypeValue) {
        ClientCard.getClientTypeValue().shouldBe(Condition.visible).shouldHave(Condition.text(clientTypeValue));
    }

    @Step("Check district label is displayed with text '{0}'.")
    public static void checkDistrictLabelText(String districtLabel) {
        ClientCard.getDistrictLabel().shouldBe(Condition.visible).shouldHave(Condition.text(districtLabel));
    }

    @Step("Check district value is displayed with text '{0}'.")
    public static void checkDistrictValueText(String districtValue) {
        ClientCard.getDistrictValue().shouldBe(Condition.visible).shouldHave(Condition.text(districtValue));
    }

    @Step("Check status label is displayed with text '{0}'.")
    public static void checkStatusLabelText(String statusLabel) {
        ClientCard.getStatusLabel().shouldBe(Condition.visible).shouldHave(Condition.text(statusLabel));
    }

    @Step("Check status value is displayed with text '{0}'.")
    public static void checkStatusValueText(String statusValue) {
        ClientCard.getStatusValue().shouldBe(Condition.visible).shouldHave(Condition.text(statusValue));
    }

    @Step("Check sector label is displayed with text '{0}'.")
    public static void checkSectorNameLabelText(String sectorLabel) {
        ClientCard.getSectorLabel().shouldBe(Condition.visible).shouldHave(Condition.text(sectorLabel));
    }

    @Step("Check sector value is displayed with text '{0}'.")
    public static void checkSectorNameValueText(String sectorValue) {
        ClientCard.getSectorValue().shouldBe(Condition.visible).shouldHave(Condition.text(sectorValue));
    }

}
