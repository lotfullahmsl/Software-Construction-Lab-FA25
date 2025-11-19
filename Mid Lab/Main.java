/*
I want to build the  logic for hotel, chatting system, a small hotel chain that wants to digitize its operations.
The system should manage guest profiles, room bookings, check-ins/check-outs, and basic billing.
It must be modular, scalable, and demonstrate strong object-oriented design using Java.
StaySmart wants the system to:
- Handle multiple guests and rooms
- Track bookings and availability
- Generate bills based on stay duration
- Provide basic automated responses for common guest queries
- Be easy to debug and maintain
*/


import java.util.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class Main {


    static class Guest {
        final String id;
        String name;
        public Guest(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }


    static class Room {
        final int number;
        final String type;
        final double rate;

        public Room(int number, String type, double rate) {
            this.number = number;
            this.type = type;
            this.rate = Math.max(0, rate);
        }
    }


    static class Booking {
        final Guest guest;
        final Room room;
        final LocalDate in;
        final LocalDate out;

        public Booking(Guest guest, Room room, LocalDate in, LocalDate out) {
            this.guest = guest;
            this.room = room;
            this.in = in;
            this.out = out;
        }

        public double bill() {
            long days = ChronoUnit.DAYS.between(in, out);
            return days * room.rate;
        }

        public boolean includesDate(LocalDate d) {
            return (!d.isBefore(in)) && d.isBefore(out);
        }

        public boolean overlaps(LocalDate otherIn, LocalDate otherOut) {
            // overlap if intervals intersect: start1 < end2 && start2 < end1
            return in.isBefore(otherOut) && otherIn.isBefore(out);
        }

        public void summary() {
            System.out.println("\n--- Booking Summary ---");
            System.out.println("Guest: " + guest.name + " (ID: " + guest.id + ")");
            System.out.println("Room: " + room.number + " (" + room.type + ")");
            System.out.println("Check-in: " + in + ", Check-out: " + out);
            System.out.println("Total: Rs." + bill());
        }
    }

    /* Simple automatic responder with ordered keywords */
    static class AutoResponder {
        private static final Map<String, String> RESPONSES = new LinkedHashMap<>();
        static {
            RESPONSES.put("wifi", "WiFi: Network `StaySmart-Guest`. Password available at the front desk or dial 0 from your room.");
            RESPONSES.put("breakfast", "Breakfast: 7:00 AM - 10:00 AM. Room service breakfast available (extra charge).");
            RESPONSES.put("checkout", "Checkout: standard time is 12:00 PM. Late checkout may be possible for a fee — contact reception.");
            RESPONSES.put("room service", "Room service is available. To place an order dial the room service extension or contact reception.");
            RESPONSES.put("housekeeping", "Housekeeping operates 9:00 AM - 5:00 PM. For special requests contact reception.");
            RESPONSES.put("parking", "We offer guest parking; valet available 24/7 at the front entrance.");
            RESPONSES.put("pool", "Pool hours: 6:00 AM - 10:00 PM. Children must be supervised.");
            RESPONSES.put("spa", "Spa services available 10:00 AM - 8:00 PM. Book at reception or call extension 234.");
            RESPONSES.put("cancel", "To cancel or modify a booking, provide your booking reference or contact reception.");
            RESPONSES.put("emergency", "For emergencies call local emergency services immediately. For in-hotel assistance dial reception.");
            RESPONSES.put("hello", "Hello! How can I assist you today?");
            RESPONSES.put("hi", "Hi there! How can I help?");
            RESPONSES.put("thanks", "You're welcome! If you need anything else, let us know.");
        }

        public static String reply(String msg) {
            if (msg == null) return "Please type your question or dial reception (extension 0) for immediate help.";
            String lower = msg.trim().toLowerCase();
            if (lower.isEmpty()) return "Please type your question or dial reception (extension 0) for immediate help.";

            for (Map.Entry<String, String> e : RESPONSES.entrySet()) {
                if (lower.contains(e.getKey())) return e.getValue();
            }

            return "Sorry, I don't have an exact answer. Please contact reception at extension 0.";
        }
    }

    /* Validator and input helpers */
    static class Validator {
        public static int readInt(Scanner sc, String prompt) {
            while (true) {
                System.out.print(prompt);
                String line = sc.nextLine();
                try {
                    return Integer.parseInt(line.trim());
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid integer.");
                }
            }
        }

        public static String readNonEmpty(Scanner sc, String prompt) {
            while (true) {
                System.out.print(prompt);
                String line = sc.nextLine();
                if (line != null && !line.trim().isEmpty()) return line.trim();
                System.out.println("Input cannot be empty.");
            }
        }

        public static LocalDate readDate(Scanner sc, String prompt) {
            while (true) {
                System.out.print(prompt);
                String line = sc.nextLine();
                try {
                    return LocalDate.parse(line.trim());
                } catch (Exception e) {
                    System.out.println("Invalid date. Use format yyyy-mm-dd.");
                }
            }
        }

        public static String readUniqueGuestId(Scanner sc, String prompt, Hotel hotel) {
            while (true) {
                String id = readNonEmpty(sc, prompt);
                if (hotel.hasGuest(id)) {
                    System.out.println("Guest ID already exists. Enter a different ID.");
                } else return id;
            }
        }
    }

    /* Hotel manager: encapsulates rooms, guests and bookings */
    static class Hotel {
        private final List<Room> rooms = new ArrayList<>();
        private final List<Booking> bookings = new ArrayList<>();
        private final Map<String, Guest> guests = new HashMap<>();

        public Hotel(Collection<Room> initialRooms) {
            rooms.addAll(initialRooms);
        }

        public boolean hasGuest(String id) {
            return guests.containsKey(id);
        }

        public void registerGuest(String id, String name) {
            if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("Guest id required");
            if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Guest name required");
            if (guests.containsKey(id)) throw new IllegalArgumentException("Guest id already exists");
            guests.put(id, new Guest(id, name));
        }

        public Guest getGuest(String id) {
            return guests.get(id);
        }

        public List<Room> listAvailableRooms(LocalDate in, LocalDate out) {
            List<Room> available = new ArrayList<>();
            for (Room r : rooms) {
                if (roomAvailableForDates(r, in, out)) available.add(r);
            }
            return available;
        }

        public Room findRoom(int number) {
            for (Room r : rooms) if (r.number == number) return r;
            return null;
        }

        private boolean roomAvailableForDates(Room room, LocalDate in, LocalDate out) {
            for (Booking b : bookings) {
                if (b.room.number == room.number && b.overlaps(in, out)) return false;
            }
            return true;
        }

        public Booking bookRoom(String guestId, int roomNumber, LocalDate in, LocalDate out) {
            Guest g = guests.get(guestId);
            if (g == null) throw new IllegalArgumentException("Guest not registered");
            if (!out.isAfter(in)) throw new IllegalArgumentException("Check-out must be after check-in");

            Room r = findRoom(roomNumber);
            if (r == null) throw new IllegalArgumentException("Invalid room number");

            if (!roomAvailableForDates(r, in, out)) throw new IllegalArgumentException("Room not available for selected dates");

            Booking b = new Booking(g, r, in, out);
            bookings.add(b);
            return b;
        }

        public List<Booking> viewBookingsForGuest(String guestId) {
            List<Booking> result = new ArrayList<>();
            for (Booking b : bookings) {
                if (b.guest.id.equals(guestId)) result.add(b);
            }
            return result;
        }

        public double checkoutRoom(int roomNumber) {
            LocalDate today = LocalDate.now();
            List<Booking> toRemove = new ArrayList<>();
            double total = 0;
            for (Booking b : bookings) {
                if (b.room.number == roomNumber) {
                    // clear bookings that include today or are already ended (guest leaving)
                    if (b.includesDate(today) || !today.isBefore(b.out)) {
                        total += b.bill();
                        toRemove.add(b);
                    }
                }
            }
            bookings.removeAll(toRemove);
            return total;
        }

        public Collection<Room> allRooms() {
            return Collections.unmodifiableCollection(rooms);
        }

        public Collection<Guest> allGuests() {
            return Collections.unmodifiableCollection(guests.values());
        }
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Hotel hotel = new Hotel(Arrays.asList(
                // add more rooms
                new Room(100, "Single", 2500),
                new Room(101, "VIP Single", 3000),
                new Room(102, "Double", 4500),
                new Room(201, "Suite", 7000),
                new Room(202, "VIP Double", 5000),
                new Room(203, "VIP Suite", 8000)

        ));

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Register Guest");
            System.out.println("2. Book Room");
            System.out.println("3. View Bookings");
            System.out.println("4. Checkout Room");
            System.out.println("5. Ask a Question");
            System.out.println("0. Exit");

            int choice = Validator.readInt(sc, "Choose option: ");
            if (choice == 0) break;

            try {
                switch (choice) {
                    case 1: {
                        String gid = Validator.readUniqueGuestId(sc, "Enter guest ID: ", hotel);
                        String gname = Validator.readNonEmpty(sc, "Enter guest name: ");
                        hotel.registerGuest(gid, gname);
                        System.out.println("Guest registered.");
                        break;
                    }

                    case 2: {
                        String guestId = Validator.readNonEmpty(sc, "Enter guest ID: ");
                        Guest guest = hotel.getGuest(guestId);
                        if (guest == null) {
                            System.out.println("Guest not found. Please register first.");
                            break;
                        }
                        LocalDate in = Validator.readDate(sc, "Check-in date (yyyy-mm-dd): ");
                        LocalDate out = Validator.readDate(sc, "Check-out date (yyyy-mm-dd): ");
                        if (!out.isAfter(in)) {
                            System.out.println("Check-out date must be after check-in date.");
                            break;
                        }
                        List<Room> available = hotel.listAvailableRooms(in, out);
                        if (available.isEmpty()) {
                            System.out.println("No rooms available for the selected dates.");
                            break;
                        }
                        System.out.println("Available Rooms:");
                        for (Room r : available) {
                            System.out.println("Room " + r.number + " - " + r.type + " - Rs." + r.rate);
                        }
                        int rnum = Validator.readInt(sc, "Choose room number: ");
                        try {
                            Booking booking = hotel.bookRoom(guestId, rnum, in, out);
                            System.out.println("Booking confirmed at " + LocalTime.now().withSecond(0).withNano(0));
                            booking.summary();
                        } catch (IllegalArgumentException ex) {
                            System.out.println("Booking failed: " + ex.getMessage());
                        }
                        break;
                    }

                    case 3: {
                        String viewId = Validator.readNonEmpty(sc, "Enter guest ID to view bookings: ");
                        Guest g = hotel.getGuest(viewId);
                        if (g == null) {
                            System.out.println("Guest not found.");
                            break;
                        }
                        List<Booking> list = hotel.viewBookingsForGuest(viewId);
                        if (list.isEmpty()) {
                            System.out.println("No bookings found for this guest.");
                        } else {
                            for (Booking b : list) b.summary();
                        }
                        break;
                    }

                    case 4: {
                        int checkoutRoom = Validator.readInt(sc, "Enter room number to checkout: ");
                        Room r = hotel.findRoom(checkoutRoom);
                        if (r == null) {
                            System.out.println("Invalid room number.");
                            break;
                        }
                        double bill = hotel.checkoutRoom(checkoutRoom);
                        if (bill == 0) {
                            System.out.println("No active or ended bookings found for this room.");
                        } else {
                            System.out.println("Room " + checkoutRoom + " cleared. Total bill: Rs." + bill);
                        }
                        break;
                    }

                    case 5: {
                        System.out.print("Ask your question: ");
                        String question = sc.nextLine();
                        System.out.println("AutoReply: " + AutoResponder.reply(question));
                        break;
                    }

                    default:
                        System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("Thank you for using StaySmart!");
        sc.close();
    }
}