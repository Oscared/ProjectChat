
import java.io.*;
import javax.xml.parsers.*;import org.w3c.dom.Document;
import org.w3c.dom.*;


public class XMLHandler {
    
    private byte key;

    private String output;

    private Document doc;

    public XMLHandler(String input) {
        //Build the background for using DOM to parse and create XML
        try {
            File inputText = new File(input);
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputText);
            doc.getDocumentElement().normalize();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Reads XML from input and outputs readable text to show in chat window
    public void ReadXML() {
        //string to edit and later use as output
        String currentString = "";

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
                        //call on request key from sender
                        //get key
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
    }

    public String sendText() {
        return output;
    }

    public void writeXML(String text) {
        String currentString = "";
        
        
        
        
        output = currentString;
    }
}
