
import java.util.Arrays;
import java.util.Comparator;


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;



public final class FastCollinearPoints {
    private SNode[][] segs;
    private int count = 0;
    private int len;
    private final LineSegment[] seg;
    private final Point[] points;
    public FastCollinearPoints(Point[] points) {
        if(points!=null) {
            int z = points.length;
            this.points = Arrays.copyOf(points, z);
            segs = new SNode[10][];
            len = 10;

            StdDraw.setXscale(0, 10);
            StdDraw.setYscale(0, 10);
            for (Point p : this.points) {
                SNode[] arr = new SNode[z - 1];
                int counter = 0;
                for (int u=0;u<z;u++) {
                    Point ygd = points[u];
                    if(ygd ==null||p==null){
                        throw new java.lang.IllegalArgumentException("Item is null");
                    }
                    if (p != ygd) {
                        if (p.compareTo(ygd) == 0) {
                            throw new java.lang.IllegalArgumentException("Repeated Point");
                        }
                        double slope = p.slopeTo(ygd);
                        arr[counter++] = new SNode(ygd, slope);

                    }
                }
                Arrays.sort(this.points);
                Arrays.sort(arr);
                double slope = 0;
                int pt = 0;
                int df = arr.length;
                for (int q = 0; q < df; q++) {
                    SNode gd = arr[q];
                    if (gd.getSlope() == slope) {
                        pt++;
                    } else {
                        if (pt >= 3) {
                            SNode[] l = new SNode[pt + 1];
                            l[0] = new SNode(p, slope);
                            for (int i = 1; i <= pt; i++) {
                                l[i] = arr[q - i];
                            }
                            pt = 0;
                            boolean contained = false;
                            if (count != 0) {
                                for (SNode[] i : segs) {
                                    if (i != null) {
                                        Arrays.sort(i, new SNode.PointOrder());
                                        Arrays.sort(l, new SNode.PointOrder());

                                        if (i.length == l.length) {
                                            boolean equals = true;
                                            for (int w = 0; w < i.length; w++) {
                                                if (!i[w].equals(l[w])) {
                                                    equals = false;
                                                }
                                            }
                                            if (equals) {
                                                contained = true;
                                            }
                                        }
                                    }
                                }
                            }
                            if (count < len) {
                                if (!contained)
                                    segs[count++] = l;
                            } else {
                                if (!contained) {
                                    SNode[][] temp = new SNode[len * 2][];
                                    int d = 0;
                                    for (SNode[] i : segs) {
                                        temp[d++] = i;
                                    }
                                    segs = temp;
                                    len *= 2;
                                    segs[count++] = l;
                                }
                            }

                        } else {
                            slope = gd.getSlope();
                            pt = 1;
                        }
                    }
                }
                if (pt >= 3) {
                    while (pt > 0) {
                        SNode[] l = new SNode[pt + 1];
                        l[0] = new SNode(p, slope);
                        for (int i = 1; i <= pt; i++) {
                            l[i] = arr[arr.length - i];
                        }
                        pt = 0;
                        boolean contained = false;
                        if (count != 0) {
                            for (SNode[] i : segs) {
                                if (i != null) {
                                    Arrays.sort(i, new SNode.PointOrder());
                                    Arrays.sort(l, new SNode.PointOrder());

                                    if (i.length == l.length) {
                                        boolean equals = true;
                                        for (int w = 0; w < i.length; w++) {
                                            if (!i[w].equals(l[w])) {
                                                equals = false;
                                            }
                                        }
                                        if (equals) {
                                            contained = true;
                                        }
                                    }
                                }
                            }
                        }
                        if (count < len) {
                            if (!contained)
                                segs[count++] = l;
                        } else {
                            if (!contained) {
                                SNode[][] temp = new SNode[len * 2][];
                                int d = 0;
                                for (SNode[] i : segs) {
                                    temp[d++] = i;
                                }
                                segs = temp;
                                len *= 2;
                                segs[count++] = l;
                            }
                        }

                    }
                }


            }
            double slo = -1;
            Arrays.sort(segs,new SNode.SNodeOrder());
            int uf = segs.length;
            for (int i = 1; i < uf; i++) {
                int len1;
                if (segs[i] != null) {
                    SNode[] dgd = segs[i];
                    SNode[] pcp = segs[i - 1];
                    if (dgd[0].getSlope() == slo) {
                        int len = pcp.length - 1;
                        len1 = dgd.length - 1;
                        if (pcp[len].getPoint().equals(dgd[len1].getPoint())) {
                            if (len > len1)
                                segs[i] = null;
                            else {
                                segs[i - 1] = null;
                            }
                        }
                    } else {
                        slo = dgd[0].getSlope();
                    }

                }
            }
            int ldj = segs.length;
            Arrays.sort(segs,new SNode.SNodeOrder());
            for (int i = 1; i < ldj; i++) {
                if (segs[i] != null && segs[i - 1] != null) {
                    SNode[] inm = segs[i];
                    SNode[] i1cd = segs[i - 1];
                    int len = i1cd.length - 1;
                    int len1 = inm.length - 1;

                    if ((i1cd[0].getPoint().equals(inm[0].getPoint())&&i1cd[0].getSlope()==(inm[0].getSlope()))||i1cd[len].getPoint().equals(inm[len1].getPoint())) {
                        if (len > len1)
                            segs[i] = null;
                        else
                            segs[i-1] = null;
                    }
                }
            }
            this.seg = new LineSegment[numberOfSegments()];
            int r = 0;
            for (SNode[] i : segs) {
                if (i != null) {
                    LineSegment temp = new LineSegment(i[0].getPoint(), i[i.length - 1].getPoint());
                    boolean equal = false;
                    for (LineSegment s : seg) {
                        if (temp.equals(s)) {
                            equal = true;

                        }
                    }
                    if (!equal) {
                        seg[r++] = temp;
                    }
                }
            }
        }

        else throw new java.lang.IllegalArgumentException("List is null");

    }

    // finds all line segments containing 4 points
    public int numberOfSegments(){
        count =0;
        int sedn = segs.length;
        for(int i=0;i<sedn;i++){
            if(segs[i]!=null){
                count++;
            }
        }
        return count;
    }        // the number of line segments
    public LineSegment[] segments(){
            LineSegment[] t = Arrays.copyOf(seg,seg.length);
            return t;

    }
    public static void main(String[] args){

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
            if(p!=null)
            p.draw();
            else throw new IllegalArgumentException("Item is null");
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);

        System.out.println(collinear.numberOfSegments());
        for(LineSegment i : collinear.segments()){
            System.out.println(i);
        }
        System.out.println(collinear.numberOfSegments());
    }

}
