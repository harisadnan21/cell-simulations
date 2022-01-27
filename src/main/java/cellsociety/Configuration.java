package cellsociety;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Configuration {
    private int numRows;
    private int numColumns;

    private int simulationType;

    private String title;
    private String author;
    private String description;

    private int[][] cellConfig;

    public void parseXML(String filename) {
        String filePath = "./data/" + filename + ".xml";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(filePath);
            Element rootElement = doc.getDocumentElement();
            NodeList nodes = rootElement.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element currentElement = (Element) nodes.item(i);

                    if(currentElement.getNodeName().equals("SimulationParameters")) {
                        //handle simulation parameters
                    } else {
                        switch (currentElement.getNodeName()) {
                            case "NumberOfRows":
                                numRows = Integer.parseInt(currentElement.getTextContent());
                                break;
                            case "NumberOfColumns":
                                numColumns = Integer.parseInt(currentElement.getTextContent());
                                break;
                            case "SimulationType":
                                simulationType = Integer.parseInt(currentElement.getTextContent());
                                break;
                            case "Title":
                                title = currentElement.getTextContent();
                                break;
                            case "Author":
                                author = currentElement.getTextContent();
                                break;
                            case "Description":
                                description = currentElement.getTextContent();
                                break;
                            case "StartingConfig":
                                setCellConfig(currentElement.getTextContent());
                                break;

                        }
                    }


//                    if (el.getNodeName().contains("staff")) {
//                        String name = el.getElementsByTagName("name").item(0).getTextContent();
//                        String phone = el.getElementsByTagName("phone").item(0).getTextContent();
//                        String email = el.getElementsByTagName("email").item(0).getTextContent();
//                        String area = el.getElementsByTagName("area").item(0).getTextContent();
//                        String city = el.getElementsByTagName("city").item(0).getTextContent();

                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

//        System.out.println(numRows);
//        System.out.println(numColumns);
//        System.out.println(simulationType);
//        System.out.println(title);
//        System.out.println(author);
//        System.out.println(description);
        for (int i = 0; i < cellConfig.length; i++) {
            for (int j = 0; j < cellConfig[0].length; j++) {
                System.out.print(cellConfig[i][j] + " ");
            }
            System.out.println();
        }





    }

    private void setCellConfig(String config) {
        cellConfig = new int[numRows][numColumns];

        String[] configChars = config.split(" ");

        if(cellConfig.length * cellConfig[0].length != configChars.length) {
            System.out.println("Incorrect config string length");
            return;
        }

        for (int i = 0; i < cellConfig.length; i++) {
            for (int j = 0; j < cellConfig[0].length; j++) {
                cellConfig[i][j] = Integer.parseInt(configChars[i + j]);
            }
        }
    }
}