package vault;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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
import java.util.stream.Stream;

import static org.skopintsev.constants.Constants.SC_OK;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VaultOperationWithdrawTest extends BaseVaultTest {

    final String WITHDRAW_OPERATION = TransactionType.WITHDRAW.getText();
    final int INITIAL_AMOUNT = 100;
    final int AMOUNT_TO_WITHDRAW = 10;

    String vaultCode;
    VaultDb vaultDbWithPositiveAmount;
    VaultOperation vaultOperation;

    @BeforeEach
    public void beforeEach() {
        vaultDbWithPositiveAmount = VaultDbFactory.amountVaultDbRequest(
                BASE_CLIENT_CODE,
                BigInteger.valueOf(INITIAL_AMOUNT),
                BASE_CURRENCY_CODE
        );
        vaultCode = vaultDbWithPositiveAmount.getVaultCode();
        vaultOperation = VaultOperation.builder()
                .vaultCode(vaultCode)
                .operationName(WITHDRAW_OPERATION)
                .amount(AMOUNT_TO_WITHDRAW)
                .build();

        VaultTransactionsDbHelper.deleteAllTestVaultTransactions();
        int rowsInserted = VaultDbHelper.insertVault(vaultDbWithPositiveAmount);
        CommonDbAssertions.checkRowsInserted(rowsInserted);
    }

    @AfterEach
    public void afterEach() {
        VaultTransactionsDbHelper.deleteAllTestVaultTransactions();
    }

    @ParameterizedTest(name = "[{index}] int amountToWithdraw = {0}")
    @MethodSource("withdrawAmountProvider")
    @Tag("smoke")
    @Description(
        """
        Test uses API to withdraw amount:
        1) less than vault.amount,
        2) equals to vault.amount.
        """)
    @Severity(SeverityLevel.BLOCKER)
    public void withdrawOperationTest(int amountToWithdraw) {
        BigInteger expectedNewBalance = amountToWithdraw == INITIAL_AMOUNT ?
                BigInteger.valueOf(0) : BigInteger.valueOf(1);

        vaultOperation.setAmount(amountToWithdraw);
        PostApiReqHelper.saveVaultOperationAndValidate(vaultOperation, SC_OK);

        vaultDbWithPositiveAmount = VaultDbHelper.selectVaultByCode(vaultCode);
        VaultDbAssertions.checkVaultField("amount", vaultDbWithPositiveAmount.getAmount(),
                expectedNewBalance);

        VaultTransactionsDb expectedVaultTransactionsDb = VaultTransactionsDb.builder()
                .vaultCode(vaultCode)
                .operationType(WITHDRAW_OPERATION)
                .amount(BigInteger.valueOf(amountToWithdraw))
                .newBalance(expectedNewBalance)
                .transactionTime(LocalDateTime.now().minusMinutes(1L))
                .build();
        VaultTransactionsDb vaultTransactionsDb = VaultTransactionsDbHelper.selectLastVaultTransactionByTime();
        VaultTransactionsDbAssertions.checkVaultTransactionDbMatchesExpectedParams(vaultTransactionsDb,
                expectedVaultTransactionsDb);
        VaultTransactionsDbAssertions.checkVaultTransactionDbTime(vaultTransactionsDb.getTransactionTime(),
                expectedVaultTransactionsDb.getTransactionTime());
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to insert amount > 0 multiple times.")
    @Severity(SeverityLevel.BLOCKER)
    public void multipleWithdrawOperationsTest() {
        int transactionsCountOld = VaultTransactionsDbHelper.getVaultTransactionsCount();
        int timesToWithdraw = GeneratorBuilder.generateRandomNumberInclusive(2, 10);

        Allure.step("Performing operation WITHDRAW AMOUNT " + timesToWithdraw + " times.");
        Allure.step("Initial vault amount = " + vaultDbWithPositiveAmount.getAmount() + ".");
        saveVaultOperationMultipleTimes(vaultOperation, SC_OK, timesToWithdraw);

        vaultDbWithPositiveAmount = VaultDbHelper.selectVaultByCode(vaultCode);
        Allure.step("Final vault amount = " + vaultDbWithPositiveAmount.getAmount() + ".");
        VaultDbAssertions.checkVaultField("amount", vaultDbWithPositiveAmount.getAmount(),
                BigInteger.valueOf(INITIAL_AMOUNT - AMOUNT_TO_WITHDRAW * timesToWithdraw));

        int transactionsCountNew = VaultTransactionsDbHelper.getVaultTransactionsCount();
        CommonDbAssertions.checkCounts(transactionsCountNew, transactionsCountOld + timesToWithdraw);
    }

    // todo: invalid withdraw - amount > vault.amount

    // todo: invalid withdraw - vault.isArchived = true



    private Stream<Arguments> withdrawAmountProvider() {
        return Stream.of(
                Arguments.of(INITIAL_AMOUNT - 1),
                Arguments.of(INITIAL_AMOUNT)
        );
    }

    public void saveVaultOperationMultipleTimes(VaultOperation vaultOperation, int expectedStatus, int times) {
        for (int i = 0; i < times; i++) {
            PostApiReqHelper.saveVaultOperationAndValidate(vaultOperation, expectedStatus);
        }
    }

}
