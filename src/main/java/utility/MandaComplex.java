package utility;

public class MandaComplex {
//    typedef struct {
//        double re;
//        double im;
//    }  complex;

    double re;
    double im;

    public MandaComplex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public MandaComplex() {
    }

    public double getRe() {
        return re;
    }

    public void setRe(double re) {
        this.re = re;
    }

    public double getIm() {
        return im;
    }

    public void setIm(double im) {
        this.im = im;
    }
}
