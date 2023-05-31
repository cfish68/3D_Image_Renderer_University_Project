package renderer;

import lighting.Light;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * Basic Ray Tracer class
 */
public class RayTracerBasic extends RayTraceBase {


    /**
     * a constant field for the amount that you want to move the ray’s head
     */
    private static final double DELTA = 0.1;

    /**
     * Constructer for RayTracerBasic that receives a scene
     *
     * @param scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Receives a ray and traces that ray:
     * Finds intersections between ray and the scene. If no intersections,
     * returns the background color. Otherwise, returns color of the closest
     * point to the rays head.
     *
     * @param ray
     * @return
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> geoPoints = scene.geometries.findGeoIntersectionsHelper(ray);
        if (geoPoints == null) {
            return scene.Background;
        }
        Color color = calcColor(ray.findClosestGeoPoint(geoPoints), ray);
        return color;
    }

    /**
     * calcColor with GeoPoint, receives a geoPopint an return the color of that point
     *
     * @param gPoint
     * @return
     */
    public Color calcColor(GeoPoint gPoint, Ray ray) {
        if (gPoint == null) {
            return scene.ambientLight.getIntensity();
        }
        return scene.ambientLight.getIntensity().add(calcLocalEffects(gPoint, ray));
    }

    /**
     * Calculates the local effects of all lightsources and returns the corresponding color.
     *
     * @param gp
     * @param ray
     * @return
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                if (unshaded(l, n, gp, lightSource)) {
                    Color iL = lightSource.getIntensity(gp.point);
                    //The light that gets diffused and scatters upon hitting the surface
                    color = color.add(iL.scale(calcDiffusive(material, nl))
                            //the light that reflects more sharply and concisely
                            , iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    /**
     * Calculates the diffusive colour of the material mat and returns it.
     * If nl=0 then the diffusive ray is at too shallow of an angle and no
     * colour is returned
     *
     * @param mat
     * @param nl
     * @return
     */
    private Double3 calcDiffusive(Material mat, double nl) {
        return mat.kD.scale(Math.abs(nl));
    }

    /**
     * Calculates the specular colour. Formula taken from the RHS of the red boxed addition on
     * slide 48, slideshow 6.
     *
     * @param mat
     * @param n         whish is the normal
     * @param l         which is LightSourceL
     * @param nl        which is n.dotProduct(l)
     * @param cameraDir
     * @return
     */
    private Double3 calcSpecular(Material mat, Vector n, Vector l, double nl, Vector cameraDir) {
        Vector r = l.subtract(n.scale(nl).scale(2)); //reflection vector
        return mat.kS
                .scale(
                        Math.pow(Math.max(0,
                                cameraDir.scale(-1).dotProduct(r)), mat.nShininess));
    }

    /**
     * function to check if a point is not being shadowed
     * " need to check if there is something that is blocking the light from you point.  (You can add parameters if you find it more efficient)"
     *
     * @param l  the light direction
     * @param n  the normal direction
     * @param gp geometry intersection point
     * @return
     */
    private boolean unshaded(Vector l, Vector n, GeoPoint gp, LightSource lightSource) {
        Vector lightDirection = l.scale(-1); // from point to light source
        //Vector epsVector = n.scale(DELTA);
        //Point point = gp.point.add(epsVector);
        Ray lightRay = new Ray(gp.point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, lightSource.getDistance(gp.point));
        if (intersections == null) {
            return true;
        }
        return false;

    }
}


