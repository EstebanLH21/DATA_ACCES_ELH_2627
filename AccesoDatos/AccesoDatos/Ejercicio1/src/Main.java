//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.util.InputMismatchException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Introduce tu nombre:");
            String nombre = sc.nextLine();
            System.out.println("Introduce tu apellidos:");
            String apellidos = sc.nextLine();
            System.out.println("Hola, " + nombre + ", " + apellidos + "!");
            System.out.println("A continuación indica la fecha de tu nacimiento");
            MyDate fecha = new MyDate();
            fecha.mostrarFecha();
        }catch(InputMismatchException ex){
            System.out.println("Error en la introduccion");
        }


        sc.close();
    }
}