import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatClient extends JFrame implements Runnable {

    Socket socket;
    JTextArea textArea;
    Thread thread;

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    String loginName;

    public ChatClient(String loginName) throws HeadlessException, IOException {
        super(loginName);
        this.loginName = loginName;
        textArea = new JTextArea(18, 50);

        socket = new Socket("localhost", 5271);

        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(loginName);
        dataOutputStream.writeUTF(loginName + " LOGIN");

        thread = new Thread(this);
        thread.start();
        setup();
    }

    public static void main(String[] args) throws IOException {
        ChatClient chatClient1 = new ChatClient("User1");
    }

    private void setup() {
        setSize(600, 400);
        JPanel panel = new JPanel();
        panel.add(new JScrollPane(textArea));
        add(panel);
        setVisible(true);
    }

    @Override
    public void run() {
        while (true) {
            try {
                textArea.append("\n" + dataInputStream.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
