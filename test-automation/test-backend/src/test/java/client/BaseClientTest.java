package client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.skopintsev.database.clients.ClientDbHelper;

public class BaseClientTest {
    @BeforeEach
    public void setUp() {
        ClientDbHelper.deleteAllTestClients();
    }

    @AfterEach
    public void tearDown() {
        ClientDbHelper.deleteAllTestClients();
    }
}
