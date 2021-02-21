import java.util.*;

public class FibonacciSumLastDigit {

    private static long fastFSLD(long n) {
        if (n <= 1)
            return n;

        long previous = 0;
        long current = 1;

        for (long i = 0; i < (n + 1) % 60; ++i) {
            long tmp_previous = previous;
            previous = current;
            current = (tmp_previous + current) % 10;
        }

        return current == 0 ? (10 - 1) : current - 1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        long s = fastFSLD(n);
        System.out.println(s);
    }
}

