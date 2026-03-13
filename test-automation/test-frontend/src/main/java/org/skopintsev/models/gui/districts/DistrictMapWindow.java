package org.skopintsev.models.gui.districts;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

public class DistrictMapWindow {

    @Getter static final SelenideElement districtMapImg = $(byTestId("districtMapImg"));

    @Getter static final SelenideElement mapBanner = $(byTestId("mapBanner"));

    @Getter static final SelenideElement mapDescription = $(byTestId("mapDescription"));
}
