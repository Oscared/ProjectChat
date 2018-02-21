
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class XMLHandler {

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
        String currentString;
        
        //if not correct the tags are not right
        if (doc.getDocumentElement().getNodeName() == "message") {
            currentString = doc.getDocumentElement().getAttribute("name")
                    + ": ";
            NodeList textNodes = doc.getElementsByTagName("text");
            for (int i = 0; i < textNodes.getLength(); i++) {
                Element eElement = (Element) textNodes.item(i);
                String textColor = eElement.getAttribute("text");
                currentString += eElement.getElementsByTagName("text")
                        .item(0).getTextContent();
                if (eElement.hasChildNodes()) {
                    eElement
                }
            }
        }
    }

    public String sendText() {
        return output;
    }

    public String writeXML(String text) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
