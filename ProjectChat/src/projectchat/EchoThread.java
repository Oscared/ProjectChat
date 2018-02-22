package projectchat;


import java.io.*;
import java.net.*;

/**
 * Denna kod beskriver de trÃ¥dar som hanterar
 * anslutningar till MultiThreadedServerDemo
 * Varje trÃ¥d lÃ¤ser in sin klients meddelanden,
 * konverterar det till versaler och skickar
 * tillbaka det. TrÃ¥den avslutar nÃ¤r klienten
 * kopplar ner.
 */
public class EchoThread extends Thread{

    // Socket, lÃ¤mnas via konstruktorn
    private Socket clientSocket = null;

    // StrÃ¶mmar fÃ¶r att lÃ¤sa/skriva till klienten
    private PrintWriter out;
    private BufferedReader in;

    // Meddelandet som konverteras och skickas tillbaka
    private String echo;

    // Konstruktorn sparar socketen lokalt
    public EchoThread(Socket sock){
	clientSocket = sock;
    }

    public void run(){

	// Vi kÃ¶r tills vi Ã¤r klara
	boolean done = false;

	// Anslut lÃ¤s- och skrivstrÃ¶mmarna
	try{
	    out = new PrintWriter(
				  clientSocket.getOutputStream(), true);
	}catch(IOException e){
	    System.out.println("getOutputStream failed: " + e);
	    System.exit(1);
	}
	try{
	    in = new BufferedReader(new InputStreamReader(
	            clientSocket.getInputStream()));
	}catch(IOException e){
	    System.out.println("getInputStream failed: " + e);
	    System.exit(1);
	}

	// Kommer vi hit gick anslutningen bra.
	// Vi skriver ut IP-nummret frÃ¥n klienten
	System.out.println("Connection Established: " 
			   + clientSocket.getInetAddress());

	// HÃ¤r lÃ¤ser vi in klientens budskap
	// och konverterar det till versaler
	// Om klienten kopplar ner gÃ¶r vi det ocksÃ¥,
	// och avslutar trÃ¥den
	while(!done){
	    try{
		echo = in.readLine();
		if(echo==null){
		    System.out.println("Client disconnect!");
		    done = true;
		}else{
		    System.out.println("Recieved: (" 
                            + clientSocket.getInetAddress() 
                            + ") " + echo);
		    out.println(echo.toUpperCase());
		}
	    }catch(IOException e){
		System.out.println("readLine failed: " + e);
		System.exit(1);
	    }
	}

	try{
	    in.close();
	    out.close();
	    clientSocket.close();
	}catch(IOException e){}
    }
}