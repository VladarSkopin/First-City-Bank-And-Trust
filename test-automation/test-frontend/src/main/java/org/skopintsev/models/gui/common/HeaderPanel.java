package org.skopintsev.models.gui.common;

import com.codeborne.selenide.SelenideElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class HeaderPanel {

    @Getter static final SelenideElement vaultsPageBtn = $(byTestId("vaultsPageBtn"));

    @Getter static final SelenideElement clientsPageBtn = $(byTestId("clientsPageBtn"));

    @Getter static final SelenideElement socialRanksPageBtn = $(byTestId("socialRanksPageBtn"));

    @Getter static final SelenideElement districtsPageBtn = $(byTestId("districtsPageBtn"));

    @Getter static final SelenideElement currenciesPageBtn = $(byTestId("currenciesPageBtn"));

    @Getter static final SelenideElement sectorsPageBtn = $(byTestId("sectorsPageBtn"));

}
