package ProjectChat;

import java.awt.*;
import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

public class XMLHandler {

    private String notMyColor = "#000000";
    private String notMyName;
    public Boolean isRequest = false;

    public XMLHandler() {
    }

    public String getColor() {
        return notMyColor;
    }

    //Reads XML from input and outputs readable text to show in chat window
    public String ReadXML(String input) {
        //string to edit and later use as output
        String output = "";
        System.out.println("Starts to read XML");
        System.out.println(input);

        if (input.equals( "<request reply=\"no\"</request>")) {
            return "Nekad anslutning";
        }
        if (input.equals("<message><disconnect /></message>")==true) {
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
                //currentString += doc.getDocumentElement().getAttribute("sender")
                //        + ": ";

                NodeList textNodes = doc.getElementsByTagName("text");
                if (textNodes.getLength() > 1) {
                    output += "Felaktigt antal text taggar. Vi får: " + "\n";
                } else if (textNodes.getLength() != 0) {
                    Element eElement = (Element) textNodes.item(0);
                    if (eElement.getAttribute("color").length() != 7) {
                        System.out.println("Som hexa " + eElement.getAttribute("color"));
                        System.out.println("Som RGB " + Color.decode(eElement.getAttribute("color")));
                        return "Wrong colorformat";
                    } else {
                        notMyColor = eElement.getAttribute("color");
                    }
                    output += eElement.getTextContent();
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
                                //System.out.println("Finds fat. Round " + j);
                                //currentString += fElement.getTextContent();
                                //send fetstil to image and append text
                            }
                        }
                        if (cursiveNodes.getLength() != 0) {
                            for (int j = 0; j < cursiveNodes.getLength(); j++) {
                                Element curElement
                                        = (Element) cursiveNodes.item(j);
                                //currentString += curElement.getTextContent();
                                //send kursiv to image and append text

                            }
                        }

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
        System.out.println(output);
        //if (input.equals(writeXML(output, notMyName, notMyColor))) {
        //}
        System.out.println(input);
        System.out.println(writeXML(output, notMyName, notMyColor));
        System.out.println(writeXML("hej", notMyName, notMyColor));
        return notMyName + ": " + output;
    }

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

    public String writeRequest(String text, String name, String color) {
        //text = text.replace("&", "&amp");
        //text = text.replace("<", "&lt");
        //text = text.replace(">", "&gt");
        //text = text.replace("\"", "&quot");
        Component compositeXML = new Component(text, name, color);
        return compositeXML.getRequest();
    }

    public String writeDecline() {
        return "<request reply=\"no\"</request>";
    }

    public String writeDisconnect() {
        return "<message><disconnect /></message>";
    }

//    public String writeXML(String text) {
//        System.out.println("Starts to write XML");
//        System.out.println("Is trying with text: " + text);
//        try {
//            DocumentBuilderFactory dbFactory
//                    = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//            Document doc = dBuilder.newDocument();
//
//            System.out.println("Is creating doc");
//            
//            Element rootElement = doc.createElement("message");
//            Attr attrName = doc.createAttribute("sender");
//            attrName.setValue(notMyName);
//            rootElement.setAttributeNode(attrName);
//            doc.appendChild(rootElement);
//
//            Element textElement = doc.createElement("text");
//            Attr attrColor = doc.createAttribute("color");
//            attrColor.setNodeValue(notMyColor);
//            textElement.setAttributeNode(attrColor);
//            rootElement.appendChild(textElement);
//            textElement.appendChild(doc.createTextNode(text));
//
//            rootElement.appendChild(textElement);
//
//            return toString(doc);
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Could not write XML properly";
//        }
//
//  }
//    
//    
//    public static String toString(Document doc) {
//    try {
//        StringWriter sw = new StringWriter();
//        TransformerFactory tf = TransformerFactory.newInstance();
//        Transformer transformer = tf.newTransformer();
//        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
//        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
//        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//
//        transformer.transform(new DOMSource(doc), new StreamResult(sw));
//        return sw.toString();
//    } catch (Exception ex) {
//        throw new RuntimeException("Error converting to String", ex);
//    }
//}
}
