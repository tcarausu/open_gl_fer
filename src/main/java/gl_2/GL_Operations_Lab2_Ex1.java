package gl_2;

import glm.vec._2.Vec2;

import javax.media.nativewindow.util.Point;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;

import java.awt.*;

import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;

public class GL_Operations_Lab2_Ex1 implements GLEventListener {
    private static Vec2 startingPoint = new Vec2(10, 10);
    private static Vec2 endPoint = new Vec2(200, 100);
    private static Point x_yPoint = new Point();
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        float x0 = endPoint.x - startingPoint.x;
        float y0 = endPoint.y - startingPoint.y;
        float D = y0 / x0 - 0.5f; //Calculate Distance "D"

        float x = startingPoint.x;
        float y = startingPoint.y;

        gl.glClear(GL_COLOR_BUFFER_BIT);
        gl.glBegin(GL2.GL_LINES);//static field

        for (int i = 0; i < x0; i++) {
            x_yPoint.setX((int) x);
            x_yPoint.setY((int) y);
            Point usable = x_yPoint;
            if (D > 0) {
                y = y + 1;
                D = D - 1;
                System.out.println("D=" + D + "\n");
            }

            x = x + 1;
            D = D + y0 / x0;

            System.out.println("D=" + D + "\n");
        }

//        gl.glVertex2d(startingPoint.x, startingPoint.y + 20);
//        gl.glVertex2d(endPoint.x, endPoint.y + 20);

        gl.glVertex2d(startingPoint.x, startingPoint.y + 20);
        gl.glVertex2d(endPoint.x, endPoint.y + 20);

        gl.glEnd();

//        9. Comparison with the LINE command.
//                Draw the line specified by the following coordinates (x1 y1+20) and (x2 y2+20).

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
}
