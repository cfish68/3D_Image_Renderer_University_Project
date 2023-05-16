package geometries;

/**
 * This class RadialGeometry is the son of Geometry but is the parent of radial geometries.
 */
public abstract class RadialGeometry extends Geometry {

    protected double radius;

    /**
     * Constructor for RadialGeometry that takes in the radius.
     * @param radius
     */
    RadialGeometry(double radius){
        if (radius <= 0){
            throw new IllegalArgumentException("Radius must be greater than 0.");
        }
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Radial Geometry with radius: " + this.radius;
    }
}
