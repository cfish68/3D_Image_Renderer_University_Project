package geometries;
import primitives.Color;
import primitives.Material;
import primitives.Vector;
import primitives.Point;

/**
 * this interface is the "parent" of all geometries it forces all geometries to
 * implement the getNormal method.
 */
public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;

    private Material material = new Material();


    public abstract Vector getNormal(Point point);



    /**
     * Setter for material of the Geometry
     * @return
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Setter for the material of geometry that returns the geometry this.
     * @param material
     * @return
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * getter for the Color emission. initially set to BLACK
     * @return
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * setter for the field emission.
     * @param emission
     * @return returns the object geometry in accordance to the builder pattern
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }
}
