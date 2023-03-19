package primitives;

/**
 * this class is used to represent a ray. (a vector from  a certain point)
 */
public class Ray {
    Point point;
    Vector vector;
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
