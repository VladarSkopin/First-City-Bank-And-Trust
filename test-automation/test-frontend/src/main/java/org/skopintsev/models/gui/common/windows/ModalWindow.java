package org.skopintsev.models.gui.common.windows;

import com.codeborne.selenide.SelenideElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModalWindow {

    @Getter static final SelenideElement modalHeader = $(byTestId("modalHeader"));

    @Getter static final SelenideElement modalBody = $(byTestId("modalBody"));

}
