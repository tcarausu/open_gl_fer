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
    private static double centerX;
    private static double centerY;

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
//        gl.glLoadIdentity();
        MandelbrotLab(500, 500, 100, 16, gl);
//        JuliaSetLab(500, 500, 100, 16, gl);

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl2 = drawable.getGL().getGL2();

        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();

        drawable.getGL().getGL2().glOrtho(0, width - 1, 0, height - 1, 0, 1);

        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glViewport(0, 0, width, height);

        centerX = width >> 1;
        centerY = height >> 1;
        MandelbrotLab(width, height, 2, 16, gl2);
//        JuliaSetLab(width, height, 2, 16, gl2);

        String s = "s";
    }

    private static double functionZnSquare(double zN, MandaComplex c) {
        return Math.pow(zN, 2) + c.getComplexReal(); // the zn1 (formula) what is this C???
    }

    public static void MandelbrotLab(int width, int height, double eps, double m, GL2 gl) {
        MandelbrotPlane plane = new MandelbrotPlane(-1.5, 0.5, -1, 1, 16, 100);
        gl.glPointSize(1.0f);

        MandaComplex c = new MandaComplex(0, 0);
        //Color based on pixel
        gl.glBegin(GL.GL_POINTS);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                u0 = retrieveU0(plane.getUmax(), plane.getUmin(), width, i);
                v0 = retrieveY0(plane.getVmax(), plane.getVmin(), height, j);
                //System.out.println(u0 + " " + v0);
                float k = -1;
                double cReal = u0;
                double cImag = v0;
                double zReal = 0;
                double zImag = 0;

                double r = 0;
                while (r < eps && k < m) {
                    k += 1;
                    double zRealNew = cReal +Math.pow(zReal, 2) - Math.pow(zImag, 2) ;
                    double zImagNew = cImag + zReal * zImag * 2;
                    zReal = zRealNew;
                    zImag = zImagNew;
                    r = Math.sqrt(Math.pow(zReal, 2) + Math.pow(zImag, 2));

                }
                System.out.println(k);
                gl.glColor3f(k / 16, k / 16, k / 16);
                //gl.glColor3f(255,,0);
                gl.glVertex2i(i, j); //    x0,y0,k ? = i,j,k ?
            }
        }
        gl.glEnd();


    }
    public static void JuliaSetLab(int width, int height, double eps, double m, GL2 gl) {
        MandelbrotPlane plane = new MandelbrotPlane(-1, 1, -1.2, 1.2, 16, 100);
        gl.glPointSize(1.0f);

        MandaComplex c = new MandaComplex(0.32, 0.043);
        //Color based on pixel
        gl.glBegin(GL.GL_POINTS);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                u0 = retrieveU0(plane.getUmax(), plane.getUmin(), width, i);
                v0 = retrieveY0(plane.getVmax(), plane.getVmin(), height, j);
                //System.out.println(u0 + " " + v0);
                float k = -1;
                double zReal = u0;
                double zImag = v0;

                double cReal = c.getComplexReal();
                double cImag = c.getComplexImag();

                double r = 0;
                while (r < eps && k < m) {
                    k += 1;
                    double zRealNew = cReal +Math.pow(zReal, 2) - Math.pow(zImag, 2) ;
                    double zImagNew = cImag + zReal * zImag * 2;
                    zReal = zRealNew;
                    zImag = zImagNew;
                    r = Math.sqrt(Math.pow(zReal, 2) + Math.pow(zImag, 2));

                }
                System.out.println(k);
                gl.glColor3f(k / 16, k / 16, k / 16);
                //gl.glColor3f(255,,0);
                gl.glVertex2i(i, j); //    x0,y0,k ? = i,j,k ?
            }
        }
        gl.glEnd();


    }


    public static void JuliaFractal() {

    }

    public static void MandelbrotBook() {

    }

    public static void main(String[] args) {

        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);

        GL_Operations_Lab8_Ex l = new GL_Operations_Lab8_Ex();

        glcanvas.addGLEventListener(l);
        glcanvas.setSize(500, 500);

        final JFrame frame = new JFrame("lab8");
        //adding canvas to frame
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        centerX = screenSize.getWidth() / 2;
//        centerY = screenSize.getHeight() / 2;
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

