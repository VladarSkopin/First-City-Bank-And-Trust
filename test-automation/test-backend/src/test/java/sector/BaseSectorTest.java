package sector;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.skopintsev.database.sectors.SectorDbHelper;

public class BaseSectorTest {
    @BeforeEach
    public void setUp() {
        SectorDbHelper.deleteAllTestSectors();
    }

    @AfterEach
    public void tearDown() {
        SectorDbHelper.deleteAllTestSectors();
    }
}
