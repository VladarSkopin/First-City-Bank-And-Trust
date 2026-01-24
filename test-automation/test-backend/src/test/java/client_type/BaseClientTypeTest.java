package client_type;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.skopintsev.database.client_types.ClientTypeDbHelper;

public class BaseClientTypeTest {
    @BeforeEach
    public void setUp() {
        ClientTypeDbHelper.deleteAllTestClientTypes();
    }

    @AfterEach
    public void tearDown() {
        ClientTypeDbHelper.deleteAllTestClientTypes();
    }
}
