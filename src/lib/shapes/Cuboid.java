package lib.shapes;

import lib.util.Gtool;
import lib.util.Point3D;

import java.awt.*;

public class Cuboid implements Shape {
    // This point is the point that is displayed left down.
    // For example a cube that has its location at (0,0,0)
    // will have corners going only (+x, 0, 0), (0, +y, 0) or (0, 0, +z).
    Point3D location;
    // This point is basically the difference between the maximal and minimal values of the three axis.
    Point3D size;

    private Point3D center;
    private Point3D right;
    private Point3D up;
    private Point3D away;
    private Point3D upRight;
    private Point3D rightFar;
    private Point3D upFar;
    private Point3D upRightFar;
    private Color color = Color.BLUE;

    private void configurePoints(){
        right = location.clone();
        right.x += size.x;

        up = location.clone();
        up.y += size.y;

        away = location.clone();
        away.z += size.z;
        upRight = location.clone();
        upRight.x += size.x;
        upRight.y += size.y;

        rightFar = location.clone();
        rightFar.x += size.x;
        rightFar.z += size.z;
        upFar = location.clone();
        upFar.y += size.y;
        upFar.z += size.z;
        upRightFar = upRight.clone();
        upRightFar.z += size.z;

        center = new Point3D(location.x + size.x/2, location.y + size.y/2, location.z + size.z/2);

    }

    public Cuboid(){
        this(Point3D.CENTER, Point3D.CENTER);
    }

    public Cuboid(Point3D location, Point3D size){
        this.location = location;
        this.size = size;

        configurePoints();
    }

    public Cuboid(int x, int y, int z, int weight, int height, int length){
        this(new Point3D(x,y,z), new Point3D(weight, height, length));
    }

    private final static short var = 40;

    @Override
    public void newCords(){
        right.      gamma+=Math.PI / var;
        up.         gamma+=Math.PI / var;
        location.   gamma+=Math.PI / var;
        away.       gamma+=Math.PI / var;
        upRight.    gamma+=Math.PI / var;
        upRightFar. gamma+=Math.PI / var;
        rightFar.   gamma+=Math.PI / var;
        upFar.      gamma+=Math.PI / var;
    }

    @Override
    public void setColor(Color c) {
        this.color = c;
    }

    @Override
    public void rotate(double dalpha, double dbeta, double dgamma, Point3D center) {
        right.      gamma+=dalpha;
        up.         gamma+=dalpha;
        location.   gamma+=dalpha;
        away.       gamma+=dalpha;
        upRight.    gamma+=dalpha;
        upRightFar. gamma+=dalpha;
        rightFar.   gamma+=dalpha;
        upFar.      gamma+=dalpha;

        right.      gamma+=dbeta;
        up.         gamma+=dbeta;
        location.   gamma+=dbeta;
        away.       gamma+=dbeta;
        upRight.    gamma+=dbeta;
        upRightFar. gamma+=dbeta;
        rightFar.   gamma+=dbeta;
        upFar.      gamma+=dbeta;

        right.      gamma+=dgamma;
        up.         gamma+=dgamma;
        location.   gamma+=dgamma;
        away.       gamma+=dgamma;
        upRight.    gamma+=dgamma;
        upRightFar. gamma+=dgamma;
        rightFar.   gamma+=dgamma;
        upFar.      gamma+=dgamma;
    }

    @Override
    public void paintThis(Graphics g, Gtool gtool) {
        // Draw
        gtool.paintXYZ_lin(g, location, right);
        gtool.paintXYZ_lin(g, location, up);
        gtool.paintXYZ_lin(g, location, away);
        gtool.paintXYZ_lin(g, up, upRight);
        gtool.paintXYZ_lin(g, right, rightFar);
        gtool.paintXYZ_lin(g, right, upRight);
        gtool.paintXYZ_lin(g, away, rightFar);
        gtool.paintXYZ_lin(g, away, upFar);
        gtool.paintXYZ_lin(g, rightFar, upRightFar);
        gtool.paintXYZ_lin(g, upRight, upRightFar);
        gtool.paintXYZ_lin(g, upFar, upRightFar);
        gtool.paintXYZ_lin(g, up, upFar);
    }
}
