package vault;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.skopintsev.database.vaults.VaultDbHelper;

public class BaseVaultTest {
    @BeforeEach
    public void setUp() {
        VaultDbHelper.deleteAllTestVaults();
    }

    @AfterEach
    public void tearDown() {
        VaultDbHelper.deleteAllTestVaults();
    }
}
