package cellsociety.Model;

import java.util.List;
import java.util.Map;

/**
 * Simple immutable value object representing simulation instance data.
 *
 * @author Edison Ooi
 */
public record SimulationData(int simulationType, int numRows, int numColumns, String title,
                             String description,
                             String author, int numNeighborRows, int numNeighborColumns,
                             String neighborConfig, String startingConfig,
                             Map<String, String> params, boolean shouldWrap) {

  // Name in data file that will indicate it represents data for this type of object
  public static final String DATA_TYPE = "CellAutomata";

  // Field names expected to appear in data file holding values for this object. These fields are commons across all SimulationTypes
  public static final List<String> GENERIC_DATA_FIELDS = List.of("SimulationType", "NumberOfRows",
      "NumberOfColumns",
      "Title", "Description", "Author", "NumberOfNeighborRows", "NumberOfNeighborColumns",
      "NeighborConfig", "StartingConfig", "ShouldWrap");

  /**
   * Create simulation data from key value pairs in Maps extracted from XML.
   *
   * @param genericValues key-value pairs associated with parameters that all simulations have
   * @param simulationParams key-value pairs associated with parameters for specific simulations
   */
  public SimulationData(Map<String, String> genericValues, Map<String, String> simulationParams) {
    this(Integer.parseInt(genericValues.get(GENERIC_DATA_FIELDS.get(0))),
        Integer.parseInt(genericValues.get(GENERIC_DATA_FIELDS.get(1))),
        Integer.parseInt(genericValues.get(GENERIC_DATA_FIELDS.get(2))),
        genericValues.get(GENERIC_DATA_FIELDS.get(3)),
        genericValues.get(GENERIC_DATA_FIELDS.get(4)),
        genericValues.get(GENERIC_DATA_FIELDS.get(5)),
        Integer.parseInt(genericValues.get(GENERIC_DATA_FIELDS.get(6))),
        Integer.parseInt(genericValues.get(GENERIC_DATA_FIELDS.get(7))),
        genericValues.get(GENERIC_DATA_FIELDS.get(8)),
        genericValues.get(GENERIC_DATA_FIELDS.get(9)),
        simulationParams,
        Boolean.getBoolean(GENERIC_DATA_FIELDS.get(10)));
  }

}
