package org.skopintsev.assertions.common.elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.skopintsev.models.gui.common.elements.FilterPanel;

import java.util.List;

public class FilterPanelAssertions {

    @Step("Check filter field to contain boolean value = '{0}'.")
    public static void checkFieldTextByBoolean(SelenideElement field, Boolean value) {
        String expectedText = value == null ? "All" : (value ? "Yes" : "No");
        field.shouldHave(Condition.exactText(expectedText));
    }

    @Step("Check filter field to contain text = '{0}'.")
    public static void checkFieldTextByString(SelenideElement field, String expectedText) {
        field.shouldHave(Condition.exactText(expectedText));
    }

    @Step("Check 'Ranks' filter elements.")
    public static void checkRanksFilterElements(List<String> expectedElements) {
        String expectedText = String.join(", ", expectedElements);
        checkFieldTextByString(
                FilterPanel.getRanksFilter(), expectedText);
    }

    @Step("Check 'Is Archived' filter.")
    public static void checkIsArchivedFilterElements(Boolean isArchived) {
        checkFieldTextByBoolean(
                FilterPanel.getRanksFilter(), isArchived);
    }

    @Step("Check 'Currencies' filter elements.")
    public static void checkCurrenciesFilterElements(List<String> expectedElements) {
        String expectedText = String.join(", ", expectedElements);
        checkFieldTextByString(
                FilterPanel.getCurrencyFilter(), expectedText);
    }

    @Step("Check 'Amount' filter.")
    public static void checkAmountFilterElements(String amountText) {
        checkFieldTextByString(
                FilterPanel.getRanksFilter(), amountText);
    }

    @Step("Check 'Last Transaction Date' filter.")
    public static void checkLastTransactionDateFilterElements(String transactionDate) {
        checkFieldTextByString(
                FilterPanel.getLastTransactionDateFilter(), transactionDate);
    }

}
