

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;


public class Point implements Comparable<Point> {
    private int x;
    private int y;
    public Point(int x, int y){
            this.x = x;
            this.y = y;

    }                      // constructs the point (x, y)
    private int getX(){
       return this.x;
    }
    private int getY(){
        return this.y;
    }
    public void draw(){
        StdDraw.point(x,y);

    }                             // draws this point
    public void drawTo(Point that){
        StdDraw.line(this.x,this.y,that.x,that.y);

    }                   // draws the line segment from this point to that point
    public String toString(){
        return "(" + x + "," + y + ")";
    }                           // string representation

    public int compareTo(Point that){
        if(this.y<that.y){
            return -1;
        }
        else if(this.y==that.y){
            if(this.x<that.x){
                return -1;
            }
            else if(this.x>that.x){
                return 1;
            }
            else{
                return 0;
            }
        }
        else{
            return 1;
        }
    }
    private boolean equals(Point p){
        if(this.x==p.getX()&&this.y==p.getY()){
            return true;
        }
        return false;
    }// compare two points by y-coordinates, breaking ties by x-coordinates
    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */

    public double slopeTo(Point that){
        if(that!=null) {
            if (this.y == that.y && this.x == that.x) {
                return Double.NEGATIVE_INFINITY;
            } else {
                if (this.y == that.y) {
                    return +0;
                } else if (this.x == that.x) {
                    return Double.POSITIVE_INFINITY;
                } else {
                    int yDiff = this.y - that.y;
                    int xDiff = this.x - that.x;
                    return (double) yDiff / xDiff;
                }
            }
        }
        else{
            throw new NullPointerException("Item is null");
        }
    }
  // the slope between this point and that point
    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  //that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */

    public Comparator<Point> slopeOrder() {
        return new slopeOrder();
    }

    private class slopeOrder implements Comparator<Point>{
        public int compare(Point po, Point pt){
            if(po==null||pt==null){
                throw new NullPointerException("Item was null");
            }
            double p = Point.this.slopeTo(po);
            double t = Point.this.slopeTo(pt);
            if(p<t){
                return -1;
            }
            else if(p==t){
                return 0;
            }
            else return 1;
        }

    }

     public static void main(String[] args){
         Point p = new Point(6, 6);
         Point q  = new Point(3, 0);
         Point r = new Point(0, 1);
         System.out.println(p.slopeOrder().compare(q,r));
         System.out.println(p.slopeTo(q));
         System.out.println(p.slopeTo(r));
     }            // compare two points by slopes they make with this point
}