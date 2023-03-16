package geometries;

/**
 * this class RadialGeometry is the sun of Geometry but is the mother of radial geometries.
 */
public abstract class RadialGeometry implements Geometry {
    protected double radius;
    RadialGeometry(double radius){
        this.radius = radius;
    }
}
