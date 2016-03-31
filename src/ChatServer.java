import java.io.*;
import java.io.IOException;
import java.net.*;
import java.util.Vector;

public class ChatServer {
    static Vector clientSockets;
    static Vector loginNames;

    public ChatServer() throws IOException {
        ServerSocket server = new ServerSocket(5271);
        clientSockets = new Vector();
        loginNames = new Vector();

        while (true) {
            Socket clientSocket = server.accept();
            AcceptClient acceptClient = new AcceptClient(clientSocket);
        }
    }

    public class AcceptClient extends Thread {
        Socket clientSocket;
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;

        public AcceptClient(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            dataInputStream = new DataInputStream(this.clientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(this.clientSocket.getOutputStream());

            String loginName = dataInputStream.readUTF();
            loginNames.add(loginName);
            clientSockets.add(this.clientSocket);

            start();
        }

        public void run() {
            while (true) {
                
            }
        }
    }
}