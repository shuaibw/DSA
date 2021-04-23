import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class SlidingWindow {
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(
                    new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }

    public static void main(String[] args) {
        FastReader scanner = new FastReader();
        int n = Integer.parseInt(scanner.nextLine());
        int[] ara = new int[n];
        for (int i = 0; i < n; i++) ara[i] = scanner.nextInt();
        int m = Integer.parseInt(scanner.nextLine());
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            while (!deque.isEmpty() && ara[deque.peekLast()] <= ara[i]) deque.removeLast();
            deque.addLast(i);
        }
        System.out.println(ara[deque.peekFirst()]);
        for (int i = m; i < n; i++) {
            if (i - deque.peekFirst() >= m) deque.removeFirst();
            while (!deque.isEmpty() && ara[deque.peekLast()] <= ara[i]) deque.removeLast();
            deque.addLast(i);
            System.out.println(ara[deque.peekFirst()]);
        }
    }
}
