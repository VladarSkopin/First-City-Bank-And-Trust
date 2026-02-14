package org.skopintsev.model.factory;

import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.model.Vault;

import java.math.BigInteger;

public class VaultApiFactory {

    public static Vault defaultVaultApiRequest(
            String clientCode, String currencyCode) {

        return Vault.builder()
                .vaultCode(GeneratorBuilder.generateTestCode())
                .clientCode(clientCode)
                .amount(BigInteger.valueOf(GeneratorBuilder.generateAmount()))
                .currencyCode(currencyCode)
                .isArchived(false)
                .build();
    }

    public static Vault codeVaultApiRequest(
            String vaultCode, String clientCode, String currencyCode) {

        return Vault.builder()
                .vaultCode(vaultCode)
                .clientCode(clientCode)
                .amount(BigInteger.valueOf(GeneratorBuilder.generateAmount()))
                .currencyCode(currencyCode)
                .isArchived(false)
                .build();
    }

    public static Vault amountVaultApiRequest(
            String clientCode, BigInteger amount, String currencyCode) {

        return Vault.builder()
                .vaultCode(GeneratorBuilder.generateTestCode())
                .clientCode(clientCode)
                .amount(amount)
                .currencyCode(currencyCode)
                .isArchived(false)
                .build();
    }

}
