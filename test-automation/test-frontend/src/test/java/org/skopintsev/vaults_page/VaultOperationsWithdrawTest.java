package org.skopintsev.vaults_page;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.skopintsev.assertions.common.elements.ButtonElementAssertions;
import org.skopintsev.assertions.common.windows.ModalWindowAssertions;
import org.skopintsev.assertions.vault.VaultOperationsWindowAssertions;
import org.skopintsev.enums.OperationTypeEnum;
import org.skopintsev.models.api.Client;
import org.skopintsev.models.api.Currency;
import org.skopintsev.models.api.factory.VaultFactory;
import org.skopintsev.models.api.vault.Vault;
import org.skopintsev.models.api.vault.VaultOperationRequest;
import org.skopintsev.models.api.vault.VaultOperationResponse;
import org.skopintsev.steps.vault.VaultCardSteps;
import org.skopintsev.steps.vault.VaultOperationsWindowSteps;
import org.skopintsev.transport.CheckApiRequestHelper;
import org.skopintsev.transport.PostApiResponseHelper;
import org.skopintsev.util.helpers.AmountHelper;

import java.math.BigInteger;
import java.util.List;

public class VaultOperationsWithdrawTest extends BaseVaultTest {

    final String CURRENCY_CODE = BASE_CURRENCIES_LIST.get(0).getCurrencyCode();
    final Client client = BASE_CLIENTS_LIST.stream()
            .filter(c -> c.getIsBlocked() == false)
            .findFirst()
            .orElse(null);
    final Vault vault = VaultFactory.generateVault(
            client.getClientCode(),
            CURRENCY_CODE,
            false);
    final String VALIDATION_MESSAGE_WITHIN_BALANCE = "✅ Within available balance";

    @Test
    @Tag("smoke")
    @Description("Test checks the withdraw operation when amount to withdraw equals the vault balance.")
    @Severity(SeverityLevel.BLOCKER)
    public void withdrawAmountEqualsToBalanceTest() {
        PostApiResponseHelper.stubGetVaults(List.of(vault));
        VaultOperationResponse vaultOperationResponse = VaultOperationResponse.builder().build();
        PostApiResponseHelper.stubPostVaultOperation(vaultOperationResponse);
        Selenide.refresh();

        VaultCardSteps.clickWithdrawBtn();

        ModalWindowAssertions.checkHeaderText("Withdraw from Vault " + vault.getVaultCode());
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

        int amountToWithdraw = vault.getAmount().intValue();
        VaultOperationsWindowSteps.typeInputAmount(amountToWithdraw);
        VaultOperationsWindowAssertions.checkAmountInput(AmountHelper.formatAmount(BigInteger.valueOf(amountToWithdraw)));
        VaultOperationsWindowAssertions.checkValidationMessage(VALIDATION_MESSAGE_WITHIN_BALANCE);

        VaultOperationsWindowAssertions.checkCancelBtnEnabled(true);
        VaultOperationsWindowAssertions.checkCancelBtnText("CANCEL");
        VaultOperationsWindowAssertions.checkSubmitBtnEnabled(true);
        VaultOperationsWindowAssertions.checkSubmitBtnText("CONFIRM");

        VaultOperationsWindowSteps.clickSubmitBtn();

        VaultOperationRequest vaultOperationRequest = VaultOperationRequest.builder()
                .operationName(OperationTypeEnum.WITHDRAW.getText())
                .amount((long) amountToWithdraw)
                .vaultCode(vault.getVaultCode())
                .build();
        CheckApiRequestHelper.checkRequestFoundByContainsJson(vaultOperationRequest);
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the withdraw operation when amount to withdraw is less than the vault balance.")
    @Severity(SeverityLevel.BLOCKER)
    public void withdrawAmountLessThanBalanceTest() {
        PostApiResponseHelper.stubGetVaults(List.of(vault));
        VaultOperationResponse vaultOperationResponse = VaultOperationResponse.builder().build();
        PostApiResponseHelper.stubPostVaultOperation(vaultOperationResponse);
        Selenide.refresh();

        VaultCardSteps.clickWithdrawBtn();

        int amountToWithdraw = vault.getAmount().intValue() - 1;
        VaultOperationsWindowSteps.typeInputAmount(amountToWithdraw);
        VaultOperationsWindowAssertions.checkAmountInput(AmountHelper.formatAmount(BigInteger.valueOf(amountToWithdraw)));
        VaultOperationsWindowAssertions.checkValidationMessage(VALIDATION_MESSAGE_WITHIN_BALANCE);

        VaultOperationsWindowAssertions.checkCancelBtnEnabled(true);
        VaultOperationsWindowAssertions.checkCancelBtnText("CANCEL");
        VaultOperationsWindowAssertions.checkSubmitBtnEnabled(true);
        VaultOperationsWindowAssertions.checkSubmitBtnText("CONFIRM");

        VaultOperationsWindowSteps.clickSubmitBtn();

        VaultOperationRequest vaultOperationRequest = VaultOperationRequest.builder()
                .operationName(OperationTypeEnum.WITHDRAW.getText())
                .amount((long) amountToWithdraw)
                .vaultCode(vault.getVaultCode())
                .build();
        CheckApiRequestHelper.checkRequestFoundByContainsJson(vaultOperationRequest);
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the withdraw operation when amount to withdraw exceeds the vault balance.")
    @Severity(SeverityLevel.BLOCKER)
    public void withdrawAmountExceedsBalanceTest() {
        PostApiResponseHelper.stubGetVaults(List.of(vault));
        VaultOperationResponse vaultOperationResponse = VaultOperationResponse.builder().build();
        PostApiResponseHelper.stubPostVaultOperation(vaultOperationResponse);
        Selenide.refresh();

        VaultCardSteps.clickWithdrawBtn();

        int amountToWithdraw = vault.getAmount().intValue() + 1;
        VaultOperationsWindowSteps.typeInputAmount(amountToWithdraw);
        VaultOperationsWindowAssertions.checkAmountInput(AmountHelper.formatAmount(BigInteger.valueOf(amountToWithdraw)));
        VaultOperationsWindowAssertions.checkValidationMessage(
                "❌ Exceeds available balance of " + AmountHelper.formatAmount(vault.getAmount()));

        VaultOperationsWindowAssertions.checkCancelBtnEnabled(true);
        VaultOperationsWindowAssertions.checkCancelBtnText("CANCEL");
        VaultOperationsWindowAssertions.checkSubmitBtnEnabled(false);
        VaultOperationsWindowAssertions.checkSubmitBtnText("CONFIRM");

        VaultOperationRequest vaultOperationRequest = VaultOperationRequest.builder()
                .operationName(OperationTypeEnum.WITHDRAW.getText())
                .amount((long) amountToWithdraw)
                .vaultCode(vault.getVaultCode())
                .build();
        CheckApiRequestHelper.checkRequestNotFoundByContainsJson(vaultOperationRequest);
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
