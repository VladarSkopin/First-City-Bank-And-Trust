package org.skopintsev.models.gui.clients;

import com.codeborne.selenide.SelenideElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientCard {

    @Getter static final SelenideElement avatar = $(byTestId("avatar"));

    @Getter static final SelenideElement nameOrTitle = $(byTestId("nameOrTitle"));

    @Getter static final SelenideElement rankName = $(byTestId("rankName"));

    @Getter static final SelenideElement blockedBanner = $(byTestId("blockedBanner"));

    @Getter static final SelenideElement clientCodeLabel = $(byTestId("clientCodeLabel"));

    @Getter static final SelenideElement clientCodeValue = $(byTestId("clientCodeValue"));

    @Getter static final SelenideElement clientTypeLabel = $(byTestId("clientTypeLabel"));

    @Getter static final SelenideElement clientTypeValue = $(byTestId("clientTypeValue"));

    @Getter static final SelenideElement districtLabel = $(byTestId("districtLabel"));

    @Getter static final SelenideElement districtValue = $(byTestId("districtValue"));

    @Getter static final SelenideElement statusLabel = $(byTestId("statusLabel"));

    @Getter static final SelenideElement statusValue = $(byTestId("statusValue"));

    @Getter static final SelenideElement sectorLabel = $(byTestId("sectorLabel"));

    @Getter static final SelenideElement sectorValue = $(byTestId("sectorValue"));
}
