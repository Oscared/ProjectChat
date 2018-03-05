/**
 * Class Component
 * Authors Martin Ståhl & Oscar Örnberg
 * Version 1.0
 * Copywrite authors
 */
package ProjectChat;

/**
 * Class that holds the composite pattern
 * @author mastah
 */
public class Component {

    String returnText;
    String text;
    String name;
    String color;
/**
 * Constructor that takes the text, name and color
 * @param inputText
 * @param newName
 * @param newColor 
 */
    public Component(String inputText, String newName, String newColor) {
        text = inputText;
        name = newName;
        color = newColor;
    }
/**
 * MessageClass that XMLs the entire message
 */
    class XMLMessage {

        XMLText subText = new XMLText();
/**
 * Empty constructor
 */
        public XMLMessage() {
        }
/**
 * Class that handles the textpart of the message
 */
        class XMLText {
            /**
             * Empty constructor
             */

            public XMLText() {

            }
/**
 * toString method that returns the text and holds color
 * @return 
 */
            public String toString() {

                return "<text color=" + "\"" + color + "\"" + ">" + text + 
                        "</text>";
 
            }

        }
/**
 * toString methods that holds the text and calls for the text XML handle part
 * @return 
 */
        public String toString() {

            return "<message sender=" + "\"" + name + "\"" + ">" + 
                    subText.toString() + "</message>";

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

    public String getRequest() {

        XMLRequest request = new XMLRequest();
        returnText = request.toString();

        return returnText;
    }
}
