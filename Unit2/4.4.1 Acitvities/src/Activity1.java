import java.io.*;

public class Activity1 {
    public static void main(String[] args) {
        try (FileInputStream in = new FileInputStream("input.txt");
             FileOutputStream out = new FileOutputStream("output.txt")) {
            byte[] buffer = new byte[128];
            int bytesRead = in.read(buffer);
            while (bytesRead != -1) {
                out.write(buffer, 0, bytesRead);
                bytesRead = in.read(buffer);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}