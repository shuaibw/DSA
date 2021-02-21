import java.util.Scanner;

public class ChangeDP {
    private static int getChange(int m, int[] coins) {
        int[] change = new int[m + 1];
        for (int i = 1; i <= m; i++) {
            int min = change[i - 1];
            for (int c : coins) {
                if (i - c >= 0 && change[i - c] < min) min = change[i - c];
            }
            change[i] = ++min;
        }
        return change[m];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int[] coins = new int[]{1, 3, 4};
        System.out.println(getChange(m, coins));
    }
}

