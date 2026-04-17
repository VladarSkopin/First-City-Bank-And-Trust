package org.skopintsev.models.gui.social_ranks;

import com.codeborne.selenide.SelenideElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SocialRanksPage {

    @Getter static final SelenideElement pageTitle = $(byTestId("pageTitle"));

    @Getter static final SelenideElement countLabel = $(byTestId("countLabel"));

    @Getter static final SelenideElement countValue = $(byTestId("countValue"));
}
