import java.util.*;

public class Task2 {
    public static void main(String[] box) {
        Scanner in = new Scanner(System.in);
        System.out.print("Rows in First: ");
        int aR = in.nextInt();
        System.out.print("Cols in First: ");
        int aC = in.nextInt();
        System.out.print("Rows in Second: ");
        int bR = in.nextInt();
        System.out.print("Cols in Second: ");
        int bC = in.nextInt();

        if (aC != bR) {
            System.out.println("Matrix dimensions not suitable.");
            return;
        }

        int[][] first = new int[aR][aC];
        int[][] second = new int[bR][bC];
        int[][] result = new int[aR][bC];

        System.out.println("Fill First:");
        for (int i = 0; i < aR; i++)
            for (int j = 0; j < aC; j++)
                first[i][j] = in.nextInt();

        System.out.println("Fill Second:");
        for (int i = 0; i < bR; i++)
            for (int j = 0; j < bC; j++)
                second[i][j] = in.nextInt();

        for (int i = 0; i < aR; i++)
            for (int j = 0; j < bC; j++)
                for (int k = 0; k < aC; k++)
                    result[i][j] += first[i][k] * second[k][j];

        System.out.println("Multiplied:");
        for (int[] r : result) {
            for (int v : r) System.out.print(v + "\t");
            System.out.println();
        }
    }
}
