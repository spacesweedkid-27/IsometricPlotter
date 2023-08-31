package lib.shapes;

import lib.util.Gtool;
import lib.util.Point3D;

import java.awt.*;

public interface Shape {
    void paintThis(Graphics g, Gtool graphicsBuffer);

    void newCords();

    void setColor(Color c);

    void rotate(double dalpha, double dbeta, double dgamma, Point3D center);
}
