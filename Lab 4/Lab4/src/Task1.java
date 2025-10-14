import java.util.Scanner;

public class Task1 {
    private int hour;
    private int minute;

    public Task1() {
        hour = 0;
        minute = 0;
    }

    public void showTime() {
        System.out.printf("%02d:%02d%n", hour, minute);
    }

    public void tick() {
        minute++;
        if (minute >= 60) {
            minute = 0;
            hour++;
            if (hour >= 24) {
                hour = 0;
            }
        }
    }

    public void setTime(int h, int m) {
        if (h >= 0 && h < 24 && m >= 0 && m < 60) {
            hour = h;
            minute = m;
        } else {
            System.out.println("Invalid time.");
        }
    }

    public void reset() {
        hour = 0;
        minute = 0;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Task1 clock = new Task1();

        while (true) {
            System.out.println("\n1. Show Time");
            System.out.println("2. Tick (Add One Minute)");
            System.out.println("3. Set Time");
            System.out.println("4. Reset");
            System.out.println("5. Exit");
            System.out.print("Choose: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid choice.");
                continue;
            }

            if (choice == 1) {
                clock.showTime();
            } else if (choice == 2) {
                clock.tick();
                clock.showTime();
            } else if (choice == 3) {
                System.out.print("Enter time (HH MM or HH:MM): ");
                String input = sc.nextLine().trim();

                int h, m;
                try {
                    if (input.contains(":")) {
                        String[] parts = input.split(":");
                        h = Integer.parseInt(parts[0]);
                        m = Integer.parseInt(parts[1]);
                    } else {
                        String[] parts = input.split(" ");
                        h = Integer.parseInt(parts[0]);
                        m = Integer.parseInt(parts[1]);
                    }
                    clock.setTime(h, m);
                } catch (Exception e) {
                    System.out.println("Invalid time format.");
                }
            } else if (choice == 4) {
                clock.reset();
                System.out.println("Clock reset.");
            } else if (choice == 5) {
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }
}
