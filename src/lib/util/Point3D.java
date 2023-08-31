package lib.util;

import lib.math.Matrix;
import lib.math.Vector;

import java.awt.*;

// Similar to Point-class but not same interface
public class Point3D implements Cloneable {
    // VALUES

    public double x;
    public double y;
    public double z;
    public double alpha;
    public double beta;
    public double gamma;
    public Point3D rotCenter;

// CONSTRUCTORS

    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3D(){
        this(0,0,0);
    }

    public Point3D(Vector point){
        setFromVector(point);
    }

    // METHODS

    public void setFromVector(Vector point){
        // never trust the user
        assert point.getHeight() != 3;

        // clone the internal 2d double array
        double[][] matrix = point.getColumnVectors();

        this.x = (int)matrix[0][0];
        this.y = (int)matrix[0][1];
        this.z = (int)matrix[0][2];
    }

    // Rotate this point according to a given Point
    // alpha is the x-axis angle
    // beta is the y-axis angle
    // gamma is the z-axis angle
    private void rotate_center(double alpha, double beta, double gamma){
        // This is the length from the center
        // Will be important to restore the length
        double lengthFromCenter = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));

        // Create Vector from this. We will mutate it until it is right
        Vector vThis = new Vector(this);

        // create all rotation matrices
        Matrix RX = Matrix.RX(alpha);
        Matrix RY = Matrix.RY(beta);
        Matrix RZ = Matrix.RZ(gamma);

        // rotate on x-axis
        vThis = Vector.multiply(RX, vThis);

        // rotate on y-axis
        vThis = Vector.multiply(RY, vThis);

        // rotate on z-axis
        vThis = Vector.multiply(RZ, vThis);

        // Update this point (we have to add the center again)
        Point3D newPoint = new Point3D(vThis);
        this.x = newPoint.x;
        this.y = newPoint.y;
        this.z = newPoint.z;

        // Ok because of float imprecision the length of the vectors converge to 0
        // Because of this we will restore the length to the center
        double newLength = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
        double fac = newLength != 0.0 ? lengthFromCenter / newLength : 0;
        this.x *= fac;
        this.y *= fac;
        this.z *= fac;

       // System.out.println(this);
    }

    // Rotate this point according to the point (0,0,0)
    private void rotate(double alpha, double beta, double gamma, Point3D center){
        this.sub(center);
        rotate_center(alpha, beta, gamma);
        this.add(center);
    }

    // getters and setters

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    public void setRotCenter(Point3D rotCenter) {
        this.rotCenter = rotCenter;
    }


    public Point3D xy(){
        return new Point3D(x, y, 0);
    }

    public Point3D xz(){
        return new Point3D(x, 0, z);
    }

    public Point3D yz(){
        return new Point3D(0, y, z);
    }

    public Point3D removeZTranslateToXY(){
        return new Point3D(this.x + this.z/2, this.y + this.z/2, 0);
    }

    public Point3D rotatedPoint(){
        Point3D temp = this.clone();
        temp.rotate(alpha, beta, gamma, rotCenter);
        return temp;
    }

    // Clone and toString

    @Override
    public Point3D clone() {
        try {
            return (Point3D) super.clone();
        } catch (CloneNotSupportedException ignore){
            return new Point3D(x,y,z);
        }
    }

    @Override
    public String toString(){
        return "(" + x + ", " + y + ", " + z + ")";
    }

    // STATICS

    public static Point3D add(Point3D a, Point3D b){
        return new Point3D(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    public static Point3D sub(Point3D a, Point3D b){
        return new Point3D(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    public static Point3D removeZTranslateToXY(Point3D in){
        return new Point3D(in.x + in.z/2, in.y + in.z/2, 0);
    }

    // OPERATIONS FROM STATICS

    public void add(Point3D b){
        this.x += b.x;
        this.y += b.y;
        this.z += b.z;
    }

    public void sub(Point3D b){
        this.x -= b.x;
        this.y -= b.y;
        this.z -= b.z;
    }

    // CONSTANTS

    public static final Point3D CENTER = new Point3D();
}
