package org.skopintsev.models.gui.currencies;

import com.codeborne.selenide.SelenideElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurrencyCard {

    @Getter static final SelenideElement currencyCode = $(byTestId("currencyCode"));

    @Getter static final SelenideElement currencyName = $(byTestId("currencyName"));

    @Getter static final SelenideElement currencyMetalTypeLabel = $(byTestId("currencyMetalTypeLabel"));

    @Getter static final SelenideElement currencyMetalTypeValue = $(byTestId("currencyMetalTypeValue"));

    @Getter static final SelenideElement currencySymbol = $(byTestId("currencySymbol"));

}
