import java.util.Scanner;

public class FibonacciHuge {
    private static long pLen(long m) {
        long prev = 0, cur = 1, next;
        long len = 0;
        do {
            next = (prev + cur) % m;
            prev = cur;
            cur = next;
            len++;
        } while (prev != 0 || cur != 1);
        return len;
    }

    private static long fibModuloLarge(long n, long m) {
        if (n <= 1)
            return n;

        long previous = 0;
        long current = 1;
        long p = pLen(m);

        for (long i = 0; i < (n - 1) % p; ++i) {
            long tmp_previous = previous;
            previous = current;
            current = (tmp_previous + current)%m;
        }

        return current;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        long m = scanner.nextLong();
        System.out.println(fibModuloLarge(n, m));
    }
}

