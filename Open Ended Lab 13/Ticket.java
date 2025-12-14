import java.text.SimpleDateFormat;
import java.util.Date;

public class Ticket {
    private int ticketNumber;
    private String destination;
    private String passengerType;
    private String journeyType;
    private int quantity;
    private double totalPrice;
    private String dateTime;

    public Ticket(int ticketNumber, String destination, String passengerType,
                  String journeyType, int quantity, double totalPrice) {
        this.ticketNumber = ticketNumber;
        this.destination = destination;
        this.passengerType = passengerType;
        this.journeyType = journeyType;
        this.quantity = quantity;
        this.totalPrice = totalPrice;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.dateTime = sdf.format(new Date());
    }

    // Getters
    public int getTicketNumber() { return ticketNumber; }
    public String getDestination() { return destination; }
    public String getPassengerType() { return passengerType; }
    public String getJourneyType() { return journeyType; }
    public int getQuantity() { return quantity; }
    public double getTotalPrice() { return totalPrice; }
    public String getDateTime() { return dateTime; }

    // Generate receipt
    public String generateReceipt() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("================================\n");
        receipt.append("   TICKET RECEIPT\n");
        receipt.append("================================\n");
        receipt.append("Ticket #: ").append(ticketNumber).append("\n");
        receipt.append("Date: ").append(dateTime).append("\n");
        receipt.append("--------------------------------\n");
        receipt.append("Destination: ").append(destination).append("\n");
        receipt.append("Passenger: ").append(passengerType).append("\n");
        receipt.append("Journey: ").append(journeyType).append("\n");
        receipt.append("Quantity: ").append(quantity).append("\n");
        receipt.append("--------------------------------\n");
        receipt.append("TOTAL: Rs. ").append(String.format("%.2f", totalPrice)).append("\n");
        receipt.append("================================\n");
        receipt.append("\nThank you for booking!\n");
        receipt.append("Have a safe journey!\n");

        return receipt.toString();
    }
}