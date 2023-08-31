package lib.util;

import lib.shapes.Shape;
import lib.shapes.Triangle;
import lib.util.Gtool;

import java.awt.*;
import java.util.ArrayList;

public class ShapeQueue {
    public ShapeQueue(){
        queue = new ArrayList<>();
    }

    public ShapeQueue(Shape shape){
        queue = new ArrayList<>();
        queue.add(shape);
    }

    public ShapeQueue(ShapeQueue queue){
        this.queue = new ArrayList<>();
        addFromQueue(queue);
    }

    private final ArrayList<lib.shapes.Shape> queue;

    public void add(lib.shapes.Shape shape){
        queue.add(shape);
    }
    public void remove(lib.shapes.Shape shape){
        queue.remove(shape);
    }
    public Shape get(int index){
        return queue.get(index);
    }

    public void paint(Graphics g, Gtool gTool){
        for (Shape shape: queue) {
            shape.paintThis(g, gTool);
        }
    }

    public void newCords(){
        for (Shape shape: queue) {
            shape.newCords();
        }
    }

    public void rotate(double dalpha, double dbeta, double dgamma, Point3D center){
        for (Shape shape: queue) {
            shape.rotate(dalpha, dbeta, dgamma, center);
        }
    }

    public void addFromQueue(ShapeQueue queue){
        addAllShapesToQueue(queue, this);
    }


    public static void addAllShapesToQueue(ShapeQueue from, ShapeQueue to){
        for (Shape shape: from.queue) {
            to.add(shape);
        }
    }



    public static final ShapeQueue SMILE;

    static {
        SMILE = new ShapeQueue();

        SMILE.add(new Triangle(new Point3D[]{
                new Point3D(0,0,0), new Point3D(-100,50,0), new Point3D(0,25,0)
        }));
        SMILE.add(new Triangle(new Point3D[]{
                new Point3D(0,0,0), new Point3D(0,25,0), new Point3D(100,50,0)
        }));

        SMILE.add(new Triangle(new Point3D[]{
                new Point3D(-100,100,0), new Point3D(-75,125,0), new Point3D(-50,100,0)
        }));
        SMILE.add(new Triangle(new Point3D[]{
                new Point3D(100,100,0), new Point3D(+75,125,0), new Point3D(50,100,0)
        }));

        SMILE.get(0).setColor(Color.RED);
        SMILE.get(1).setColor(Color.RED);
    }

}
