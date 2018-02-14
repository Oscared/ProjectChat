public class ServerThread extends Thread {

    private Socket clientSocket;

    private PrintWriter writer;

    private BufferedReader reader;

    private Int ID;

    public ServerThread(Socket clientSocket, int ID) {
    }

    public void run() {
    }

    public Int GetID() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
