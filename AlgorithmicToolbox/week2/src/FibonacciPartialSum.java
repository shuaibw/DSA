import java.util.Scanner;

public class FibonacciPartialSum {
    private static int fastFLD(int n) {
        if (n <= 1)
            return n;

        int previous = 0;
        int current = 1;

        for (int i = 0; i < n - 1; ++i) {
            int tmp = previous;
            previous = current;
            current = (tmp + current) % 10;
        }

        return current;
    }


    private static long fastFPSLD(long from, long to) {
        long a = fastFLD((int) ((to + 2) % 60));
        long b = fastFLD((int) ((from + 1) % 60));
        if (a > b) return (a - b) % 10;
        return (a - b + 10) % 10;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long from = scanner.nextLong();
        long to = scanner.nextLong();
        System.out.println(fastFPSLD(from, to));
    }
}

