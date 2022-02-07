package cellsociety;

import cellsociety.Model.CellularAutomataAlgorithm;
import cellsociety.Model.FallingSand;
import cellsociety.Model.GameOfLife;
import cellsociety.Model.Percolation;
import cellsociety.Model.SchellingSegregation;
import cellsociety.Model.SimulationData;
import cellsociety.Model.SpreadingOfFire;
import cellsociety.Model.WaTor;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class handles parsing XML files and returning a completed object.
 *
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 * @author Edison Ooi
 */
public class ConfigurationXMLParser {

  // Readable error message that can be displayed by the GUI
  public static final String ERROR_MESSAGE = "XML file does not represent %s";
  // name of root attribute that notes the type of file expecting to parse
  private final String TYPE_ATTRIBUTE;
  // keep only one documentBuilder because it is expensive to make and can reset it before parsing
  private final DocumentBuilder DOCUMENT_BUILDER;


  /**
   * Create parser for XML files of given type.
   */
  public ConfigurationXMLParser(String type) throws XMLException {
    DOCUMENT_BUILDER = getDocumentBuilder();
    TYPE_ATTRIBUTE = type;
  }

  /**
   * Get data contained in this XML file as an object
   */
  public SimulationData getSimulationData(File dataFile) throws XMLException {
    Element root = getRootElement(dataFile);

    // read data associated with the fields given by the object
    Map<String, String> genericSimulationValues = new HashMap<>();
    for (String field : SimulationData.GENERIC_DATA_FIELDS) {
      genericSimulationValues.put(field, getTextValue(root, field));
    }

    Map<String, String> specificSimulationParams = getSpecificParams(root,
        Integer.parseInt(genericSimulationValues.get("SimulationType")));

    return new SimulationData(genericSimulationValues, specificSimulationParams);
  }

  // get root element of an XML file
  private Element getRootElement(File xmlFile) throws XMLException {
    try {
      DOCUMENT_BUILDER.reset();
      Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
      return xmlDocument.getDocumentElement();
    } catch (SAXException | IOException e) {
      throw new XMLException(e);
    }
  }

  // returns if this is a valid XML file for the specified object type
  private boolean isValidFile(Element root, String type) {
    return getAttribute(root, TYPE_ATTRIBUTE).equals(type);
  }

  // get value of Element's attribute
  private String getAttribute(Element e, String attributeName) {
    return e.getAttribute(attributeName);
  }

  // get value of Element's text
  private String getTextValue(Element e, String tagName) {
    NodeList nodeList = e.getElementsByTagName(tagName);
    if (nodeList.getLength() > 0) {
      return nodeList.item(0).getTextContent();
    } else {
      return "";
    }
  }

  private Map<String, String> getSpecificParams(Element root, int algorithmType) {
    Map<String, String> params = new HashMap<>();

    List<String> keys = new ArrayList<>();

    switch (algorithmType) {
      case CellularAutomata.GAME_OF_LIFE -> keys = GameOfLife.SPECIFIC_PARAMS;
      case CellularAutomata.PERCOLATION -> keys = Percolation.SPECIFIC_PARAMS;
      case CellularAutomata.SCHELLING_SEGREGATION -> keys = SchellingSegregation.SPECIFIC_PARAMS;
      case CellularAutomata.SPREADING_OF_FIRE -> keys = SpreadingOfFire.SPECIFIC_PARAMS;
      case CellularAutomata.WATOR -> keys = WaTor.SPECIFIC_PARAMS;
      case CellularAutomata.FALLING_SAND -> keys = FallingSand.SPECIFIC_PARAMS;
    }

    for (String key : keys) {
      params.put(key, getTextValue(root, key));
    }

    return params;
  }

  // boilerplate code needed to make ANY DocumentBuilder
  private DocumentBuilder getDocumentBuilder() throws XMLException {
    try {
      return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new XMLException(e);
    }
  }
}