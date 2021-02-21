import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class PointsAndSegments {
    private static int[] countSegments(ArrayList<Pair> pairs, int numPoints) {
        Collections.sort(pairs);
        int[] count = new int[numPoints];
        int covered = 0;
        for (Pair pair : pairs) {
            if (pair.type == 0) covered++;
            else if (pair.type == 1) count[pair.sn] = covered;
            else covered--;
        }
        return count;
    }

    static class Pair implements Comparable<Pair> {
        public int value, type, sn = 0;//0-->left end, 1-->point, 2-->right end

        Pair(int value, int type, int sn) {
            this.value = value;
            this.type = type;
            this.sn = sn;
        }

        @Override
        public int compareTo(Pair o) {
            if (value != o.value) return Integer.compare(value, o.value);
            return Integer.compare(type, o.type);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n, m;
        n = scanner.nextInt();
        m = scanner.nextInt();
        int l;
        int r;
        int p;
        ArrayList<Pair> pairs = new ArrayList<>(n + m);
        for (int i = 0; i < n; i++) {
            l = scanner.nextInt();
            r = scanner.nextInt();
            pairs.add(new Pair(l, 0, -1));
            pairs.add(new Pair(r, 2, -1));
        }
        for (int i = 0; i < m; i++) {
            p = scanner.nextInt();
            pairs.add(new Pair(p, 1, i));
        }
        //use fastCountSegments
        int[] cnt = countSegments(pairs, m);
        for (int x : cnt) {
            System.out.print(x + " ");
        }
    }
}

