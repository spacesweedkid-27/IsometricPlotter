package lib.shapes;

import lib.util.Gtool;
import lib.util.Point3D;

import java.awt.*;

public class Sphere2 implements Shape{
    private final Point3D middle;
    private final double radius;
    private Color color = Color.BLUE;

    public Sphere2(){
        middle = new Point3D();
        radius = 0;
    }

    public Sphere2(Point3D middle, double radius){
        this.middle = middle;
        this.radius = radius;
    }

    @Override
    public void paintThis(Graphics g, Gtool graphicsBuffer) {
        Color old = g.getColor();
        graphicsBuffer.fillXYZ_sphere(g, middle, radius);
        g.setColor(this.color);
        graphicsBuffer.drawXYZ_sphere(g, middle, radius);
        g.setColor(old);
    }

    @Override
    public void newCords() {
        // Do nothing for now
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
