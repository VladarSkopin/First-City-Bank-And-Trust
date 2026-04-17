package org.skopintsev.vaults_page;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.skopintsev.assertions.common.elements.ButtonElementAssertions;
import org.skopintsev.assertions.common.windows.ModalWindowAssertions;
import org.skopintsev.assertions.vault.VaultCardAssertions;
import org.skopintsev.assertions.vault.VaultOperationsWindowAssertions;
import org.skopintsev.enums.OperationTypeEnum;
import org.skopintsev.models.api.client.Client;
import org.skopintsev.models.api.Currency;
import org.skopintsev.models.api.factory.VaultFactory;
import org.skopintsev.models.api.vault.Vault;
import org.skopintsev.models.api.vault.VaultOperationRequest;
import org.skopintsev.models.api.vault.VaultOperationResponse;
import org.skopintsev.steps.vault.VaultCardSteps;
import org.skopintsev.steps.vault.VaultOperationsWindowSteps;
import org.skopintsev.transport.CheckApiRequestHelper;
import org.skopintsev.transport.PostApiResponseHelper;
import org.skopintsev.util.GeneratorBuilder;
import org.skopintsev.util.helpers.AmountHelper;

import java.math.BigInteger;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class VaultOperationsDepositTest extends BaseVaultTest {

    final String CURRENCY_CODE = BASE_CURRENCIES_LIST.get(0).getCurrencyCode();
    final Client client = BASE_CLIENTS_LIST.stream()
            .filter(c -> c.getIsBlocked() == false)
            .findFirst()
            .orElse(null);
    final Vault vault = VaultFactory.generateVault(
            client.getClientCode(),
            CURRENCY_CODE,
            false);

    @Test
    @Tag("smoke")
    @Description("Test checks the deposit operation with zero amount.")
    @Severity(SeverityLevel.BLOCKER)
    public void depositZeroAmountTest() {
        vault.setAmount(BigInteger.valueOf(0));
        PostApiResponseHelper.stubGetVaults(List.of(vault));
        Selenide.refresh();

        VaultCardSteps.clickDepositBtn();

        ModalWindowAssertions.checkHeaderText("Deposit to Vault " + vault.getVaultCode());
        ModalWindowAssertions.checkModalBodyMinHeight(300);
        ModalWindowAssertions.checkModalBodyMinWidth(600);
        ButtonElementAssertions.checkCrossCloseBtnIsVisible();
        ButtonElementAssertions.checkCrossCloseBtnEnabled(true);

        VaultOperationsWindowAssertions.checkClientLabel("Client:");
        VaultOperationsWindowAssertions.checkClientName(client.getNameOrTitle());
        VaultOperationsWindowAssertions.checkVaultLabel("Vault ID:");
        VaultOperationsWindowAssertions.checkVaultCode(vault.getVaultCode());
        VaultOperationsWindowAssertions.checkCurrentBalanceLabel("Current Balance:");
        VaultOperationsWindowAssertions.checkCurrentBalanceValue(AmountHelper.formatAmount(vault.getAmount()));
        VaultOperationsWindowAssertions.checkAmountLabel("Enter Amount (" + getCurrencyName(CURRENCY_CODE) + "):");
        VaultOperationsWindowAssertions.checkAmountInput("");
        VaultOperationsWindowAssertions.checkInputHint("Enter numeric value only.");

        VaultOperationsWindowAssertions.checkCancelBtnEnabled(true);
        VaultOperationsWindowAssertions.checkCancelBtnText("CANCEL");
        VaultOperationsWindowAssertions.checkSubmitBtnEnabled(false);
        VaultOperationsWindowAssertions.checkSubmitBtnText("CONFIRM");
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the deposit operation with empty vault.")
    @Severity(SeverityLevel.BLOCKER)
    public void depositPositiveAmountEmptyVaultTest() {
        vault.setAmount(BigInteger.valueOf(0));
        PostApiResponseHelper.stubGetVaults(List.of(vault));
        Selenide.refresh();

        VaultCardSteps.clickDepositBtn();
        int randomAmount = GeneratorBuilder.generateAmount();
        VaultOperationsWindowSteps.typeInputAmount(randomAmount);
        VaultOperationsWindowAssertions.checkAmountInput(AmountHelper.formatAmount(BigInteger.valueOf(randomAmount)));

        VaultOperationsWindowAssertions.checkCancelBtnEnabled(true);
        VaultOperationsWindowAssertions.checkCancelBtnText("CANCEL");
        VaultOperationsWindowAssertions.checkSubmitBtnEnabled(true);
        VaultOperationsWindowAssertions.checkSubmitBtnText("CONFIRM");

        VaultOperationsWindowSteps.clickCancelBtn();
        VaultCardAssertions.checkBalanceValueText("0");

        VaultOperationRequest vaultOperationRequest = VaultOperationRequest.builder()
                .operationName(OperationTypeEnum.DEPOSIT.getText())
                .amount((long) randomAmount)
                .vaultCode(vault.getVaultCode())
                .build();
        CheckApiRequestHelper.checkRequestNotFoundByContainsJson(vaultOperationRequest);
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the deposit operation with non-empty vault.")
    @Severity(SeverityLevel.BLOCKER)
    public void depositPositiveAmountNonEmptyVaultTest() {
        Vault vault = VaultFactory.generateVault(
                CURRENCY_CODE,
                client.getClientCode(),
                false);
        PostApiResponseHelper.stubGetVaults(List.of(vault));

        VaultOperationResponse vaultOperationResponse = VaultOperationResponse.builder().build();
        PostApiResponseHelper.stubPostVaultOperation(vaultOperationResponse);

        Selenide.refresh();

        VaultCardSteps.clickDepositBtn();
        int randomAmount = GeneratorBuilder.generateAmount();
        VaultOperationsWindowSteps.typeInputAmount(randomAmount);
        VaultOperationsWindowAssertions.checkAmountInput(AmountHelper.formatAmount(BigInteger.valueOf(randomAmount)));

        VaultOperationsWindowAssertions.checkCancelBtnEnabled(true);
        VaultOperationsWindowAssertions.checkCancelBtnText("CANCEL");
        VaultOperationsWindowAssertions.checkSubmitBtnEnabled(true);
        VaultOperationsWindowAssertions.checkSubmitBtnText("CONFIRM");

        VaultOperationsWindowSteps.clickSubmitBtn();

        VaultOperationRequest vaultOperationRequest = VaultOperationRequest.builder()
                .operationName(OperationTypeEnum.DEPOSIT.getText())
                .amount((long) randomAmount)
                .vaultCode(vault.getVaultCode())
                .build();
        CheckApiRequestHelper.checkRequestFoundByContainsJson(vaultOperationRequest);
    }


    public String getCurrencyName(String currencyCode) {
        Currency currency = BASE_CURRENCIES_LIST
                .stream()
                .filter(c -> c.getCurrencyCode().equals(currencyCode))
                .findFirst()
                .orElse(null);
        return currency.getCurrencyName();
    }
}
