package vault;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.TestInstance;
import org.skopintsev.helper.enums.TransactionType;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VaultOperationWithdrawTest extends BaseVaultTest {

    final String WITHDRAW_OPERATION = TransactionType.WITHDRAW.getText();

    // todo: valid withdraw - amount < vault.amount

    // todo: valid withdraw - amount = vault.amount

    // todo: multiple withdrawals

    // todo: invalid withdraw - amount > vault.amount

    // todo: invalid withdraw - vault.isArchived = true

}
