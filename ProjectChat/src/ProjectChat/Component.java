/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjectChat;

/**
 *
 * @author mastah
 */
public class Component {

    String returnText;
    String text;
    String name;
    String color;

    public Component(String inputText, String newName, String newColor) {
        text = inputText;
        name = newName;
        color = newColor;
    }

    class XMLMessage {

        XMLText subText = new XMLText();

        public XMLMessage() {
        }
        
        class XMLText {

            public XMLText() {

            }

            public String toString() {

                return "<text color=" + "\"" + color + "\"" + ">" + text + "</text>";
                //return "<text>" + text + "</text>";
            }

        }

        public String toString() {

            return "<message sender=" + "\"" + name + "\"" + ">" + subText.toString() + "</message>";
            //return "<message>" + subText.toString() + "</message>";
        }
    }
    class XMLFatStyle {

        public String toString() {

            return "<fetstil>" + text + "</fetstil>";
        }
    }

    class XMLItalic {

        public String toString() {

            return "<kursiv>" + text + "</kursiv>";
        }
    }
    
    class XMLRequest {

                public String toString() {
                    return "<request>" + text + "</request>";
                }
    }
//Ej klar
    public String getText() {

        XMLMessage message = new XMLMessage();
        returnText = message.toString();

        return returnText;
    }
    
    public String getRequest(){
        
        XMLRequest request = new XMLRequest();
        returnText = request.toString();
       
        return returnText;
    }
}
