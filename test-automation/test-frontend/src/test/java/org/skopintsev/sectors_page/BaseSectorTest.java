package org.skopintsev.sectors_page;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.skopintsev.BaseTest;
import org.skopintsev.models.api.Sector;
import org.skopintsev.models.api.SubSector;
import org.skopintsev.models.api.factory.SectorFactory;
import org.skopintsev.models.api.factory.SubSectorFactory;
import org.skopintsev.transport.PostApiResponseHelper;
import org.skopintsev.util.OpenUrl;

import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public class BaseSectorTest extends BaseTest {

    List<Sector> BASE_SECTORS_LIST = SectorFactory.generateSectorList();
    List<SubSector> BASE_SUB_SECTORS_LIST = SubSectorFactory.generateSubSectorList(
            BASE_SECTORS_LIST.get(0).getSectorCode());

    @BeforeEach
    public void openSectorsPage() {
        PostApiResponseHelper.stubGetSectors(BASE_SECTORS_LIST);
        PostApiResponseHelper.stubGetSubSectors(BASE_SUB_SECTORS_LIST);
        OpenUrl.openSectorsPage();
    }


    protected String getSectorName(String sectorCode) {
        Sector sector = BASE_SECTORS_LIST
                .stream()
                .filter(s -> s.getSectorCode().equals(sectorCode))
                .findFirst()
                .orElse(null);
        return sector.getSectorName();
    }
}
