package sectors.subsectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.skopintsev.database.sectors.subsectors.SubSectorDbHelper;

public class BaseSubSectorTest {
    @BeforeEach
    public void setUp() {
        SubSectorDbHelper.deleteAllTestSubSectors();
    }

    @AfterEach
    public void tearDown() {
        SubSectorDbHelper.deleteAllTestSubSectors();
    }
}
