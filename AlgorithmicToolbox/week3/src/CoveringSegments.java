import java.util.*;

public class CoveringSegments {

    private static ArrayList<Integer> optimalPoints(Segment[] segments) {
        Arrays.sort(segments, Comparator.comparingInt(o -> o.start));
        ArrayList<Integer> points = new ArrayList<>();
        for (int i = 0; i < segments.length;) {
            int currentMaxLeft = segments[i].start;
            int currentMinRight = segments[i].end;
            while (i < segments.length && currentMinRight >= segments[i].start) {
                if (segments[i].end < currentMinRight) currentMinRight = segments[i].end;
                currentMaxLeft = segments[i].start;
                i++;
            }
            points.add(currentMaxLeft);
        }
        return points;
    }

    private static class Segment {
        int start, end;

        Segment(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return String.format("%d %d", start, end);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];
        for (int i = 0; i < n; i++) {
            int start, end;
            start = scanner.nextInt();
            end = scanner.nextInt();
            segments[i] = new Segment(start, end);
        }
        ArrayList<Integer> points = optimalPoints(segments);
        System.out.println(points.size());
        for (int point : points) {
            System.out.print(point + " ");
        }
    }
}
 
