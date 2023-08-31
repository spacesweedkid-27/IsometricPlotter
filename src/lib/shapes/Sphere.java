package lib.shapes;

import lib.util.Point3D;

public class Sphere extends Ovaloid{
    public Sphere(){
        super();
    }
    public Sphere(Point3D location, double radius){
        super(location, new Point3D(2*radius, 2*radius, 2*radius));
    }

    public Sphere(int x, int y, int z, double radius){
        this(new Point3D(x,y,z), radius);
    }
}
