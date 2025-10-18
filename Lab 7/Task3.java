import java.util.*;

public class Task3 {
    public static void main(String[] wind) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Length of first array: ");
        int n = scan.nextInt();
        System.out.print("Length of second array: ");
        int m = scan.nextInt();

        int[] left = new int[n];
        int[] right = new int[m];

        System.out.println("Insert first array:");
        for (int i = 0; i < n; i++) left[i] = scan.nextInt();
        System.out.println("Insert second array:");
        for (int i = 0; i < m; i++) right[i] = scan.nextInt();

        boolean same = Arrays.equals(left, right);

        if (same)
            System.out.println("Arrays echo each other perfectly.");
        else
            System.out.println("Arrays differ in rhythm or length.");
    }
}
