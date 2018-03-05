/**
 * Class XMLHandler
 * Authors Martin Ståhl & Oscar Örnberg
 * Version 1.0
 * Copywrite authors
 */
package ProjectChat;

import java.awt.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

/**
 * Class that handles the XMLing of the text
 *
 * @author mastah
 */
public class XMLHandler {

    public Boolean isRequest = false;
    private String notMyColor = "#000000";
    private String notMyName;

    /**
     * Empty constructor
     */
    public XMLHandler() {
    }

    /**
     * Method that returns color of the other person
     *
     * @return
     */
    public String getColor() {
        return notMyColor;
    }

    /**
     * Reads XML from input and outputs readable text to show in chat window
     *
     * @param input
     * @return
     */
    public String ReadXML(String input) {
        //string to edit and later use as output
        String output = "";
        System.out.println("Starts to read XML");
        System.out.println(input);

        if (input.equals("<request reply=\"no\"</request>")) {
            return "Nekad anslutning";
        }
        if (input.equals("<message><disconnect /></message>") == true) {
            if (notMyName == null) {
                return "Ny ickenamngiven användare har lämnat samtalet";
            } else {
                return notMyName + " har lämnat samtalet";
            }
        }
        //Build the background for using DOM to parse and create XML
        //(Help from internet)
        try {
            DocumentBuilderFactory dbFactory;
            DocumentBuilder dBuilder;
            Document doc;

            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(input));
            doc = dBuilder.parse(is);
            doc.getDocumentElement().normalize();
            if (doc.getDocumentElement().getNodeName().equals("request")) {
                Element rElement = (Element) doc.getElementsByTagName("request").item(0);
                output += rElement.getTextContent();
                isRequest = true;
                return output;
            }

            //if false the start tag is not right
            if (doc.getDocumentElement().getNodeName().equals("message")) {
                //Attach name and : to text to be displayed
                System.out.println("Finds message tag");
                notMyName = doc.getDocumentElement().getAttribute("sender");

                NodeList textNodes = doc.getElementsByTagName("text");
                if (textNodes.getLength() > 1) {
                    output += "Felaktigt antal text taggar. Vi får: " + "\n";
                } else if (textNodes.getLength() != 0) {
                    Element eElement = (Element) textNodes.item(0);

                    if (eElement.getAttribute("color").length() != 7) {
                        return "Wrong colorformat";
                    } else {
                        notMyColor = eElement.getAttribute("color");
                    }

                    System.out.println("First " + eElement.getTextContent());
                    
                    checkTags(eElement);
                    System.out.println("Later " + eElement.getTextContent());
                    output += eElement.getTextContent();
                } else if (textNodes.getLength() == 0) {
                    System.out.println("No text nodes");
                    System.out.println("Text content is: "
                            + doc.getElementsByTagName("message").
                                    item(0).getTextContent());
                    if (doc.getElementsByTagName("message").item(0).
                            getTextContent().equals("<disconnect />")) {
                        return notMyName
                                + " has left the building and disconnected.";
                    }
                }
            } //If starttag not right
            else {
                return "Wrong textformat";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        output = output.replace("&lt;", "<");
        output = output.replace("&gt;", ">");
        output = output.replace("&quot;", "\"");
        output = output.replace("&amp;", "&");

        return notMyName + ": " + output;
    }

    public void checkTags(Element eElement) {
        NodeList childList = eElement.getChildNodes();
        for (int i = 0; i < childList.getLength(); i++) {
            System.out.println("Going through children " + i + " " + childList.item(i).getNodeName());
            if (!("#text".equals(childList.item(i).getNodeName())
                    || "fetstil".equals(childList.item(i).getNodeName())
                    || childList.item(i).getNodeName().equals("kursiv"))) {
                System.out.println(i);
                childList.item(i).getParentNode().removeChild(childList.item(i));
                i--;
            }
            else if(childList.item(i).getNodeName().equals("fetstil") 
                    || childList.item(i).getNodeName().equals("kursiv")){
                checkTags((Element) childList.item(i));
            }
        }
    }

    /**
     * Method that writes XML with the help of the compositeclass
     *
     * @param text
     * @param name
     * @param color
     * @return
     */
    public String writeXML(String text, String name, String color) {
        text = text.replace("&", "&amp;");
        text = text.replace("<", "&lt;");
        text = text.replace(">", "&gt;");
        text = text.replace("\"", "&quot;");
        text = text.replace("\n", "").replace("\r", "");
        System.out.println(text);
        Component compositeXML = new Component(text, name, color);
        return compositeXML.getText();
    }

    /**
     * Method that writes request
     *
     * @param text
     * @param name
     * @param color
     * @return
     */
    public String writeRequest(String text, String name, String color) {
        text = text.replace("&", "&amp;");
        text = text.replace("<", "&lt;");
        text = text.replace(">", "&gt;");
        text = text.replace("\"", "&quot;");
        Component compositeXML = new Component(text, name, color);
        return compositeXML.getRequest();
    }

    /**
     * writes the declineanswer
     *
     * @return
     */
    public String writeDecline() {
        return "<request reply=\"no\"</request>";
    }

    /**
     * writes the disconnect answer
     *
     * @return
     */
    public String writeDisconnect(String ownName) {
        return "<message sender=\"" + ownName
                + "\">&lt;disconnect /&gt;</message>";
    }
}
