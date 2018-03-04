package ProjectChat;

import java.awt.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

public class XMLHandler {

    private Crypto AESKrypto;

    private Crypto CaesarKrypto;

    private String output;

    private String name;

    private String color = "#000000";

    public XMLHandler() {
        AESKrypto = new Crypto("AES");
        CaesarKrypto = new Crypto("Caesar");
    }

    public void setName(String inName) {
        name = inName;
    }
    public String getName(){
        return name;
    }

    public void setColor(String inColor) {
        color = inColor;
    }

    public String getColor() {
        return color;
    }

    //Reads XML from input and outputs readable text to show in chat window
    public String ReadXML(String input) {
        //string to edit and later use as output
        String currentString = "";
        System.out.println("Starts to read XML");

        //Build the background for using DOM to parse and create XML
        try {
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(input));
            Document doc = dBuilder.parse(is);
            doc.getDocumentElement().normalize();
            
            if (doc.getDocumentElement().getNodeName().equals("request")){
                Element rElement = (Element) doc.getElementsByTagName("request").item(0);
                currentString += rElement.getTextContent();
            }
            
            
            //if false the start tag is not right
            if (doc.getDocumentElement().getNodeName().equals("message")) {
                //Attach name and : to text to be displayed
                System.out.println("Finds message tag");
                currentString += doc.getDocumentElement().getAttribute("sender")
                        + ": ";
                //Martin lagt till följande rad:
                name = doc.getDocumentElement().getAttribute("sender");
                
                NodeList textNodes = doc.getElementsByTagName("text");
                for (int i = 0; i < textNodes.getLength(); i++) {
                    Element eElement = (Element) textNodes.item(i);
                    color = eElement.getAttribute("color");
                    currentString += eElement.getTextContent();
                    if (eElement.hasChildNodes()) {
                        NodeList cryptoNodes
                                = eElement.getElementsByTagName("encrypted");
                        NodeList fatNodes
                                = eElement.getElementsByTagName("fetstil");
                        NodeList cursiveNodes
                                = eElement.getElementsByTagName("kursiv");
                        if (cryptoNodes.getLength() != 0) {
                            //try-catch catch if no key ask for key
                            //Crypto krypto = new Crypto(keyFromSender);
                            for (int j = 0; j < cryptoNodes.getLength(); j++) {
                                Element cElement
                                        = (Element) cryptoNodes.item(j);
                                //krypto.deCrypt(cElement.getTextContent());

                            }
                        }
                        if (fatNodes.getLength() != 0) {
                            for (int j = 0; j < fatNodes.getLength(); j++) {
                                Element fElement = (Element) fatNodes.item(j);
                                currentString += fElement.getTextContent();
                                //send fetstil to image and append text
                            }
                        }
                        if (cursiveNodes.getLength() != 0) {
                            for (int j = 0; j < cursiveNodes.getLength(); j++) {
                                Element curElement
                                        = (Element) cursiveNodes.item(j);
                                currentString += curElement.getTextContent();
                                //send kursiv to image and append text

                            }
                        }

                    }

                }

            }
            //If starttag not right
            else{
                output = "Wrong textformat";
            }

            output = currentString;

        } catch (Exception e) {
            e.printStackTrace();
        }
            return output;
    }


    public String writeXML(String text) {
        Component compositeXML = new Component(text, name, color);
        output = compositeXML.getText();
        return output;
    }
    
    public String writeRequest(String text) {
        Component compositeXML = new Component(text, name, color);
        output = compositeXML.getRequest();
        return output;
    }
}
