package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public abstract class RayTracerBase {
    protected Scene scene;

    /**
     * constructor for RayTracerbasic which receives a scene as a parameter
     * @param scene
     */
    public RayTracerBase(Scene scene){
        this.scene = scene;
    }

    /**
     * eceives a ray as a parameter and returns a color
     * @param ray
     * @return
     */
    public abstract Color traceRay(Ray ray);

}
