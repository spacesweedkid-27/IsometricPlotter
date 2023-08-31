package lib.shapes;

import lib.math.Vector;
import lib.util.Gtool;
import lib.util.Point3D;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FromStl implements Shape{
    private final ArrayList<Triangle> triangles;

    public final String name;

    private Point3D center;

    public FromStl(){
        this("monke.stl");
    }

    public FromStl(String path){
        ArrayList<Triangle> oldArray = new ArrayList<>();

        Scanner file = null;
        try {
            file = new Scanner(new File(path));
        } catch (FileNotFoundException ignore){
            System.err.println("Stl-file not found: \"" + path + "\"");
            System.exit(-1);
        }
        // Ignore first "solid"
        file.next("solid");
        file.skip(" ");
        // Set the name to what's after that
        name = file.nextLine();
        // Also skip this
        //System.out.println(name);
        file.nextLine();
        while (file.hasNext()){
            if (!file.hasNext("vertex")){
                file.next();
                continue;
            }
            file.next("vertex");
            file.skip(" ");
            // Now we only get 3 double numbers seperated by " "
            // these are x,y and z coordinates for points.
            String[] splitted1 = file.nextLine().split(" ");
            file.next("vertex");
            file.skip(" ");
            String[] splitted2 = file.nextLine().split(" ");
            file.next("vertex");
            file.skip(" ");
            String[] splitted3 = file.nextLine().split(" ");

            double scale = 300;

            oldArray.add(new Triangle(
                    new Point3D(scale * Double.parseDouble(splitted1[0]),scale *  Double.parseDouble(splitted1[1]),scale *  Double.parseDouble(splitted1[2])),
                    new Point3D(scale * Double.parseDouble(splitted2[0]), scale * Double.parseDouble(splitted2[1]), scale * Double.parseDouble(splitted2[2])),
                    new Point3D(scale * Double.parseDouble(splitted3[0]), scale * Double.parseDouble(splitted3[1]), scale * Double.parseDouble(splitted3[2]))));
        }



        // calculate all center average of triangles and name this the new center

        Vector temp = Vector.NULL.clone();
        for (Triangle tri: oldArray) {
            // Add center of tri
            tri.configureCenter();
            temp = new Vector(Vector.add(temp, new Vector(tri.getCenter())));
        }

        // Devide by number of triangles to get average
        temp = new Vector(Vector.divConstant(temp, oldArray.size()));
        final Point3D converted = new Point3D(temp);
        center = converted;
        //System.out.println(converted);

        // Now copy the center of the triangles

        for (Triangle tri: oldArray) {
            tri.setCenter(converted);

        }

        triangles = oldArray;
    }

    @Override
    public void paintThis(Graphics g, Gtool graphicsBuffer) {
        //System.out.println(triangles.size());
        for (Triangle tri: triangles) {
            //System.out.println(tri);
            tri.paintThis(g, graphicsBuffer);
        }
    }

    @Override
    public void newCords() {
        for (Triangle tri: triangles) {
            tri.newCords();
        }
    }

    @Override
    public void setColor(Color c) {
        for (Triangle tri: triangles) {
            tri.setColor(c);
        }
    }

    @Override
    public void rotate(double dalpha, double dbeta, double dgamma, Point3D center){
        // Ignore center
        for (Triangle tri: triangles) {
            tri.rotate(dalpha,dbeta,dgamma,tri.getCenter());
        }
    }
}
