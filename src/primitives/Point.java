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
        return new Point(xyz.add(v.xyz));
    }

    /***
     *calculates the distance between this point and point param
     * @param point other point of which we want distance to be calculated to.
     * @return this method returns a double which is the distance between the two points
     */
    public double distance(Point point) {
        return Math.sqrt(this.distanceSquared(point));
    }

    /***
     *
     * @param point
     * @return
     */
    public double distanceSquared(Point point) {
        Double3 x = point.xyz.subtract(xyz);
        Double3 squared = x.product(x);
        return squared.d1+squared.d2+squared.d3;
    }

}
