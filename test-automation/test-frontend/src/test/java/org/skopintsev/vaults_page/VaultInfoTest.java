package org.skopintsev.vaults_page;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.skopintsev.assertions.common.elements.ButtonElementAssertions;
import org.skopintsev.assertions.vault.VaultCardAssertions;
import org.skopintsev.assertions.vault.VaultsPageAssertions;
import org.skopintsev.models.api.Client;
import org.skopintsev.models.api.Currency;
import org.skopintsev.models.api.Vault;
import org.skopintsev.models.api.factory.VaultFactory;
import org.skopintsev.transport.PostApiResponseHelper;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class VaultInfoTest extends BaseVaultTest {

    String CURRENCY_CODE = BASE_CURRENCIES_LIST.get(0).getCurrencyCode();

    @Test
    @Tag("smoke")
    @Description("Test checks the display of Vaults page information.")
    @Severity(SeverityLevel.BLOCKER)
    public void vaultsPageInfoTest() {
        VaultsPageAssertions.checkPageTitleText("First City Bank & Trust Vault");
        VaultsPageAssertions.checkActiveVaultsCountLabelText("ACTIVE VAULTS: ");
        VaultsPageAssertions.checkActiveVaultsCountValueText(
                BASE_VAULTS_LIST.stream()
                        .filter(v -> v.getIsArchived() != true)
                        .count());
        VaultsPageAssertions.checkCurrenciesCountLabelText("UNIQUE CURRENCIES: ");
        VaultsPageAssertions.checkCurrenciesCountValueText(
                BASE_VAULTS_LIST.stream()
                        .map(Vault::getCurrencyCode)
                        .collect(Collectors.toSet())
                        .size());
        ButtonElementAssertions.checkRetryBtnExists(false);
    }

    @ParameterizedTest(name = "[{index}] boolean isBlockedClient = {0}")
    @ValueSource(booleans = {true, false})
    @Tag("smoke")
    @Description(
            """
            Test checks the display of a single vault card information:
            "1) when client is blocked,
            "2) when client is active.
            """)
    @Severity(SeverityLevel.BLOCKER)
    public void vaultCardInfoTest(boolean isBlockedClient) {
        Client client = BASE_CLIENTS_LIST.stream()
                .filter(c -> c.getIsBlocked() == isBlockedClient)
                .findFirst()
                .orElse(null);
        Vault vault = VaultFactory.generateVault(
                client.getClientCode(),
                CURRENCY_CODE,
                false);
        List<Vault> vaultsList = List.of(vault);
        PostApiResponseHelper.stubGetVaults(vaultsList);
        Selenide.refresh();

        VaultCardAssertions.checkVaultTitle("VAULT " + vault.getVaultCode());
        VaultCardAssertions.checkCurrencyName(getCurrencyName(CURRENCY_CODE));
        VaultCardAssertions.checkClientIdLabelText("CLIENT ID:");
        VaultCardAssertions.checkClientCodeText(vault.getClientCode());
        VaultCardAssertions.checkClientNameLabelText("CLIENT:");
        VaultCardAssertions.checkClientNameValueText(
                isBlockedClient ? client.getNameOrTitle() + " (BLOCKED)" : client.getNameOrTitle());
        VaultCardAssertions.checkStatusLabelText("STATUS:");
        VaultCardAssertions.checkStatusValueText(
                isBlockedClient ? "BLOCKED 🔒" : "ACTIVE ✅"
        );
        VaultCardAssertions.checkLastAccessLabelText("LAST ACCESS:");
        VaultCardAssertions.checkLastAccessDateTimeText(formatLocalDateTime(vault.getModifiedAt()));
        VaultCardAssertions.checkBalanceLabelText("CURRENT BALANCE:");
        VaultCardAssertions.checkBalanceValueText(formatAmount(vault.getAmount()));
        VaultCardAssertions.checkDepositBtnText("DEPOSIT");
        VaultCardAssertions.checkDepositBtnEnabled(isBlockedClient ? false : true);
        VaultCardAssertions.checkWithdrawBtnText("WITHDRAW");
        VaultCardAssertions.checkWithdrawBtnEnabled(isBlockedClient ? false : true);
    }

    @Test
    @Tag("regression")
    @Description("Test checks the display of a vault card when its balance is zero.")
    @Severity(SeverityLevel.BLOCKER)
    public void vaultsZeroBalanceTest() {
        Client client = BASE_CLIENTS_LIST.stream()
                .filter(c -> !c.getIsBlocked())
                .findFirst()
                .orElse(null);
        Vault vault = VaultFactory.generateVault(
                client.getClientCode(),
                CURRENCY_CODE,
                false);
        vault.setAmount(BigInteger.valueOf(0));
        PostApiResponseHelper.stubGetVaults(List.of(vault));
        Selenide.refresh();

        VaultCardAssertions.checkBalanceLabelText("CURRENT BALANCE:");
        VaultCardAssertions.checkBalanceValueText(formatAmount(vault.getAmount()));
        VaultCardAssertions.checkDepositBtnText("DEPOSIT");
        VaultCardAssertions.checkDepositBtnEnabled(true);
        VaultCardAssertions.checkWithdrawBtnText("WITHDRAW");
        VaultCardAssertions.checkWithdrawBtnEnabled(false);
    }

    @Test
    @Tag("regression")
    @Description("Test checks the display of the Vaults page when all the vaults are archived.")
    @Severity(SeverityLevel.NORMAL)
    public void vaultsOnlyArchivedTest() {
        BASE_VAULTS_LIST.forEach(vault -> vault.setIsArchived(true));
        PostApiResponseHelper.stubGetVaults(BASE_VAULTS_LIST);
        Selenide.refresh();

        VaultsPageAssertions.checkPageTitleText("No Active Vaults");
        ButtonElementAssertions.checkRetryBtnExists(false);
    }

    @Test
    @Tag("regression")
    @Description("Test checks the display of the Vaults page in case of empty response list.")
    @Severity(SeverityLevel.BLOCKER)
    public void vaultsEmptyResponseTest() {
        PostApiResponseHelper.stubGetVaults(Collections.emptyList());
        Selenide.refresh();

        VaultsPageAssertions.checkPageTitleText("No Active Vaults");
        ButtonElementAssertions.checkRetryBtnExists(false);
    }

    @Test
    @Tag("regression")
    @Description("Test checks the display of the Vaults page when no vaults were found.")
    @Severity(SeverityLevel.BLOCKER)
    public void vaultsNotFoundTest() {
        PostApiResponseHelper.stubGetVaultsNotFound(Collections.emptyList());
        Selenide.refresh();

        VaultsPageAssertions.checkPageTitleText("Failed to Load Vaults");
        ButtonElementAssertions.checkRetryBtnExists(true);
        ButtonElementAssertions.checkRetryBtnIsVisible();
        ButtonElementAssertions.checkRetryBtnEnabled(true);
    }

    @Test
    @Tag("regression")
    @Description("Test checks the display of the Vaults page in case of server error response.")
    @Severity(SeverityLevel.BLOCKER)
    public void vaultsServerErrorTest() {
        PostApiResponseHelper.stubGetVaultsServerError(Collections.emptyList());
        Selenide.refresh();

        VaultsPageAssertions.checkPageTitleText("Failed to Load Vaults");
        ButtonElementAssertions.checkRetryBtnExists(true);
        ButtonElementAssertions.checkRetryBtnIsVisible();
        ButtonElementAssertions.checkRetryBtnEnabled(true);
    }


    public String getCurrencyName(String currencyCode) {
        Currency currency = BASE_CURRENCIES_LIST
                .stream()
                .filter(c -> c.getCurrencyCode().equals(currencyCode))
                .findFirst()
                .orElse(null);
        return currency.getCurrencyName();
    }

    public static String formatAmount(BigInteger amount) {
        NumberFormat formatter = NumberFormat.getInstance();
        return formatter.format(amount);
    }

    public static String formatLocalDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy, hh:mm a", Locale.ENGLISH);
        return dateTime.format(formatter);
    }
}
