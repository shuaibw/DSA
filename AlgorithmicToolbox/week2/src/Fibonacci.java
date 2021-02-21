import java.util.Scanner;

public class Fibonacci {
    private static long calc_fib(int n) {
        if (n <= 1) return n;
        long prev = 0, cur = 1;
        long temp;
        for (int i = 2; i <= n; i++) {
            temp = prev + cur;
            prev = cur;
            cur = temp;
        }
        return cur;
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        System.out.println(calc_fib(n));
    }
}
