import java.util.*;

public class Inversions {

    private static long inversion(int[] a, int left, int right) {
        if (left >= right) return 0;
        int mid = left + (right - left) / 2;
        long x = inversion(a, left, mid);
        long y = inversion(a, mid + 1, right);
        long z = splitInversion(a, left, mid, right);
        return x + y + z;
    }

    private static long splitInversion(int[] a, int left, int mid, int right) {
        int[] leftSubAra = Arrays.copyOfRange(a, left, mid + 1);
        int[] rightSubAra = Arrays.copyOfRange(a, mid + 1, right + 1);
        int i = 0;
        int j = 0;
        int k = left;
        long inversions = 0;
        while (i < leftSubAra.length && j < rightSubAra.length) {
            if (leftSubAra[i] <= rightSubAra[j]) {
                a[k] = leftSubAra[i];
                i++;
            } else if (leftSubAra[i] > rightSubAra[j]) {
                a[k] = rightSubAra[j];
                j++;
                inversions += (leftSubAra.length - i);
            }
            k++;
        }
        while (i < leftSubAra.length) a[k++] = leftSubAra[i++];
        while (j < rightSubAra.length) a[k++] = rightSubAra[j++];
        return inversions;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        System.out.println(inversion(a, 0, a.length - 1));
    }
}

