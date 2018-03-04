/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjectChat;

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author oscar
 */
public class ExtraFunc {
    //    public void writeXML(String text) {
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
//            attrName.setValue(name);
//            rootElement.setAttributeNode(attrName);
//            doc.appendChild(rootElement);
//
//            Element textElement = doc.createElement("text");
//            Attr attrColor = doc.createAttribute("color");
//            attrColor.setNodeValue(color);
//            textElement.setAttributeNode(attrColor);
//            rootElement.appendChild(textElement);
//            textElement.appendChild(doc.createTextNode(text));
//
//            rootElement.appendChild(textElement);
//
//            output = toString(doc);
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//  }
//    
//    
//    public void writeFileRequest(String input, File file,
//            String type, String key) {
//
//        try {
//            DocumentBuilderFactory dbFactory
//                    = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//            Document doc = dBuilder.newDocument();
//
//            Element rootElement = doc.createElement("message");
//            Attr attrName = doc.createAttribute("name");
//            attrName.setValue(name);
//            rootElement.setAttributeNode(attrName);
//
//            Element fileElement = doc.createElement("fileresponse");
//            Attr attrFileName = doc.createAttribute("name");
//            Attr attrSize = doc.createAttribute("size");
//            Attr attrType = doc.createAttribute("type");
//            Attr attrKey = doc.createAttribute("key");
//
//            attrFileName.setNodeValue(file.getName());
//            String size = String.valueOf(file.length());
//            attrSize.setNodeValue(size);
//            attrType.setNodeValue(type);
//            attrKey.setNodeValue(key);
//
//            fileElement.setAttributeNode(attrFileName);
//            fileElement.setAttributeNode(attrSize);
//            fileElement.setAttributeNode(attrType);
//            fileElement.setAttributeNode(attrKey);
//            fileElement.appendChild(doc.createTextNode(input));
//
//            rootElement.appendChild(fileElement);
//
//            output = toString(doc);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void writeFileResponse(String input, String reply, int port, String key) {
//
//        try {
//            DocumentBuilderFactory dbFactory
//                    = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//            Document doc = dBuilder.newDocument();
//
//            Element rootElement = doc.createElement("message");
//            Attr attrName = doc.createAttribute("name");
//            attrName.setValue(name);
//            rootElement.setAttributeNode(attrName);
//
//            Element fileElement = doc.createElement("fileresponse");
//            Attr attrReply = doc.createAttribute("reply");
//            Attr attrPort = doc.createAttribute("port");
//            Attr attrKey = doc.createAttribute("key");
//
//            attrReply.setNodeValue(reply);
//            String portString = String.valueOf(port);
//            attrPort.setNodeValue(portString);
//            attrKey.setNodeValue(key);
//
//            fileElement.setAttributeNode(attrReply);
//            fileElement.setAttributeNode(attrPort);
//            fileElement.setAttributeNode(attrKey);
//            fileElement.appendChild(doc.createTextNode(input));
//
//            rootElement.appendChild(fileElement);
//
//            output = toString(doc);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//public String ReadXML(String input) {
//        //string to edit and later use as output
//        String currentString = "";
//        System.out.println("Starts to read XML");
//
//        //Build the background for using DOM to parse and create XML
//        try {
//            DocumentBuilderFactory dbFactory
//                    = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//            InputSource is = new InputSource();
//            is.setCharacterStream(new StringReader(input));
//            Document doc = dBuilder.parse(is);
//            doc.getDocumentElement().normalize();
//            
//            if (doc.getDocumentElement().getNodeName().equals("request")){
//                Element rElement = (Element) doc.getElementsByTagName("request").item(0);
//                currentString += rElement.getTextContent();
//            }
//            
//            
//            //if false the start tag is not right
//            if (doc.getDocumentElement().getNodeName().equals("message")) {
//                //Attach name and : to text to be displayed
//                System.out.println("Finds message tag");
//                currentString += doc.getDocumentElement().getAttribute("sender")
//                        + ": ";
//                //Martin lagt till följande rad:
//                name = doc.getDocumentElement().getAttribute("sender");
//                
//                NodeList textNodes = doc.getElementsByTagName("text");
//                for (int i = 0; i < textNodes.getLength(); i++) {
//                    Element eElement = (Element) textNodes.item(i);
//                    color = eElement.getAttribute("color");
//                    currentString += eElement.getTextContent();
//                    if (eElement.hasChildNodes()) {
//                        NodeList cryptoNodes
//                                = eElement.getElementsByTagName("encrypted");
//                        NodeList fatNodes
//                                = eElement.getElementsByTagName("fetstil");
//                        NodeList cursiveNodes
//                                = eElement.getElementsByTagName("kursiv");
//                        if (cryptoNodes.getLength() != 0) {
//                            //try-catch catch if no key ask for key
//                            //Crypto krypto = new Crypto(keyFromSender);
//                            for (int j = 0; j < cryptoNodes.getLength(); j++) {
//                                Element cElement
//                                        = (Element) cryptoNodes.item(j);
//                                //krypto.deCrypt(cElement.getTextContent());
//
//                            }
//                        }
//                        if (fatNodes.getLength() != 0) {
//                            for (int j = 0; j < fatNodes.getLength(); j++) {
//                                Element fElement = (Element) fatNodes.item(j);
//                                currentString += fElement.getTextContent();
//                                //send fetstil to image and append text
//                            }
//                        }
//                        if (cursiveNodes.getLength() != 0) {
//                            for (int j = 0; j < cursiveNodes.getLength(); j++) {
//                                Element curElement
//                                        = (Element) cursiveNodes.item(j);
//                                currentString += curElement.getTextContent();
//                                //send kursiv to image and append text
//
//                            }
//                        }
//
//                    }
//
//                }
//
//                if (doc.getElementsByTagName("filerequest").getLength()
//                        != 0) {
//                    Element fElement = (Element) doc.getElementsByTagName("filerequest");
//                    FileHandler fileHandler = new FileHandler();
//                    //fileHandler.setSize(fElement.getAttribute("size"));
//                    //fileHandler.setText(fElement.getAttribute("name") + fElement.getTextContent());
//                    //fileHandler.initiateHandling(); ??
//                }
//
//                if (doc.getElementsByTagName("fileresponse").getLength()
//                        != 0) {
//                    Element rElement = (Element) doc.getElementsByTagName("fileresponse");
//                    //currentFileHandlerBeingUsed.answer(rElement.getAttribute("reply"));
//                    //currentFileHandlerBeingUsed.sendFile(rElement.getAttribute("port"));
//                }
//
//            }
//
//            output = currentString;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//            return output;
//    }
}
