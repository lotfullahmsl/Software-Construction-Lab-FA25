public class PriceCalculator {

    // Calculate total price based on selections
    public static double calculatePrice(String destination, String passengerType,
                                        String journeyType, int quantity) {
        // Get base price
        double basePrice = getBasePrice(destination);

        // Apply passenger type discount (Child = 50% off)
        if (passengerType.equals("Child")) {
            basePrice = basePrice * 0.5;
        }

        // Apply journey type (Return = Double price)
        if (journeyType.equals("Return")) {
            basePrice = basePrice * 2;
        }

        // Calculate total with quantity
        return basePrice * quantity;
    }

    // Get base price for each destination
    private static double getBasePrice(String destination) {
        switch (destination) {
            case "Karachi": return 1500.0;
            case "Lahore": return 2000.0;
            case "Islamabad": return 1800.0;
            case "Rawalpindi": return 1700.0;
            case "Faisalabad": return 1600.0;
            case "Multan": return 1400.0;
            case "Peshawar": return 2200.0;
            default: return 0.0;
        }
    }
}