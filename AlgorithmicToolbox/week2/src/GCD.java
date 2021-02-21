import java.util.*;

public class GCD {
    private static int fastGCD(int a, int b) {
        int temp = 0;
        while (b >= 1) {
            temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();

        System.out.println(fastGCD(a, b));
    }
}
