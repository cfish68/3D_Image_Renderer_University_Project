package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static primitives.Util.alignZero;

/**
 * Basic Ray Tracer class
 */
public class RayTracerBasic extends RayTraceBase {


    private static final Double3 k = Double3.ONE;

    /**
     * a constant field for the amount that you want to move the ray’s head
     */
    private static final double DELTA = 0.1;
    /**
     * Recursive stop condition for maximum of reflection and transparency
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /**
     * Recursive stop condition for minimum of reflection and transparency
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

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
        if(geoPoints == null) {
            return scene.Background;
        }
        Color color = calcColor(ray.findClosestGeoPoint(geoPoints), ray);
        return color;
    }

    /**
     * calcColor function.(helper)
     * @param gp
     * @param ray
     * @return
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        if(gp == null){
            return scene.ambientLight.getIntensity();
        }
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, k)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * recursive calcColor
     * @param gp
     * @param ray
     * @param level
     * @param k
     * @return
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        if(gp == null){
            return scene.ambientLight.getIntensity();
        }
        Color color = calcLocalEffects(gp, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }


    /**
     * Calculates the local effects of all lightsources and returns the corresponding color.
     * @param gp
     * @param ray
     * @return
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir (); Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v)); if (nv == 0) return color;
        Material material = gp.geometry.getMaterial();

        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));

            if (nl * nv > 0) { // sign(nl) == sing(nv)
               // if(unshaded(gp, lightSource, l, n, nl)) {//gp, lightSource, l, n, nl
                Double3 ktr = newTransparency(gp, lightSource, l, n);

                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)){
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    //The light that gets diffused and scatters upon hitting the surface
                    color = color.add(iL.scale(calcDiffusive(material, nl)),
                            //the light that reflects more sharply and concisely
                            iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    /**
     * Calculates the diffusive colour of the material mat and returns it.
     * If nl=0 then the diffusive ray is at too shallow of an angle and no
     * colour is returned
     * @param mat
     * @param nl
     * @return
     */
    private Double3 calcDiffusive(Material mat, double nl){
        return mat.kD.scale(Math.abs(nl));
    }

    /**
     * Calculates the specular colour. Formula taken from the RHS of the red boxed addition on
     * slide 48, slideshow 6.
     * @param mat
     * @param n whish is the normal
     * @param l which is LightSourceL
     * @param nl which is n.dotProduct(l)
     * @param cameraDir
     * @return
     */
    private Double3 calcSpecular(Material mat, Vector n, Vector l, double nl, Vector cameraDir){
        Vector r = l.subtract(n.scale(nl).scale(2)); //reflection vector
        return mat.kS
                .scale(
                        Math.pow(Math.max(0,
                                cameraDir.scale(-1).dotProduct(r)), mat.nShininess));
    }

    /**
     * function to check if a point is not being shadowed
     * " need to check if there is something that is blocking the light from you point.
     * (You can add parameters if you find it more efficient)"
     * @param l the light direction
     * @param n the normal direction
     * @param gp geometry intersection point
     * @return
     */
    private boolean unshaded( GeoPoint gp, LightSource lightSource, Vector l, Vector n, double nl) {
        Vector lightDirection = l.scale(-1); // from point to light source

        Ray lightRay = new Ray(gp.point, lightDirection, n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, lightSource.getDistance(gp.point));
        if (intersections == null) {
            return true;
        }
        intersections = intersections.stream().filter(g -> g.geometry.getMaterial().kT.equals(Double3.ZERO)
                 ).collect(Collectors.toList());
        return intersections.isEmpty();
    }

    /**
     * calculates reflection and refraction
     * @param gp
     * @param ray
     * @param level
     * @param k
     * @return
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k){
        Color color = Color.BLACK;
        Material mat = gp.geometry.getMaterial();
        Double3 kr = mat.getKr(), kkr = k.product(kr);
        Vector n = gp.geometry.getNormal(gp.point);

        //reflection segment
        Ray reflectedRay = constructReflectedRay(gp, ray.getDir(), n);
        GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
            color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
        }

        //refraction segment
        Ray refractedRay = constructRefractedRay(gp.point, ray, n);
        GeoPoint refractedPoint = findClosestIntersection(refractedRay);

        Double3 kt = mat.getKt(), kkt = k.product(kt);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
             color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
        }
        return color;
    }

    /**
     * Method which calculates the reflected ray and return it
     * @param gp
     * @param dir
     * @param n
     * @return
     */
    private Ray constructReflectedRay(GeoPoint gp, Vector dir, Vector n){
        //𝒗 − 𝟐 ∙ (𝒗 ∙ 𝒏) ∙ n
        Vector r = dir.subtract(n.scale(n.dotProduct(dir)).scale(2)); //reflection vector
        return new Ray(gp.point, r, n);
    }

    /**
     * Returns the refracted ray.
     * @param GP
     * @param inRay
     * @param n
     * @return
     */
    private Ray constructRefractedRay(Point GP, Ray inRay, Vector n){
        return new Ray(GP, inRay.getDir(), n);
    }

    /**
     * finds closest geoPoint of a ray and returns it
     * @param ray
     * @return
     */
    private GeoPoint findClosestIntersection(Ray ray){
        return ray.findClosestGeoPoint(scene.geometries.findGeoIntersectionsHelper(ray));
    }

//    /**
//     * Method for calculating transparency at a GeoPoint
//     * @param gp
//     * @param lightSource
//     * @param l Vector from light source
//     * @param n
//     * @return
//     */
//    private Double3 transparency(GeoPoint gp, LightSource lightSource, Vector l, Vector n) {
//        Double3 ktr = Double3.ONE;
//        Vector lightDirection = l.scale(-1); // from point to light source
//
//        Ray lightRay = new Ray(gp.point, lightDirection, n);
//        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, lightSource.getDistance(gp.point));
//        if (intersections == null) {
//            return ktr;
//        }
//        //intersections = intersections.stream().filter(g -> !g.geometry.getMaterial().kT.equals(Double3.ZERO)
//        //).collect(Collectors.toList());
//        for(GeoPoint g: intersections){
//            ktr = ktr.product(g.geometry.getMaterial().kT);
//        }
//        return ktr;
//    }


    private Double3 newTransparency(GeoPoint gp, LightSource lightSource, Vector l, Vector n) {
        Double3 ktr = Double3.ONE;
        Vector lightDirection = l.scale(-1); // from point to light source
        List<Ray> rays = superSampleRays(lightDirection, gp, lightSource, n, scene.getSoftShadowDef()-1, lightSource.getRadius());
        if(rays == null){
            return ktr;
        }
        Double3 total = Double3.ZERO;
        for(Ray ray: rays){
            Double3 ktr1 = Double3.ONE;
            List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray, lightSource.getDistance(gp.point));
            if (intersections != null) {
                for(GeoPoint g: intersections){
                    ktr1 = ktr1.product(g.geometry.getMaterial().kT);
                }
                total = total.add(ktr1);
            }
            total = total.add(ktr1);
        }

        total = total.product(Double3.ONE.scale(1d/rays.size()));
        return ktr.product(total);

    }


    /**
     * function to get a list of rays to trace for soft shadows
     * @param dir ray from gp to the lightSource
     * @param gp
     * @param lightSource
     * @param n
     * @return
     */
    private List<Ray> superSampleRays(Vector dir, GeoPoint gp, LightSource lightSource, Vector n, int num, double radius){
        //obtain perpendicular vector to the ray
        if(lightSource.getDistance(gp.point) != Double.POSITIVE_INFINITY){
            Vector up = dir.getPerpendicular().normalize();
            Vector right = up.crossProduct(dir).normalize();

            //now use those rays to "move around the light source"
            Point lsPoint = gp.point.add(dir.normalize().scale(lightSource.getDistance(gp.point)));
            List<Point> points = grid(lsPoint, up, right, num, radius);
            List<Ray> rays = new ArrayList<Ray>();
            for(Point point : points){
                rays.add(new Ray(gp.point, point.subtract(gp.point).normalize(), n));
            }
            return rays;

        }else { //Its infinite directional light and returns -getL
            return List.of(new Ray(gp.point, lightSource.getL(gp.point).scale(-1),n));
        }
//            return null;
    }


    /**
     *
     * @param midPoint
     * @param up
     * @param right
     * @param n
     * @param radius
     * @return
     */
    private List<Point>  grid(Point midPoint, Vector up, Vector right, int n, double radius){

        List<Point> points = new ArrayList<Point>();
        points.add(midPoint);
        if(radius == 0 || n == 0){
            return points;
        }
        Point start = midPoint.add(up.scale(radius)).add(right.scale(radius));
        points.add(start);
        double diameter = radius*2;
        for(double i = diameter/n; i < diameter; i+=diameter/n){
            for(double j = diameter/n; j<diameter; j+=diameter/n){

                points.add(start.add(up.scale(-i).add(right.scale(-j))));

            }
        }
        return points;
    }
}


