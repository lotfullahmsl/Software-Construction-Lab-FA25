import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static class Book {
        String title;
        String author;
        boolean isAvailable;
        String patron;

        Book(String title, String author) {
            this.title = title;
            this.author = author;
            this.isAvailable = true;
            this.patron = null;
        }
    }

    private ArrayList<Book> books = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public Main() {
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald"));
        books.add(new Book("1984", "George Orwell"));
        books.add(new Book("To Kill a Mockingbird", "Harper Lee"));
        System.out.println("Library Management System initialized with 3 books.");
    }

    public void addBook(String title, String author) {
        books.add(new Book(title, author));
        System.out.println("Added: " + title + " by " + author);
    }

    public void displayAvailableBooks() {
        System.out.println("Available Books:");
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).isAvailable) {
                System.out.println((i + 1) + ". " + books.get(i).title + " by " + books.get(i).author);
            }
        }
    }

    public void issueBook() {
        displayAvailableBooks();
        System.out.print("Enter book number to check out: ");
        int index = scanner.nextInt() - 1;
        if (index >= 0 && index < books.size() && books.get(index).isAvailable) {
            System.out.print("Enter patron name: ");
            scanner.nextLine(); // Clear buffer
            String patron = scanner.nextLine();
            books.get(index).isAvailable = false;
            books.get(index).patron = patron;
            System.out.println("Checked out " + books.get(index).title + " to " + patron);
        } else {
            System.out.println("Invalid selection or book not available!");
        }
    }

    public void returnBook() {
        System.out.println("Borrowed Books:");
        for (int i = 0; i < books.size(); i++) {
            if (!books.get(i).isAvailable) {
                System.out.println((i + 1) + ". " + books.get(i).title + " (Patron: " + books.get(i).patron + ")");
            }
        }
        System.out.print("Enter book number to return: ");
        int index = scanner.nextInt() - 1;
        if (index >= 0 && index < books.size() && !books.get(index).isAvailable) {
            books.get(index).isAvailable = true;
            books.get(index).patron = null;
            System.out.println(books.get(index).title + " has been returned.");
        } else {
            System.out.println("Invalid selection or book not borrowed!");
        }
    }

    public void generateReport() {
        System.out.println("=== Library Report ===");
        System.out.println("Available Books:");
        displayAvailableBooks();
        System.out.println("Borrowed Books:");
        for (Book book : books) {
            if (!book.isAvailable) {
                System.out.println("  " + book.title + " (Patron: " + book.patron + ")");
            }
        }
    }

    public static void main(String[] args) {
        Main library = new Main();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== Library Management System ===");
            System.out.println("  1. Add Book");
            System.out.println("  2. Display Available Books");
            System.out.println("  3. Issue Book");
            System.out.println("  4. Return Book");
            System.out.println("  5. Generate Report");
            System.out.println("  6. Quit");
            System.out.print("Choose an option (1-6): ");

            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.print("Enter title: ");
                scanner.nextLine(); // Clear buffer
                String title = scanner.nextLine();
                System.out.print("Enter author: ");
                String author = scanner.nextLine();
                library.addBook(title, author);
            } else if (choice == 2) {
                library.displayAvailableBooks();
            } else if (choice == 3) {
                library.issueBook();
            } else if (choice == 4) {
                library.returnBook();
            } else if (choice == 5) {
                library.generateReport();
            } else if (choice == 6) {
                System.out.println("Thank you for using the Library Management System!");
                break;
            } else {
                System.out.println("Invalid choice! Try again.");
            }
            System.out.println();
        }
        scanner.close();
    }
}