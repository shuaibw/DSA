import java.util.*;

public class FibonacciLastDigit {
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int c = fastFLD(n);
        System.out.println(c);
    }
}

