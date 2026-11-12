import java.util.Scanner;

public class Activity2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Day: ");
        int day = Integer.parseInt(sc.nextLine());
        System.out.print("Month: ");
        int month = Integer.parseInt(sc.nextLine());
        System.out.print("Year: ");
        int year = Integer.parseInt(sc.nextLine());

        if (day >= 1 && day <= 31 && month >= 1 && month <= 12) {
            System.out.println("Date: " + day + "/" + month + "/" + year);
        } else {
            System.out.println("Incorrect date.");
        }
        sc.close();
    }
}