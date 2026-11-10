import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try(FileInputStream fIn = new FileInputStream("D:\\testin.txt");
            FileOutputStream fOut = new FileOutputStream("D:\\testout.txt")){

            byte[] buffer = new byte[128];
            int bytesRead;
            while((bytesRead = fIn.read(buffer)) != -1){
                fOut.write(buffer,0,bytesRead);
            }
        }catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }

    }
}