package gl_1;

import glm.Glm;
import glm.mat._4.Mat4;
import glm.vec._2.Vec2;
import glm.vec._3.Vec3;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;

public class Home implements GLEventListener {
    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        //drawing top
        gl.glBegin ( GL2.GL_LINES );
        gl.glVertex3f( -0.3f, 0.3f, 0 );
        gl.glVertex3f( 0.3f,0.3f, 0 );
        gl.glEnd();
        //drawing bottom
        gl.glBegin( GL2.GL_LINES );
        gl.glVertex3f( -0.3f,-0.3f, 0 );
        gl.glVertex3f( 0.3f,-0.3f, 0 );
        gl.glEnd();
        //drawing the right edge
        gl.glBegin( GL2.GL_LINES );
        gl.glVertex3f( -0.3f,0.3f, 0 );
        gl.glVertex3f( -0.3f,-0.3f, 0 );
        gl.glEnd();
        //drawing the left edge
        gl.glBegin( GL2.GL_LINES );
        gl.glVertex3f( 0.3f,0.3f,0 );
        gl.glVertex3f( 0.3f,-0.3f,0 );
        gl.glEnd();
        //building roof
        //building lft dia
        gl.glBegin( GL2.GL_LINES );
        gl.glVertex3f( 0f,0.6f, 0 );
        gl.glVertex3f( -0.3f,0.3f, 0 );
        gl.glEnd();
        //building rt  dia
        gl.glBegin( GL2.GL_LINES );
        gl.glVertex3f( 0f,0.6f, 0 );
        gl.glVertex3f( 0.3f,0.3f, 0 );
        gl.glEnd();
        //building door
        //drawing top
        gl.glBegin ( GL2.GL_LINES );
        gl.glVertex3f( -0.05f, 0.05f, 0 );
        gl.glVertex3f( 0.05f, 0.05f, 0 );
        gl.glEnd();
        //drawing the left edge
        gl.glBegin ( GL2.GL_LINES );
        gl.glVertex3f( -0.05f, 0.05f, 0 );
        gl.glVertex3f( -0.05f, -0.3f, 0 );
        gl.glEnd();
        //drawing the right edge
        gl.glBegin ( GL2.GL_LINES );
        gl.glVertex3f( 0.05f, 0.05f, 0 );
        gl.glVertex3f( 0.05f, -0.3f, 0 );
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
        Home l = new Home();
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