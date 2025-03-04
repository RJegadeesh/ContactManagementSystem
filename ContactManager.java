import java.io.*;
import java.util.*;

class Contact implements Serializable {
    String name, phone, email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phone + ", Email: " + email;
    }
}

public class ContactManager {
    private static final String FILE_NAME = "contacts.dat";
    private static List<Contact> contacts = new ArrayList<>();

    public static void main(String[] args) {
        loadContacts();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n📞 Contact Management System 📞");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Edit Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addContact(scanner);
                case 2 -> viewContacts();
                case 3 -> editContact(scanner);
                case 4 -> deleteContact(scanner);
                case 5 -> saveContacts();
                default -> System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 5);

        scanner.close();
    }

    private static void addContact(Scanner scanner) {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        contacts.add(new Contact(name, phone, email));
        System.out.println("✅ Contact Added Successfully!");
    }

    private static void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found!");
        } else {
            System.out.println("\n📜 Contact List:");
            for (int i = 0; i < contacts.size(); i++) {
                System.out.println((i + 1) + ". " + contacts.get(i));
            }
        }
    }

    private static void editContact(Scanner scanner) {
        viewContacts();
        System.out.print("Enter contact number to edit: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline

        if (index >= 0 && index < contacts.size()) {
            System.out.print("Enter New Name: ");
            contacts.get(index).name = scanner.nextLine();
            System.out.print("Enter New Phone: ");
            contacts.get(index).phone = scanner.nextLine();
            System.out.print("Enter New Email: ");
            contacts.get(index).email = scanner.nextLine();
            System.out.println("✏️ Contact Updated Successfully!");
        } else {
            System.out.println("Invalid selection!");
        }
    }

    private static void deleteContact(Scanner scanner) {
        viewContacts();
        System.out.print("Enter contact number to delete: ");
        int index = scanner.nextInt() - 1;

        if (index >= 0 && index < contacts.size()) {
            contacts.remove(index);
            System.out.println("🗑️ Contact Deleted Successfully!");
        } else {
            System.out.println("Invalid selection!");
        }
    }

    private static void saveContacts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(contacts);
            System.out.println("💾 Contacts Saved!");
        } catch (IOException e) {
            System.out.println("Error saving contacts!");
        }
    }

    private static void loadContacts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            contacts = (List<Contact>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            contacts = new ArrayList<>();
        }
    }
}