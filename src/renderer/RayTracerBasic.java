package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class RayTracerBasic extends RayTracerBase {

    public RayTracerBasic(Scene scene){
        super(scene);
    }
    @Override
    public Color traceRay(Ray ray) {

        List<Point> points = scene.geometries.findIntersections(ray);
        if(points == null){
            return scene.Background;
        }
        //ray.findClosestPoint(points);
        Color color = calcColor(ray.findClosestPoint(points));
        return color;
    }

    /**
     * calcColor receives a point as a param and return the color
     * at this stage the function shall return the color of the ambient light of the scene.
     * @param point
     * @return
     */
    public Color calcColor(Point point){
        return scene.ambientLight.getIntensity();
    }
}
