package lib.shapes;

import lib.math.Matrix;
import lib.math.Vector;
import lib.util.Gtool;
import lib.util.Point3D;

import java.awt.*;

public class Triangle implements Shape, Cloneable{

    // Both pp for sexy usage
    Color color = new Color(255,0,0,50);
    Point3D[] points = new Point3D[3];
    private Point3D center;

    public Triangle(){
        // Do nothing
    }

    public Triangle(Point3D[] points){
        this.points = points;
    }

    public Triangle(Point3D A, Point3D B, Point3D C){
        points[0] = A;
        points[1] = B;
        points[2] = C;
    }

    public void configureCenter(){
        // Add all points together and convert to vector
        Vector temp = new Vector(Point3D.add(points[0], Point3D.add(points[1], points[2])));

        // Devide through 3 and "cast" to vector
        temp = new Vector(Matrix.divConstant(temp, 3));

        // Cast to Point3D
        center = new Point3D(temp);
    }

    public Point3D getCenter(){
        return center;
    }

    @Override
    public void setColor(Color color){
        this.color = color;
    }

    @Override
    public void rotate(double dalpha, double dbeta, double dgamma, Point3D center) {
        if (!center.equals(this.center)){
            setCenter(center);
        }

        points[0].alpha += dalpha;
        points[1].alpha += dalpha;
        points[2].alpha += dalpha;

        points[0].beta += dbeta;
        points[1].beta += dbeta;
        points[2].beta += dbeta;

        points[0].gamma += dgamma;
        points[1].gamma += dgamma;
        points[2].gamma += dgamma;
    }

    @Override
    public void paintThis(Graphics g, Gtool graphicsBuffer) {
        points[0].setRotCenter(getCenter());
        points[1].setRotCenter(getCenter());
        points[2].setRotCenter(getCenter());

        Point3D[] rotated = points.clone();
        for (int i = 0; i < points.length; ++i)
            rotated[i] = rotated[i].rotatedPoint();

        Color old = g.getColor();
        g.setColor(this.color);
        // FILL TRIANGLE
        graphicsBuffer.fillXYZ_triangle(g, rotated[0], rotated[1], rotated[2]);
        g.setColor(old);
        // DRAW OUTLINE
        graphicsBuffer.drawXYZ_triangle(g, rotated[0], rotated[1], rotated[2]);
    }

    @Override
    public void newCords() {
        for (Point3D point: this.points) {
            // point.gamma += Math.toRadians((0.5 * 360.0 / 160.0));
            // point.alpha += 0.8 * Math.toRadians((0.5 * 360.0 / 160.0));
        }
    }

    @Override
    public Triangle clone(){
        try {
            return (Triangle) super.clone();
        } catch (CloneNotSupportedException ignore){
            return new Triangle(this.points[0].clone(), this.points[1].clone(), this.points[2].clone());
        }
    }

    @Override
    public String toString(){
        return "T(" +points[0] + ", " + points[1]+ ", " + points[2] + ")";
    }

    public void setCenter(Point3D center) {
        this.center = center;
    }
}
