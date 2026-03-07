package org.skopintsev.assertions.common;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.skopintsev.models.gui.common.HeaderPanel;

import java.util.Arrays;
import java.util.List;

public class HeaderPanelAssertions {

    @Step("Check header panel tab should have class 'nav-link active' when clicked.")
    public static void checkHeaderPanelTabActive(SelenideElement panelTab) {
        panelTab.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .shouldHave(Condition.cssClass("nav-link"))
                .shouldHave(Condition.cssClass("active"));
    }

    @Step("Check header panel tab should have class 'nav-link' when NOT clicked.")
    public static void checkHeaderPanelTabNotActive(SelenideElement panelTab) {
        panelTab.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .shouldHave(Condition.cssClass("nav-link"))
                .shouldNotHave(Condition.cssClass("active"));
    }

    // Verifies that all header panel tabs are inactive except the given active tab (the one just clicked)
    public static void verifyAllOtherTabsInactive(SelenideElement activeTab) {
        String activeTestId = activeTab.getAttribute("data-testid");

        List<SelenideElement> allTabs = Arrays.asList(
                HeaderPanel.getVaultsPageBtn(),
                HeaderPanel.getClientsPageBtn(),
                HeaderPanel.getSocialRanksPageBtn(),
                HeaderPanel.getDistrictsPageBtn(),
                HeaderPanel.getCurrenciesPageBtn(),
                HeaderPanel.getSectorsPageBtn()
        );

        for (SelenideElement tab : allTabs) {
            if (!tab.getAttribute("data-testid").equals(activeTestId)) {
                checkHeaderPanelTabNotActive(tab);
            }
        }
    }

}
