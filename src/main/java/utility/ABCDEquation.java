package utility;

import glm.vec._3.Vec3;

public class ABCDEquation {

    private double A;
    private double B;
    private double C;
    private double D;
    private Vec3 normal;

    public ABCDEquation() {
    }

    public ABCDEquation(double a, double b, double c, double d) {
        A = a;
        B = b;
        C = c;
        D = d;
        normal = new Vec3(A,B,C);
    }

    public double getA() {
        return A;
    }

    public void setA(double a) {
        A = a;
    }

    public double getB() {
        return B;
    }

    public void setB(double b) {
        B = b;
    }

    public double getC() {
        return C;
    }

    public void setC(double c) {
        C = c;
    }

    public double getD() {
        return D;
    }

    public void setD(double d) {
        D = d;
    }

    public Vec3 getNormal() {
        return normal;
    }

    public void setNormal(Vec3 normal) {
        this.normal = normal;
    }
}
