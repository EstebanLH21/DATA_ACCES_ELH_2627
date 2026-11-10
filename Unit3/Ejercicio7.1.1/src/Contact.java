import java.io.Serializable;

public class Contact implements Serializable {
    //Propiedades
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String description;
    //Constructor
    public Contact(String name, String surname, String email, String phone, String description) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.description = description;
    }
    //Getters
    public String getName()    { return name; }
    public String getSurname() { return surname; }
    public String getEmail()   { return email; }
    public String getPhone()   { return phone; }
    public String getDescription() { return description; }

    public String getFullName() {
        return name + " " + surname;
    }
    @Override
    public String toString() {
        return "Nombre: " + name + " " + surname +
                " | Email: " + email +
                " | Teléfono: " + phone +
                " | Descripción: " + description;
    }
}
