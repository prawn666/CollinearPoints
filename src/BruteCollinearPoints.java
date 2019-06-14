public class BruteCollinearPoints {

    private LineSegment[] lineSegments;
    private int index;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        lineSegments = new LineSegment[points.length * (points.length - 1) * (points.length - 2) * (points.length - 3)];
        index = 0;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    if (points[i].slopeTo(points[j]) != points[j].slopeTo(points[k])) {
                        break;
                    }
                    for (int l = k + 1; l < points.length; l++) {
                        if (points[i].slopeTo(points[j]) == points[k].slopeTo(points[l])) {
                            lineSegments[index++] = new LineSegment(points[i], points[l]);
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return index;
    }

    public LineSegment[] segments() {
        return lineSegments;
    }

}
