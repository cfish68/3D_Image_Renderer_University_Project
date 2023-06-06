package primitives;

/**
 * this class is used to represent a vector it uses; a point for its representation.
 */
public class Vector extends Point{

    /**
     * Constructs a vector that takes in 3 doubles
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
    public Vector add(Vector vector) {
         return new Vector(this.xyz.add(vector.xyz));

    }

    /**
     * performs subtraction between two vectors and returns the resulting vector. ((A-B) vector from B to A)
     * @param vector
     * @return
     */
    public Vector subtract(Vector vector){
        if (this.equals(vector)){
            throw new IllegalArgumentException("Two equal vectors cannot be subtracted.");
        }
        return new Vector(this.xyz.subtract(vector.xyz));
    }

    /**
     * multiplies a vector by a number.
     * @param scalar
     * @return  Returns a new vector which is the old vector scaled
     */
    public Vector scale(double scalar){
        return new Vector(this.xyz.scale(scalar));
    }

    /**
     * uses variables from Double3 to get the dot product where A . B = a1 * b1 + a2 * b2 + a3 * b3
     * @param vector
     * @return
     */
    public double dotProduct(Vector vector){
        return this.xyz.d1 * vector.xyz.d1 +
                this.xyz.d2 * vector.xyz.d2 +
                this.xyz.d3 * vector.xyz.d3;
    }

    /**
     * uses variables from Double3 to get the cross product where
     * A X B = (b*z-c*y, cx -az, ay - bx)
     * @param vector
     * @return
     */
    public Vector crossProduct(Vector vector){
        return new Vector(this.xyz.d2*vector.xyz.d3-this.xyz.d3*vector.xyz.d2, this.xyz.d3*vector.xyz.d1-this.xyz.d1*vector.xyz.d3, this.xyz.d1*vector.xyz.d2-this.xyz.d2*vector.xyz.d1);
    }

    /**
     * (a,b,c) -> a^2+ b^2 +c^2
     * @return
     */
    public double lengthSquared(){
        return this.dotProduct(this);
    }

    /**
     * using the length squared formula we achieve the length of the vector by using the sqrt
     * @return
     */
    public double length(){
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * method to get a vector of length 1 uses the vector/the magnitude.
     * @return
     */
    public Vector normalize(){
        return this.scale(1/this.length());
    }

    /**
     * checks first the object passed is in-fact a vector so that we don't have a runtime error and then checks if it's the same vector
     * @param obj
     * @return true if it is the same vector false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof Vector)){
            return false;
        }
        return super.equals(obj);

    }

    @Override
    public String toString() {
        return "Vector: " + xyz.toString();
    }


    /**
     * returns a perpendicular vector to the current one
     * @return
     */
    public Vector getPerpendicular(){
        Vector n = this.normalize();
        if(n.getX()!=0){
            return new Vector(n.getY(), -n.getX(),n.getZ());
        }
        else if(n.getY()!=0){
            return new Vector(-n.getY(), n.getX(),n.getZ());
        }
        else{//the zero vector does not exist
            return new Vector(-n.getZ(), n.getY(),n.getX());
        }
    }

}
