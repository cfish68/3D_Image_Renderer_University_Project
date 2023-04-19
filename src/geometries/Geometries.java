package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
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
        geometries = new ArrayList<>();
    }

    /**
     * Constructor that takes in a list of geometries
     * @param geometries
     */
    public Geometries(List<Intersectable> geometries) {
        this.geometries = geometries;
    }

    /**
     * Adds a Geometry g to geometries
     * @param g
     */
    public void add(Geometry g){
        geometries.add(g);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
