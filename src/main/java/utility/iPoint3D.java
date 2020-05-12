package utility;


public class iPoint3D {
    double x;
    double y;
    double z;

    public iPoint3D() {
    }

    public iPoint3D(double _x, double _y, double _z) {
        x = _x;
        y = _y;
        z = _z;
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

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y +", " + z + ")";
    }
}


