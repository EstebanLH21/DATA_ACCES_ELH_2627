import java.util.Scanner;

public class MyDate {
    // Properties
    private int day;
    private int month;
    private int year;

    // Setters (Activity 3.3.3)
    public void setDay(int day) {
        if (day >= 1 && day <= 31) {
            this.day = day;
        } else {
            System.out.println("Incorrect day.");
        }
    }

    public void setMonth(int month) {
        if (month >= 1 && month <= 12) {
            this.month = month;
        } else {
            System.out.println("Incorrect month.");
        }
    }

    public void setYear(int year) {
        if (year >= 1) {
            this.year = year;
        } else {
            System.out.println("Incorrect year.");
        }
    }

    // Check leap year (Activity 3.3.4)
    public boolean isLeapYear() {
        if (year % 400 == 0) {
            return true;
        } else if (year % 4 == 0 && year % 100 != 0) {
            return true;
        } else {
            return false;
        }
    }

    // Check full date consistency (Activity 3.3.4)
    public boolean isDateValid() {
        int daysInMonth = 31;

        if (month == 4 || month == 6 || month == 9 || month == 11) {
            daysInMonth = 30;
        } else if (month == 2) {
            if (isLeapYear()) {
                daysInMonth = 29;
            } else {
                daysInMonth = 28;
            }
        }

        return day >= 1 && day <= daysInMonth && month >= 1 && month <= 12;
    }

    public void show() {
        System.out.println("Date: " + day + "/" + month + "/" + year);
    }

    // Main method to test the class
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MyDate myDate = new MyDate();

        System.out.print("Enter day: ");
        myDate.setDay(Integer.parseInt(sc.nextLine()));

        System.out.print("Enter month: ");
        myDate.setMonth(Integer.parseInt(sc.nextLine()));

        System.out.print("Enter year: ");
        myDate.setYear(Integer.parseInt(sc.nextLine()));

        if (myDate.isDateValid()) {
            myDate.show();
            if (myDate.isLeapYear()) {
                System.out.println("The year is a leap year.");
            } else {
                System.out.println("The year is not a leap year.");
            }
        } else {
            System.out.println("The date does not exist.");
        }

        sc.close();
    }
}