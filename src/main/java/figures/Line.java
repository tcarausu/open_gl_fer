package figures;

import glm.Glm;
import glm.mat._4.Mat4;
import glm.vec._2.Vec2;
import glm.vec._3.Vec3;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.swing.*;
import java.awt.*;

public class Line implements GLEventListener {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

//        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
//        gl.glLoadIdentity();
//        gl.glOrtho(0, screenSize.width-1, screenSize.height-1, 0,0,1);
//        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);


//        gl.glViewport(0,0,screenSize.width, screenSize.height);
        gl.glBegin(GL2.GL_LINES);//static field
        gl.glVertex2d(0.50, -0.50);
        gl.glVertex2d(-0.50, 0.50);
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

    Mat4 camera(float translate, Vec2 rotate) {

        Mat4 projection = Glm.perspective_(45.0f, 4.0f / 3.0f, 0.1f, 100.0f);
        Mat4 view = new Mat4(1.0f).translate(new Vec3(0.0f, 0.0f, -translate));
        view.rotate(rotate.y, new Vec3(-1.0f, 0.0f, 0.0f));
        view.rotate(rotate.x, new Vec3(0.0f, 1.0f, 0.0f));
        Mat4 model = new Mat4(1.0f).scale(new Vec3(0.5f));

        return projection.mul(view).mul(model);
    }

    public static void main(String[] args) {
        //getting the capabilities object of GL2 profile
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        Line l = new Line();
        glcanvas.addGLEventListener(l);
        glcanvas.setSize(screenSize.width, screenSize.height);
//        glcanvas.setSize(1000, 1000);
        //creating frame
        final JFrame frame = new JFrame("straight Line");
        //adding canvas to frame
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }//end of main
}//end of classimport javax.media.opengl.GL2;