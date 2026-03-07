package org.skopintsev.models.gui.common.tables;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.skopintsev.util.helpers.SelenideHelper.byTestId;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class Table {

    @Getter static final ElementsCollection tableItems = $$(byXpath("//tbody/tr"));
    @Getter static final SelenideElement emptyTableMsg =$(byText("Nothing found"));

    public static SelenideElement getTableBody(SelenideElement table) {
        return table.$("tbody");
    }

    public static ElementsCollection getTableRows(SelenideElement table) {
        return getTableBody(table).$$("tr");
    }

    public static SelenideElement getTableRow(int index, SelenideElement table) {
        return getTableRows(table).get(index);
    }

    public static SelenideElement getTableRowElement(
            int index, SelenideElement table, String cellId) {
        SelenideElement row = getTableRow(index, table);
        return row.$(byTestId(cellId));
    }
    
    public static SelenideElement getRowElementById(SelenideElement tableItem, String cellId) {
        return tableItem.$(byTestId(cellId));
    }
}
