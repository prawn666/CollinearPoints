import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private final LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        Point[] sorted = points.clone();
        checkNull(sorted);
        Arrays.sort(sorted);
        checkUnique(sorted);
        ArrayList<LineSegment> lineSegmentArrayList = new ArrayList<>();
        for (int i = 0; i < sorted.length; i++) {
            for (int j = i + 1; j < sorted.length; j++) {
                for (int k = j + 1; k < sorted.length; k++) {
                    if (Double.compare(sorted[i].slopeTo(sorted[j]), sorted[j].slopeTo(sorted[k])) == 0) {
                        for (int i1 = k + 1; i1 < sorted.length; i1++) {
                            if (Double.compare(sorted[i].slopeTo(sorted[j]), sorted[k].slopeTo(sorted[i1])) == 0) {
                                lineSegmentArrayList.add(new LineSegment(sorted[i], sorted[i1]));
                            }
                        }
                    }
                }
            }
        }
        lineSegments = lineSegmentArrayList.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return lineSegments.clone();
    }

    private void checkNull(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void checkUnique(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }
}
