package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import java.util.List;

/**
 * Basic Ray Tracer class
 */
public class RayTracerBasic extends RayTraceBase {

    /**
     * Constructer for RayTracerBasic that receives a scene
     * @param scene
     */
    public RayTracerBasic(Scene scene){
        super(scene);
    }

    /**
     * Receives a ray and traces that ray:
     * Finds intersections between ray and the scene. If no intersections,
     * returns the background color. Otherwise, returns color of the closest
     * point to the rays head.
     * @param ray
     * @return
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> geoPoints = scene.geometries.findGeoIntersectionsHelper(ray);
        if(geoPoints == null)
        {
            return scene.Background;
        }
        Color color = calcColor(ray.findClosestGeoPoint(geoPoints));



//        List<Point> points = scene.geometries.findIntersections(ray);
//        if(points == null){
//            return scene.Background;
//        }
//        //ray.findClosestPoint(points);
//        Color color = calcColor(ray.findClosestPoint(points));
        return color;
    }

    /**
     * calcColor receives a point as a param and return the color
     * at this stage the function shall return the color of the ambient light of the scene.
     * @param point
     * @return
     */
    public Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }

    /**
     * calcColor with GeoPoint, receives a geoPopint an return the color of that point
     * @param gPoint
     * @return
     */
    public Color calcColor(GeoPoint gPoint){
        if(gPoint == null){
            return scene.ambientLight.getIntensity();
        }
        return scene.ambientLight.getIntensity().add(gPoint.geometry.getEmission());
    }
}
