package org.skopintsev.models.gui.sector;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

public class SectorsPage {

    @Getter static final SelenideElement pageTitle = $(byTestId("pageTitle"));

    @Getter static final SelenideElement countLabelSubSectors = $(byTestId("countLabelSubSectors"));

    @Getter static final SelenideElement countValueSubSectors = $(byTestId("countValueSubSectors"));

    @Getter static final SelenideElement countLabelSectors = $(byTestId("countLabelSectors"));

    @Getter static final SelenideElement countValueSectors = $(byTestId("countValueSectors"));
}
