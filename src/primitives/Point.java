package primitives;

/**
 * this class will be used to represent a point, this point is 3 dimensional.
 */
public class Point {
    Double3 xyz;

    /**
     * constructor which takes 3 doubles
     * @param x
     * @param y
     * @param z
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x,y,z);
    }

    /**
     * constructor which takes the defined double3 class variable and creates point
     * @param x
     */
    public Point(Double3 x) {
        xyz = x;
    }

    /**
     *– receives a Point as a parameter, returns a vector from second point to the point which
     * called the method
     * ix x is the point that called the method and y is the pt that is the parameter then x-y is the point we are interested in.
     * @param pt
     * @return return a vector as a result of the subtraction
     */
    public Vector subtract(Point pt){
        return new Vector(this.xyz.subtract(pt.xyz));
    }

    /**
     * adds a vector to the point. Returns a new point
     * (in other words; move a point in the direction and distance of given vector
     * @param moverVector
     * @return the new point after the move.
     */
    public Point add(Vector moverVector) {
        return new Point(xyz.add(moverVector.xyz));
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
     *the formula we are computing here is ((x2 – x1)² + (y2 – y1)²) + (z2 – z1)²
     * the first line does the subtraction the second line squares the result and finally the return statement line also adds them up.
     * @param point
     * @return
     */
    public double distanceSquared(Point point) {
        Double3 x = point.xyz.subtract(xyz);
        Double3 squared = x.product(x);
        return squared.d1+squared.d2+squared.d3;
    }

    /**
     * checks first the object passed is in-fact a point so that we don't have a runtime error and then checks if it's the same point
     * @param obj
     * @return true if it is the same point false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Point)){
            return false;
        }
        if(this.xyz.equals(((Point) obj).xyz))
            return true;
        return false;
    }
}
