import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class FastCollinearPoints {

    private final LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        ArrayList<LineSegment> lineSegmentArrayList = new ArrayList<>();
        Point[] sorted = points.clone();
        Point[] sortedBySlope = points.clone();
        Arrays.sort(sorted);
        checkNull(sorted);
        checkUnique(sorted);

        for (int i = 0; i < sorted.length; i++) {
            Arrays.sort(sortedBySlope, sorted[i].slopeOrder().thenComparing(Point::compareTo));
            int count = 0;
            int max = 0;
            boolean flag = false;
            LinkedList<Point> lastLinked = new LinkedList<>();
            for (int j = 1; j < sortedBySlope.length; j++) {

                if (Double.compare(sortedBySlope[j - 1].slopeTo(sorted[i]), sortedBySlope[j].slopeTo(sorted[i])) == 0) {
                    if (sorted[i].compareTo(sortedBySlope[j - 1]) > 0 || flag) {
                        count = 0;
                        flag = true;
                        continue;
                    }
                    count++;


                    if (sortedBySlope[j - 1].compareTo(new Point(4600, 8200)) == 0) {
                        System.out.println();
                    }


                    if (max < count) {
                        max = count;
                        lastLinked.clear();
                        lastLinked.addFirst(sortedBySlope[j]);
                    } else if (max == count) {
                        lastLinked.addFirst(sortedBySlope[j]);
                    }

                } else {
                    count = 0;
                    flag = false;
                }
            }
            if (max >= 2) {
                for (Point elem : lastLinked) {
                    lineSegmentArrayList.add(new LineSegment(sorted[i], elem));
                }
            }
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