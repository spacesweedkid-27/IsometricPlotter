package lib.shapes;

import lib.shapes.Shape;
import lib.util.Gtool;
import lib.util.Point3D;

import java.awt.*;

public class Ovaloid implements Shape {
    // Not like in Cuboid, this time the location is the middle point
    Point3D location;
    // This point is basically the difference between the maximal and minimal values of the three axis.
    Point3D size;

    private Point3D down;
    private Point3D up;
    private Point3D left;
    private Point3D right;
    private Point3D front;
    private Point3D far;

    private Color color;
    private void configurePoints(){
        down = location.clone();
        down.y -= size.y / 2;
        up = location.clone();
        up.y += size.y / 2;
        left = location.clone();
        left.x -= size.x / 2;
        right = location.clone();
        right.x += size.x / 2;
        front = location.clone();
        front.z -= size.z / 2;
        far = location.clone();
        far.z += size.z / 2;
    }

    public Ovaloid(){
        this.location = new Point3D();
        this.size = new Point3D();

        configurePoints();
    }

    public Ovaloid(Point3D location, Point3D size){
        this.location = location;
        this.size = size;

        configurePoints();
    }

    public Ovaloid(int x, int y, int z, int weight, int height, int length){
        this.location = new Point3D(x,y,z);
        this.size = new Point3D(weight, height, length);

        System.out.println("init an Ovaloid with: " + this.location + " " + this.size);

        configurePoints();
    }
    // Draws a very low resolution polygon version
    @Override
    public void paintThis(Graphics g, Gtool graphicsBuffer) {
        Point3D[] rotClones = new Point3D[]{down.rotatedPoint(), front.rotatedPoint(), right.rotatedPoint(), left.rotatedPoint(), far.rotatedPoint(), up.rotatedPoint()};

        graphicsBuffer.drawXYZ_triangle(g, rotClones[0], rotClones[1], rotClones[2]);
        graphicsBuffer.drawXYZ_triangle(g, rotClones[0], rotClones[1], rotClones[3]);
        graphicsBuffer.drawXYZ_triangle(g, rotClones[0], rotClones[4], rotClones[2]);
        graphicsBuffer.drawXYZ_triangle(g, rotClones[0], rotClones[4], rotClones[3]);
        graphicsBuffer.drawXYZ_triangle(g, rotClones[5], rotClones[1], rotClones[2]);
        graphicsBuffer.drawXYZ_triangle(g, rotClones[5], rotClones[1], rotClones[3]);
        graphicsBuffer.drawXYZ_triangle(g, rotClones[5], rotClones[4], rotClones[2]);
        graphicsBuffer.drawXYZ_triangle(g, rotClones[5], rotClones[4], rotClones[3]);
    }

    @Override
    public void newCords() {
        // rotate all points by a bit
        down.gamma += Math.PI / 31;
        up.gamma += Math.PI / 31;
        left.gamma += Math.PI / 31;
        right.gamma += Math.PI / 31;
        front.gamma += Math.PI / 31;
        far.gamma += Math.PI / 31;
    }

    @Override
    public void setColor(Color c) {
        this.color = c;
    }

    // TODO
    @Override
    public void rotate(double dalpha, double dbeta, double dgamma, Point3D center) {

    }
}
