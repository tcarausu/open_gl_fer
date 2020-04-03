package figures;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;

public class ICGVector implements GLEventListener {

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glBegin(GL2.GL_LINES);

        //drawing the base
        gl.glBegin(GL2.GL_LINES);
        gl.glVertex3f(1f, 5f, 0);
        gl.glVertex3f(-1f, 0f, 0);
        gl.glEnd();

//        //drawing the right edge
//        gl.glBegin(GL2.GL_LINES);
//        gl.glVertex3f(0f, 0.50f, 0);
//        gl.glVertex3f(-0.50f, -0.50f, 0);
//        gl.glEnd();
//
//        //drawing the lft edge
//        gl.glBegin(GL2.GL_LINES);
//        gl.glVertex3f(0f, 0.50f, 0);
//        gl.glVertex3f(0.50f, -0.50f, 0);
//        gl.glEnd();

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

        //getting the capabilities object of GL2 profile
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        ICGVector l = new ICGVector();
        glcanvas.addGLEventListener(l);
        glcanvas.setSize(400, 400);

        //creating frame
        final JFrame frame = new JFrame("ICG");

        //adding canvas to frame
        frame.getContentPane().add(glcanvas);

        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }//end of main

}//end of classimport javax.media.opengl.GL2;

