package scene;

import lighting.AmbientLight;
import geometries.Geometries;
import geometries.Geometry;
import lighting.Light;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * Class that handles the scene
 */
public class Scene {
    public String name;
    public Color Background = Color.BLACK;
    //Ambient light with an initial color of black as the default constructor is black
    public AmbientLight ambientLight = new AmbientLight();
    public Geometries geometries;
    public List<LightSource> lights = new LinkedList<LightSource>();

    /**
     * Soft shadow definition parameter, 1 is for 1 ray
     * For bigger than 1, the amount of rays is the square of softShadowDef
     */
    private int SOFT_SHADOW_DEF = 1;

    /**
     * Returns the softShadowDef of the scene
     * @return
     */
    public int getSoftShadowDef() {
        return SOFT_SHADOW_DEF;
    }

    /**
     * Sets the soft shadow def of the scene and returns the scene
     * 1 is for 1 ray
     * For bigger than 1, the amount of rays is the square of softShadowDef
     * @param softShadowDef
     * @return
     */
    public Scene setSoftShadowDef(int softShadowDef) {
        SOFT_SHADOW_DEF = softShadowDef;
        return this;
    }

    /**
     * Setter for List<LightSource> lights that returns this (the Scene object)
     * @param lights
     * @return
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    /**
     *   A constructor that will receive the scene’s name,
     *   and it will also initialize an empty collection of geometries.
     * @param name
     */
    public Scene(String name){
        this.name = name;
        geometries = new Geometries();
    }

    /**
     * Setter for the background colour that returns the scene (this)
     * @param background
     * @return
     */
    public Scene setBackground(Color background) {
        Background = background;
        return this;
    }

    /**
     * Setter for the ambient light that returns the scene (this)
     * @param ambientLight
     * @return
     */
    public Scene setAmbientLight(AmbientLight ambientLight){
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Setter that receives 0 or more geometries and adds them to the scene,
     * then returns the scene (this).
     * @param geometry
     * @return
     */
    public Scene setGeometries(Geometry... geometry){
        this.geometries.add(geometry);
        return this;
    }
}
