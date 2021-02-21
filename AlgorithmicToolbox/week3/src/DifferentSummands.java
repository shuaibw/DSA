import java.util.*;

public class DifferentSummands {
    private static List<Integer> optimalSummands(int n) {
        List<Integer> summands = new ArrayList<>();
        for (int i = 1; n != 0; i++) {
            if (n < i) {
                int lastIdx = summands.size() - 1;
                summands.set(lastIdx, summands.get(lastIdx) + n);
                n = 0;
            } else {
                summands.add(i);
                n -= i;
            }
        }
        return summands;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> summands = optimalSummands(n);
        System.out.println(summands.size());
        for (Integer summand : summands) {
            System.out.print(summand + " ");
        }
    }
}

