import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Partition3 {
    private static int partition3(int[] A) {
        int sum = Arrays.stream(A).sum();
        if (sum % 3 != 0) return 0;
        int W = sum / 3;
        int val1 = fillPack(W, A);
        int val2 = fillPack(2 * W, A);
        if (val1 == W && val2 == 2 * W) return 1;
        return 0;

    }

    private static int fillPack(int W, int[] w) {
        int[][] dpTable = new int[w.length + 1][W + 1];
        for (int i = 1; i <= w.length; i++) {
            for (int j = 1; j <= W; j++) {
                if (w[i - 1] > j) dpTable[i][j] = dpTable[i - 1][j];
                else dpTable[i][j] = Integer.max(dpTable[i - 1][j], dpTable[i - 1][j - w[i - 1]] + w[i - 1]);
                //Either you continue without i-1 element, or you find something else that fits and brings a greater value
            }
        }
        return dpTable[w.length][W];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] A = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = scanner.nextInt();
        }
        System.out.println(partition3(A));
    }
}

