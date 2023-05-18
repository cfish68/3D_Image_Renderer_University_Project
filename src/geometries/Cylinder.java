package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import primitives.Util;

import java.util.List;

/**
 * Class cylinder which inherits from Tube. This class has a height (not infinite like tube.)
 * an axisRay from Tube and a radius because it is a RadialGeometry.
 */
public class Cylinder extends Tube{

    double height;
    /**
     * constructor which takes in a radius and a ray to create the tube part of the
     * cylinder, and a height for the cylinder
     * @param radius
     * @param ray
     */
    public Cylinder(double radius, Ray ray, double height) {
        super(radius, ray);
        if (height <= 0){
            throw new IllegalArgumentException("Height must be greater than 0.");
        }
        this.height = height;
    }

    /**
     * returns the normal at a given point.
     * @param point
     * @return
     */
    @Override
    public Vector getNormal(Point point) {
        //Check if on base
        if (Util.isZero(axisRay.getDir().dotProduct(point.subtract(this.axisRay.getP0())))){
            //returns negative of axis ray normalised
            return this.axisRay.getDir().normalize().scale(-1);

        } else if (Util.isZero(axisRay.getDir().dotProduct(point. //Check if on top
                subtract(this.axisRay.getP0().add(this.axisRay.getDir().normalize().scale(this.height))))))
            return this.axisRay.getDir().normalize();
        //If neither do super(point)
        return super.getNormal(point);
    }

    /**
     * to string for a cylinder
     * @return
     */
    @Override
    public String toString() {
        return super.toString() + " and Tube with height: " + this.height;
    }



    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        return null;
    }
}
