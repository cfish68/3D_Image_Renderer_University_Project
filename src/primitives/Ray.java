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

    /**
     * Getter for point of ray
     * @return
     */
    public Point getP0(){
        return p0;
    }

    /**
     * Getter for direction of ray
     * @return
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * Checks first the object passed is in-fact a ray so that we don't have a runtime error
     * and then checks if it's the same ray with the same attributes.
     * @param obj
     * @return true if it is the same point false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if(!(obj instanceof Ray)){
            return false;
        }
        return this.p0.equals(((Ray) obj).p0) && this.dir.equals(((Ray) obj).dir);
    }

    @Override
    public String toString() {
        return "Ray starting at " + p0.toString()
                + "\nWith " + dir.toString();
    }


}
