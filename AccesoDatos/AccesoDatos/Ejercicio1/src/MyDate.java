
import java.util.Scanner;

public class MyDate {

    private int dia;
    private int mes;
    private int anio;

    public void pedirFecha() {
        Scanner sc = new Scanner(System.in);

        boolean diaOK = false;
        boolean mesOK = false;
        boolean anioOK = false;
        boolean fechaOK = false;

        while (!fechaOK) {

            // Pedir el día
            diaOK = false;
            while (!diaOK) {
                System.out.print("Introduce el día: ");
                dia = Integer.parseInt(sc.nextLine());

                if (dia >= 1 && dia <= 31) {
                    diaOK = true;
                } else {
                    System.out.println("Día incorrecto.");
                }
            }

            // Pedir el mes
            mesOK = false;
            while (!mesOK) {
                System.out.print("Introduce el mes: ");
                mes = Integer.parseInt(sc.nextLine());

                if (mes >= 1 && mes <= 12) {
                    mesOK = true;
                } else {
                    System.out.println("Mes incorrecto.");
                }
            }

            // Pedir el año
            anioOK = false;
            while (!anioOK) {
                System.out.print("Introduce el año: ");
                anio = Integer.parseInt(sc.nextLine());

                if (anio >= 1) {
                    anioOK = true;
                } else {
                    System.out.println("Año incorrecto.");
                }
            }

            // Comprobar la fecha completa
            if (esFechaValida()) {
                fechaOK = true;
            } else {
                System.out.println("La fecha no existe. Inténtalo de nuevo.");
            }
        }

        // Comprobar si el año es bisiesto
        if (esBisiesto()) {
            System.out.println("El año es bisiesto.");
        } else {
            System.out.println("El año no es bisiesto.");
        }
    }

    public boolean esBisiesto() {
        if (anio % 400 == 0) {
            return true;
        } else if (anio % 4 == 0 && anio % 100 != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean esFechaValida() {
        int diasMes = 31;

        if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
            diasMes = 30;
        } else if (mes == 2) {
            if (esBisiesto()) {
                diasMes = 29;
            } else {
                diasMes = 28;
            }
        }
        return dia >= 1 && dia <= diasMes;



    }

    public void mostrarFecha() {
        System.out.println("Fecha: " + dia + "/" + mes + "/" + anio);
    }
}

