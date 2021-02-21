import java.util.Scanner;

public class CarFueling {
    static int computeMinRefills(int tank, int[] stops) {
        int numRefills = 0, currentRefill = 0, lastRefill;
        while (currentRefill < stops.length - 1) {
            lastRefill = currentRefill;
            while (currentRefill < (stops.length - 1) && stops[currentRefill + 1] - stops[lastRefill] <= tank)
                currentRefill++;
            if (currentRefill == lastRefill) return -1;
            if (currentRefill < stops.length - 1) numRefills++;
        }
        return numRefills;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dist = scanner.nextInt();
        int tank = scanner.nextInt();
        int n = scanner.nextInt();
        int stops[] = new int[n + 2];
        stops[0] = 0;
        for (int i = 1; i <= n; i++) {
            stops[i] = scanner.nextInt();
        }
        stops[n + 1] = dist;

        System.out.println(computeMinRefills(tank, stops));
    }
}
