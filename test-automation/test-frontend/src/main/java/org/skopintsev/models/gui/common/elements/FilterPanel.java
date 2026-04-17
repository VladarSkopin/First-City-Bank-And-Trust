package org.skopintsev.models.gui.common.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

public class FilterPanel {

    @Getter static final SelenideElement ranksFilter = $(byTestId("ranksFilter"));

    @Getter static final SelenideElement isArchivedFilter = $(byTestId("isArchivedFilter"));

    @Getter static final SelenideElement currencyFilter = $(byTestId("currencyFilter"));

    @Getter static final SelenideElement amountFilter = $(byTestId("amountFilter"));

    @Getter static final SelenideElement lastTransactionDateFilter = $(byTestId("lastTransactionDateFilter"));
}
