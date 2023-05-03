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
    public Geometries(Intersectable... geometries) {
        for(Intersectable g: geometries)
        {
            this.geometries.add(g);
        }
        //this.geometries.addAll(geometries);
    }

    /**
     * Adds a Geometry g to geometries
     * @param g
     */
    public void add(Intersectable... g){
        geometries.addAll(List.of(g));
        //for(Intersectable item : g){
          //  geometries.add(item);//Todo: Hi avi I changed add implementation please let me know if this is ok.
        //}
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
        else { //TODO: I don't know how to do this without creating a list beforehand
            LinkedList<Point> result = null;
            for (Intersectable geometry : geometries) {
                List<Point> intersections = geometry.findIntersections(ray);
                if (intersections != null) {
                    if (result == null) result = new LinkedList<>(intersections);
                    else result.addAll(intersections);
                }
            }

            return result;
        }
    }
}
