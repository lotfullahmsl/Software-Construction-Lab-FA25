import java.util.*;

public class Task1 {
    public static void main(String[] words) {
        Scanner box = new Scanner(System.in);
        System.out.print("How many values? ");
        int count = box.nextInt();
        int[] pocket = new int[count];
        for (int i = 0; i < count; i++) {
            System.out.print("Put number " + (i + 1) + ": ");
            pocket[i] = box.nextInt();
        }

        pocket = Arrays.stream(pocket).distinct().toArray();
        Arrays.sort(pocket);

        if (pocket.length < 2) {
            System.out.println("Need at least two distinct numbers.");
            return;
        }

        int lowTwo = pocket[1];
        int highTwo = pocket[pocket.length - 2];

        System.out.println("Sorted: " + Arrays.toString(pocket));
        System.out.println("Second Smallest: " + lowTwo);
        System.out.println("Second Largest: " + highTwo);
    }
}
