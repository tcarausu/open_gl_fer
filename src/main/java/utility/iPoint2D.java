package utility;


public class iPoint2D {
    double x;
    double y;

    public iPoint2D() {
    }

    public iPoint2D(double _x, double _y) {
        x = _x;
        y = _y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}


