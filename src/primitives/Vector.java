package primitives;

public class Vector extends Point{
    /**
     * Cronstructs a vector
     * @param x coordinate
     * @param y coordinate
     * @param z coordinate
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Argument can't equal zero vector");

    }

    /**
     *
     * @param xyz
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Argument can't equal zero vector");
    }

    /**
     * Performs addition between two vectors and returns the result as a vector.
     * @param vector
     */
    public add(Vector vector) {

    }

}
