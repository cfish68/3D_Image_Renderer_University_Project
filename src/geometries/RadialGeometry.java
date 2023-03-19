package geometries;

/**
 * This class RadialGeometry is the son of Geometry but is the parent of radial geometries.
 */
public abstract class RadialGeometry implements Geometry {

    protected double radius;

    /**
     * Constructor for RadialGeometry that takes in the radius.
     * @param radius
     */
    RadialGeometry(double radius){
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Radial Geometry with radius: " + this.radius;
    }
}
