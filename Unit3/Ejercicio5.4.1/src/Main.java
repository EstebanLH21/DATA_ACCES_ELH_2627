import java.io.*;
import java.util.Scanner;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //Pedir el nombre del archivo
        System.out.println("Nombre del archivo: ");
        String nombre = sc.nextLine();
        File archivo = new File(nombre);

        //2. Si existe, comprobar que sea un archivo y preguntar
        boolean append = false;
        int numeroLinea = 1;
        //  Comprobar que existe
        if(archivo.exists()){
            //Comprobar que no es un directorio y si un archivo
            if(!archivo.isFile()){
                System.out.println("No es un archivo, es un directorio.");
                return;
            }
            //Averiguar si tienes permisos para escribir
            if(!archivo.canWrite()){
                System.out.println("No tienes permiso para escribir.");
                return;
            }
            //Preguntar si sobrescribit o añadir
            System.out.println("El archivo ya existe. ¿Deseas escribir (s) o añadir (a)? ");
            String respuesta = sc.nextLine().trim().toLowerCase();
            if(respuesta.equals("a")){
                append = true;
                numeroLinea = contarLineas(archivo) + 1;
                System.out.println("Añadiendo al final (empezando en línea " + numeroLinea + ")");
            }else{
                append = false;
                numeroLinea = 1;
                System.out.println("Sobrescribiendo desde el principio");
            }
        }else{
            System.out.println("El archivo no existe, se creará uno nuevo.");
        }
        //3.Abrir el archivo y escribir
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(archivo,append)))){
            System.out.println("Escribe frases. Para terminar, escribe FIN");
            String frase = "";
            //bucle
            while(!frase.equals("FIN")){
                System.out.print("> ");
                frase = sc.nextLine();
                if(!frase.equals("FIN")){
                    pw.println(numeroLinea + " " + frase);
                    numeroLinea++;
                }
            }
            System.out.println("Guardado en: " + archivo.getAbsolutePath());
        }catch(IOException ex){
            System.out.println("Error al escribir el archivo.");
            System.out.println("Error: " + ex.getMessage());
        }
        sc.close();



    }
    public static int contarLineas(File archivo){
        int contador = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(archivo))){
            String linea = br.readLine();
            while(linea != null){
                contador++;
                linea = br.readLine();
            }
        }catch(IOException ex){
            System.out.println("Error al escribir el archivo: " + ex.getMessage());
        }
        return contador;
    }
}