package lab8;

import utility.MandaComplex;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;

import static javax.media.opengl.GL.GL_POINTS;
import static lab8.GL_Operations_Lab8_Ex.divergence_test;

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
        gl. glEnd();
    }

    public static void main(String[] args)  {

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
