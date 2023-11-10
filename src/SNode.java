
import java.util.Comparator;
public class SNode implements Comparable<SNode>{
    Point point;
    double slope;
    public SNode(Point p,double slope){
        this.slope = slope;
        point = p;
    }

    @Override
    public int compareTo(SNode o) {
        if(this.slope>o.slope){
            return 1;
        }
        else if(this.slope==o.slope){
            return 0;
        }
        else{
            return -1;
        }

    }

    public double getSlope() {
        return slope;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
    public void setSlope(double slope){
        this.slope = slope;
    }

    public String toString(){
        return this.slope+" " + this.point+"\n";
    }
    public boolean equals(SNode t){
        if(this.slope==t.slope&&this.point==t.point){
            return true;
        }
        return false;
    }

    public Comparator<SNode> PointOrder = new PointOrder();
    public Comparator<SNode[]> SNodeOrder = new SNodeOrder();

public static class SNodeOrder implements Comparator<SNode[]>{
    public int compare(SNode[] t,SNode[] l) {
        int r;
        if (t != null && l != null) {
            if (t[0].getSlope() > l[0].getSlope()) {
                return 1;
            } else if (t[0].getSlope() == l[0].getSlope()) {
                return 0;
            } else return -1;
        }
        else return 0;
    }
}
    public static class PointOrder implements Comparator<SNode>{
        public int compare(SNode t,SNode l){
            if(t.getPoint().compareTo(l.getPoint())>0){
                return 1;
            }
            else if(t.getPoint().compareTo(l.getPoint())<0){
                return -1;
            }
            else{
                return 0;
            }
        }
    }
    }

