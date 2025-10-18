import java.util.*;
import java.time.*;

public class Task1{
    public static final String DESIGN_DOC = """
UML-Like Class Diagram (text):

BankingSystem (main)
  - starts the ATM interface

Bank
  - Map<String, Customer> customers
  - Map<String, BankAccount> accounts
  - methods to register customers, find customers/accounts, seed sample data

Customer
  - String customerId
  - String name
  - String pin
  - List<BankAccount> accounts
  - int failedPinCount
  - LocalDateTime blockedUntil

BankAccount (abstract)
  - String accountNumber
  - String customerId
  - double balance
  - AccountStatus status
  - List<Transaction> history
  - abstract deposit, withdraw, getBalance

SavingsAccount extends BankAccount
  - double minimumBalance

CheckingAccount extends BankAccount
  - double overdraftLimit

Transaction
  - String id, TransactionType type, TransactionStatus status, double amount, LocalDateTime time, String fromAccount, String toAccount

ATM
  - interacts only with Bank
  - handles authentication, menus, operations

Data Structure Justification:
- customers stored in HashMap<String, Customer> for O(1) lookup by customer ID or card number.
- accounts stored in HashMap<String, BankAccount> for O(1) lookup by account number when transferring or querying.
- Customer holds ArrayList<BankAccount> for ordered account listing and iteration.
- Each BankAccount keeps an ArrayList<Transaction> to preserve chronological order and allow easy iteration for statements.

Inheritance and Polymorphism Explanation:
- BankAccount is abstract and defines the common interface for deposit, withdraw, and getBalance.
- SavingsAccount and CheckingAccount override withdraw/deposit to implement minimum balance and overdraft rules respectively.
- ATM and Bank deal with BankAccount references and call methods polymorphically, letting concrete subclasses enforce their rules.

Error Handling Strategy:
- Custom exceptions: InsufficientFundsException, InvalidAccountException, AccountBlockedException used to separate business logic failures from input errors.
- PIN attempts tracked in Customer; after 3 failed attempts customer is blocked for 5 minutes (temporary block). blockedUntil records when access is restored.
- All user input validated at the ATM layer using try/catch to prevent the program from crashing.

""";

    public static void main(String[] args) {
        Bank bank = new Bank("My Friendly Bank");
        bank.seedSampleData();
        ATM humanAtm = new ATM(bank);
        humanAtm.welcomeAndRun();
    }
}

class Bank {
    private String name;
    private Map<String, Customer> customers = new HashMap<>();
    private Map<String, BankAccount> accounts = new HashMap<>();

    public Bank(String name) {
        this.name = name;
    }

    public void registerCustomer(Customer who) {
        customers.put(who.getCustomerId(), who);
        for (BankAccount a : who.getAccounts()) {
            accounts.put(a.getAccountNumber(), a);
        }
    }

    public Customer fetchCustomerById(String id) {
        return customers.get(id);
    }

    public BankAccount fetchAccountByNumber(String accNo) {
        return accounts.get(accNo);
    }

    public void addAccount(BankAccount acc) {
        accounts.put(acc.getAccountNumber(), acc);
        Customer c = customers.get(acc.getCustomerId());
        if (c != null) c.addAccount(acc);
    }

    public void seedSampleData() {
        Customer ali = new Customer("CUST1001", "Ali Khan", "1234");
        Customer sana = new Customer("CUST1002", "Sana Noor", "4321");

        SavingsAccount aliSav = new SavingsAccount("ACC2001", ali.getCustomerId(), 5000.0, 1000.0);
        CheckingAccount aliChk = new CheckingAccount("ACC2002", ali.getCustomerId(), 2000.0, -500.0);

        SavingsAccount sanaSav = new SavingsAccount("ACC3001", sana.getCustomerId(), 3000.0, 500.0);

        ali.addAccount(aliSav);
        ali.addAccount(aliChk);
        sana.addAccount(sanaSav);

        registerCustomer(ali);
        registerCustomer(sana);
    }

    public Collection<Customer> allCustomers() {
        return customers.values();
    }
}

class Customer {
    private String customerId;
    private String name;
    private String pin;
    private List<BankAccount> accounts = new ArrayList<>();
    private int failedPinCount = 0;
    private LocalDateTime blockedUntil = null;

    public Customer(String customerId, String name, String pin) {
        this.customerId = customerId;
        this.name = name;
        this.pin = pin;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public List<BankAccount> getAccounts() { return accounts; }
    public void addAccount(BankAccount a) { accounts.add(a); }
    public boolean isBlocked() {
        if (blockedUntil == null) return false;
        if (LocalDateTime.now().isAfter(blockedUntil)) {
            blockedUntil = null;
            failedPinCount = 0;
            return false;
        }
        return true;
    }
    public boolean verifyPin(String attempt) {
        if (isBlocked()) return false;
        if (this.pin.equals(attempt)) {
            failedPinCount = 0;
            return true;
        } else {
            failedPinCount++;
            if (failedPinCount >= 3) blockedUntil = LocalDateTime.now().plusMinutes(5);
            return false;
        }
    }
    public int getFailedPinCount() { return failedPinCount; }
    public LocalDateTime getBlockedUntil() { return blockedUntil; }
}

enum AccountStatus { ACTIVE, BLOCKED, CLOSED }

enum TransactionType { DEPOSIT, WITHDRAWAL, TRANSFER }

enum TransactionStatus { SUCCESS, FAILED_INSUFFICIENT_FUNDS, FAILED_INVALID_ACCOUNT }

abstract class BankAccount {
    protected String accountNumber;
    protected String customerId;
    protected double balance;
    protected AccountStatus status = AccountStatus.ACTIVE;
    protected List<Transaction> history = new ArrayList<>();

    public BankAccount(String accountNumber, String customerId, double initial) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.balance = initial;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getCustomerId() { return customerId; }
    public AccountStatus getStatus() { return status; }
    public double getBalance() { return balance; }
    public List<Transaction> getHistory() { return history; }

    public abstract Transaction putMoney(double amount);
    public abstract Transaction takeMoney(double amount) throws InsufficientFundsException;
}

class SavingsAccount extends BankAccount {
    private double minimumBalance;

    public SavingsAccount(String accountNumber, String customerId, double initial, double minimum) {
        super(accountNumber, customerId, initial);
        this.minimumBalance = minimum;
    }

    public Transaction putMoney(double amount) {
        if (amount <= 0) {
            Transaction t = new Transaction(TransactionType.DEPOSIT, amount, this.accountNumber, null, TransactionStatus.FAILED_INVALID_ACCOUNT);
            history.add(t);
            return t;
        }
        balance += amount;
        Transaction t = new Transaction(TransactionType.DEPOSIT, amount, this.accountNumber, null, TransactionStatus.SUCCESS);
        history.add(t);
        return t;
    }

    public Transaction takeMoney(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            Transaction t = new Transaction(TransactionType.WITHDRAWAL, amount, this.accountNumber, null, TransactionStatus.FAILED_INVALID_ACCOUNT);
            history.add(t);
            return t;
        }
        if (balance - amount < minimumBalance) {
            Transaction t = new Transaction(TransactionType.WITHDRAWAL, amount, this.accountNumber, null, TransactionStatus.FAILED_INSUFFICIENT_FUNDS);
            history.add(t);
            throw new InsufficientFundsException("Minimum balance violated");
        }
        balance -= amount;
        Transaction t = new Transaction(TransactionType.WITHDRAWAL, amount, this.accountNumber, null, TransactionStatus.SUCCESS);
        history.add(t);
        return t;
    }
}

class CheckingAccount extends BankAccount {
    private double overdraftLimit; // negative allowed down to this value

    public CheckingAccount(String accountNumber, String customerId, double initial, double overdraftLimit) {
        super(accountNumber, customerId, initial);
        this.overdraftLimit = overdraftLimit;
    }

    public Transaction putMoney(double amount) {
        if (amount <= 0) {
            Transaction t = new Transaction(TransactionType.DEPOSIT, amount, this.accountNumber, null, TransactionStatus.FAILED_INVALID_ACCOUNT);
            history.add(t);
            return t;
        }
        balance += amount;
        Transaction t = new Transaction(TransactionType.DEPOSIT, amount, this.accountNumber, null, TransactionStatus.SUCCESS);
        history.add(t);
        return t;
    }

    public Transaction takeMoney(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            Transaction t = new Transaction(TransactionType.WITHDRAWAL, amount, this.accountNumber, null, TransactionStatus.FAILED_INVALID_ACCOUNT);
            history.add(t);
            return t;
        }
        if (balance - amount < overdraftLimit) {
            Transaction t = new Transaction(TransactionType.WITHDRAWAL, amount, this.accountNumber, null, TransactionStatus.FAILED_INSUFFICIENT_FUNDS);
            history.add(t);
            throw new InsufficientFundsException("Overdraft limit exceeded");
        }
        balance -= amount;
        Transaction t = new Transaction(TransactionType.WITHDRAWAL, amount, this.accountNumber, null, TransactionStatus.SUCCESS);
        history.add(t);
        return t;
    }
}

class Transaction {
    private static long seq = 100000;
    private String id;
    private TransactionType type;
    private TransactionStatus status;
    private double amount;
    private LocalDateTime timestamp;
    private String fromAccount;
    private String toAccount;

    public Transaction(TransactionType type, double amount, String fromAccount, String toAccount, TransactionStatus status) {
        this.id = "TRX" + (++seq);
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.status = status;
    }

    public String getId() { return id; }
    public TransactionType getType() { return type; }
    public TransactionStatus getStatus() { return status; }
    public double getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getFromAccount() { return fromAccount; }
    public String getToAccount() { return toAccount; }

    public String shortReceipt(double balanceAfter) {
        return "Receipt -> " + id + " | " + type + " | " + amount + " | NewBal: " + balanceAfter + " | " + timestamp.toString();
    }

    public String toString() {
        return id + " | " + type + " | " + amount + " | " + status + " | " + timestamp;
    }
}

class ATM {
    private Bank bank;
    private Scanner in = new Scanner(System.in);

    public ATM(Bank bank) {
        this.bank = bank;
    }

    public void welcomeAndRun() {
        while (true) {
            System.out.println("\n=== Welcome to " + bankName() + " ATM ===");
            System.out.println("1) Sign in with Card Number (Customer ID)");
            System.out.println("2) Exit");
            System.out.print("Choose: ");
            String choice = in.nextLine().trim();
            if (choice.equals("1")) handleSignIn();
            else if (choice.equals("2")) { System.out.println("Goodbye"); break; }
            else System.out.println("Try again");
        }
    }

    private String bankName() { return "My Friendly Bank"; }

    private void handleSignIn() {
        System.out.print("Enter card/customer id: ");
        String id = in.nextLine().trim();
        Customer who = bank.fetchCustomerById(id);
        if (who == null) { System.out.println("No such customer"); return; }
        if (who.isBlocked()) {
            System.out.println("Account is blocked until: " + who.getBlockedUntil());
            return;
        }
        System.out.print("Enter PIN: ");
        String pin = in.nextLine().trim();
        if (!who.verifyPin(pin)) {
            System.out.println("Incorrect PIN. Attempts: " + who.getFailedPinCount());
            if (who.isBlocked()) System.out.println("Temporarily blocked until " + who.getBlockedUntil());
            return;
        }
        System.out.println("Welcome, " + who.getName());
        customerMenu(who);
    }

    private void customerMenu(Customer who) {
        while (true) {
            System.out.println("\nAccounts for " + who.getName() + ":");
            List<BankAccount> accs = who.getAccounts();
            for (int i = 0; i < accs.size(); i++) {
                BankAccount a = accs.get(i);
                System.out.println((i+1) + ") " + a.getAccountNumber() + " (" + a.getClass().getSimpleName() + ") Bal: " + a.getBalance());
            }
            System.out.println((accs.size()+1) + ") Logout");
            System.out.print("Choose account: ");
            String sel = in.nextLine().trim();
            int s;
            try { s = Integer.parseInt(sel); }
            catch (Exception e) { System.out.println("Invalid"); continue; }
            if (s == accs.size() + 1) { System.out.println("Logging out"); break; }
            if (s < 1 || s > accs.size()) { System.out.println("Invalid"); continue; }
            BankAccount chosen = accs.get(s-1);
            accountOperations(chosen);
        }
    }

    private void accountOperations(BankAccount acc) {
        while (true) {
            System.out.println("\n--- Account " + acc.getAccountNumber() + " Menu ---");
            System.out.println("1) Check Balance");
            System.out.println("2) Deposit Funds");
            System.out.println("3) Withdraw Funds");
            System.out.println("4) View Transaction History");
            System.out.println("5) Back");
            System.out.print("Choose: ");
            String c = in.nextLine().trim();
            if (c.equals("1")) System.out.println("Balance: " + acc.getBalance());
            else if (c.equals("2")) {
                System.out.print("Amount to deposit: ");
                try {
                    double amt = Double.parseDouble(in.nextLine().trim());
                    Transaction t = acc.putMoney(amt);
                    if (t.getStatus() == TransactionStatus.SUCCESS) System.out.println(t.shortReceipt(acc.getBalance()));
                    else System.out.println("Deposit failed");
                } catch (Exception e) { System.out.println("Bad amount"); }
            }
            else if (c.equals("3")) {
                System.out.print("Amount to withdraw: ");
                try {
                    double amt = Double.parseDouble(in.nextLine().trim());
                    try {
                        Transaction t = acc.takeMoney(amt);
                        if (t.getStatus() == TransactionStatus.SUCCESS) System.out.println(t.shortReceipt(acc.getBalance()));
                    } catch (InsufficientFundsException ex) {
                        System.out.println("Withdraw failed: " + ex.getMessage());
                    }
                } catch (Exception e) { System.out.println("Bad amount"); }
            }
            else if (c.equals("4")) {
                List<Transaction> h = acc.getHistory();
                if (h.isEmpty()) System.out.println("No transactions yet");
                else for (Transaction t : h) System.out.println(t.toString());
            }
            else if (c.equals("5")) break;
            else System.out.println("Try again");
        }
    }
}

class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String m) { super(m); }
}

class InvalidAccountException extends Exception {
    public InvalidAccountException(String m) { super(m); }
}

class AccountBlockedException extends Exception {
    public AccountBlockedException(String m) { super(m); }
}
