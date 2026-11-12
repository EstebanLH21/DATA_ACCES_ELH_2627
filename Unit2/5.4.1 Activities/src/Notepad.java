import java.io.*;
import java.util.Scanner;

public class Notepad {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);


        System.out.print("Enter file name: ");
        String fileName = sc.nextLine();


        File file = new File(fileName);


        boolean append = false;
        int lineNumber = 1;


        if (file.exists()) {
            System.out.print("File exists. Overwrite (o) or Append (a)? ");
            String answer = sc.nextLine();

            if (answer.equals("a")) {
                append = true; // Queremos añadir al final
                // Contamos las líneas actuales y sumamos 1 para la siguiente
                lineNumber = countLines(file) + 1;
            }
        }


        // Usamos try-with-resources para que se cierre solo
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, append)))) {

            System.out.println("Write your sentences (type 'FIN' to stop):");

            String sentence = "";

            // 6. Bucle para pedir frases hasta que escriba FIN
            while (!sentence.equals("FIN")) {
                System.out.print("> ");
                sentence = sc.nextLine();

                // Si no es FIN, la guardamos
                if (!sentence.equals("FIN")) {
                    pw.println(lineNumber + ". " + sentence);
                    lineNumber++; // Aumentamos el número de línea
                }
            }
            System.out.println("Saved!");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }

    // Método auxiliar para contar las líneas que ya tiene el archivo
    public static int countLines(File file) {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while (line != null) {
                count++;
                line = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
        return count;
    }
}