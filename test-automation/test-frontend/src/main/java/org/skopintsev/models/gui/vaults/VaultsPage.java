package org.skopintsev.models.gui.vaults;

import com.codeborne.selenide.SelenideElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class VaultsPage {

    @Getter static final SelenideElement pageTitle = $(byTestId("pageTitle"));

    @Getter static final SelenideElement countLabelVaults = $(byTestId("countLabelVaults"));

    @Getter static final SelenideElement countValueVaults = $(byTestId("countValueVaults"));

    @Getter static final SelenideElement countLabelCurrencies = $(byTestId("countLabelCurrencies"));

    @Getter static final SelenideElement countValueCurrencies = $(byTestId("countValueCurrencies"));
}
