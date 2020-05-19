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

    public double getComplexReal() {
        return re;
    }

    public void setRe(double re) {
        this.re = re;
    }

    public double getComplexImag() {
        return im;
    }

    public void setIm(double im) {
        this.im = im;
    }

    public static MandaComplex sum(MandaComplex c1, MandaComplex c2)
    {
        //creating a temporary complex number to hold the sum of two numbers
        MandaComplex temp = new MandaComplex(0, 0);

        temp.re = c1.re + c2.re;
        temp.im = c1.im + c2.im;

        //returning the output complex number
        return temp;
    }

}
