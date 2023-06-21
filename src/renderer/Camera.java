package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.stream.*;
import java.util.MissingResourceException;

import static primitives.Util.isZero;

public class Camera {

    @Override
    public String toString() {
        return (location.toString()+to.toString()+imageWriter.toString());
    }

    //Camera attributes
    private Point location;
    private Vector to;
    private Vector up;
    private Vector right;
    //View Plane attributes
    private int width;
    private int height;
    private double distance;

    private ImageWriter imageWriter;
    private RayTraceBase rayTraceBase;

    /**
     * Constructor for a Camera object, taking in location and vectors 'up' and 'to'.
     * vectors 'up' and 'to' must be orthogonal. The constructor creates the vector 'right'
     * being perpendicular to 'up' and 'to'.
     * @param location
     * @param up
     * @param to
     */
    public Camera(Point location, Vector to, Vector up){
        if(!isZero(up.dotProduct(to))){
            throw new IllegalArgumentException("ERROR: Camera vectors up and to must be perpendicular");
        }
        this.location = location;
        right = up.crossProduct(to).normalize();
        this.to = to.normalize();
        this.up = up.normalize();
    }

    /**
     * ImageWriter setter
     * @param imageWriter
     * @return
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * rayTracer setter
     * @param rayTraceBase
     * @return
     */
    public Camera setRayTracer(RayTraceBase rayTraceBase) {
        this.rayTraceBase = rayTraceBase;
        return this;
    }

    /**
     * For every pixel in the view plane, trace the ray from that pixel
     * and write the corresponding color to the imageWriter
     */
    public Camera renderImage(){
        //todo: split if statement to appropriate segments and throw appropriate MissingResourceException
        if(this.location== null || this.imageWriter == null || this.rayTraceBase == null){
            throw new MissingResourceException("renderImage must have all attributes instantiated", "RayTracerBasic", "1" );
        }
        int Nx = imageWriter.getNx();
        int Ny = imageWriter.getNy();
        //throw new UnsupportedOperationException();
        Pixel.initialize(Ny, Nx, 0);
        IntStream.range(0, Ny).parallel().forEach(i ->{
            IntStream.range(0, Nx).parallel().forEach(j -> {
                Color color = rayTraceBase.traceRay(constructRay(Nx, Ny, j, i));
                this.imageWriter.writePixel(j,i,color);
                Pixel.pixelDone();
                //Pixel.printPixel();
            });
        });

        //OLD non-Threading Image render code
//        for(int i = 0; i < Nx; i++){
//            for(int j = 0; j < Ny; j++){
//                Color color = rayTraceBase.traceRay(constructRay(Nx, Ny,j,i));
//                this.imageWriter.writePixel(j,i,color);
//            }
//        }
        return this;
    }

    /**
     * prints a grid of a color with squares the size of interval.
     * @param interval
     * @param color
     */
    public void printGrid(int interval, Color color){
        if(imageWriter == null)
            throw new MissingResourceException("imageWriter is null", "imageWriter", "2");
        //todo: check this is correct and comment
        int Nx = imageWriter.getNx();
        int Ny = imageWriter.getNy();
        for(int i = 0; i < Nx; i+=interval)
            for(int j = 0; j < Ny; j++){
                imageWriter.writePixel(i,j, color);
            }
        for(int j = 0; j<Ny; j+=interval)
            for(int i = 0; i < Nx; i++){
                imageWriter.writePixel(i,j, color);
            }
    }

    /**
     * If imageWriter isn't null, calls {@link ImageWriter#writeToImage()}
     */
    public Camera writeToImage(){
        if(imageWriter == null)
            throw new MissingResourceException("imageWriter is null", "imageWriter", "2");
        imageWriter.writeToImage();
        return this;
    }

    /**
     * getter for location
     * @return
     */
    public Point getLocation() {
        return location;
    }

    /**
     * getter for vector to
     * @return
     */
    public Vector getTo() {
        return to;
    }

    /**
     * getter for vector up
     * @return
     */
    public Vector getUp() {
        return up;
    }

    /**
     * getter for vector right
     * @return
     */
    public Vector getRight() {
        return right;
    }

    /**
     *A set function for the View Plane size which receives two parameters -
     * width and height (in this order) and returns the camera’s object it self (this)
     * @param width
     * @param height
     * @return
     */
    public Camera setVPSize(int width, int height){
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     *A set function for the View Plane distance from the camera,
     * the function returns the camera’s object (this).
     * @param distance
     * @return
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Constructs a Ray through a pixel for creating the image on the view plane.
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @return
     */
    public Ray constructRay(int nX, int nY, int j, int i){
        double pixelheight = (double)height/nX;
        double pixelWidth = (double)width/nY;
        Point p0 = location.add(to.scale(distance));
        Point pPixel;

        int x = (nX / 2);
        int y = (nY / 2);

        int moveX = -1*(i-x);
        int moveY = -1*(j-y);
        //calculate "how many pixels" we need to move
        if(moveX == 0 && moveY == 0)//if they are both 0 then we are in the 'middle'
            pPixel = p0;
        else if(moveX == 0)//if move x is 0 then the other 1 is not
            pPixel = p0.add(right.scale(moveY*pixelWidth));
        else if(moveY == 0) {//otherwise move y is 0 and the other 1 is not
            pPixel = p0.add(up.scale(moveX*pixelheight));
        }
        else//if both were not 0 then both are not zero.
        {
            pPixel = p0.add(right.scale(pixelWidth * moveY)).add(up.scale(pixelheight * moveX));//p0.add(up.scale(pixelheight * moveX).add(right.scale(pixelWidth * moveY)));
        }
        //check for odd and even cases. if odd we are already in the middle of the wanted pixel
        if(nX%2 == 1 && nY%2 == 1)
            return new Ray(location, pPixel.subtract(location));
        //rows are even. we must add half a pixel in height
        else if(nX%2 == 0 &nY%2 == 1){
            pPixel = pPixel.add(up.scale(pixelheight*-0.5));
            return new Ray(location, pPixel.subtract(location));
        }
        //columns are even we must add 0.5 in width
        else if(nX%2 == 1 && nY%2 == 0){
            pPixel = pPixel.add(right.scale(pixelWidth*-0.5));
            return new Ray(location, pPixel.subtract(location));
        }
        else//both rows and columns is even, add both height and width half a pixel.
        {
            pPixel = pPixel.add(right.scale(pixelWidth*-0.5).add(up.scale(pixelheight*-0.5)));
            return new Ray(location, pPixel.subtract(location));
        }

        //move to the 'top left' of the viewPlane
        //p0 is the middle of the viewPlane
        //Point topLeftCorner = p0.add(up.scale(height/2)).add(right.scale(-1*(width/2)));//NOTICE!!! this could also be the 'top right corner'. It'll still work just from the opposite direction.

        //Point centerOfPixel = topLeftCorner.add(right.scale(pixelWidth*(j+0.5))).add(up.scale(-1*pixelheight*(i+0.5)));
        //return new Ray(location, centerOfPixel.subtract(location));


    }

}
