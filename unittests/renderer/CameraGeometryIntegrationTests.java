package renderer;

import geometries.Geometry;
import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Check integration between creating rays from the camera and
 * calculating intersections between rays and geometric bodies.
 */
public class CameraGeometryIntegrationTests {

    /**
     * Returns the number of intersections for camera at resolution 3*3
     * @param camera
     * @param geometry
     * @return
     */
    private int viewPlaneIntersections(Camera camera, Geometry geometry) {
        int nX = 3, nY = 3;
        int total = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                total += geometry.findIntersections(camera.constructRay(nX,nY,j,i)).size();
            }
        }
        return total;
    }

    /**
     * Function for testing Camera sphere integration and ray intersection points between them.
     */
    @Test
    void CameraSphereTests() {
        Camera camera = new Camera(new Point(0, 0, 0),new Vector(0,0,1), new Vector(0,-1,0)).setVPDistance(1).setVPSize(3, 3);;
        Sphere sphere = new Sphere(1, new Point(0, 0, -3));
        //the center of the viewPlane is (0,-1,0) (also the center of the middle
        // pixel) the "top" is (0,-1,1.5) and the "right" is (1.5,-1,0) each pixel is a 1 by 1. the center of the top left pixel is\
        //center of pixels
        // [top row   [(-1,-1,1),(0,-1,1),(1,-1,1)],
        // middle row[(-1,-1,0),(0,-1,0),(1,-1,0)],
        // bottom row[(-1,-1,-1),(0,-1,-1),(1,-1,-1)]]
//        ArrayList<Ray> rays = new ArrayList<Ray>();
//        for(int i = 0; i < 3; i++){
//            for(int j = 0; j < 3; j++){
//                rays.add(camera.constructRay(3,3,j,i));
//            }
//
//        }

        //first test seems to only use the center pixel hence the above implementation (incomplete) has been abandoned and kept for further tests.
        //TC01: Sphere is after view plane expected is 2 intersection points
        assertEquals(2, sphere.findIntersections(camera.constructRay(3,3,1,1)).size(), "ERROR: TC01 failed simple case sphere after plane with one ray thru the middle of the plane");
    }

    @Test
    void CameraPlaneTests() {
        Camera camera = new Camera(new Point(4, 0, 0),new Vector(0,0,1), new Vector(-1,0,0))
                .setVPDistance(1).setVPSize(3, 3);

        //TC01: Plane is directly in front and orthogonal to the camera
        Plane plane = new Plane(new Point(1,0,0), new Point(1,1,0), new Point(1,0,1));
        assertEquals(9, viewPlaneIntersections(camera, plane),
                "TC01: Incorrect number of intersections, expected: 9");

        //TC02: Plane is slanted with slope 3:1
        plane = new Plane(new Point(1,0,0), new Point(1,1,0), new Point(2,0,3));
        assertEquals(9, viewPlaneIntersections(camera, plane),
                "TC02: Incorrect number of intersections, expected: 9");

        //TC03: Plane is slanted so that only the top and middle row rays intersect the plane at slope 1:2
        plane = new Plane(new Point(1,0,0), new Point(1,1,0), new Point(2,0,1));
        assertEquals(6, viewPlaneIntersections(camera, plane),
                "TC03: Incorrect number of intersections, expected: 6");

    }


}
