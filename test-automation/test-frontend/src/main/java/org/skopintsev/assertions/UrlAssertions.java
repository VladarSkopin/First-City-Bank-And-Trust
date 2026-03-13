package org.skopintsev.assertions;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.skopintsev.constants.Constants;

public class UrlAssertions {

    @Step("Check that current URL opened is on the 'VAULT' page.")
    public static void checkVaultsUrl(String url) {
        Assertions.assertThat(url).isEqualTo(Constants.FIRST_CITY_BANK_REACT_URL + "/");
    }

    @Step("Check that current URL opened is on the 'CLIENTS' page.")
    public static void checkClientsUrl(String url) {
        Assertions.assertThat(url).isEqualTo(Constants.CLIENTS_PAGE_URL);
    }

    @Step("Check that current URL opened is on the 'SOCIAL RANKS' page.")
    public static void checkSocialRanksUrl(String url) {
        Assertions.assertThat(url).isEqualTo(Constants.SOCIAL_RANKS_PAGE_URL);
    }

    @Step("Check that current URL opened is on the 'DISTRICTS' page.")
    public static void checkDistrictsUrl(String url) {
        Assertions.assertThat(url).isEqualTo(Constants.DISTRICTS_PAGE_URL);
    }

    @Step("Check that current URL opened is on the 'CURRENCIES' page.")
    public static void checkCurrenciesUrl(String url) {
        Assertions.assertThat(url).isEqualTo(Constants.CURRENCIES_PAGE_URL);
    }

    @Step("Check that current URL opened is on the 'SECTORS' page.")
    public static void checkSectorsUrl(String url) {
        Assertions.assertThat(url).isEqualTo(Constants.SECTORS_PAGE_URL);
    }
}
