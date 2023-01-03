import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadServer {

    //Метод для установки порта для подключения клиентов через файл настроек (например, settings.txt);
    private static int getPortFromTxt(String fileName) throws IOException {
        try (FileReader reader = new FileReader(fileName)) {
            int c;
            StringBuilder sb = new StringBuilder();
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }
            return Integer.parseInt(sb.toString());
        }
    }

    public static void main(String[] args) {

        int port = 0;
        try {
            port = getPortFromTxt("Settings.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection accepted");
                new ClientSocketRequestHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
