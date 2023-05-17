package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class uses the Composite design pattern to group geometries together
 * and create a scene which includes several geometries.
 */
public class Geometries extends Intersectable{

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
    public Geometries(Intersectable... geometries) {
        if(geometries!=null) {
            if(this.geometries == null)
                this.geometries = new LinkedList<>();
            for (Intersectable g : geometries) {
                this.geometries.add(g);
            }
        }
        //this.geometries.addAll(geometries);
    }

    /**
     * Adds a Geometry g to geometries
     * @param g
     */
    public void add(Intersectable... g){
        geometries.addAll(List.of(g));
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        if (geometries.isEmpty())
            return null;
        else {
            LinkedList<GeoPoint> result = null;
            for (Intersectable geometry : geometries) {
                List<GeoPoint> intersections = geometry.findGeoIntersectionsHelper(ray);
                if (intersections != null) {
                    if (result == null) result = new LinkedList<>(intersections);
                    else result.addAll(intersections);
                }
            }

            return result;
        }
    }
}
