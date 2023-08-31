package lib.shapes;

import lib.util.Point3D;

public class Cube extends Cuboid{
    public Cube(){
        super();
    }

    public Cube(Point3D location, int scale){
        super(location, new Point3D(scale, scale, scale));
    }

    public Cube(int x, int y, int z, int scale){
        super(new Point3D(x,y,z), new Point3D(scale, scale, scale));
    }
}
