import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;

public class ClosestMemory {

    static class Point {
        long x, y;

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }

        public static Comparator<Point> sortByX() {
            return Comparator.comparingLong(o -> o.x);
        }

        public static Comparator<Point> sortByY() {
            return Comparator.comparingLong(o -> o.y);
        }

        public static long eucDistSqr(Point a, Point b) {
            return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
        }
    }

    static long minimalDistance(ArrayList<Point> sortedX, ArrayList<Point> sortedY) {
        assert sortedX.size() == sortedY.size();
        if (sortedX.size() == 1) return Long.MAX_VALUE;
        if (sortedX.size() == 2) return Point.eucDistSqr(sortedX.get(0), sortedX.get(1));
        int mid = sortedX.size() / 2;
        ArrayList<Point> leftX = new ArrayList<>(sortedX.subList(0, mid));
        ArrayList<Point> leftY = new ArrayList<>(sortedY.subList(0, mid));
        ArrayList<Point> rightX = new ArrayList<>(sortedX.subList(mid, sortedX.size()));
        ArrayList<Point> rightY = new ArrayList<>(sortedY.subList(mid, sortedY.size()));
        long leftHalf = minimalDistance(leftX, leftY);
        long rightHalf = minimalDistance(rightX, rightY);
        long delta = Long.min(leftHalf, rightHalf);
        long splitMin = minimalSplitDist(sortedX, sortedY, delta);
        return Long.min(delta, splitMin);
    }

    static long minimalSplitDist(ArrayList<Point> sortedX, ArrayList<Point> sortedY, long delta) {
        assert sortedX.size() == sortedY.size() && sortedX.size() >= 3;
        long x = sortedX.get(sortedX.size() / 2 - 1).x;
        ArrayList<Point> deltaY = new ArrayList<>();
        for (Point p : sortedY) {
            if (p.x >= (x - delta) && p.x <= (x + delta)) deltaY.add(p);
        }
        long minSplitDist = Long.MAX_VALUE;
        for (int i = 0; i < deltaY.size(); i++) {
            for (int j = i + 1; j <= Integer.min((i + 7), deltaY.size() - 1); j++) {
                long currentDist = Point.eucDistSqr(deltaY.get(i), deltaY.get(j));
                if (currentDist < minSplitDist) minSplitDist = currentDist;
            }
        }
        return minSplitDist;
    }

    public static void main(String[] args) {
        reader = new BufferedReader(new InputStreamReader(System.in));
        writer = new PrintWriter(System.out);
        int n = nextInt();
        ArrayList<Point> sortedX = new ArrayList<>(n);
        int x, y;
        for (int i = 0; i < n; i++) {
            x = nextInt();
            y = nextInt();
            sortedX.add(new Point(x, y));
        }
        ArrayList<Point> sortedY = new ArrayList<>(sortedX);
        sortedX.sort(Point.sortByX());
        sortedY.sort(Point.sortByY());
        long distSqr = minimalDistance(sortedX, sortedY);
        System.out.println(Math.sqrt(distSqr));
        writer.close();
    }

    static BufferedReader reader;
    static PrintWriter writer;
    static StringTokenizer tok = new StringTokenizer("");


    static String next() {
        while (!tok.hasMoreTokens()) {
            String w = null;
            try {
                w = reader.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (w == null)
                return null;
            tok = new StringTokenizer(w);
        }
        return tok.nextToken();
    }

    static int nextInt() {
        return Integer.parseInt(next());
    }
}
