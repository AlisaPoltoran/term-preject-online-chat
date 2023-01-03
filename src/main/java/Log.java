import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Log {
    private static Log INSTANCE = null;

    private Log() {
    }

    public static Log getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (Log.class) {
                if (INSTANCE == null){
                    INSTANCE = new Log();
                }
            }
        }
        return INSTANCE;
    }

    public void log (String level, String msg) throws IOException {
        String text = "[" + level + "#]" + LocalDateTime.now() + "=====" + msg + System.lineSeparator();
        try (FileWriter writer = new FileWriter("File.log", true)) {
            writer.write(text);
            writer.flush();
        }
    }

}
