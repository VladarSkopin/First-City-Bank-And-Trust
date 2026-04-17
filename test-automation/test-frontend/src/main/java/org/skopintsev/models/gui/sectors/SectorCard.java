package org.skopintsev.models.gui.sectors;

import com.codeborne.selenide.SelenideElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SectorCard {

    @Getter static final SelenideElement subSectorName = $(byTestId("subSectorName"));

    @Getter static final SelenideElement subSectorCodeLabel = $(byTestId("subSectorCodeLabel"));

    @Getter static final SelenideElement subSectorCodeValue = $(byTestId("subSectorCodeValue"));

    @Getter static final SelenideElement sectorNameLabel = $(byTestId("sectorNameLabel"));

    @Getter static final SelenideElement sectorNameValue = $(byTestId("sectorNameValue"));

    @Getter static final SelenideElement viewDescriptionBtn = $(byTestId("viewDescriptionBtn"));
}
