/**
 * Class Component
 * Authors Martin Ståhl & Oscar Örnberg
 * Version 1.0
 * Copywrite authors
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
 
            }

        }

        public String toString() {

            return "<message sender=" + "\"" + name + "\"" + ">" + subText.toString() + "</message>";
            
            //return "<message sender=" + "\"" + name + "\"" + ">" + "<text color=" + "\"" + color + "\"" + ">" + "<fetstil>"
            //        + "<encrypted>" + "<filerequest>" + subText.toString() + "</filerequest>"
            //        + "</encrypted>" + "</fetstil>" + "</text>" + "</message>";

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
