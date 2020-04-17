package gl_2;

import glm.vec._2.Vec2;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;
import java.awt.*;

import static java.lang.StrictMath.abs;
import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;
import static utility.Bresenham_Utility.plotLine;

public class GL_Operations_Lab2_Ex1 implements GLEventListener {
    private static Vec2 startingPoint = new Vec2(10, 10);
    private static Vec2 endPoint = new Vec2(200, 100);

//    private static Vec2 startingPoint = new Vec2(100, 100);
//    private static Vec2 endPoint = new Vec2(200, 10);

    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static float D;

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