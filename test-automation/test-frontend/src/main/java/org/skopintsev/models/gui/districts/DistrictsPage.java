package org.skopintsev.models.gui.districts;

import com.codeborne.selenide.SelenideElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class DistrictsPage {

    @Getter static final SelenideElement pageTitle = $(byTestId("pageTitle"));

    @Getter static final SelenideElement districtsCountLabel = $(byTestId("countLabel"));

    @Getter static final SelenideElement districtsCountValue = $(byTestId("countValue"));
}
