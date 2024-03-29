package figures;

import glm.Glm;
import glm.mat._4.Mat4;
import glm.vec._2.Vec2;
import glm.vec._3.Vec3;

//import javax.media.opengl.*;
//import javax.media.opengl.awt.GLCanvas;
//import static javax.media.opengl.GL.GL_TRIANGLES;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import javax.swing.*;

import static com.jogamp.opengl.GL.GL_TRIANGLES;


public class Overlapping_triangles implements GLEventListener {
    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        gl.glBegin(GL_TRIANGLES);
        // Prvi trokut:
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glVertex3f(-0.9f, -0.9f, -0.9f);
        gl.glVertex3f(0.9f, -0.9f, -0.9f);
        gl.glVertex3f(0.0f, 0.9f, 0.9f);

        // Drugi trokut:
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glVertex3f(-0.9f, 0.9f, -0.9f);
        gl.glVertex3f(0.9f, 0.9f, -0.9f);
        gl.glVertex3f(0.0f, -0.9f, 0.9f);
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

        Mat4 projection = Glm.perspective_(45.0f, 4.0f/3.0f, 0.1f, 100.0f);
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
        Overlapping_triangles l = new Overlapping_triangles();
        glcanvas.addGLEventListener(l);
        glcanvas.setSize(400, 400);
        //creating frame
        final JFrame frame = new JFrame("straight Line");
        //adding canvas to frame
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }//end of main
}//end of classimport javax.media.opengl.GL2;