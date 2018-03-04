/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjectChat;

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
    
    
    public void writeFileRequest(String input, File file,
            String type, String key) {

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

}
