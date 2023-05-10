package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * Base abstract class for all Ray Tracers
 */
public abstract class RayTraceBase {
    protected Scene scene;

    /**
     * constructor for RayTraceBase which receives a scene as a parameter
     * @param scene
     */
    public RayTraceBase(Scene scene){
        this.scene = scene;
    }

    /**
     * Receives a ray as a parameter and returns a color
     * @param ray
     * @return
     */
    public abstract Color traceRay(Ray ray);

}
