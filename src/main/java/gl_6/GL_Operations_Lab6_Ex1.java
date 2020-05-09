package gl_6;

import utility.iPoint2D;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;

import static java.lang.StrictMath.pow;
import static javax.media.opengl.GL.GL_LINE_STRIP;

public class GL_Operations_Lab6_Ex1 implements GLEventListener {
    // Chapter 7.3 brazier - Lab6
    // Chapter 7.3 brazier - Lab6
    // Chapter 7.3 brazier - Lab6

    @Override
    public void init(GLAutoDrawable drawable) {

    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void display(GLAutoDrawable drawable) {

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

    }

    public static void main(String[] args) {
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        GL_Operations_Lab6_Ex1 l = new GL_Operations_Lab6_Ex1();

        glcanvas.addGLEventListener(l);
        glcanvas.setSize(700, 700);

        final JFrame frame = new JFrame("lab6");
        //adding canvas to frame
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }


    static void compute_factors(int n, int[] factors) {
        int i, a = 1;
        for (i = 1; i <= n + 1; i++) {
            factors[i - 1] = a;
            a = a * (n - i + 1) / i;
        }
    }

    static void draw_bezier(iPoint2D[] points, int points_count, int divs, GL2 gl) {
        iPoint2D p = new iPoint2D();
        int n = points_count - 1;
//        int[] factors =  new int[](sizeof(int[]) points_count);
        int[] factors = new int[points_count];
        double t, b;
        float px, py;
        compute_factors(n, factors);
        gl.glBegin(GL_LINE_STRIP);
        for (int i = 0; i <= divs; i++) {
            t = 1.0 / divs * i;
            px = 0;
            py = 0;
//            p.setX(0);
//            p.setY(0);
            for (int j = 0; j <= n; j++) {
                if (j == 0) {
                    b = factors[j] * pow(1 - t, n);
                } else if (j == n) {
                    b = factors[j] * pow(t, n);
                } else {
                    b = factors[j] * pow(t, j) * pow(1 - t, n - j);
                }
                px += b * points[j].getX();
                py += b * points[j].getY();
            }
            gl.glVertex2f(px, py);
//            gl.glVertex2f((float)p.getX(), (float)p.getY());
        }
        gl.glEnd();
//        free(factors);
//        Runtime.getRuntime().gc();
    }
}
