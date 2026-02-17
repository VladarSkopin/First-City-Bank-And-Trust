package vault;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.*;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.assertions.db.vaults.VaultDbAssertions;
import org.skopintsev.assertions.db.vaults.VaultTransactionsDbAssertions;
import org.skopintsev.database.factory.VaultDbFactory;
import org.skopintsev.database.vaults.VaultDb;
import org.skopintsev.database.vaults.VaultDbHelper;
import org.skopintsev.database.vaults.VaultTransactionsDb;
import org.skopintsev.database.vaults.VaultTransactionsDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.helper.enums.TransactionType;
import org.skopintsev.model.vaults.VaultOperation;
import org.skopintsev.transport.PostApiReqHelper;

import java.math.BigInteger;
import java.time.LocalDateTime;

import static org.skopintsev.constants.Constants.SC_OK;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VaultOperationInsertTest extends BaseVaultTest {

    final String INSERT_OPERATION = TransactionType.INSERT.getText();
    final int AMOUNT_TO_INSERT = GeneratorBuilder.generateAmount();

    VaultDb vaultDbZeroAmount = VaultDbFactory.amountVaultDbRequest(
            BASE_CLIENT_CODE,
            BigInteger.valueOf(0),
            BASE_CURRENCY_CODE
    );

    @BeforeEach
    public void beforeEach() {
        VaultTransactionsDbHelper.deleteAllVaultTransactions();
        int rowsInserted = VaultDbHelper.insertVault(vaultDbZeroAmount);
        CommonDbAssertions.checkRowsInserted(rowsInserted);
    }

    @AfterEach
    public void afterEach() {
        VaultTransactionsDbHelper.deleteAllVaultTransactions();
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to insert amount > 0.")
    @Severity(SeverityLevel.BLOCKER)
    public void insertPositiveAmountTest() {
        String vaultCode = vaultDbZeroAmount.getVaultCode();
        VaultOperation vaultOperation = VaultOperation.builder()
                .vaultCode(vaultCode)
                .operationName(INSERT_OPERATION)
                .amount(AMOUNT_TO_INSERT)
                .build();

        PostApiReqHelper.saveVaultOperationAndValidate(vaultOperation, SC_OK);

        vaultDbZeroAmount = VaultDbHelper.selectVaultByCode(vaultCode);
        VaultDbAssertions.checkVaultField("amount", vaultDbZeroAmount.getAmount(),
                BigInteger.valueOf(vaultOperation.getAmount()));

        VaultTransactionsDb expectedVaultTransactionsDb = VaultTransactionsDb.builder()
                .vaultCode(vaultCode)
                .operationType(INSERT_OPERATION)
                .amount(BigInteger.valueOf(AMOUNT_TO_INSERT))
                .newBalance(BigInteger.valueOf(AMOUNT_TO_INSERT))
                .transactionTime(LocalDateTime.now().minusMinutes(1L))
                .build();
        VaultTransactionsDb vaultTransactionsDb = VaultTransactionsDbHelper.selectLastVaultTransactionByTime();
        VaultTransactionsDbAssertions.checkVaultTransactionDbMatchesExpectedParams(vaultTransactionsDb,
                expectedVaultTransactionsDb);
        VaultTransactionsDbAssertions.checkVaultTransactionDbTime(vaultTransactionsDb.getTransactionTime(),
                expectedVaultTransactionsDb.getTransactionTime());
    }

    // todo: multiple inserts

    // todo: invalid insert - amount = 0

    // todo: invalid insert - amount < 0

    // todo: invalid insert - amount = null

    // todo: invalid insert - vaultCode = "", " ", null, random

    // todo: invalid operation - "", " ", null, random

    // todo: invalid operation - vault.isArchived = true

}
