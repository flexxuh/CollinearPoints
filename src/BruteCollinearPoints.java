
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
public final class BruteCollinearPoints {
    private SNode[][] segs;
    private int count = 0;
    private int len;
    private final LineSegment[] seg;
    private final Point[] points;
    public BruteCollinearPoints(Point[] points){
        if(points!=null) {
            for(Point p:points){
                if(p==null){
                    throw new IllegalArgumentException("Null element");
                }
            }
            this.points =Arrays.copyOf(points, points.length);
            segs = new SNode[10][];
            len = 10;
            Arrays.sort(this.points);
            StdDraw.setXscale(0, 10);
            StdDraw.setYscale(0, 10);
            int ghn = points.length;
            for(Point p : this.points) {
                SNode[] arr = new SNode[ghn - 1];
                int counter = 0;
                for (Point t : this.points) {
                    if (p != t) {
                        if(p.compareTo(t) == 0){
                            throw new IllegalArgumentException("Repeated Point");
                        }
                        double slope = p.slopeTo(t);
                        arr[counter++] = new SNode(t, slope);

                    }
                }

                Arrays.sort(arr);
                double slope = 0;
                int pt = 0;
                int lgm = arr.length;
                for (int q=0;q<lgm;q++) {
                    SNode tgv = arr[q];
                    if(tgv.getSlope()==slope){
                        pt++;
                    }
                    else{
                        if(pt>=3){
                            SNode[] l = new SNode[pt+1];
                            l[0] = new SNode(p,slope);
                            for(int i=1;i<=pt;i++) {
                                l[i] = arr[q - i];
                            }
                            pt = 0;
                            boolean contained = false;
                            if(count!=0){
                                for(SNode[] i:segs){
                                    if(i!=null) {
                                        Arrays.sort(i,new SNode.PointOrder());
                                        Arrays.sort(l,new SNode.PointOrder());
                                        int legd = i.length;
                                        if (legd == l.length) {
                                            boolean equals = true;
                                            for (int w = 0; w < legd; w++) {
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
                            if(count<len) {
                                if(!contained)
                                    segs[count++] = l;
                            }
                            else{
                                if(!contained) {
                                    SNode[][] temp = new SNode[len * 2][];
                                    int d=0;
                                    for (SNode[] i : segs) {
                                        temp[d++] = i;
                                    }
                                    segs = temp;
                                    len *= 2;
                                    segs[count++] = l;
                                }
                            }

                        }
                        else{
                            slope = tgv.getSlope();
                            pt = 1;
                        }
                    }
                }
                if(pt>=3){
                    while(pt>0){
                        SNode[] l = new SNode[pt+1];
                        l[0] = new SNode(p,slope);
                        for(int i=1;i<=pt;i++) {
                            l[i] = arr[lgm - i];
                        }
                        pt = 0;
                        boolean contained = false;
                        if(count!=0){
                            for(SNode[] i:segs){
                                if(i!=null) {
                                    Arrays.sort(i,new SNode.PointOrder());
                                    Arrays.sort(l,new SNode.PointOrder());
                                    int pcp = i.length;
                                    if (pcp == l.length) {
                                        boolean equals = true;
                                        for (int w = 0; w < pcp; w++) {
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
                        if(count<len) {
                            if(!contained)
                                segs[count++] = l;
                        }
                        else{
                            if(!contained) {
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
            this.seg = new LineSegment[numberOfSegments()];
            int r=0;
            for(SNode[] i:segs){
                if(i!=null) {
                    seg[r++] = new LineSegment(i[0].getPoint(), i[i.length - 1].getPoint());
                }
            }

        }
        else throw new IllegalArgumentException("List is null");
    }  // finds all line segments containing 4 points
    public int numberOfSegments(){
        count =0;
        for(int i=0;i<segs.length;i++){
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
        System.out.println(collinear.numberOfSegments());
    }


}
