import java.util.*;

public class LargestNumber {
    private static String largestNumber(String[] a) {
        //write your code here
        Comparator<String> comp = (s1, s2) -> (s2 + s1).compareTo(s1 + s2);
        Arrays.sort(a, comp);
        return String.join("", a);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        String[] a = new String[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.next();
        }
        System.out.println(largestNumber(a));
    }
}

