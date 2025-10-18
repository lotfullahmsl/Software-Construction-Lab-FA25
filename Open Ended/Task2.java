import java.util.*;
import java.time.*;

// you have to make the code the way in which you dont h

public class Task2 {
    public static final String DESIGN_DOC = """
UML-Like Class Diagram (text):

BankingSystem (main)
  - boots the ATM-like interface

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
  - abstract deposit, withdraw

SavingsAccount extends BankAccount
  - double minimumBalance

CheckingAccount extends BankAccount
  - double overdraftLimit

Transaction
  - String id, TransactionType type, TransactionStatus status, double amount, LocalDateTime time, fromAccount, toAccount

ATM
  - interacts only with Bank
  - handles registration, authentication, account creation, transfers, and account operations

Data Structure Justification:
- customers in HashMap for O(1) lookup by customer id/card.
- accounts in HashMap for O(1) lookup by account number.
- Customer keeps ArrayList of accounts to present an ordered list to the user.
- Each BankAccount keeps an ArrayList<Transaction> to store chronological transactions.

Inheritance & Polymorphism:
- BankAccount defines the interface; SavingsAccount and CheckingAccount implement rules.
- Bank and ATM operate on BankAccount references allowing polymorphic behavior.

Error handling:
- Custom exceptions: InsufficientFundsException, InvalidAccountException, AccountBlockedException.
- PIN lock after 3 wrong attempts — temporary block for 5 minutes.

""";

    public static void main(String[] args) {
        Bank marketBank = new Bank("Kindly Bank");
        marketBank.seedSampleData();
        HumanLikeATM teller = new HumanLikeATM(marketBank);
        teller.greetAndServe();
    }
}

class Bank {
    private String bankName;
    private Map<String, Customer> customers = new HashMap<>();
    private Map<String, BankAccount> accounts = new HashMap<>();
    private long custSeq = 1000;
    private long accSeq = 2000;

    public Bank(String bankName) { this.bankName = bankName; }

    public String makeCustomerId() { return "C" + (++custSeq); }
    public String makeAccountId() { return "A" + (++accSeq); }

    public void registerCustomer(Customer p) {
        customers.put(p.getCustomerId(), p);
        for (BankAccount a : p.getAccounts()) accounts.put(a.getAccountNumber(), a);
    }

    public Customer findCustomer(String id) { return customers.get(id); }
    public BankAccount findAccount(String an) { return accounts.get(an); }

    public void addAccount(BankAccount a) {
        accounts.put(a.getAccountNumber(), a);
        Customer c = customers.get(a.getCustomerId());
        if (c != null) c.addAccount(a);
    }

    public Collection<Customer> snapshotOfCustomers() { return customers.values(); }

    public void seedSampleData() {
        Customer rafi = new Customer(makeCustomerId(), "Rafi Ahmed", "1111");
        Customer hira = new Customer(makeCustomerId(), "Hira Malik", "2222");

        SavingsAccount rafiSavings = new SavingsAccount(makeAccountId(), rafi.getCustomerId(), 1500.0, 500.0);
        CheckingAccount rafiChecking = new CheckingAccount(makeAccountId(), rafi.getCustomerId(), 800.0, -300.0);

        SavingsAccount hiraSavings = new SavingsAccount(makeAccountId(), hira.getCustomerId(), 2500.0, 1000.0);

        rafi.addAccount(rafiSavings);
        rafi.addAccount(rafiChecking);
        hira.addAccount(hiraSavings);

        registerCustomer(rafi);
        registerCustomer(hira);
    }
}

class Customer {
    private String customerId;
    private String fullName;
    private String pin;
    private List<BankAccount> accounts = new ArrayList<>();
    private int failedPinCount = 0;
    private LocalDateTime blockedUntil = null;

    public Customer(String id, String name, String pin) {
        this.customerId = id; this.fullName = name; this.pin = pin;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return fullName; }
    public List<BankAccount> getAccounts() { return accounts; }
    public void addAccount(BankAccount a) { accounts.add(a); }

    public boolean isCurrentlyBlocked() {
        if (blockedUntil == null) return false;
        if (LocalDateTime.now().isAfter(blockedUntil)) { blockedUntil = null; failedPinCount = 0; return false; }
        return true;
    }

    public boolean checkPin(String guess) {
        if (isCurrentlyBlocked()) return false;
        if (this.pin.equals(guess)) { failedPinCount = 0; return true; }
        failedPinCount++;
        if (failedPinCount >= 3) blockedUntil = LocalDateTime.now().plusMinutes(5);
        return false;
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

    public BankAccount(String an, String owner, double initial) {
        this.accountNumber = an; this.customerId = owner; this.balance = initial;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getCustomerId() { return customerId; }
    public double currentBalance() { return balance; }
    public List<Transaction> recentHistory() { return history; }

    public abstract Transaction giveMoney(double amount);
    public abstract Transaction takeMoney(double amount) throws InsufficientFundsException;
}

class SavingsAccount extends BankAccount {
    private double minimumBalance;

    public SavingsAccount(String an, String owner, double initial, double minimum) {
        super(an, owner, initial); this.minimumBalance = minimum; }

    public Transaction giveMoney(double amount) {
        if (amount <= 0) {
            Transaction t = new Transaction(TransactionType.DEPOSIT, amount, accountNumber, null, TransactionStatus.FAILED_INVALID_ACCOUNT);
            history.add(t); return t;
        }
        balance += amount;
        Transaction t = new Transaction(TransactionType.DEPOSIT, amount, accountNumber, null, TransactionStatus.SUCCESS);
        history.add(t); return t;
    }

    public Transaction takeMoney(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            Transaction t = new Transaction(TransactionType.WITHDRAWAL, amount, accountNumber, null, TransactionStatus.FAILED_INVALID_ACCOUNT);
            history.add(t); return t;
        }
        if (balance - amount < minimumBalance) {
            Transaction t = new Transaction(TransactionType.WITHDRAWAL, amount, accountNumber, null, TransactionStatus.FAILED_INSUFFICIENT_FUNDS);
            history.add(t); throw new InsufficientFundsException("Would breach minimum balance");
        }
        balance -= amount;
        Transaction t = new Transaction(TransactionType.WITHDRAWAL, amount, accountNumber, null, TransactionStatus.SUCCESS);
        history.add(t); return t;
    }
}

class CheckingAccount extends BankAccount {
    private double overdraftLimit;

    public CheckingAccount(String an, String owner, double initial, double overdraftLimit) {
        super(an, owner, initial); this.overdraftLimit = overdraftLimit; }

    public Transaction giveMoney(double amount) {
        if (amount <= 0) {
            Transaction t = new Transaction(TransactionType.DEPOSIT, amount, accountNumber, null, TransactionStatus.FAILED_INVALID_ACCOUNT);
            history.add(t); return t;
        }
        balance += amount;
        Transaction t = new Transaction(TransactionType.DEPOSIT, amount, accountNumber, null, TransactionStatus.SUCCESS);
        history.add(t); return t;
    }

    public Transaction takeMoney(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            Transaction t = new Transaction(TransactionType.WITHDRAWAL, amount, accountNumber, null, TransactionStatus.FAILED_INVALID_ACCOUNT);
            history.add(t); return t;
        }
        if (balance - amount < overdraftLimit) {
            Transaction t = new Transaction(TransactionType.WITHDRAWAL, amount, accountNumber, null, TransactionStatus.FAILED_INSUFFICIENT_FUNDS);
            history.add(t); throw new InsufficientFundsException("Overdraft limit reached");
        }
        balance -= amount;
        Transaction t = new Transaction(TransactionType.WITHDRAWAL, amount, accountNumber, null, TransactionStatus.SUCCESS);
        history.add(t); return t;
    }
}

class Transaction {
    private static long serial = 7000;
    private String id;
    private TransactionType type;
    private TransactionStatus status;
    private double amount;
    private LocalDateTime when;
    private String fromAccount;
    private String toAccount;

    public Transaction(TransactionType type, double amount, String fromAccount, String toAccount, TransactionStatus status) {
        this.id = "T" + (++serial);
        this.type = type; this.amount = amount; this.when = LocalDateTime.now();
        this.fromAccount = fromAccount; this.toAccount = toAccount; this.status = status;
    }

    public String getId() { return id; }
    public TransactionType getType() { return type; }
    public TransactionStatus getStatus() { return status; }
    public double getAmount() { return amount; }
    public LocalDateTime whenHappened() { return when; }
    public String getFrom() { return fromAccount; }
    public String getTo() { return toAccount; }

    public String receipt(double newBal) {
        return "Receipt: " + id + " | " + type + " | Amt: " + amount + " | NewBal: " + newBal + " | At: " + when;
    }

    public String toString() { return id + " | " + type + " | " + amount + " | " + status + " | " + when; }
}

class HumanLikeATM {
    private Bank bank;
    private Scanner in = new Scanner(System.in);

    public HumanLikeATM(Bank bank) { this.bank = bank; }

    public void greetAndServe() {
        while (true) {
            System.out.println("\n--- Welcome to " + bankName() + " ---");
            System.out.println("1) Sign in");
            System.out.println("2) New customer sign-up");
            System.out.println("3) List my customers (for testing)");
            System.out.println("4) Exit");
            System.out.print("Pick: ");
            String pick = in.nextLine().trim();
            if (pick.equals("1")) signInFlow();
            else if (pick.equals("2")) signUpNewPerson();
            else if (pick.equals("3")) listCustomersQuick();
            else if (pick.equals("4")) { System.out.println("Take care"); break; }
            else System.out.println("Not understood");
        }
    }

    private String bankName() { return "Kindly Bank"; }

    private void listCustomersQuick() {
        Collection<Customer> all = bank.snapshotOfCustomers();
        if (all.isEmpty()) { System.out.println("No customers yet"); return; }
        for (Customer c : all) System.out.println(c.getCustomerId() + " -> " + c.getName());
    }

    private void signUpNewPerson() {
        System.out.print("Your full name: ");
        String name = in.nextLine().trim();
        if (name.isEmpty()) { System.out.println("Name can't be empty"); return; }
        System.out.print("Choose a 4-digit PIN: ");
        String pin = in.nextLine().trim();
        if (!pin.matches("\\d{4}")) { System.out.println("PIN must be 4 digits"); return; }
        String cid = bank.makeCustomerId();
        Customer newbie = new Customer(cid, name, pin);
        bank.registerCustomer(newbie);
        System.out.println("Welcome " + name + ", your customer id is: " + cid);
        System.out.println("Now you can create an account");
        makeNewAccountFor(newbie);
    }

    private void makeNewAccountFor(Customer who) {
        System.out.println("Pick account type: 1) Savings 2) Checking");
        String t = in.nextLine().trim();
        if (!t.equals("1") && !t.equals("2")) { System.out.println("Cancel"); return; }
        System.out.print("Initial deposit amount: ");
        double init;
        try { init = Double.parseDouble(in.nextLine().trim()); }
        catch (Exception e) { System.out.println("Bad amount"); return; }
        String accId = bank.makeAccountId();
        if (t.equals("1")) {
            System.out.print("Minimum balance for this savings (suggested 500): ");
            double min;
            try { min = Double.parseDouble(in.nextLine().trim()); } catch (Exception e) { min = 500.0; }
            SavingsAccount s = new SavingsAccount(accId, who.getCustomerId(), init, min);
            who.addAccount(s); bank.addAccount(s);
            System.out.println("Created savings account: " + accId);
        } else {
            System.out.print("Overdraft allowed negative limit (suggest -300): ");
            double overd;
            try { overd = Double.parseDouble(in.nextLine().trim()); } catch (Exception e) { overd = -300.0; }
            CheckingAccount c = new CheckingAccount(accId, who.getCustomerId(), init, overd);
            who.addAccount(c); bank.addAccount(c);
            System.out.println("Created checking account: " + accId);
        }
    }

    private void signInFlow() {
        System.out.print("Enter your customer id: ");
        String cid = in.nextLine().trim();
        Customer who = bank.findCustomer(cid);
        if (who == null) { System.out.println("No such customer"); return; }
        if (who.isCurrentlyBlocked()) { System.out.println("Access blocked until " + who.getBlockedUntil()); return; }
        System.out.print("Enter PIN: ");
        String tryPin = in.nextLine().trim();
        if (!who.checkPin(tryPin)) {
            System.out.println("Wrong PIN. Attempts: " + who.getFailedPinCount());
            if (who.isCurrentlyBlocked()) System.out.println("Temporarily blocked until " + who.getBlockedUntil());
            return;
        }
        System.out.println("Hello " + who.getName());
        loggedCustomerMenu(who);
    }

    private void loggedCustomerMenu(Customer who) {
        while (true) {
            System.out.println("\nYour accounts:");
            List<BankAccount> accs = who.getAccounts();
            for (int i = 0; i < accs.size(); i++) System.out.println((i+1) + ") " + accs.get(i).getAccountNumber() + " (" + accs.get(i).getClass().getSimpleName() + ") Bal:" + accs.get(i).currentBalance());
            System.out.println("a) Create new account  b) Transfer money  c) Logout");
            System.out.print("Choose number or letter: ");
            String sel = in.nextLine().trim();
            if (sel.equalsIgnoreCase("a")) makeNewAccountFor(who);
            else if (sel.equalsIgnoreCase("b")) transferFlow(who);
            else if (sel.equalsIgnoreCase("c")) { System.out.println("Bye"); break; }
            else {
                int pick;
                try { pick = Integer.parseInt(sel); } catch (Exception e) { System.out.println("Try again"); continue; }
                if (pick < 1 || pick > accs.size()) { System.out.println("Bad choice"); continue; }
                BankAccount chosen = accs.get(pick-1);
                operateOnAccount(chosen);
            }
        }
    }

    private void transferFlow(Customer who) {
        System.out.println("Transfer - choose source account number from your list");
        List<BankAccount> accs = who.getAccounts();
        for (int i = 0; i < accs.size(); i++) System.out.println((i+1) + ") " + accs.get(i).getAccountNumber() + " Bal:" + accs.get(i).currentBalance());
        System.out.print("Pick source: ");
        int s;
        try { s = Integer.parseInt(in.nextLine().trim()); } catch (Exception e) { System.out.println("Bad"); return; }
        if (s < 1 || s > accs.size()) { System.out.println("Bad pick"); return; }
        BankAccount source = accs.get(s-1);
        System.out.print("Enter destination account number: ");
        String destNo = in.nextLine().trim();
        BankAccount dest = bank.findAccount(destNo);
        if (dest == null) { System.out.println("No such destination account"); return; }
        System.out.print("Amount to move: ");
        double amt;
        try { amt = Double.parseDouble(in.nextLine().trim()); } catch (Exception e) { System.out.println("Bad amount"); return; }
        if (amt <= 0) { System.out.println("Amount must be positive"); return; }
        try {
            Transaction out = source.takeMoney(amt);
            Transaction inT = dest.giveMoney(amt);
            Transaction combined = new Transaction(TransactionType.TRANSFER, amt, source.getAccountNumber(), dest.getAccountNumber(), TransactionStatus.SUCCESS);
            source.recentHistory().add(combined);
            dest.recentHistory().add(combined);
            System.out.println("Transfer done. " + combined.receipt(source.currentBalance()));
        } catch (InsufficientFundsException ex) {
            System.out.println("Transfer failed: " + ex.getMessage());
        }
    }

    private void operateOnAccount(BankAccount acc) {
        while (true) {
            System.out.println("\nAccount " + acc.getAccountNumber() + " Menu");
            System.out.println("1) Check balance  2) Deposit  3) Withdraw  4) View history  5) Back");
            System.out.print("Pick: ");
            String pick = in.nextLine().trim();
            if (pick.equals("1")) System.out.println("Balance: " + acc.currentBalance());
            else if (pick.equals("2")) {
                System.out.print("Amount to deposit: ");
                try { double amt = Double.parseDouble(in.nextLine().trim()); Transaction t = acc.giveMoney(amt); if (t.getStatus() == TransactionStatus.SUCCESS) System.out.println(t.receipt(acc.currentBalance())); else System.out.println("Deposit failed"); } catch (Exception e) { System.out.println("Bad amount"); }
            }
            else if (pick.equals("3")) {
                System.out.print("Amount to withdraw: ");
                try { double amt = Double.parseDouble(in.nextLine().trim()); try { Transaction t = acc.takeMoney(amt); if (t.getStatus() == TransactionStatus.SUCCESS) System.out.println(t.receipt(acc.currentBalance())); } catch (InsufficientFundsException ex) { System.out.println("Withdraw failed: " + ex.getMessage()); } } catch (Exception e) { System.out.println("Bad amount"); }
            }
            else if (pick.equals("4")) {
                List<Transaction> h = acc.recentHistory(); if (h.isEmpty()) System.out.println("No transactions yet"); else for (Transaction t : h) System.out.println(t.toString());
            }
            else if (pick.equals("5")) break;
            else System.out.println("Try again");
        }
    }
}

class InsufficientFundsException extends Exception { public InsufficientFundsException(String m) { super(m); } }
class InvalidAccountException extends Exception { public InvalidAccountException(String m) { super(m); } }
class AccountBlockedException extends Exception { public AccountBlockedException(String m) { super(m); } }
