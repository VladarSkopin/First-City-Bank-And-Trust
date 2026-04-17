package org.skopintsev.models.gui.districts;

import com.codeborne.selenide.SelenideElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class DistrictMapWindow {

    @Getter static final SelenideElement districtMapImg = $(byTestId("districtMapImg"));

    @Getter static final SelenideElement mapBanner = $(byTestId("mapBanner"));

    @Getter static final SelenideElement mapDescription = $(byTestId("mapDescription"));
}
