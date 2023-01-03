import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocketRequestHandler extends Thread{
    private Socket clientSocket;
    public ClientSocketRequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try(Socket localSocket = clientSocket;
            PrintWriter out = new PrintWriter(localSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(localSocket.getInputStream()));
        ) {
            String LEVEL = "SERVER";
            String msg;
            String entry;

            while (!localSocket.isClosed()) {
                entry = in.readLine();
                Log.getINSTANCE().log(LEVEL, entry);

                if (entry.contains("/exit")) {
                    msg = "Server reply - " + "[" + entry + "] - OK";
                    out.println(msg);
                    Log.getINSTANCE().log(LEVEL, msg);
                    out.flush();
                    break;
                } else {
                    msg = "Server reply - [" + entry + "] - OK";
                    out.println(msg);
                    Log.getINSTANCE().log(LEVEL, msg);
                    out.flush();
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
