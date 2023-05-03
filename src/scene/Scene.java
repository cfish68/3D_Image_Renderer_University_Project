package scene;

import elements.AmbientLight;
import geometries.Geometries;
import geometries.Geometry;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Scene {
    public String name;
    public Color Background;
    //Ambient light with an initial color of black as the default constructor is black
    public AmbientLight ambientLight = new AmbientLight();
    public Geometries geometries;

    /**
     *   A constructor that will receive the scene’s name
     *   and it will also initialize an empty collection of geometries.
     * @param name
     */
    public Scene(String name){
        this.name = name;
        geometries = new Geometries();
    }

    public Scene setBackground(Color background) {
        Background = background;
        return this;
    }
    public Scene setAmbientLight(AmbientLight ambientLight){
        this.ambientLight = ambientLight;
        return this;
    }
    public Scene setGeometries(Geometry... geometry){
        this.geometries.add(geometry);
        return this;
    }
}
