import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;



//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final String ARCHIVO = "contacts.obj";
    private static ArrayList<Contact> contactos = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        cargarContactos();
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerOpcion();

            if (opcion == 1) {
                crearContacto();
            } else if (opcion == 2) {
                mostrarContactos();
            } else if (opcion == 3) {
                buscarContacto();
            } else if (opcion == 4) {
                guardarContactos();
                salir = true;
                System.out.println("¡Hasta luego!");
            } else {
                System.out.println("Opción no válida.");
            }
        }

    }

    private static int leerOpcion() {
        int opcion = -1;
        try {
            opcion = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            opcion = -1;
        }
        return opcion;
    }
    private static void crearContacto() {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Apellido: ");
        String apellido = sc.nextLine();

        System.out.println("Email:  ");
        String email = sc.nextLine();

        System.out.println("Telefono:  ");
        String telefono = sc.nextLine();

        System.out.println("Descripción: ");
        String descripcion = sc.nextLine();

        // ... email, teléfono, descripción

        Contact c = new Contact(nombre, apellido, email, telefono, descripcion);
        contactos.add(c);

        System.out.println("Contacto añadido.");
        guardarContactos();
    }
    private static void mostrarContactos() {
        if (contactos.isEmpty()) {
            System.out.println("No hay contactos.");
            return;
        }

        System.out.println("\n--- LISTA DE CONTACTOS ---");
        for (int i = 0; i < contactos.size(); i++) {
            System.out.println((i + 1) + ". " + contactos.get(i));
        }
    }
    //Buscar
    private static void buscarContacto() {
        System.out.println("Buscar por:");
        System.out.println("1. Nombre completo");
        System.out.println("2. Teléfono");
        System.out.print("Opción: ");
        int opcion = leerOpcion();
        if (opcion == 1) {
            System.out.println("Nombre completo: ");
            String nombreCompleto = sc.nextLine().trim().toLowerCase();

            boolean encontrado = false;
            for(int i = 0; i < contactos.size(); i++){
                Contact c = contactos.get(i);
                if(c.getFullName().toLowerCase().equals(nombreCompleto)){
                    System.out.println(c);
                    encontrado = true;
                }
            }
            if(!encontrado){
                System.out.println("Contacto no encontrado");
            }
        }else if (opcion == 2) {
            System.out.println("Teléfono: ");
            String telefono = sc.nextLine().trim();
            boolean encontrado = false;
            for(int i = 0; i < contactos.size();i++){
                Contact c = contactos.get(i);
                if(c.getPhone().equals(telefono)){
                    System.out.println(c);
                    encontrado = true;
                }
            }
            if(!encontrado){
                System.out.println("Contacto no encontrado");

            }
        }
    }
    private static void mostrarMenu() {
        System.out.println("\n--- AGENDA DE CONTACTOS ---");
        System.out.println("1. Crear contacto");
        System.out.println("2. Mostrar contactos");
        System.out.println("3. Buscar contacto");
        System.out.println("4. Salir");
        System.out.print("Elige una opción: ");
    }
    //Serializar
    private static void guardarContactos() {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARCHIVO))){
            out.writeObject(contactos);
        }catch (IOException e){
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }
    //Desserializar
    @SuppressWarnings("unchecked")
    private static void cargarContactos() {
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) {
            System.out.println("No existe " + ARCHIVO + ". Se creará uno nuevo.");
            //                             ↑ sin comillas
            return;
        }
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(archivo))) {

            contactos = (ArrayList<Contact>) in.readObject();
            System.out.println("Cargados " + contactos.size() + " contactos.");

        }catch (IOException | ClassNotFoundException e){
        System.out.println("Error al cargar: " + e.getMessage());}
    }




}