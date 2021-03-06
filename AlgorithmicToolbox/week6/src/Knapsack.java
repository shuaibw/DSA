import java.util.Scanner;

public class Knapsack {
    static int optimalWeight(int W, int[] w) {
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
        int W, n;
        W = scanner.nextInt();
        n = scanner.nextInt();
        int[] w = new int[n];
        for (int i = 0; i < n; i++) {
            w[i] = scanner.nextInt();
        }
        System.out.println(optimalWeight(W, w));
    }
}

