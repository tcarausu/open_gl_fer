package lab8;

import utility.MandaComplex;

//import javax.media.opengl.*;
//import javax.media.opengl.awt.GLCanvas;
//import static javax.media.opengl.GL.GL_POINTS;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.*;

import static com.jogamp.opengl.GL.GL_POINTS;


public class GL_Operation_Lab8_book implements GLEventListener {
    private static double xmin = 0;
    private static double xmax = 599;
    private static double ymin = 0;
    private static double ymax = 599;
    private static double umin = -2;
    private static double umax = 1;
    private static double vmin = -1.2;
    private static double vmax = 1.2;

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
        GL2 gl2 = drawable.getGL().getGL2();

        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();

        drawable.getGL().getGL2().glOrtho(-5, 5, -5, 5, -5, 10);

        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glViewport(0, 0, width, height);
        renderScene(gl2);

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
            double next_re = Math.pow(z.getComplexReal(), 2) - Math.pow(z.getComplexImag(), 2) + c.getComplexReal();
            double next_im = 2 * z.getComplexReal() * z.getComplexImag() + c.getComplexImag();
            z.setRe(next_re);
            z.setIm(next_im);
            double module2 = Math.pow(z.getComplexReal(), 2) + Math.pow(z.getComplexImag(), 2);
            if (module2 > 4) return i;
        }
        return -1;
    }

    void renderScene(GL2 gl) {
        gl.glPointSize(1);
        gl.glBegin(GL_POINTS);
        for (double y = ymin; y <= ymax; y++) {
            for (double x = xmin; x <= xmax; x++) {
                MandaComplex c = new MandaComplex();

                c.setRe((x - xmin) / (xmax - xmin) * (umax - umin) + umin);
                c.setIm((y - ymin) / (ymax - ymin) * (vmax - vmin) + vmin);
                int n = divergence_test(c, 16);
                if (n == -1) {
                    gl.glColor3f(0.0f, 0.0f, 0.0f);
                } else {
                    gl.glColor3f(1.0f, 1.0f, 1.0f);
                }
                gl.glVertex2d(x, y);
            }
        }
        gl.glEnd();
    }

    public static void main(String[] args) {

        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        GL_Operation_Lab8_book l = new GL_Operation_Lab8_book();

        glcanvas.addGLEventListener(l);
        glcanvas.setSize(640, 800);


        final JFrame frame = new JFrame("lab7");
        //adding canvas to frame
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}
