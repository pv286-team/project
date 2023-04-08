package cz.muni.fi.pv286;

import java.io.*;

public class TestUtils {

    public static void setInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }
    public static ByteArrayOutputStream getOutput() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        return out;
    }

    public static String readFile(String path) {
        try {
            FileReader reader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
                line = bufferedReader.readLine();
            }
            String fileContents = stringBuilder.toString();
            reader.close();
            return fileContents;
        } catch (Exception e) {
            assert(false);
        }
        return "";
    }
}
