package org.skopintsev.models.gui.social_ranks;

import com.codeborne.selenide.SelenideElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SocialRankCard {

    @Getter static final SelenideElement rankName = $(byTestId("rankName"));

    @Getter static final SelenideElement rankCode = $(byTestId("rankCode"));

    @Getter static final SelenideElement rankDescription = $(byTestId("rankDescription"));

    @Getter static final SelenideElement privilegeLabel = $(byTestId("privilegeLabel"));

    @Getter static final SelenideElement privilegeValue = $(byTestId("privilegeValue"));

    @Getter static final SelenideElement accessRightsLabel = $(byTestId("accessRightsLabel"));

    @Getter static final SelenideElement accessRightsValue = $(byTestId("accessRightsValue"));

    @Getter static final SelenideElement viewRegulationsBtn = $(byTestId("viewRegulationsBtn"));
}
