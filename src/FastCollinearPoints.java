import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {

    private final LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        ArrayList<LineSegment> lineSegmentArrayList = new ArrayList<>();
        Arrays.sort(points);
        checkNull(points);
        checkUnique(points);
        for (int i = 0; i < points.length; i++) {
//            LineSegment lineSegment =
//                    sort(points.clone(), points[i].slopeOrder().thenComparing(Point::compareTo), points[i]);

//            if (lineSegment != null) {
//                lineSegmentArrayList.add(lineSegment);
//            }
            Point[] sortedBySlope = points.clone();
            Arrays.sort(sortedBySlope, points[i].slopeOrder());
            Point last = null;
            int count = 0;
            int max = 0;

            for (int j = 0; j < sortedBySlope.length - 1; j++) {
                if (Double.compare(sortedBySlope[j].slopeTo(points[i]), sortedBySlope[j + 1].slopeTo(points[i])) == 0) {
                    count++;
                } else {
                    count = 0;
                }
                if (count > max) {
                    max = count;
                    last = sortedBySlope[j+1];
                }
            }
            if (max >= 2) {
                lineSegmentArrayList.add(new LineSegment(points[i], last));
            }
          //  points[i] = null;

        }

        lineSegments = lineSegmentArrayList.toArray(new LineSegment[0]);

    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return lineSegments.clone();
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

    private LineSegment sort(Point[] points, Comparator<Point> comparator, Point point) {
        Point[] aux = new Point[points.length];
        sort(points, aux, 0, points.length - 1, comparator);
        System.out.println(point);
        for (int i = 0; i < points.length; i++) {
            System.out.println(point.slopeTo(points[i]));
        }
        System.out.println();

        Point last = null;
        int count = 0;
        int max = 0;

        for (int i = 0; i < points.length - 1; i++) {
            if (Double.compare(points[i].slopeTo(point), points[i + 1].slopeTo(point)) == 0) {
                count++;
            } else {
                count = 0;
            }
            if (count > max) {
                max = count;
                last = points[i+1];
            }
        }

        if (max >= 2) {
            return new LineSegment(point, last);
        }

        return null;

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

        for (int k = low; k <= high; k++) {
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