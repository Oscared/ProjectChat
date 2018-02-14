/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectchat;
import java.io.*;
import java.net.*;

/**
 * Detta program startar en server som lyssnar pÃ¥ port 4444
 * och konverterar alla inkommande meddelanden till versaler
 * innan den skickar tillbaks dem till klienten
 * 
 * En ny trÃ¥d startas fÃ¶r varje anslutande klient
 * och programmet kÃ¶rs till det stÃ¤ngs av utifrÃ¥n
 */
/**
 *
 * @author oscar
 */
public class MultiThreadServer {
    // Serverns socket
    private ServerSocket serverSocket;

    public static void main(String[] args){
	MultiThreadServer servDemo = new MultiThreadServer();
    }

    public MultiThreadServer(){

	// Starta serverns socket
	try {
	    serverSocket = new ServerSocket(4444);
	} catch (IOException e) {
	    System.out.println("Could not listen on port: 4444");
	    System.exit(-1);
	}
	
	// Lyssna efter klienter.
        // Varje gÃ¥ng en klient ansluter atartas en
	// ny trÃ¥d av typen 'EchoThread', som sedan
	// behandlar resten av kommunikationen
	// EkotrÃ¥darna tar klientsocketen som argument
	// fÃ¶r att veta vem som har anslutit sig
	while(true){
	    Socket clientSocket = null;
	    try {
		clientSocket = serverSocket.accept();
	    } catch (IOException e) {
		System.out.println("Accept failed: 4444");
		System.exit(-1);
	    }
	    Thread thr = new EchoThread(clientSocket);
	    thr.start();
	}
    }
}
