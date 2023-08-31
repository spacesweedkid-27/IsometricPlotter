package lib.math;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Matrix implements Cloneable {
    protected final int weight;
    protected final int height;

    protected double[][] matrix;

    public Matrix(int weight, int height) {
        this.weight = weight;
        this.height = height;

        // Init as array of columns basically
        // This representation makes it easier to think of
        // it as a row of vectors if you display vectors from top to bottom
        // Column-major order
        this.matrix = new double[weight][height];
    }

    public Matrix(double[][] matrix){
        setMatrix(matrix);
        this.height = matrix[0].length;
        this.weight = matrix.length;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public void set(int x, int y, double val) {
        this.matrix[x][y] = val;
    }

    // Basically the same as matrix but in row-major order
    public double[][] getRowVectors() {
        double[][] result = new double[height][weight];

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < weight; j++) {
                result[i][j] = matrix[j][i];
            }
        }
        return result;
    }

    public double[][] getColumnVectors() {
        return this.matrix.clone();
    }

    // OPERATIONS

    public static Matrix addConstant(Matrix A, double b){
        Matrix temp = new Matrix(A.weight, A.height);

        for (int i = 0; i < A.weight; ++i) {
            for (int j = 0; j < A.height; ++j) {
                temp.set(i, j, A.matrix[i][j] + b);
            }
        }

        return temp;
    }

    public static Matrix mulConstant(Matrix A, double b){
        Matrix temp = new Matrix(A.weight, A.height);

        for (int i = 0; i < A.weight; ++i) {
            for (int j = 0; j < A.height; ++j) {
                temp.set(i, j, A.matrix[i][j] * b);
            }
        }

        return temp;
    }

    public static Matrix divConstant(Matrix A, double b){
        Matrix temp = new Matrix(A.weight, A.height);

        for (int i = 0; i < A.weight; ++i) {
            for (int j = 0; j < A.height; ++j) {
                temp.set(i, j, A.matrix[i][j] / b);
            }
        }

        return temp;
    }


    public static Matrix add(Matrix A, Matrix B){
        assert A.weight != B.weight || A.height != B.height;

        Matrix temp = new Matrix(A.weight, A.height);

        for (int i = 0; i < A.weight; ++i) {
            for (int j = 0; j < A.height; ++j) {
                temp.set(i, j, A.matrix[i][j] + B.matrix[i][j]);
            }
        }

        return temp;
    }

    public static Matrix sub(Matrix A, Matrix B){
        assert A.weight != B.weight || A.height != B.height;

        Matrix temp = new Matrix(A.weight, A.height);

        for (int i = 0; i < A.weight; ++i) {
            for (int j = 0; j < A.height; ++j) {
                temp.set(i, j, A.matrix[i][j] - B.matrix[i][j]);
            }
        }

        return temp;
    }

    public static Matrix mulComponent(Matrix A, Matrix B){
        assert A.weight != B.weight || A.height != B.height;

        Matrix temp = new Matrix(A.weight, A.height);

        for (int i = 0; i < A.weight; ++i) {
            for (int j = 0; j < A.height; ++j) {
                temp.set(i, j, A.matrix[i][j] * B.matrix[i][j]);
            }
        }

        return temp;
    }

    public static Matrix divComponent(Matrix A, Matrix B){
        assert A.weight != B.weight || A.height != B.height;

        Matrix temp = new Matrix(A.weight, A.height);

        for (int i = 0; i < A.weight; ++i) {
            for (int j = 0; j < A.height; ++j) {
                temp.set(i, j, A.matrix[i][j] / B.matrix[i][j]);
            }
        }

        return temp;
    }

    // Very slow implementation
    // Matches the by hand approach.
    public static Matrix multiply(Matrix A, Matrix B) {
        if (A.weight != B.height) {
            throw new ArithmeticException("Cannot multiply these matrices.");
        }

        Matrix result = new Matrix(B.weight, A.height);

        for (int i = 0; i < A.height; i++) {
            for (int j = 0; j < B.weight; j++) {
                result.set(j, i, Vector.scalar(A.getRowVectors()[i], B.getColumnVectors()[j]));
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        for (double[] row : getRowVectors()) {
            for (double val : row) {
                stringBuilder.append(val);
                stringBuilder.append(", ");
            }
            // Remove the last ", "
            stringBuilder.setLength(stringBuilder.length() - 2);
            stringBuilder.append("]\n[");
        }
        // Remove the last "\n["
        stringBuilder.setLength(stringBuilder.length() - 2);

        return stringBuilder.toString();
    }


    // Rotation matrices

    public static Matrix RX(double alpha){
        Matrix temp = new Matrix(3,3);
        temp.setMatrix(new double[][]{
                {1,0,0},
                {0, Math.cos(alpha), Math.sin(alpha)},
                {0, -Math.sin(alpha), Math.cos(alpha)}
        });
        return temp;
    }

    public static Matrix RY(double beta){
        Matrix temp = new Matrix(3,3);
        temp.setMatrix(new double[][]{
                {Math.cos(beta),0,-sin(beta)},
                {0, 1, 0},
                {sin(beta), 0, cos(beta)}
        });
        return temp;
    }

    public static Matrix RZ(double gamma){
        Matrix temp = new Matrix(3,3);
        temp.setMatrix(new double[][]{
                {cos(gamma),sin(gamma),0},
                {-sin(gamma), cos(gamma), 0},
                {0, 0, 1}
        });
        return temp;
    }

    // GETTERS AND SETTERS

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }


    // CLONER
    @Override
    public Matrix clone(){
        try {
            return (Matrix) super.clone();
        } catch (CloneNotSupportedException e) {
            return new Matrix(this.matrix);
        }
    }
}