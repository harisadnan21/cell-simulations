package cellsociety;

import java.util.List;
import java.util.Map;

public record SimulationData(int simulationType, int numRows, int numColumns, String title, String description,
                             String author, CellState[][] startingConfig, Map<String, String> params) {
    // Name in data file that will indicate it represents data for this type of object
    public static final String DATA_TYPE = "SimulationData";
    // Field names expected to appear in data file holding values for this object. These fields are commons across
    // all SimulationTypes
    public static final List<String> GENERIC_DATA_FIELDS = List.of("SimulationType", "NumberOfRows", "NumberOfColumns",
                                                            "Title", "Description", "Author", "StartingConfig")
    public SimulationData(Map<String, String> configValues) {

    }
}
