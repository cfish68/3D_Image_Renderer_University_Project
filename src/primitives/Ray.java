package primitives;

/**
 * this class is used to represent a ray. (a vector from  a certain point)
 */
public class Ray {
    Point p0;
    Vector dir;

    /**
     * Constructor for Ray that takes in a point and vector.
     * @param point
     * @param dir
     */
    public Ray(Point point, Vector dir){
        this.p0 = point;
        this.dir = dir;
    }
//TODO: make equals

    @Override
    public String toString() {
        return "Ray starting at " + p0.toString()
                + "\nWith " + dir.toString();
    }
}
