package vault;

import io.qameta.allure.*;
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
import org.skopintsev.helper.enums.TransactionTypeEnum;
import org.skopintsev.model.vaults.VaultOperation;
import org.skopintsev.transport.api.VaultsApiClient;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.skopintsev.constants.Constants.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VaultOperationInsertTest extends BaseVaultTest {

    final String INSERT_OPERATION = TransactionTypeEnum.INSERT.getText();
    final int AMOUNT_TO_INSERT = GeneratorBuilder.generateAmount();

    String vaultCode;
    VaultDb vaultDbZeroAmount;
    VaultOperation vaultOperation;

    @BeforeEach
    public void beforeEach() {
        vaultDbZeroAmount = VaultDbFactory.amountVaultDbRequest(
                BASE_CLIENT_CODE,
                BigInteger.valueOf(0),
                BASE_CURRENCY_CODE
        );
        vaultCode = vaultDbZeroAmount.getVaultCode();
        vaultOperation = VaultOperation.builder()
                .vaultCode(vaultCode)
                .operationName(INSERT_OPERATION)
                .amount(AMOUNT_TO_INSERT)
                .build();

        VaultTransactionsDbHelper.deleteAllTestVaultTransactions();
        int rowsInserted = VaultDbHelper.insertVault(vaultDbZeroAmount);
        CommonDbAssertions.checkRowsInserted(rowsInserted);
    }

    @AfterEach
    public void afterEach() {
        VaultTransactionsDbHelper.deleteAllTestVaultTransactions();
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to insert amount > 0.")
    @Severity(SeverityLevel.BLOCKER)
    public void insertOperationWithPositiveAmountTest() {
        VaultsApiClient.saveVaultOperationAndValidate(vaultOperation, SC_OK);

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

    @Test
    @Tag("smoke")
    @Description("Test uses API to insert amount > 0 multiple times.")
    @Severity(SeverityLevel.BLOCKER)
    public void multipleInsertOperationsTest() {
        int transactionsCountOld = VaultTransactionsDbHelper.getVaultTransactionsCount();
        int timesToInsert = GeneratorBuilder.generateRandomNumberInclusive(2, 10);

        Allure.step("Performing operation INSERT AMOUNT " + timesToInsert + " times.");
        Allure.step("Initial vault amount = " + vaultDbZeroAmount.getAmount() + ".");
        saveVaultOperationMultipleTimes(vaultOperation, SC_OK, timesToInsert);

        vaultDbZeroAmount = VaultDbHelper.selectVaultByCode(vaultCode);
        Allure.step("Final vault amount = " + vaultDbZeroAmount.getAmount() + ".");
        VaultDbAssertions.checkVaultField("amount", vaultDbZeroAmount.getAmount(),
                BigInteger.valueOf((long) AMOUNT_TO_INSERT * timesToInsert));

        int transactionsCountNew = VaultTransactionsDbHelper.getVaultTransactionsCount();
        CommonDbAssertions.checkCounts(transactionsCountNew, transactionsCountOld + timesToInsert);
    }

    @ParameterizedTest(name = "[{index}] int amount = {0}")
    @MethodSource("amountProvider")
    @Tag("smoke")
    @Description(
        """
        Test uses API to insert:
        1) amount = 0,
        2) amount = -1,
        3) amount = null
        """)
    @Severity(SeverityLevel.BLOCKER)
    public void insertOperationWithInvalidAmount(Integer amount) {
        int transactionsCountOld = VaultTransactionsDbHelper.getVaultTransactionsCount();
        Allure.step("Initial vault amount = " + vaultDbZeroAmount.getAmount() + ".");

        VaultOperation vaultOperationZeroAmount = VaultOperation.builder()
                .vaultCode(vaultCode)
                .operationName(INSERT_OPERATION)
                .amount(amount)
                .build();
        VaultsApiClient.saveVaultOperationAndValidate(vaultOperationZeroAmount, SC_SERVER_ERROR);

        vaultDbZeroAmount = VaultDbHelper.selectVaultByCode(vaultCode);
        Allure.step("Final vault amount = " + vaultDbZeroAmount.getAmount() + ".");
        VaultDbAssertions.checkVaultField("amount", vaultDbZeroAmount.getAmount(),
                BigInteger.ZERO);

        int transactionsCountNew = VaultTransactionsDbHelper.getVaultTransactionsCount();
        CommonDbAssertions.checkCounts(transactionsCountNew, transactionsCountOld);
    }

    @ParameterizedTest(name = "[{index}] String invalidVaultCode = {0}")
    @MethodSource("testCodeInvalidProvider")
    @Tag("regression")
    @Description(
        """
        Test uses API to post a transaction operation:
        1) with vault code = null,
        2) with vault code = empty string,
        3) with vault code = whitespace,
        4) with vault code absent on the database.
        """)
    @Severity(SeverityLevel.NORMAL)
    public void insertOperationWithInvalidVaultCode(String invalidVaultCode) {
        int transactionsCountOld = VaultTransactionsDbHelper.getVaultTransactionsCount();

        vaultOperation.setVaultCode(invalidVaultCode);
        VaultsApiClient.saveVaultOperationAndValidate(
                vaultOperation,
                invalidVaultCode != null && !invalidVaultCode.trim().isEmpty() ? SC_NOT_FOUND : SC_SERVER_ERROR);

        int transactionsCountNew = VaultTransactionsDbHelper.getVaultTransactionsCount();
        CommonDbAssertions.checkCounts(transactionsCountNew, transactionsCountOld);
    }

    @ParameterizedTest(name = "[{index}] String invalidTransactionName = {0}")
    @MethodSource("testCodeInvalidProvider")
    @Tag("regression")
    @Description(
        """
        Test uses API to post a transaction operation:
        1) with operation name = null,
        2) with operation name = empty string,
        3) with operation name = whitespace,
        4) with invalid operation name.
        """)
    @Severity(SeverityLevel.NORMAL)
    public void insertOperationWithInvalidOperationName(String invalidTransactionName) {
        int transactionsCountOld = VaultTransactionsDbHelper.getVaultTransactionsCount();

        vaultOperation.setOperationName(invalidTransactionName);
        VaultsApiClient.saveVaultOperationAndValidate(vaultOperation, SC_SERVER_ERROR);

        int transactionsCountNew = VaultTransactionsDbHelper.getVaultTransactionsCount();
        CommonDbAssertions.checkCounts(transactionsCountNew, transactionsCountOld);
    }

    // tried different possible ways, but did not function properly with a @MethodSource
    @Test
    @Tag("regression")
    @Description(
        """
        Test uses API to post a transaction operation:
        1) with vault code that needs to be trimmed,
        2) with vault code in lowercase,
        3) with vault code in mixed case.
        """)
    @Flaky
    public void insertOperationValidVaultCodeTest() {
        List<String> vaultCodeVariations = List.of(
                " " + vaultCode + " ",
                vaultCode.toLowerCase(),
                vaultCode.charAt(0) + vaultCode.substring(1).toLowerCase()
        );

        for (String vaultCodeVariation : vaultCodeVariations) {
            int transactionsCountOld = VaultTransactionsDbHelper.getVaultTransactionsCount();

            VaultOperation vaultOperation = VaultOperation.builder()
                    .vaultCode(vaultCodeVariation)
                    .operationName(INSERT_OPERATION)
                    .amount(AMOUNT_TO_INSERT)
                    .build();
            VaultsApiClient.saveVaultOperationAndValidate(vaultOperation, SC_OK);

            int transactionsCountNew = VaultTransactionsDbHelper.getVaultTransactionsCount();
            CommonDbAssertions.checkCounts(transactionsCountNew, transactionsCountOld + 1);
        }
    }

    @ParameterizedTest(name = "[{index}] String operationName = {0}")
    @MethodSource("operationNameValidProvider")
    @Tag("regression")
    @Description(
        """
        Test uses API to post a transaction operation:
        1) with operation name that needs to be trimmed,
        2) with operation name in lowercase,
        3) with operation name in mixed case.
        """)
    @Severity(SeverityLevel.NORMAL)
    public void insertOperationValidOperationNameTest(String operationName) {
        int transactionsCountOld = VaultTransactionsDbHelper.getVaultTransactionsCount();

        vaultOperation.setOperationName(operationName);
        VaultsApiClient.saveVaultOperationAndValidate(vaultOperation, SC_OK);

        int transactionsCountNew = VaultTransactionsDbHelper.getVaultTransactionsCount();
        CommonDbAssertions.checkCounts(transactionsCountNew, transactionsCountOld + 1);
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to insert amount for an archived vault (isArchived = true).")
    @Severity(SeverityLevel.CRITICAL)
    public void insertOperationForArchivedVault() {
        int transactionsCountOld = VaultTransactionsDbHelper.getVaultTransactionsCount();

        VaultDb vaultDbArchived = VaultDbFactory.defaultVaultDbRequest(
                BASE_CLIENT_CODE,
                BASE_CURRENCY_CODE
        );
        vaultDbArchived.setIsArchived(true);
        VaultDbHelper.insertVault(vaultDbArchived);

        vaultOperation.setVaultCode(vaultDbArchived.getVaultCode());
        VaultsApiClient.saveVaultOperationAndValidate(vaultOperation, SC_SERVER_ERROR);

        int transactionsCountNew = VaultTransactionsDbHelper.getVaultTransactionsCount();
        CommonDbAssertions.checkCounts(transactionsCountNew, transactionsCountOld);
    }


    public void saveVaultOperationMultipleTimes(VaultOperation vaultOperation, int expectedStatus, int times) {
        for (int i = 0; i < times; i++) {
            VaultsApiClient.saveVaultOperationAndValidate(vaultOperation, expectedStatus);
        }
    }

    private Stream<Arguments> amountProvider() {
        return Stream.of(
                Arguments.of(0),
                Arguments.of(-1),
                Arguments.of((Object) null)
        );
    }

    private Stream<Arguments> testCodeInvalidProvider() {
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of(GeneratorBuilder.generateString(5))
        );
    }

    private Stream<Arguments> operationNameValidProvider() {
        return Stream.of(
                Arguments.of(" " + INSERT_OPERATION + " "),
                Arguments.of(INSERT_OPERATION.toLowerCase()),
                Arguments.of(INSERT_OPERATION.charAt(0) + INSERT_OPERATION.substring(1).toLowerCase())
        );
    }
}
