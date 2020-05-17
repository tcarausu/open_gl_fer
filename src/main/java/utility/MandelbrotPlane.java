package utility;

public class MandelbrotPlane {

    double umin;
    double umax;
    double vmin;
    double vmax;
    double m;
    double eps;

    public MandelbrotPlane(double _umin, double _umax, double _vmin, double _vmax, double _m, double _eps) {
        umin = _umin;
        umax = _umax;
        vmin = _vmin;
        vmax = _vmax;
        m = _m;
        eps = _eps;
    }

    public double getUmin() {
        return umin;
    }

    public void setUmin(double umin) {
        this.umin = umin;
    }

    public double getUmax() {
        return umax;
    }

    public void setUmax(double umax) {
        this.umax = umax;
    }

    public double getVmin() {
        return vmin;
    }

    public void setVmin(double vmin) {
        this.vmin = vmin;
    }

    public double getVmax() {
        return vmax;
    }

    public void setVmax(double vmax) {
        this.vmax = vmax;
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
    }

    public double getEps() {
        return eps;
    }

    public void setEps(double eps) {
        this.eps = eps;
    }
}
