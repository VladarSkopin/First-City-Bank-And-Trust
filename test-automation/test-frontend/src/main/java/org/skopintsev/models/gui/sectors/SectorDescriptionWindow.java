package org.skopintsev.models.gui.sectors;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

public class SectorDescriptionWindow {

    @Getter static final SelenideElement subSectorCodeWindow = $(byTestId("subSectorCodeWindow"));

    @Getter static final SelenideElement sectorNameWindow = $(byTestId("sectorNameWindow"));

    @Getter static final SelenideElement sectorDescription = $(byTestId("sectorDescription"));

}
