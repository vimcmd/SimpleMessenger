import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
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

    public static void main(String[] args) throws IOException {
        ChatServer chatServer = new ChatServer();
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
                try {
                    String msgFromClient = dataInputStream.readUTF();
                    StringTokenizer stringTokenizer = new StringTokenizer(msgFromClient);
                    String loginName = stringTokenizer.nextToken();
                    String mesgType = stringTokenizer.nextToken();

                    for(int i = 0; i < loginNames.size(); i++) {
                        Socket pSocket = (Socket) clientSockets.elementAt(i);
                        DataOutputStream pOut = new DataOutputStream(pSocket.getOutputStream());
                        pOut.writeUTF(loginName + " has logged in.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}