package gl_2;

import glm.vec._2.Vec2;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;
import java.awt.*;

import static java.lang.StrictMath.abs;
import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;

public class GL_Operations_Lab2_Ex1 implements GLEventListener {
    private static Vec2 startingPoint = new Vec2(10, 10);
    private static Vec2 endPoint = new Vec2(200, 100);

//    private static Vec2 startingPoint = new Vec2(100, 100);
//    private static Vec2 endPoint = new Vec2(200, 10);

    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private float D;

    private float dx, dy, sx, sy, err, yi, xi, e2;

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, 1000, 1000);
        gl.glMatrixMode(gl.GL_PROJECTION_MATRIX);
        gl.glLoadIdentity();
        drawable.getGL().getGL2().glOrtho(0, 1000, 0, 1000, 0, 1);
        gl.glMatrixMode(gl.GL_MODELVIEW_MATRIX);

        D = startingPoint.y / startingPoint.x - 0.5f; //Calculate Distance "D"

        gl.glClearColor(1, 1, 1, 0);
        gl.glClear(GL_COLOR_BUFFER_BIT);
        //static field
        gl.glColor3f(0, 0, 0);
        gl.glBegin(gl.GL_POINTS);

        plotLine(startingPoint.x, startingPoint.y, endPoint.x, endPoint.y, gl);
//        plotLineErr(x0, y0, x1, y1, gl);
        gl.glEnd();

        gl.glBegin(GL2.GL_LINES);
        gl.glVertex2d(startingPoint.x, startingPoint.y + 20);
        gl.glVertex2d(endPoint.x, endPoint.y + 20);

        gl.glEnd();

        gl.glFlush();
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        //method body
    }

    @Override
    public void init(GLAutoDrawable arg0) {
        // method body
    }

    @Override
    public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
        // method body
    }

    public static void main(String[] args) {
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        GL_Operations_Lab2_Ex1 l = new GL_Operations_Lab2_Ex1();
        glcanvas.addGLEventListener(l);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        glcanvas.setSize(screenSize.width, screenSize.height);

        //creating frame
        final JFrame frame = new JFrame("lab2");
        //adding canvas to frame
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void plotLineLow(float x0, float y0, float x1, float y1, GL2 gl) {
        dx = x1 - x0;
        dy = y1 - y0;

        yi = 1;
        if (dy < 0) {
            yi = -1;
            dy = -dy;
        }
        D = 2 * dy - dx;
        float y = y0;

        for (int i = (int) x0; i < x1; i++) {
            gl.glVertex2i(i, (int) y);
            if (D > 0) {
                y = y + yi;
                D = D - 2 * dx;
            }
            D = D + 2 * dy;
        }
    }


    public void plotLineHigh(float x0, float y0, float x1, float y1, GL2 gl) {
        dx = x1 - x0;
        dy = y1 - y0;
        xi = 1;
        if (dx < 0) {
            xi = -1;
            dx = -dx;
        }
        D = 2 * dx - dy;
        float x = x0;

        for (int i = (int) y0; i < y1; i++) {
            gl.glVertex2i((int) x, (int) (float) i);
            if (D > 0) {
                x = x + xi;
                D = D - 2 * dy;
            }
            D = D + 2 * dx;
        }
    }

    public void plotLine(float x0, float y0, float x1, float y1, GL2 gl) {
        if (abs(y1 - y0) < abs(x1 - x0)) {
            if (x0 > x1) {
                plotLineLow(x1, y1, x0, y0, gl);

            } else plotLineLow(x0, y0, x1, y1, gl);

        } else {
            if (y0 > y1)
                plotLineHigh(x1, y1, x0, y0, gl);
            else
                plotLineHigh(x0, y0, x1, y1, gl);
        }
    }

    public void plotLineErr(float x0, float y0, float x1, float y1, GL2 gl) {
        dx = abs(x1 - x0);
        sx = x0 < x1 ? 1 : -1;
        dy = -abs(y1 - y0);
        sy = y0 < y1 ? 1 : -1;
        err = dx + dy;  /* error value e_xy */
        while (true) {  /* loop */
            gl.glVertex2i((int) x0, (int) y0);

            if (x0 == x1 && y0 == y1) break;
            e2 = 2 * err;
            if (e2 >= dy) {
                err += dy; /* e_xy+e_x > 0 */
                x0 += sx;
            }
            if (e2 <= dx) { /* e_xy+e_y < 0 */
                err += dx;
                y0 += sy;
            }
        }
    }


//    Mat4 camera(float translate, Vec2 rotate) {
//
//        Mat4 projection = Glm.perspective_(45.0f, 4.0f / 3.0f, 0.1f, 100.0f);
//        Mat4 view = new Mat4(1.0f).translate(new Vec3(0.0f, 0.0f, -translate));
//        view.rotate(rotate.y, new Vec3(-1.0f, 0.0f, 0.0f));
//        view.rotate(rotate.x, new Vec3(0.0f, 1.0f, 0.0f));
//        Mat4 model = new Mat4(1.0f).scale(new Vec3(0.5f));
//
//        return projection.mul(view).mul(model);
//    }

}