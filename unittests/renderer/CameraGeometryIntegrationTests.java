package renderer;

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


}
