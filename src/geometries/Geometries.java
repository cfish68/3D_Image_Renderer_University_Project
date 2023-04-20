package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class uses the Composite design pattern to group geometries together
 * and create a scene which includes several geometries.
 */
public class Geometries implements Intersectable{

    private List<Intersectable> geometries;

    /**
     * Empty Constructor which creates an empty list of geometries
     */
    public Geometries() {
        geometries = List.of();
    }

    /**
     * Constructor that takes in a list of geometries
     * @param geometries
     */
    public Geometries(Intersectable... geometries) {
        this.geometries = List.of(geometries);
    }

    /**
     * Adds a Geometry g to geometries
     * @param g
     */
    public void add(Geometry g){
        geometries.add(g);
    }

    /**
     * Takes in a ray and returns all intersections with the geometries within the list geometries
     * @param ray
     * @return
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        if (geometries.isEmpty())
            return null;
        else
        { //TODO: I don't know how to do this without creating a list beforehand
            List<Point> result = new ArrayList<Point>();
            for (Intersectable geometry : geometries) {
                List<Point> intersections = geometry.findIntersections(ray);
                if(intersections!=null){
                    result.addAll(intersections);
                }
            }

            if (!result.isEmpty())
                return result;
            else
                return null;
        }
    }
}
