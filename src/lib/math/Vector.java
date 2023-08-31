package lib.math;

import lib.util.Point3D;

public class Vector extends Matrix{
    public Vector(int height) {
        super(1, height);
    }

    public Vector(double[] vector){
        super(1, vector.length);
        this.matrix[0] = vector;
    }

    // Create a vector pointing to a point
    public Vector(Point3D point){
        super(1, 3);
        this.matrix[0][0] = point.x;
        this.matrix[0][1] = point.y;
        this.matrix[0][2] = point.z;
    }

    public Vector(Matrix vector){
        super(1, vector.height);
        assert vector.weight > 1;
        this.matrix[0] = vector.matrix[0];
    }


    public static double scalar(Vector A, Vector B){
        double result = 0;
        for (double a: A.matrix[0]) {
            for (double b: B.matrix[0]) {
                result += a * b;
            }
        }
        return result;
    }

    public static double scalar(double[] A, double[] B){

        // We have to because we don't trust the user
        assert A.length != B.length;

        double result = 0;
        for (int i = 0; i < A.length; i++) {
            result += A[i] * B[i];
        }
        return result;
    }


    public static Vector multiply(Matrix A, Matrix B){
        return new Vector(Matrix.multiply(A,B));
    }

    @Override
    public Vector clone(){
        return (Vector) super.clone();
    }


    public static final Vector NULL = new Vector(new double[]{0,0,0});
}
