package sectors.subsectors;

import org.junit.jupiter.api.*;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.database.sectors.SectorDb;
import org.skopintsev.database.sectors.SectorDbHelper;
import org.skopintsev.database.sectors.subsectors.SubSectorDbHelper;
import org.skopintsev.helper.GeneratorBuilder;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseSubSectorTest {

    protected final String BASE_SECTOR_CODE = GeneratorBuilder.generateTestCode();
    protected final String BASE_SECTOR_NAME = GeneratorBuilder.generateString(10);
    protected final String BASE_SECTOR_DESCRIPTION = GeneratorBuilder.generateString(100);

    @BeforeAll
    public void beforeAll() {
        SectorDb baseSectorDb = SectorDb.builder()
                .sectorCode(BASE_SECTOR_CODE)
                .sectorName(BASE_SECTOR_NAME)
                .description(BASE_SECTOR_DESCRIPTION)
                .build();
        int rowsInserted = SectorDbHelper.insertSector(baseSectorDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);
    }

    @AfterAll
    public void afterAll() {
        SectorDbHelper.deleteSector(BASE_SECTOR_CODE);
    }

    @BeforeEach
    public void setUp() {
        SubSectorDbHelper.deleteAllTestSubSectors();
    }

    @AfterEach
    public void tearDown() {
        SubSectorDbHelper.deleteAllTestSubSectors();
    }
}
