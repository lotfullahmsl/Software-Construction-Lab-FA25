import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class MailItem {
    private final String from;
    private final String to;
    private final String message;

    public MailItem(String from, String to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    public void displayMail() {
        System.out.println("From: " + from);
        System.out.println("To: " + to);
        System.out.println("Message: " + message);
        System.out.println("----------------------------");
    }

    @Override
    public String toString() {
        return "From: " + from + "\nTo: " + to + "\nMessage: " + message;
    }
}

class MailServer {
    private static MailServer instance;
    private Map<String, MailClient> clients;

    private MailServer() {
        clients = new HashMap<>();
    }

    public static MailServer getInstance() {
        if (instance == null) {
            instance = new MailServer();
        }
        return instance;
    }

    public void registerClient(MailClient client) {
        if (!clients.containsKey(client.getUsername())) {
            clients.put(client.getUsername(), client);
        } else {
            System.out.println("Username '" + client.getUsername() + "' is already registered.");
        }
    }

    public boolean userExists(String username) {
        return clients.containsKey(username);
    }

    public void sendMail(MailItem mail) {
        String recipient = mail.getTo();
        if (clients.containsKey(recipient)) {
            clients.get(recipient).receiveMail(mail);
            System.out.println("Mail sent successfully to " + recipient + "!");
        } else {
            System.out.println("Error: Recipient '" + recipient + "' does not exist!");
        }
    }

    public MailClient getClient(String username) {
        return clients.get(username);
    }

    // Optional reset for testing/debugging
    public void resetServer() {
        clients.clear();
        System.out.println("MailServer reset completed.");
    }
}

class MailClient {
    private String username;
    private List<MailItem> inbox;
    private MailServer server;

    public MailClient(String username, MailServer server) {
        this.username = username;
        this.server = server;
        inbox = new ArrayList<>();
        this.server.registerClient(this);
    }

    public String getUsername() {
        return username;
    }

    public void sendMail(String to, String message) {
        // Validation checks
        if (to == null || to.trim().isEmpty()) {
            System.out.println("Error: Recipient username cannot be empty!");
            return;
        }
        if (to.equalsIgnoreCase(this.username)) {
            System.out.println("Error: You cannot send mail to yourself!");
            return;
        }
        if (message == null || message.trim().isEmpty()) {
            System.out.println("Error: Message cannot be empty!");
            return;
        }

        MailItem mail = new MailItem(this.username, to.trim(), message.trim());
        server.sendMail(mail);
    }

    public void receiveMail(MailItem mail) {
        inbox.add(mail);
    }

    public void showInbox() {
        System.out.println("\nInbox of " + username + ":");
        if (inbox.isEmpty()) {
            System.out.println("No messages.");
        } else {
            for (MailItem mail : inbox) {
                mail.displayMail();
            }
        }
    }
}

public class Task1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MailServer server = MailServer.getInstance();

        System.out.println("=== Welcome to Java Mail System ===");

        while (true) {
            System.out.println("\n1. Register User");
            System.out.println("2. Send Mail");
            System.out.println("3. Check Inbox");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            String input = sc.nextLine().trim();

            // Validate menu input safely
            if (!input.matches("[1-4]")) {
                System.out.println("Invalid input! Please enter a number between 1 and 4.");
                continue;
            }

            int choice = Integer.parseInt(input);

            switch (choice) {
                case 1:
                    System.out.print("Enter new username: ");
                    String username = sc.nextLine().trim();

                    if (username.isEmpty()) {
                        System.out.println("Error: Username cannot be empty!");
                        break;
                    }
                    if (server.userExists(username)) {
                        System.out.println("Error: Username already exists!");
                    } else {
                        new MailClient(username, server);
                        System.out.println("User '" + username + "' registered successfully!");
                    }
                    break;

                case 2:
                    System.out.print("Enter your username: ");
                    String sender = sc.nextLine().trim();
                    if (sender.isEmpty()) {
                        System.out.println("Error: Username cannot be empty!");
                        break;
                    }
                    if (!server.userExists(sender)) {
                        System.out.println("Error: You need to register first!");
                        break;
                    }

                    System.out.print("Enter recipient username: ");
                    String recipient = sc.nextLine().trim();
                    System.out.print("Enter your message: ");
                    String message = sc.nextLine();

                    server.getClient(sender).sendMail(recipient, message);
                    break;

                case 3:
                    System.out.print("Enter your username: ");
                    String user = sc.nextLine().trim();

                    if (user.isEmpty()) {
                        System.out.println("Error: Username cannot be empty!");
                        break;
                    }
                    if (!server.userExists(user)) {
                        System.out.println("Error: User does not exist!");
                        break;
                    }
                    server.getClient(user).showInbox();
                    break;

                case 4:
                    System.out.println("Exiting Java Mail System. Goodbye!");
                    sc.close();
                    System.exit(0);
            }
        }
    }
}

