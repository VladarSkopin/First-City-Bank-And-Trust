package org.skopintsev.models.gui.common.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoadingSpinner {

    @Getter static final SelenideElement pageLoader = $(byTestId("loading-spinner"));
}
