package org.skopintsev.models.gui.currencies;

import com.codeborne.selenide.SelenideElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurrenciesPage {

    @Getter static final SelenideElement pageTitle = $(byTestId("pageTitle"));

    @Getter static final SelenideElement exchangeRateTitle = $(byTestId("exchangeRateTitle"));

    @Getter static final SelenideElement exchangeRateLabel = $(byTestId("exchangeRateLabel"));

    @Getter static final SelenideElement exchangeRateValue = $(byTestId("exchangeRateValue"));

    @Getter static final SelenideElement footerNote = $(byTestId("warningNote"));

}
