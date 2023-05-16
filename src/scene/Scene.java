package scene;

import lighting.AmbientLight;
import geometries.Geometries;
import geometries.Geometry;
import primitives.Color;

/**
 * Class that handles the scene
 */
public class Scene {
    public String name;
    public Color Background;
    //Ambient light with an initial color of black as the default constructor is black
    public AmbientLight ambientLight = new AmbientLight();
    public Geometries geometries;

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
