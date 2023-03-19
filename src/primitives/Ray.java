package primitives;

/**
 * this class is used to represent a ray. (a vector from  a certain point)
 */
public class Ray {
    Point point;
    Vector vector;

    /**
     * Constructor for Ray that takes in a point and vector.
     * @param point
     * @param vector
     */
    public Ray(Point point, Vector vector){
        this.point = point;
        this.vector = vector;
    }

    @Override
    public String toString() {
        return "Ray starting at " + point.toString()
                + "\nWith " + vector.toString();
    }
}
