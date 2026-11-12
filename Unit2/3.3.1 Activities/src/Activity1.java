import java.util.Scanner;

public class Activity1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("First surname: ");
        String surname1 = sc.nextLine();
        System.out.print("Second surname: ");
        String surname2 = sc.nextLine();
        System.out.println("Hello, " + name + " " + surname1 + " " + surname2 + "!");
        sc.close();
    }
}