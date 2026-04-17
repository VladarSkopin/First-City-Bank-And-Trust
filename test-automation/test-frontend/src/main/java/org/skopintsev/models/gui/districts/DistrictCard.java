package org.skopintsev.models.gui.districts;

import com.codeborne.selenide.SelenideElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class DistrictCard {

    @Getter static final SelenideElement districtCode = $(byTestId("districtCode"));

    @Getter static final SelenideElement districtName = $(byTestId("districtName"));

    @Getter static final SelenideElement detailedMapBtn = $(byTestId("detailedMapButton"));
}
