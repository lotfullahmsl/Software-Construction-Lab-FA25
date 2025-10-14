import java.util.HashMap;
import java.util.Scanner;

public class Task1 {
    private HashMap<String, String> contacts = new HashMap<>();

    public void addContact(String personName, String phoneNumber) {
        if (contacts.containsKey(personName)) {
            System.out.println("The name " + personName + " already exists. Do you want to update the number? (yes/no)");
            Scanner input = new Scanner(System.in);
            String choice = input.nextLine().trim().toLowerCase();
            if (choice.equals("yes")) {
                contacts.put(personName, phoneNumber);
                System.out.println("Updated " + personName + "'s number to " + phoneNumber);
            } else {
                System.out.println("No changes made for " + personName);
            }
            return;
        }

        if (contacts.containsValue(phoneNumber)) {
            System.out.println("This number is already saved under another name.");
            for (String key : contacts.keySet()) {
                if (contacts.get(key).equals(phoneNumber)) {
                    System.out.println("Existing entry: " + key + " -> " + phoneNumber);
                }
            }
            System.out.println("Do you still want to save it? (yes/no)");
            Scanner input = new Scanner(System.in);
            String choice = input.nextLine().trim().toLowerCase();
            if (!choice.equals("yes")) {
                System.out.println("Contact not saved.");
                return;
            }
        }

        contacts.put(personName, phoneNumber);
        System.out.println("Saved contact: " + personName + " -> " + phoneNumber);
    }

    public void findContact(String personName) {
        String number = contacts.get(personName);
        if (number != null) {
            System.out.println(personName + "'s number is " + number);
        } else {
            System.out.println("No record found for " + personName);
        }
    }

    public void showAllContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
            return;
        }
        System.out.println("\nAll saved contacts:");
        for (String name : contacts.keySet()) {
            System.out.println(name + " -> " + contacts.get(name));
        }
    }

    public void deleteContact(String personName) {
        if (contacts.containsKey(personName)) {
            contacts.remove(personName);
            System.out.println(personName + " removed from the phonebook.");
        } else {
            System.out.println("No record found for " + personName);
        }
    }

    public static void main(String[] args) {
        Task1 phoneBook = new Task1();
        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add contact");
            System.out.println("2. Find contact");
            System.out.println("3. Show all contacts");
            System.out.println("4. Delete contact");
            System.out.println("5. Exit");
            System.out.print("Select: ");
            int choice = in.nextInt();
            in.nextLine();

            if (choice == 1) {
                System.out.print("Enter name: ");
                String name = in.nextLine();
                System.out.print("Enter number: ");
                String number = in.nextLine();
                phoneBook.addContact(name, number);
            } else if (choice == 2) {
                System.out.print("Enter name: ");
                String name = in.nextLine();
                phoneBook.findContact(name);
            } else if (choice == 3) {
                phoneBook.showAllContacts();
            } else if (choice == 4) {
                System.out.print("Enter name to delete: ");
                String name = in.nextLine();
                phoneBook.deleteContact(name);
            } else if (choice == 5) {
                System.out.println("Closing phonebook.");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
        in.close();
    }
}
