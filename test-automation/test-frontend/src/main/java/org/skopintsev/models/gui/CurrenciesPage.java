package org.skopintsev.models.gui;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;


@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurrenciesPage {

    // todo: static final SelenideElement currenciesPageLoader = $(byTestId("loading-spinner"));
    @Getter static final SelenideElement currenciesPageLoader = $(Selectors.byClassName("loading-spinner"));

    // todo: page title

    // todo: monetary system exchange rate

    // todo: footer note

}
