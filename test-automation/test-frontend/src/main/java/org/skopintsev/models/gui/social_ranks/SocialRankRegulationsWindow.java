package org.skopintsev.models.gui.social_ranks;

import com.codeborne.selenide.SelenideElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SocialRankRegulationsWindow {

    @Getter static final SelenideElement regulationCode = $(byTestId("regulationCode"));

    @Getter static final SelenideElement regulationLevel = $(byTestId("regulationLevel"));

    @Getter static final SelenideElement regulationDescription = $(byTestId("regulationDescription"));

    @Getter static final SelenideElement sealBanner = $(byTestId("sealBanner"));
}
