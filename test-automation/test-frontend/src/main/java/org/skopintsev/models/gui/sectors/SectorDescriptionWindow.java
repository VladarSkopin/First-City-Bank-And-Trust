package org.skopintsev.models.gui.sectors;

import com.codeborne.selenide.SelenideElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SectorDescriptionWindow {

    @Getter static final SelenideElement subSectorCodeWindow = $(byTestId("subSectorCodeWindow"));

    @Getter static final SelenideElement sectorNameWindow = $(byTestId("sectorNameWindow"));

    @Getter static final SelenideElement sectorDescription = $(byTestId("sectorDescription"));
}
