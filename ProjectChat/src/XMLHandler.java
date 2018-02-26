
import java.awt.*;
import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

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
    
    
    public void setName(String Name){
        name = Name;
    }

    //Reads XML from input and outputs readable text to show in chat window
    public void ReadXML(String input) {
        //string to edit and later use as output
        String currentString = "";

        //Build the background for using DOM to parse and create XML
        try {
            File inputText = new File(input);
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputText);
            doc.getDocumentElement().normalize();

            //if false the start tag is not right
            if (doc.getDocumentElement().getNodeName().equals("message")) {
                //Attach name and : to text to be displayed
                currentString += doc.getDocumentElement().getAttribute("name")
                        + ": ";
                NodeList textNodes = doc.getElementsByTagName("text");
                for (int i = 0; i < textNodes.getLength(); i++) {
                    //Om man har <text> jajdasjasdl <encrypted>hasd</encrypted> jaasdklj </text> hur funkar det då?
                    Element eElement = (Element) textNodes.item(i);
                    String textColor = eElement.getAttribute("text");
                    currentString += eElement.getElementsByTagName("text")
                            .item(0).getTextContent();
                    if (eElement.hasChildNodes()) {
                        NodeList cryptoNodes = eElement.getElementsByTagName("encrypted");
                        NodeList fatNodes = eElement.getElementsByTagName("fetstil");
                        NodeList cursiveNodes = eElement.getElementsByTagName("kursiv");
                        if (cryptoNodes.getLength() != 0) {
                            //try-catch catch if no key ask for key
                            //Crypto krypto = new Crypto(keyFromSender);
                            for (int j = 0; j < cryptoNodes.getLength(); j++) {
                                Element cElement = (Element) cryptoNodes.item(j);
                                //krypto.deCrypt(cElement.getTextContent());

                            }
                        }
                        if (fatNodes.getLength() != 0) {
                            for (int j = 0; j < fatNodes.getLength(); j++) {
                                Element fElement = (Element) fatNodes.item(j);
                                //send fetstil to image and append text
                            }
                        }
                        if (cursiveNodes.getLength() != 0) {
                            for (int j = 0; j < cursiveNodes.getLength(); j++) {
                                Element curElement = (Element) cursiveNodes.item(j);
                                //send kursiv to image and append text

                            }
                        }

                    }

                }

                if (doc.getElementsByTagName("filerequest").getLength()
                        != 0) {
                    Element fElement = (Element) doc.getElementsByTagName("filerequest");
                    FileHandler fileHandler = new FileHandler();
                    //fileHandler.setSize(fElement.getAttribute("size"));
                    //fileHandler.setText(fElement.getAttribute("name") + fElement.getTextContent());
                    //fileHandler.initiateHandling(); ??
                }

                if (doc.getElementsByTagName("fileresponse").getLength()
                        != 0) {
                    Element rElement = (Element) doc.getElementsByTagName("fileresponse");
                    //currentFileHandlerBeingUsed.answer(rElement.getAttribute("reply"));
                    //currentFileHandlerBeingUsed.sendFile(rElement.getAttribute("port"));
                }

            }

            output = currentString;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String sendText() {
        return output;
    }

    public void writeXML(String text) {

        try {
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("message");
            Attr attrName = doc.createAttribute("name");
            attrName.setValue(name);
            rootElement.setAttributeNode(attrName);

            Element textElement = doc.createElement("text");
            Attr attrColor = doc.createAttribute("color");
            attrColor.setNodeValue(color);
            textElement.setAttributeNode(attrColor);
            textElement.appendChild(doc.createTextNode(text));

            rootElement.appendChild(textElement);

            output = toString(doc);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void writeFileRequest(String input, File file, String type, String key) {

        try {
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("message");
            Attr attrName = doc.createAttribute("name");
            attrName.setValue(name);
            rootElement.setAttributeNode(attrName);

            Element fileElement = doc.createElement("fileresponse");
            Attr attrFileName = doc.createAttribute("name");
            Attr attrSize = doc.createAttribute("size");
            Attr attrType = doc.createAttribute("type");
            Attr attrKey = doc.createAttribute("key");
            
            attrFileName.setNodeValue(file.getName());
            String size = String.valueOf(file.length());
            attrSize.setNodeValue(size);
            attrType.setNodeValue(type);
            attrKey.setNodeValue(key);
            
            fileElement.setAttributeNode(attrFileName);     
            fileElement.setAttributeNode(attrSize);
            fileElement.setAttributeNode(attrType);
            fileElement.setAttributeNode(attrKey);
            fileElement.appendChild(doc.createTextNode(input));

            rootElement.appendChild(fileElement);
            
            output = toString(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public void writeFileResponse(String input, String reply, int port, String key) {

        try {
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("message");
            Attr attrName = doc.createAttribute("name");
            attrName.setValue(name);
            rootElement.setAttributeNode(attrName);

            Element fileElement = doc.createElement("fileresponse");
            Attr attrReply = doc.createAttribute("reply");
            Attr attrPort = doc.createAttribute("port");
            Attr attrKey = doc.createAttribute("key");
            
            attrReply.setNodeValue(reply);
            String portString = String.valueOf(port);
            attrPort.setNodeValue(portString);
            attrKey.setNodeValue(key);
            
            fileElement.setAttributeNode(attrReply);     
            fileElement.setAttributeNode(attrPort);
            fileElement.setAttributeNode(attrKey);
            fileElement.appendChild(doc.createTextNode(input));

            rootElement.appendChild(fileElement);
            
            output = toString(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    //From stackOverflow https://stackoverflow.com/questions/2567416/xml-document-to-string
    public static String toString(Document doc) {
    try {
        StringWriter sw = new StringWriter();
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        transformer.transform(new DOMSource(doc), new StreamResult(sw));
        return sw.toString();
    } catch (Exception ex) {
        throw new RuntimeException("Error converting to String", ex);
    }
}
}
