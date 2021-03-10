import java.util.Scanner;

public class PlacingParentheses {
    private static long getMaximValue(String exp) {
        int[] digits = new int[(exp.length() + 1) / 2];
        char[] op = new char[exp.length() / 2];
        char[] ara = exp.toCharArray();
        for (int i = 0, j = 0, k = 0; i < ara.length; i++) {
            if (i % 2 == 0) digits[j++] = Integer.parseInt(String.valueOf(ara[i]));
            else op[k++] = ara[i];
        }
        long[][] M = new long[digits.length][digits.length];
        long[][] m = new long[digits.length][digits.length];
        for (int i = 0; i < digits.length; i++) {
            M[i][i] = digits[i];
            m[i][i] = digits[i];
        }
        for (int k = 1; k < digits.length; k++) {
            int i = 0, j = k;
            while (j < digits.length) {
                long[] minMax = minAndMax(i, j, M, m, op);
                m[i][j] = minMax[0];
                M[i][j] = minMax[1];
                i++;
                j++;
            }
        }
        return M[0][digits.length - 1];
    }

    private static long[] minAndMax(int i, int j, long[][] M, long[][] m, char[] op) {
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        for (int k = i; k < j; k++) {
            long[] val = new long[4];
            val[0] = eval(M[i][k], M[k + 1][j], op[k]);
            val[1] = eval(M[i][k], m[k + 1][j], op[k]);
            val[2] = eval(m[i][k], M[k + 1][j], op[k]);
            val[3] = eval(m[i][k], m[k + 1][j], op[k]);
            for (long c : val) {
                if (c < min) min = c;
                if (c > max) max = c;
            }
        }
        return new long[]{min, max};
    }

    private static long eval(long a, long b, char op) {
        if (op == '+') {
            return a + b;
        } else if (op == '-') {
            return a - b;
        } else if (op == '*') {
            return a * b;
        } else {
            assert false;
            return 0;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String exp = scanner.next();
        System.out.println(getMaximValue(exp));
    }
}

