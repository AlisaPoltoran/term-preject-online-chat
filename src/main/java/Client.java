import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    //Прочитать настройки приложения из файла настроек - например, номер порта сервера
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

        //Прочитать настройки приложения из файла настроек - например, номер порта сервера
        int port = 0;
        try {
            port = getPortFromTxt("Settings.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String host = "localhost";
        String LEVEL = "USER";
        String entry;
        String msg;
        Scanner scanner = new Scanner(System.in);
        User user = new User();

        // Подключение к указанному в настройках серверу
        try (Socket clientSocket = new Socket(host, port);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            System.out.println("Client connected to socket.");
            System.out.println();
            System.out.println("Client writing channel = oos & reading channel = ois initialized.");

            // Выбор имени для участия в чате;
            System.out.print("Hello new User! To join the chat please choose a name: ");
            user.setUserName(scanner.nextLine());

            while (!clientSocket.isOutputShutdown()) {
                System.out.print("Print a msg to the server: ");

                entry = scanner.nextLine();
                msg = user.getUserName() + " said " + entry;
                out.println(msg);
                Log.getINSTANCE().log(LEVEL, msg);
                Log.getINSTANCE().log(LEVEL, user.getUserName() + " sent message " + entry + " to server.");

                //Для выхода из чата нужно набрать команду выхода - “/exit”;
                if (entry.equalsIgnoreCase("/exit")) {
                    if (in.read() > -1) {
                        System.out.println("reading...");
                        String input = in.readLine();
                        System.out.println(input);
                    }
                    break;
                }

                Log.getINSTANCE().log(LEVEL, "Client sent message & start waiting for data from server...");

                if (in.read() > -1) {
                    Log.getINSTANCE().log(LEVEL, "reading...");
                    entry = in.readLine();
                    Log.getINSTANCE().log(LEVEL, entry);
                }

            }
            Log.getINSTANCE().log(LEVEL, "Closing connections & channels on client's side - DONE.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

