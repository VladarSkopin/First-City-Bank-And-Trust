package org.skopintsev.models.gui.clients;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

public class ClientSearchPanel {

    @Getter static final SelenideElement inputTextClientName = $(byTestId("inputTextClientName"));

    @Getter static final SelenideElement selectRank = $(byTestId("selectRank"));

    @Getter static final SelenideElement selectType = $(byTestId("selectType"));

    @Getter static final SelenideElement selectSubSector = $(byTestId("selectSubSector"));

    @Getter static final SelenideElement selectDistrict = $(byTestId("selectDistrict"));

    @Getter static final SelenideElement isBlockedLabel = $(byTestId("isBlockedLabel"));

    @Getter static final SelenideElement inputCheckboxIsBlocked = $(byTestId("inputCheckboxIsBlocked"));

    @Getter static final SelenideElement btnSearch = $(byTestId("btnSearch"));

    @Getter static final SelenideElement btnReset = $(byTestId("btnReset"));
}
