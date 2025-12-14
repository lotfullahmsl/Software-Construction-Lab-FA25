/*
import java.awt.*;
import javax.swing.*;

public class TicketMachineGUI extends JFrame {

    // Components
    private JLabel titleLabel;
    private JLabel destinationLabel;
    private JLabel ticketTypeLabel;
    private JLabel quantityLabel;
    private JLabel totalLabel;

    private JComboBox<String> destinationCombo;
    private JRadioButton adultRadio;
    private JRadioButton childRadio;
    private JRadioButton oneWayRadio;
    private JRadioButton returnRadio;

    private JTextField quantityField;
    private JTextField totalPriceField;

    private JButton purchaseButton;
    private JButton cancelButton;
    private JButton resetButton;

    private JTextArea receiptArea;

    public TicketMachineGUI() {
        // Set up the frame
        setTitle("Train & Bus Ticket Machine");
        setSize(600, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Create main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 248, 255));

        // Title Panel
        JPanel titlePanel = createTitlePanel();

        // Input Panel (Center)
        JPanel inputPanel = createInputPanel();

        // Button Panel
        JPanel buttonPanel = createButtonPanel();

        // Receipt Panel
        JPanel receiptPanel = createReceiptPanel();

        // Add panels to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel and receipt panel to frame
        add(mainPanel, BorderLayout.CENTER);
        add(receiptPanel, BorderLayout.EAST);

        setVisible(true);
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(70, 130, 180));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        titleLabel = new JLabel("TICKET BOOKING SYSTEM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        panel.add(titleLabel);
        return panel;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 15));
        panel.setBackground(new Color(240, 248, 255));

        // Destination Section
        panel.add(createDestinationPanel());

        // Passenger Type Section
        panel.add(createPassengerTypePanel());

        // Journey Type Section
        panel.add(createJourneyTypePanel());

        // Quantity Section
        panel.add(createQuantityPanel());

        // Total Price Section
        panel.add(createTotalPricePanel());

        return panel;
    }

    private JPanel createDestinationPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Select Destination"));

        destinationLabel = new JLabel("Destination:");
        destinationLabel.setFont(new Font("Arial", Font.BOLD, 14));

        String[] destinations = {"Select City", "Karachi", "Lahore", "Islamabad",
                "Rawalpindi", "Faisalabad", "Multan", "Peshawar"};
        destinationCombo = new JComboBox<>(destinations);
        destinationCombo.setPreferredSize(new Dimension(200, 30));

        panel.add(destinationLabel);
        panel.add(destinationCombo);

        return panel;
    }

    private JPanel createPassengerTypePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Passenger Type"));

        ticketTypeLabel = new JLabel("Select Type:");
        ticketTypeLabel.setFont(new Font("Arial", Font.BOLD, 14));

        adultRadio = new JRadioButton("Adult");
        childRadio = new JRadioButton("Child");

        adultRadio.setBackground(Color.WHITE);
        childRadio.setBackground(Color.WHITE);

        ButtonGroup passengerGroup = new ButtonGroup();
        passengerGroup.add(adultRadio);
        passengerGroup.add(childRadio);

        adultRadio.setSelected(true);

        panel.add(ticketTypeLabel);
        panel.add(adultRadio);
        panel.add(childRadio);

        return panel;
    }

    private JPanel createJourneyTypePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Journey Type"));

        JLabel journeyLabel = new JLabel("Select Journey:");
        journeyLabel.setFont(new Font("Arial", Font.BOLD, 14));

        oneWayRadio = new JRadioButton("One Way");
        returnRadio = new JRadioButton("Return");

        oneWayRadio.setBackground(Color.WHITE);
        returnRadio.setBackground(Color.WHITE);

        ButtonGroup journeyGroup = new ButtonGroup();
        journeyGroup.add(oneWayRadio);
        journeyGroup.add(returnRadio);

        oneWayRadio.setSelected(true);

        panel.add(journeyLabel);
        panel.add(oneWayRadio);
        panel.add(returnRadio);

        return panel;
    }

    private JPanel createQuantityPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Number of Tickets"));

        quantityLabel = new JLabel("Quantity:");
        quantityLabel.setFont(new Font("Arial", Font.BOLD, 14));

        quantityField = new JTextField("1");
        quantityField.setPreferredSize(new Dimension(100, 30));

        panel.add(quantityLabel);
        panel.add(quantityField);

        return panel;
    }

    private JPanel createTotalPricePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBackground(new Color(144, 238, 144));
        panel.setBorder(BorderFactory.createTitledBorder("Total Amount"));

        totalLabel = new JLabel("Total Price:");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));

        totalPriceField = new JTextField("Rs. 0.00");
        totalPriceField.setPreferredSize(new Dimension(150, 35));
        totalPriceField.setEditable(false);
        totalPriceField.setFont(new Font("Arial", Font.BOLD, 16));
        totalPriceField.setBackground(Color.WHITE);

        panel.add(totalLabel);
        panel.add(totalPriceField);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBackground(new Color(240, 248, 255));

        purchaseButton = new JButton("Purchase Ticket");
        purchaseButton.setPreferredSize(new Dimension(150, 40));
        purchaseButton.setBackground(new Color(34, 139, 34));
        purchaseButton.setForeground(Color.WHITE);
        purchaseButton.setFont(new Font("Arial", Font.BOLD, 14));

        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(150, 40));
        cancelButton.setBackground(new Color(220, 20, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));

        resetButton = new JButton("Reset");
        resetButton.setPreferredSize(new Dimension(150, 40));
        resetButton.setBackground(new Color(255, 165, 0));
        resetButton.setForeground(Color.WHITE);
        resetButton.setFont(new Font("Arial", Font.BOLD, 14));

        panel.add(purchaseButton);
        panel.add(resetButton);
        panel.add(cancelButton);

        return panel;
    }

    private JPanel createReceiptPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(250, 0));

        JLabel receiptLabel = new JLabel("Receipt/Summary");
        receiptLabel.setFont(new Font("Arial", Font.BOLD, 16));
        receiptLabel.setHorizontalAlignment(SwingConstants.CENTER);
        receiptLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));

        receiptArea = new JTextArea(10, 20);
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        receiptArea.setText("No ticket purchased yet.\n\nSelect options and\nclick 'Purchase Ticket'");
        receiptArea.setBackground(new Color(255, 255, 224));

        JScrollPane scrollPane = new JScrollPane(receiptArea);

        panel.add(receiptLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TicketMachineGUI();
            }
        });
    }
}*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicketMachineGUI extends JFrame {

    // Components
    private JLabel titleLabel;
    private JLabel destinationLabel;
    private JLabel ticketTypeLabel;
    private JLabel quantityLabel;
    private JLabel totalLabel;

    private JComboBox<String> destinationCombo;
    private JRadioButton adultRadio;
    private JRadioButton childRadio;
    private JRadioButton oneWayRadio;
    private JRadioButton returnRadio;

    private JTextField quantityField;
    private JTextField totalPriceField;

    private JButton purchaseButton;
    private JButton cancelButton;
    private JButton resetButton;

    private JTextArea receiptArea;

    // Ticket counter
    private int ticketNumber = 1000;

    public TicketMachineGUI() {
        // Set up the frame
        setTitle("Train & Bus Ticket Machine");
        setSize(600, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Create main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 248, 255));

        // Title Panel
        JPanel titlePanel = createTitlePanel();

        // Input Panel (Center)
        JPanel inputPanel = createInputPanel();

        // Button Panel
        JPanel buttonPanel = createButtonPanel();

        // Receipt Panel
        JPanel receiptPanel = createReceiptPanel();

        // Add panels to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel and receipt panel to frame
        add(mainPanel, BorderLayout.CENTER);
        add(receiptPanel, BorderLayout.EAST);

        // Add event listeners
        addEventListeners();

        setVisible(true);
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(70, 130, 180));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        titleLabel = new JLabel("TICKET BOOKING SYSTEM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        panel.add(titleLabel);
        return panel;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 15));
        panel.setBackground(new Color(240, 248, 255));

        panel.add(createDestinationPanel());
        panel.add(createPassengerTypePanel());
        panel.add(createJourneyTypePanel());
        panel.add(createQuantityPanel());
        panel.add(createTotalPricePanel());

        return panel;
    }

    private JPanel createDestinationPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Select Destination"));

        destinationLabel = new JLabel("Destination:");
        destinationLabel.setFont(new Font("Arial", Font.BOLD, 14));

        String[] destinations = {"Select City", "Karachi", "Lahore", "Islamabad",
                "Rawalpindi", "Faisalabad", "Multan", "Peshawar"};
        destinationCombo = new JComboBox<>(destinations);
        destinationCombo.setPreferredSize(new Dimension(200, 30));

        panel.add(destinationLabel);
        panel.add(destinationCombo);

        return panel;
    }

    private JPanel createPassengerTypePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Passenger Type"));

        ticketTypeLabel = new JLabel("Select Type:");
        ticketTypeLabel.setFont(new Font("Arial", Font.BOLD, 14));

        adultRadio = new JRadioButton("Adult");
        childRadio = new JRadioButton("Child");

        adultRadio.setBackground(Color.WHITE);
        childRadio.setBackground(Color.WHITE);

        ButtonGroup passengerGroup = new ButtonGroup();
        passengerGroup.add(adultRadio);
        passengerGroup.add(childRadio);

        adultRadio.setSelected(true);

        panel.add(ticketTypeLabel);
        panel.add(adultRadio);
        panel.add(childRadio);

        return panel;
    }

    private JPanel createJourneyTypePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Journey Type"));

        JLabel journeyLabel = new JLabel("Select Journey:");
        journeyLabel.setFont(new Font("Arial", Font.BOLD, 14));

        oneWayRadio = new JRadioButton("One Way");
        returnRadio = new JRadioButton("Return");

        oneWayRadio.setBackground(Color.WHITE);
        returnRadio.setBackground(Color.WHITE);

        ButtonGroup journeyGroup = new ButtonGroup();
        journeyGroup.add(oneWayRadio);
        journeyGroup.add(returnRadio);

        oneWayRadio.setSelected(true);

        panel.add(journeyLabel);
        panel.add(oneWayRadio);
        panel.add(returnRadio);

        return panel;
    }

    private JPanel createQuantityPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Number of Tickets"));

        quantityLabel = new JLabel("Quantity:");
        quantityLabel.setFont(new Font("Arial", Font.BOLD, 14));

        quantityField = new JTextField("1");
        quantityField.setPreferredSize(new Dimension(100, 30));

        panel.add(quantityLabel);
        panel.add(quantityField);

        return panel;
    }

    private JPanel createTotalPricePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBackground(new Color(144, 238, 144));
        panel.setBorder(BorderFactory.createTitledBorder("Total Amount"));

        totalLabel = new JLabel("Total Price:");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));

        totalPriceField = new JTextField("Rs. 0.00");
        totalPriceField.setPreferredSize(new Dimension(150, 35));
        totalPriceField.setEditable(false);
        totalPriceField.setFont(new Font("Arial", Font.BOLD, 16));
        totalPriceField.setBackground(Color.WHITE);

        panel.add(totalLabel);
        panel.add(totalPriceField);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBackground(new Color(240, 248, 255));

        purchaseButton = new JButton("Purchase Ticket");
        purchaseButton.setPreferredSize(new Dimension(150, 40));
        purchaseButton.setBackground(new Color(34, 139, 34));
        purchaseButton.setForeground(Color.WHITE);
        purchaseButton.setFont(new Font("Arial", Font.BOLD, 14));

        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(150, 40));
        cancelButton.setBackground(new Color(220, 20, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));

        resetButton = new JButton("Reset");
        resetButton.setPreferredSize(new Dimension(150, 40));
        resetButton.setBackground(new Color(255, 165, 0));
        resetButton.setForeground(Color.WHITE);
        resetButton.setFont(new Font("Arial", Font.BOLD, 14));

        panel.add(purchaseButton);
        panel.add(resetButton);
        panel.add(cancelButton);

        return panel;
    }

    private JPanel createReceiptPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(250, 0));

        JLabel receiptLabel = new JLabel("Receipt/Summary");
        receiptLabel.setFont(new Font("Arial", Font.BOLD, 16));
        receiptLabel.setHorizontalAlignment(SwingConstants.CENTER);
        receiptLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));

        receiptArea = new JTextArea(10, 20);
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        receiptArea.setText("No ticket purchased yet.\n\nSelect options and\nclick 'Purchase Ticket'");
        receiptArea.setBackground(new Color(255, 255, 224));

        JScrollPane scrollPane = new JScrollPane(receiptArea);

        panel.add(receiptLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // ========== TASK 2: EVENT LISTENERS & FUNCTIONALITY ==========

    private void addEventListeners() {
        // Real-time price calculation
        destinationCombo.addActionListener(e -> updatePrice());
        adultRadio.addActionListener(e -> updatePrice());
        childRadio.addActionListener(e -> updatePrice());
        oneWayRadio.addActionListener(e -> updatePrice());
        returnRadio.addActionListener(e -> updatePrice());
        quantityField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                updatePrice();
            }
        });

        // Purchase Button
        purchaseButton.addActionListener(e -> purchaseTicket());

        // Reset Button
        resetButton.addActionListener(e -> resetForm());

        // Cancel Button
        cancelButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to exit?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    // Update price display
    private void updatePrice() {
        try {
            String destination = (String) destinationCombo.getSelectedItem();
            if (destination.equals("Select City")) {
                totalPriceField.setText("Rs. 0.00");
                return;
            }

            String passengerType = adultRadio.isSelected() ? "Adult" : "Child";
            String journeyType = oneWayRadio.isSelected() ? "One Way" : "Return";
            int quantity = Integer.parseInt(quantityField.getText().trim());

            if (quantity < 1) {
                quantity = 1;
                quantityField.setText("1");
            }

            // Use PriceCalculator class
            double totalPrice = PriceCalculator.calculatePrice(
                    destination, passengerType, journeyType, quantity
            );

            totalPriceField.setText(String.format("Rs. %.2f", totalPrice));

        } catch (NumberFormatException ex) {
            totalPriceField.setText("Rs. 0.00");
        }
    }

    // Purchase ticket
    private void purchaseTicket() {
        String destination = (String) destinationCombo.getSelectedItem();

        // Validation
        if (destination.equals("Select City")) {
            JOptionPane.showMessageDialog(this,
                    "Please select a destination!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityField.getText().trim());
            if (quantity < 1) {
                throw new NumberFormatException();
            }

            String passengerType = adultRadio.isSelected() ? "Adult" : "Child";
            String journeyType = oneWayRadio.isSelected() ? "One Way" : "Return";

            // Calculate total price
            double totalPrice = PriceCalculator.calculatePrice(
                    destination, passengerType, journeyType, quantity
            );

            // Create Ticket object
            Ticket ticket = new Ticket(ticketNumber, destination, passengerType,
                    journeyType, quantity, totalPrice);

            // Display receipt
            receiptArea.setText(ticket.generateReceipt());

            // Show success message
            JOptionPane.showMessageDialog(this,
                    "Ticket purchased successfully!\nTicket #" + ticketNumber,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            ticketNumber++;

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid quantity!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Reset form
    private void resetForm() {
        destinationCombo.setSelectedIndex(0);
        adultRadio.setSelected(true);
        oneWayRadio.setSelected(true);
        quantityField.setText("1");
        totalPriceField.setText("Rs. 0.00");
        receiptArea.setText("No ticket purchased yet.\n\nSelect options and\nclick 'Purchase Ticket'");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicketMachineGUI());
    }
}