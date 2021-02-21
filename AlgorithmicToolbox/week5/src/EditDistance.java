import java.util.*;

class EditDistance {
    public static int EditDistance(String s, String t) {
        int[][] dpAra = new int[s.length() + 1][t.length() + 1];
        for (int i = 0; i <= s.length(); i++) dpAra[i][0] = i;
        for (int i = 0; i <= t.length(); i++) dpAra[0][i] = i;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= t.length(); j++) {
                int diff = s.charAt(i - 1) == t.charAt(j - 1) ? 0 : 1;
                int rep = dpAra[i - 1][j - 1] + diff;
                int del = dpAra[i - 1][j] + 1;
                int ins = dpAra[i][j - 1] + 1;
                if (rep <= del && rep <= ins) dpAra[i][j] = rep;
                else if (del <= rep && del <= ins) dpAra[i][j] = del;
                else dpAra[i][j] = ins;
            }
        }
        return dpAra[s.length()][t.length()];
    }

    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);

        String s = scan.next();
        String t = scan.next();

        System.out.println(EditDistance(s, t));
    }

}
