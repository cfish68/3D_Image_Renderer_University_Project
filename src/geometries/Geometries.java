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





    /**
     * bounding box for geometries
     */
    protected static boolean boundingBoxOn = false;
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
        if(boundingBoxOn == true){
            for(Intersectable geo: g){
                geo.setBoundingRegion();
            }
        }
        geometries.addAll(List.of(g));

    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

        if (geometries.isEmpty())
            return null;
        else if (boundingBoxOn == true) {
            List<Intersectable> boundedGeometries = boundingBoxIntersections(ray);
           if(boundedGeometries.isEmpty()){
               return null;
           }
            return calcIntersections(ray, boundedGeometries);
        }
        return calcIntersections(ray, geometries);
    }


    @Override
    public BoundingRegion setBoundingRegion() {
        return null;
    }

    public List<Intersectable> boundingBoxIntersections(Ray ray){
        LinkedList<Intersectable> result = new LinkedList<>();
        for(Intersectable g : geometries){
            double txMin = (g.boundingRegion.min.getX() - ray.getP0().getX())/ray.getDir().getX();
            double txMax = (g.boundingRegion.max.getX() - ray.getP0().getX())/ray.getDir().getX();
            if (txMax < txMin) {
                double tmp = txMax;
                txMax = txMin;
                txMin = tmp;
            }
            double tyMin = (g.boundingRegion.min.getY() - ray.getP0().getY())/ray.getDir().getY();
            double tyMax = (g.boundingRegion.max.getY() - ray.getP0().getY())/ray.getDir().getY();
            if (tyMax < tyMin) {
                double tmp = tyMax;
                tyMax = tyMin;
                tyMin = tmp;
            }
            double tzMin = (g.boundingRegion.min.getZ() - ray.getP0().getZ())/ray.getDir().getZ();
            double tzMax = (g.boundingRegion.max.getZ() - ray.getP0().getZ())/ray.getDir().getZ();
            if (tzMax < tzMin) {
                double tmp = tzMax;
                tzMax = tzMin;
                tzMin = tmp;
            }
            double tMin = txMin>tyMin ? txMin :tyMin; //max of the min
            double tMax = txMax<tyMax ? txMax : tyMax; //min of the max

            if(txMin > tyMax || tyMin > txMax){
                continue;//don't add to the list of intersectables to be checked
            }
            if(tMin > tzMax || tzMin > tMax){
                continue;//don't add to the list of intersecatbles to be checked
            }
            result.add(g);


        }
        return result;
    }

    /**
     * sets the bounding region boolean to be true and sets all the relevant bounding regions
     */
    public void setBoudningBoxOn(){
        boundingBoxOn = true;
        for(Intersectable g : geometries){
            g.setBoundingRegion();
        }
    }

    /**
     * calculate the geoPoints of the ray intersections
     * @param ray
     * @param geometryList
     * @return
     */
    private List<GeoPoint> calcIntersections(Ray ray, List<Intersectable> geometryList){
        LinkedList<GeoPoint> result = null;
        for (Intersectable geometry : geometryList) {
            List<GeoPoint> intersections = geometry.findGeoIntersectionsHelper(ray);
            if (intersections != null) {
                if (result == null) result = new LinkedList<>(intersections);
                else result.addAll(intersections);
            }
        }
        return result;
    }
}
