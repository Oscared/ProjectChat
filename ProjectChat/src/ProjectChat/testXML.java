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
public class testXML {
    
    String input1 = "<message sender=\"Oscar\"> <<text> felaktig text </text></message>";
    String input2 = "<text> Inga message taggar </text>";
    String input3 = "<message sender=\"Oscar\"><text color=\"#000000\">Hejsan! <fetstil> tjockt</fetstil></text></message>";
    
    public testXML(){
        
        XMLHandler xml = new XMLHandler();
        System.out.println(xml.ReadXML(input1));
        System.out.println(xml.ReadXML(input2));
        System.out.println(xml.ReadXML(input3));
        
    }
    
    public static void main(String[] args){
        testXML test= new testXML();
    }
}
