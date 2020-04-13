package utility;

public class iEdge2D {
    double a;
    double b;
    double c;

    public iEdge2D() {
    }

    public iEdge2D(double _a, double _b, double _c) {
        a = _a;
        b = _b;
        c = _c;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "a=" + a + ", b=" + b + ", c=" + c;
    }
}