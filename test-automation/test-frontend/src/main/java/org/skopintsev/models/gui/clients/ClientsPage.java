package org.skopintsev.models.gui.clients;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

public class ClientsPage {

    @Getter static final SelenideElement pageTitle = $(byTestId("pageTitle"));

    @Getter static final SelenideElement countLabel = $(byTestId("countLabel"));

    @Getter static final SelenideElement countValue = $(byTestId("countValue"));

    @Getter static final SelenideElement countLabelActive = $(byTestId("countLabelActive"));

    @Getter static final SelenideElement countValueActive = $(byTestId("countValueActive"));

    @Getter static final SelenideElement countLabelBlocked = $(byTestId("countLabelBlocked"));

    @Getter static final SelenideElement countValueBlocked = $(byTestId("countValueBlocked"));

    @Getter static final SelenideElement warningNote = $(byTestId("warningNote"));
}
