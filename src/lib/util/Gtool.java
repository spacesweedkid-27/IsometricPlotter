package lib.util;

import java.awt.*;

public class Gtool {
    public Gtool(int width, int height){
        MIDDLE = new int[]{width/2, height/2};
    }

    // x-y Cords for the middle of the Graphics
    public final int[] MIDDLE;
    public void paintPixel(Graphics g, int x, int y){
        g.fillRect(x, y, 1, 1);
    }

    // Paint a pixel in the x,y,z format
    public void paintXYZ_pix(Graphics g, int x, int y, int z){
        paintPixel(g, MIDDLE[0] + x + z/2, MIDDLE[1] - y + z/2);
    }

    // Paint a line in the x,y,z format
    public void paintXYZ_lin(Graphics g, int x1, int y1, int z1, int x2, int y2, int z2){
        g.drawLine(MIDDLE[0] + x1 + z1/2, MIDDLE[1] - (y1 + z1/2), MIDDLE[0] + x2 + z2/2,MIDDLE[1] - (y2 + z2/2));
    }

    // Same as above but with Points and not coordinates
    public void paintXYZ_lin(Graphics g, Point3D p1, Point3D p2){
        g.drawLine((int) (MIDDLE[0] + p1.x + p1.z/2), (int) (MIDDLE[1] - (p1.y + p1.z/2)), (int) (MIDDLE[0] + p2.x + p2.z/2), (int) (MIDDLE[1] - (p2.y + p2.z/2)));
    }

    // Draw a rectangle that has p1 and p2 as points not directly connected
    // and p3 is the third point that has a mirrored counterpart p4 which we will calculate
    public void drawXYZ_rect(Graphics g, Point3D p1, Point3D p2, Point3D p3){
        // Calculate p4
        // Point3D objects are basically vectors, so we add the difference vector between p3 and p2 to p1
        Point3D p4 = Point3D.add(Point3D.sub(p2,p3), p1);
        // Now we just draw all lines
        paintXYZ_lin(g, p1, p3);
        paintXYZ_lin(g, p3, p2);
        paintXYZ_lin(g, p2, p4);
        paintXYZ_lin(g, p4, p1);
    }

    public void drawXYZ_triangle(Graphics g, Point3D p1, Point3D p2, Point3D p3){
        paintXYZ_lin(g, p1, p3);
        paintXYZ_lin(g, p3, p2);
        paintXYZ_lin(g, p2, p1);
    }

    public void fillXYZ_triangle(Graphics g, Point3D p1, Point3D p2, Point3D p3){
        p1 = p1.removeZTranslateToXY();
        p2 = p2.removeZTranslateToXY();
        p3 = p3.removeZTranslateToXY();
        g.fillPolygon(new int[]{MIDDLE[0] + (int) p1.x, MIDDLE[0] +  (int) p2.x, MIDDLE[0] +  (int) p3.x}, new int[]{MIDDLE[1] - (int) p1.y, MIDDLE[1] - (int) p2.y, MIDDLE[1] - (int) p3.y}, 3);
    }

    public void drawlXYZ_polygon(Graphics g, Point3D[] points){
        Point3D[] translated = new Point3D[points.length];

        int[] xs = new int[points.length];
        int[] ys = new int[points.length];

        for (int i = 0; i < points.length; ++i){
            translated[i] = points[i].removeZTranslateToXY();
            xs[i] = (int)translated[i].x + MIDDLE[0];
            ys[i] = (int)translated[i].y - MIDDLE[1];
        }

        g.drawPolygon(xs, ys, points.length);
    }


    public void fillXYZ_polygon(Graphics g, Point3D[] points){
        Point3D[] translated = new Point3D[points.length];

        int[] xs = new int[points.length];
        int[] ys = new int[points.length];

        for (int i = 0; i < points.length; ++i){
            translated[i] = points[i].removeZTranslateToXY();
            xs[i] = (int)translated[i].x + MIDDLE[0];
            ys[i] = (int)translated[i].y - MIDDLE[1];
        }

        g.fillPolygon(xs, ys, points.length);
    }

    public void drawXYZ_sphere(Graphics g, Point3D point, double radius){
        Point3D translated = point.removeZTranslateToXY();
        g.drawOval((int)(MIDDLE[0] + translated.x - radius), (int)(MIDDLE[1] - translated.y - radius), (int)(2 * radius), (int)(2 * radius));
    }

    public void fillXYZ_sphere(Graphics g, Point3D point, double radius){
        Point3D translated = point.removeZTranslateToXY();
        g.fillOval((int)(MIDDLE[0] + translated.x - radius), (int)(MIDDLE[1] - translated.y - radius), (int)(2 * radius), (int)(2 * radius));
    }


}
