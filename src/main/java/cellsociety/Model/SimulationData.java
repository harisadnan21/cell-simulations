package cellsociety.Model;

import java.util.List;
import java.util.Map;

public record SimulationData(int simulationType, int numRows, int numColumns, String title,
                             String description,
                             String author, String startingConfig, Map<String, String> params) {

  // Name in data file that will indicate it represents data for this type of object
  public static final String DATA_TYPE = "CellAutomata";
  // Field names expected to appear in data file holding values for this object. These fields are commons across all SimulationTypes
  public static final List<String> GENERIC_DATA_FIELDS = List.of("SimulationType", "NumberOfRows",
      "NumberOfColumns",
      "Title", "Description", "Author", "StartingConfig");

  public SimulationData(Map<String, String> genericValues, Map<String, String> simulationParams) {
    this(Integer.parseInt(genericValues.get(GENERIC_DATA_FIELDS.get(0))),
        Integer.parseInt(genericValues.get(GENERIC_DATA_FIELDS.get(1))),
        Integer.parseInt(genericValues.get(GENERIC_DATA_FIELDS.get(2))),
        genericValues.get(GENERIC_DATA_FIELDS.get(3)),
        genericValues.get(GENERIC_DATA_FIELDS.get(4)),
        genericValues.get(GENERIC_DATA_FIELDS.get(5)),
        genericValues.get(GENERIC_DATA_FIELDS.get(6)),
        simulationParams);
  }


}
