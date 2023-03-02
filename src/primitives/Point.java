package primitives;

public class Point {
    Double3 xyz;

    public Point(double x, double y, double z) {
        xyz = new Double3(x,y,z);
    }

    public Point(Double3 x) {
        xyz = x;
    }

    /**
     *
     * @param p
     * @return
     */
    public Vector subtract(Point p){
        //return xyz.subtract(p.xyz);
    }

    public Point add(Vector v) {

    }

    public double distance(Point p) {

    }

    public double distanceSquared(Point p) {

    }

}
