import java.util.Scanner;

public class Main {
    private String[] routes = {"City A to City B", "City B to City C", "City C to City A"};
    private double[] prices = {5.0, 7.5, 6.0};
    private double totalMoneyCollected = 0.0;
    private double currentBalance = 0.0;

    public Main() {
        System.out.println("Welcome to the Ticket Machine!");
        System.out.println("Available routes: ");
        for (int i = 0; i < routes.length; i++) {
            System.out.println("  " + (i + 1) + ". " + routes[i] + " - $" + prices[i]);
        }
        System.out.println();
    }

    public double checkBalance() {
        return currentBalance;
    }

    public void insertMoney(double amount) {
        if (amount > 0) {
            currentBalance += amount;
            System.out.println("Inserted $" + amount + ". Current balance: $" + currentBalance);
        } else {
            System.out.println("Please insert a valid amount!");
        }
    }

    public double calculateTotalCost(int routeIndex, int quantity) {
        if (routeIndex >= 0 && routeIndex < routes.length && quantity > 0) {
            return prices[routeIndex] * quantity;
        } else {
            System.out.println("Invalid route or quantity!");
            return 0.0;
        }
    }

    public void printTicket(int routeIndex, int quantity) {
        double totalCost = calculateTotalCost(routeIndex, quantity);
        if (totalCost == 0) {
            return;
        }
        if (currentBalance >= totalCost) {
            currentBalance -= totalCost;
            totalMoneyCollected += totalCost;
            System.out.println("-------------------");
            System.out.println("Ticket Details:");
            System.out.println("  Route: " + routes[routeIndex]);
            System.out.println("  Quantity: " + quantity);
            System.out.println("  Total Cost: $" + totalCost);
            System.out.println("  Remaining Balance: $" + currentBalance);
            System.out.println("-------------------");
            calculateChange();
        } else {
            double needed = totalCost - currentBalance;
            System.out.println("Insufficient funds! Please insert $" + String.format("%.2f", needed) + " more.");
        }
        System.out.println();
    }

    private void calculateChange() {
        if (currentBalance > 0) {
            System.out.println("Here's your change: $" + String.format("%.2f", currentBalance));
            currentBalance = 0.0;
        }
    }

    public double getTotalMoneyCollected() {
        return totalMoneyCollected;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Main machine = new Main();
        
        while (true) {
            System.out.println("=== Ticket Machine Menu ===");
            System.out.println("  1. Insert Money");
            System.out.println("  2. Print Ticket");
            System.out.println("  3. Check Total Collected");
            System.out.println("  4. Quit");
            System.out.print("Choose an option (1-4): ");
            
            int choice = scanner.nextInt();
            
            if (choice == 1) {
                System.out.print("Amount to insert: $");
                double amount = scanner.nextDouble();
                machine.insertMoney(amount);
            } else if (choice == 2) {
                System.out.print("Route (1-3): ");
                int route = scanner.nextInt() - 1;
                System.out.print("Quantity: ");
                int qty = scanner.nextInt();
                machine.printTicket(route, qty);
            } else if (choice == 3) {
                System.out.println("Total money collected: $" + String.format("%.2f", machine.getTotalMoneyCollected()));
                System.out.println();
            } else if (choice == 4) {
                System.out.println("Thanks for using the Ticket Machine!");
                break;
            } else {
                System.out.println("Invalid choice! Try again.");
            }
        }
        scanner.close();
    }
}

