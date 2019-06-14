import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class FastCollinearPoints {
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        int counter = 0;
        for (int i = 0; i < points.length; i++) {
            sort(points, i);
        }
    }

    public int numberOfSegments() {

    }

    public LineSegment[] segments() {

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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    private LineSegment sort(Point[] points, int index, Comparator<Point> comparator) { //todo change linesegment to tuple
       Point[] aux = new Point[points.length];
       sort(points, aux, 0, points.length - 1, comparator);
    }

    private void sort(Point[] a, Point[] aux, int low, int high, Comparator<Point> comparator) {
        if (low >= high) {
            return;
        }

        int mid = low + (high - low) / 2;
        sort(a, aux, low, mid, comparator);
        sort(a, aux, mid + 1, high, comparator);
        merge(a, aux, low, mid, high, comparator);
    }

    private void merge(Point[] a, Point[] aux, int low, int mid, int high, Comparator<Point> comparator) {
        for (int i = low; i <= high; i++) {
            aux[i] = a[i];
        }

        int i = low;
        int j = mid + 1;

        for (int k = low; k < high; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > high) {
                a[k] = aux[i++];
            } else if (comparator.compare(aux[i], aux[j]) > 0) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
    }

}