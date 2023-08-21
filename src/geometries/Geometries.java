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

    /**
     * Find and return a list of the intersections with bounding boxes.
     * @param ray
     * @return
     */
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
            double tNear = txMin>tyMin ? txMin :tyMin; //max of the min
            double tFar = txMax<tyMax ? txMax : tyMax; //min of the max

            if(txMin > tyMax || tyMin > txMax){
                continue;//don't add to the list of intersectables to be checked
            }
            if(tNear > tzMax || tzMin > tFar){
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
     * shut off bounding box
     */
    public void setBoundingBoxOff(){
        boundingBoxOn = false;
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

//UNUSED CODE FROM OTHER TRY OF boundingBoxIntersections()
//    /**
//     * Find and return a list of the intersections with bounding boxes.
//     * @param ray
//     * @return
//     */
//    public List<Intersectable> boundingBoxIntersections(Ray ray){
//        //Plane ray intersection points
//        double t1x, t1y, t1z, t2x, t2y, t2z, tNear, tFar;
//        LinkedList<Intersectable> result = new LinkedList<>();
//
//        for(Intersectable g : geometries){
//            //Check and assign t1x, which is the closer x-axis to P0 of the ray
//            if (Math.abs(ray.getP0().getX() - g.boundingRegion.min.getX()) < Math.abs(ray.getP0().getX() - g.boundingRegion.max.getX())){
//                t1x = g.boundingRegion.min.getX();
//                t2x = g.boundingRegion.max.getX();
//            }else {
//                t1x = g.boundingRegion.max.getX();
//                t2x = g.boundingRegion.min.getX();
//            }
//
//            //Check and assign t1y, which is the closer y-axis to P0 of the ray
//            if (Math.abs(ray.getP0().getY() - g.boundingRegion.min.getY()) < Math.abs(ray.getP0().getY() - g.boundingRegion.max.getY())){
//                t1y = g.boundingRegion.min.getY();
//                t2y = g.boundingRegion.max.getY();
//            }else {
//                t1y = g.boundingRegion.max.getY();
//                t2y = g.boundingRegion.min.getY();
//            }
//
//            //Check and assign t1x, which is the closer z-axis to P0 of the ray
//            if (Math.abs(ray.getP0().getZ() - g.boundingRegion.min.getZ()) < Math.abs(ray.getP0().getZ() - g.boundingRegion.max.getZ())){
//                t1z = g.boundingRegion.min.getZ();
//                t2z = g.boundingRegion.max.getZ();
//            }else {
//                t1z = g.boundingRegion.max.getZ();
//                t2z = g.boundingRegion.min.getZ();
//            }
//
//            //Find tNear, max(t1x, t1y, t1z)
//            if (t1x > t1y)
//                tNear = t1x;
//            else
//                tNear = t1y;
//            if (t1z > tNear)
//                tNear = t1z;
//
//            //Find tFar, min(t2x, t2y, t2z)
//            if (t2x < t2y)
//                tFar = t2x;
//            else
//                tFar = t2y;
//            if (t2z < tFar)
//                tFar = t2z;
//
//            //These tests don't work, not sure what the slides I found were talking about
//            if (tNear > tFar)
//                continue; //Box is missed if tNear > tFar
//            else if (tNear < tFar)
//                continue;
//            else
//                result.add(g);
//
//        }
//        return result;
//    }
