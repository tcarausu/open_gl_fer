package utility;

import glm.vec._3.Vec3;

import java.util.ArrayList;

public class FaceTriangle {
    private Vec3 normal;
    private boolean visible = true;

    private double A;
    private double B;
    private double C;
    private double D;
    private Vec3 firstEdge;
    private Vec3 secondEdge;
    private Vec3 thirdEdge;

    public FaceTriangle() {
    }

    public FaceTriangle(double a, double b, double c) {
        A = a;
        B = b;
        C = c;
        normal = new Vec3(A, B, C);
    }

    public FaceTriangle(Vec3 firstEdge, Vec3 secondEdge, Vec3 thirdEdge) {
        this.firstEdge = firstEdge;
        this.secondEdge = secondEdge;
        this.thirdEdge = thirdEdge;
    }

    public ArrayList<Vec3> getEdgeVectors() {
        ArrayList<Vec3> vec3s = new ArrayList<>();
        vec3s.add(firstEdge);
        vec3s.add(secondEdge);
        vec3s.add(thirdEdge);
        return vec3s;
    }

    public void setEquation(double _a, double _b, double _c, double _d) {
        A = _a;
        B = _b;
        C = _c;
        D = _d;
        normal = new Vec3(A, B, C);
    }

    public Vec3 getFirstEdge() {
        return firstEdge;
    }

    public void setFirstEdge(Vec3 firstEdge) {
        this.firstEdge = firstEdge;
    }

    public Vec3 getSecondEdge() {
        return secondEdge;
    }

    public void setSecondEdge(Vec3 secondEdge) {
        this.secondEdge = secondEdge;
    }

    public Vec3 getThirdEdge() {
        return thirdEdge;
    }

    public void setThirdEdge(Vec3 thirdEdge) {
        this.thirdEdge = thirdEdge;
    }

    public Vec3 getNormal() {
        return normal;
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

    public void setVisibility(boolean b) {
        visible = b;
    }

    public boolean isVisible() {
        return visible;
    }
}
