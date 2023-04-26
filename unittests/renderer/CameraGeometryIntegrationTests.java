package renderer;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
    private int viewGeometryIntersections(Camera camera, Geometry geometry) {
        int nX = 3, nY = 3;
        int total = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                List<Point> points=  geometry.findIntersections(camera.constructRay(nX,nY,j,i));
                if(points!=null)
                    total += points.size();
            }
        }
        return total;
    }

    /**
     * Function for testing Camera sphere integration and ray intersection points between them.
     */
    @Test
    void CameraSphereTests() {
        Camera camera = new Camera(new Point(0, 0, 0), new Vector(0,-1,0),
                new Vector(0,0,1)).setVPDistance(1).setVPSize(3, 3);;
        Sphere sphere = new Sphere(1, new Point(0, -3, 0));

        //TC01: Sphere is after view plane expected is 2 intersection points
        assertEquals(2, sphere.findIntersections(camera.constructRay(3,3,1,1)).size(),
                "ERROR: TC01 failed simple case sphere after plane with one ray thru the middle of the plane");

        //TC02: Sphere is in view plane and all pixels intersect twice equalling 18
        sphere = new Sphere(2.5,new Point(0,-3,0));
        assertEquals(18, viewGeometryIntersections(camera,sphere),
                "TC02: camera is before sphere, view plane is in sphere and all pixels go through two points." +
                        " expected intersections is 18");

        //TC03: Sphere is on view plane where expected amount of intersections is 10 (the corners don't intersect)
        sphere = new Sphere(2,new Point(0,-2.5,0));
        assertEquals(10, viewGeometryIntersections(camera,sphere),
                "TC03: sphere is on view plane and there are 10 intersections is not working correctly");

        //TC04: camera is inside sphere expected amount of intersections is 9
        sphere = new Sphere(1,new Point(0,0.5,0));
        assertEquals(9,viewGeometryIntersections(camera,sphere),
                "TC04: camera is inside sphere. Expected intersections is 9");

        //TC05: sphere is before camera
        sphere = new Sphere(1, new Point(0,3,0));
        assertEquals(0,viewGeometryIntersections(camera, sphere),
                "TC05: sphere is before the camera. Expected amount of intersections is 0");

        //TC06: camera is on sphere pointing out
        sphere = new Sphere(1, new Point(0,1,0));
        assertEquals(0,viewGeometryIntersections(camera, sphere),
                "TC06: Camera is on sphere pointing outward. Expected amount of intersections is 0");
    }

    /**
     * Function for testing Camera Plane integration and ray intersection points between them.
     */
    @Test
    void CameraPlaneTests() {
        Camera camera = new Camera(new Point(4, 0, 0), new Vector(-1,0,0),new Vector(0,0,1))
                .setVPDistance(1).setVPSize(3, 3);

        //TC01: Plane is directly in front and orthogonal to the camera
        Plane plane = new Plane(new Point(1,0,0), new Point(1,1,0), new Point(1,0,1));
        assertEquals(9, viewGeometryIntersections(camera, plane),
                "TC01: Incorrect number of intersections, expected: 9");

        //TC02: Plane is slanted with slope 3:1
        plane = new Plane(new Point(1,0,0), new Point(1,1,0), new Point(2,0,3));
        assertEquals(9, viewGeometryIntersections(camera, plane),
                "TC02: Incorrect number of intersections, expected: 9");

        //TC03: Plane is slanted so that only the top and middle row rays intersect the plane at slope 1:2
        plane = new Plane(new Point(1,0,0), new Point(1,1,0), new Point(2,0,1));
        int result = viewGeometryIntersections(camera, plane);
        assertEquals(6, viewGeometryIntersections(camera, plane),
                "TC03: Incorrect number of intersections, expected: 6");

    }

    /**
     * Function for testing Camera Triangle integration and ray intersection points between them.
     */
    @Test
    void CameraTriangleTests() {
        Camera camera = new Camera(new Point(4, 0, 0), new Vector(-1,0,0),new Vector(0,0,1))
                .setVPDistance(1).setVPSize(3, 3);

        //TCO1: Triangle only intersects 1 middle ray
        Triangle triangle = new Triangle(new Point(1,-0.5,-0.5), new Point(1,0.5,-0.5), new Point(1,0,0.5));
        assertEquals(1, viewGeometryIntersections(camera, triangle),
                "TC01: Incorrect number of intersections, expected: 1");

        //TC02: Triangle intersects middle and top middle rays
        triangle = new Triangle(new Point(1,-0.5,-0.5), new Point(1,0.5,-0.5), new Point(1,0,50));
        assertEquals(2, viewGeometryIntersections(camera, triangle),
                "TC02: Incorrect number of intersections, expected: 2");

    }

}
