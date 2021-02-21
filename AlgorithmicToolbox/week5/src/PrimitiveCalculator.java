import java.util.*;

public class PrimitiveCalculator {
    private static List<Integer> optimal_sequence(int n) {
        List<Integer> sequence = new ArrayList<>();
        int[] ops = new int[n + 1];
        int[] prev = new int[n + 1];
        prev[1] = -1;
        for (int i = 2; i <= n; i++) {
            int min = ops[i - 1];
            int p = i - 1;
            if (i % 2 == 0 && (i / 2) > 0 && ops[i / 2] < min) {
                min = ops[i / 2];
                p = i / 2;
            }
            if (i % 3 == 0 && (i / 3) > 0 && ops[i / 3] < min) {
                min = ops[i / 3];
                p = i / 3;
            }
            ops[i] = ++min;
            prev[i] = p;
        }
        int i = n;
        sequence.add(n);
        while (prev[i] != -1) {
            sequence.add(prev[i]);
            i = prev[i];
        }
        Collections.reverse(sequence);
        return sequence;
    }

    private static List<Integer> wrong_sequence(int n) {
        List<Integer> sequence = new ArrayList<>();
        int[] ops = new int[n + 1];
        int[] prev = new int[n + 1];
        prev[1] = -1;
        for (int i = 2; i <= n; i++) {
            int min = ops[i - 1];
            int p = i - 1;
            if (i % 3 == 0 && (i / 3) > 0 && ops[i / 3] < min) {
                min = ops[i / 3];
                p = i / 3;
            } else if (i % 2 == 0 && (i / 2) > 0 && ops[i / 2] < min) {
                min = ops[i / 2];
                p = i / 2;
            }

            ops[i] = ++min;
            prev[i] = p;
        }
        int i = n;
        sequence.add(n);
        while (prev[i] != -1) {
            sequence.add(prev[i]);
            i = prev[i];
        }
        Collections.reverse(sequence);
        return sequence;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int n = random.nextInt(2000) + 1;
        List<Integer> correct = optimal_sequence(n);
        List<Integer> wrong = wrong_sequence(n);
        while (correct.size() == wrong.size()) {
            n = random.nextInt(2000) + 1;
            correct = optimal_sequence(n);
            wrong = wrong_sequence(n);
        }
        System.out.println("Correct len: " + (correct.size() - 1));
        for (Integer i : correct) {
            System.out.printf("%d ", i);
        }
        System.out.println();
        System.out.println("Wrong len: " + (wrong.size() - 1));
        for (Integer i : wrong) {
            System.out.printf("%d ", i);
        }
    }
}

