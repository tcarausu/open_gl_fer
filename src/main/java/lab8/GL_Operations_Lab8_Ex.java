package lab8;

import utility.MandaComplex;
import utility.MandelbrotPlane;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;

public class GL_Operations_Lab8_Ex implements GLEventListener {

    private static final double x0 = 0;
    private static final double y0 = 0;
    private static double u0;
    private static double v0;

    @Override
    public void init(GLAutoDrawable drawable) {

    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void display(GLAutoDrawable drawable) {

        final GL2 gl = drawable.getGL().getGL2();

        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();


    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
//        FPSAnimator animator = new FPSAnimator(drawable, 120);
//        animator.start();
//
        GL2 gl2 = drawable.getGL().getGL2();

        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();

        drawable.getGL().getGL2().glOrtho(-5, 5, -5, 5, -5, 10);

        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glViewport(0, 0, width, height);

        MandelbrotPlane plane = new MandelbrotPlane(-1.5, 0.5, -1, 1, 16, 100);
        u0 = retrieveU0(plane.getUmax(), plane.getUmin(), width, x0);
        v0 = retrieveY0(plane.getVmax(), plane.getVmin(), height, y0);


        MandelbrotLab(width, height, u0, v0, 100, 16);

        String s = "s";
    }

    private static double functionZ(double zN, MandaComplex c) {
        return Math.pow(zN, 2) + c.getRe(); // the zn1 (formula) what is this C???
    }

    /**
     * for each pixel (Px, Py) on the screen do
     * x0 = scaled x coordinate of pixel (scaled to lie in the Mandelbrot X scale (-2.5, 1))
     * y0 = scaled y coordinate of pixel (scaled to lie in the Mandelbrot Y scale (-1, 1))
     * x := 0.0
     * y := 0.0
     * iteration := 0
     * max_iteration := 1000
     * while (x×x + y×y ≤ 2×2 AND iteration < max_iteration) do
     * xtemp := x×x - y×y + x0
     * y := 2×x×y + y0
     * x := xtemp
     * iteration := iteration + 1
     * <p>
     * color := palette[iteration]
     * plot(Px, Py, color)
     */
    public static void MandelbrotWiki(int width, int height) {

//        double x0 = scaled x coordinate of pixel (scaled to lie in the Mandelbrot X scale(-2.5, 1))
//        double y0 = scaled y coordinate of pixel (scaled to lie in the Mandelbrot Y scale(-1, 1))
        double x = 0.0;
        double y = 0.0;
        double iteration = 0;
        double max_iteration = 1000; // is this m?
        double xtemp;
        double eps; // what do i set here?

        while (((Math.pow(x, 2) + Math.pow(y, 2)) <= Math.pow(2, 2))
                && iteration < max_iteration) {
            xtemp = (Math.pow(x, 2) - Math.pow(y, 2)) + x0;
            y = 2 * x * y + y0;
            x = xtemp;
            iteration = iteration + 1;
        }

//        color = palette[iteration];
//        plot(Px, Py, color);


    }

    public static void MandelbrotLab(int width, int height, double u0, double v0, double eps, double m) {
        MandaComplex c = new MandaComplex(0, 0);

        for (int i = (int) x0; i < width; i++) {
            for (int j = (int) y0; j < height; j++) {
                double k = -1;
                double cReal = u0;
                double cImag = v0;
                double z0 = 0;
                double r = 0;
                while (r < eps && k < m) {
                    k += 1;
                    double zN = 0;// what do i have here??
                    double zN1 = functionZ(zN, c);
                    r = Math.sqrt(Math.pow(cReal, 2) + Math.pow(cImag, 2));//r = Math.sqrt(Math.pow(zreal, 2) + Math.pow(zimag, 2));
                    String s = "s";
                }
            }

        }


    }


    public static void JuliaFractal() {

    }

    public static void MandelbrotBook() {

    }


    /**
     * typedef struct {
     * double re;
     * double im;
     * }
     * complex;
     * public static int divergence_test (complex c,int limit){
     * complex z;
     * z.re = 0;
     * z.im = 0;
     * for (int i = 1; i <= limit; i++) {
     * double next_re = z.re * z.re - z.im * z.im + c.re;
     * double next_im = 2*z.re*z.im + c.im;
     * z.re = next_re;
     * z.im = next_im;
     * double modul2 = z.re*z.re +z.im*z.im;
     * if (modul2 > 4) return i;
     * }
     * return -1;
     * }
     */

    public static int divergence_test(MandaComplex c, int limit) {
        MandaComplex z = new MandaComplex(0, 0);
//        z.re = 0;
//        z.im = 0;
        for (int i = 1; i <= limit; i++) {
            double next_re = Math.pow(z.getRe(), 2) - Math.pow(z.getIm(), 2) + c.getRe();
            double next_im = 2 * z.getRe() * z.getIm() + c.getIm();
            z.setRe(next_re);
            z.setIm(next_im);
            double module2 = Math.pow(z.getRe(), 2) + Math.pow(z.getIm(), 2);
            if (module2 > 4) return i;
        }
        return -1;
    }

    public static void main(String[] args) {

        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);

        GL_Operations_Lab8_Ex l = new GL_Operations_Lab8_Ex();

        glcanvas.addGLEventListener(l);
        glcanvas.setSize(640, 800);

        final JFrame frame = new JFrame("lab8");
        //adding canvas to frame
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private static double retrieveU0(double umax, double umin, double xmax, double x0) {
        double a = (umax - umin) * x0;
        return a / xmax + umin;
    }

    private static double retrieveY0(double vmax, double vmin, double ymax, double y0) {
        double a = (vmax - vmin) * y0;
        return a / ymax + vmin;
    }

}

