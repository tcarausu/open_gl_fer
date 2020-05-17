package gl_5;

import gl_4.GL_Operations_Lab4_Ex1;
import glm.mat._3.Mat3;
import glm.mat._4.Mat4;
import glm.vec._3.Vec3;
import glm.vec._4.Vec4;
import utility.ABCDEquation;
import utility.Constant;
import utility.FaceTriangle;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static gl_4.GL_Operations_Lab4_Ex1.setupVectorsAndTriangles;
import static utility.GL_lab5_getTandGT.*;

public class GL_Operations_Lab5_Ex1 implements GLEventListener {
    private static AtomicInteger counterElements = new AtomicInteger();
    private static AtomicInteger polyElCounter = new AtomicInteger();
    private static String[] vectorsTopLine, trianglePointersLine;
    private static LinkedHashMap<String, LinkedHashMap<ABCDEquation, ArrayList<Vec3>>> triangleLineWithABCDValues = new LinkedHashMap<>();

    private static Vec3 original_G = new Vec3(0, 0, 0); // x,y,z (s)
    private static Vec3 original_O = new Vec3(3, 3, 3); // EYE/OPERATOR // x,y,z (0)

    private static Vec3 teddy_G = new Vec3(50, 50, 50); // x,y,z (s)
    private static Vec3 teddy_O = new Vec3(100, 100, 100); // EYE/OPERATOR // x,y,z (0)

    private static List<Vec3> vec3s = new ArrayList<>();
    private static List<Double> elementValues = new ArrayList<>();
    private static List<Double> polyValues = new ArrayList<>();


    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glColor3f(0, 0, 0);

        cubeLab5(triangleLineWithABCDValues, gl);

        gl.glFlush();

    }

    @Override
    public void init(GLAutoDrawable drawable) {

    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

//        drawable.getGL().getGL2().glOrtho(-5, 5, -5, 5, -5, 10);
        drawable.getGL().getGL2().glOrtho(-15, 15, -15, 15, -15, 100);
        gl.glMatrixMode(GL2.GL_MODELVIEW);

        //fullscreen
        gl.glViewport(0, 0, width, height);
    }

    public static void main(String[] args) throws FileNotFoundException {
//        triangleLineWithABCDValues = setupVectorsAndTriangles(new File(Constant.kocka)); // Cube
        triangleLineWithABCDValues = setupVectorsAndTriangles(new File(Constant.teddy)); //Teddy
//        triangleLineWithABCDValues = setupVectorsAndTriangles(new File(Constant.teapot)); //Teddy

        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        GL_Operations_Lab5_Ex1 l = new GL_Operations_Lab5_Ex1();

        glcanvas.addGLEventListener(l);
        glcanvas.setSize(700, 700);

        final JFrame frame = new JFrame("lab5");
        //adding canvas to frame
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void cubeLab5(LinkedHashMap<String, LinkedHashMap<ABCDEquation, ArrayList<Vec3>>> triangleLineWithABCDValues, GL2 gl) {
        // For each point in Cube * T ; or vice-versa
//        Mat4 matrixT = TandGs(original_O,original_G);
        Mat4 matrixT = TandGs(teddy_O, teddy_G);

        //Vec4 getAP = getAP(original_O, getHDist(original_O, original_G));

        System.out.println("Multiplication of Matrix T with AP");
//        matrixT.print();

        triangleLineWithABCDValues.forEach((key, value) -> {
            for (Map.Entry<ABCDEquation, ArrayList<Vec3>> entry : value.entrySet()) {
                ArrayList<Vec3> valueVectors = entry.getValue();
                // Default Values for the Cube (lab4)
                Vec3 firstVector = valueVectors.get(0);
                Vec3 secondVector = valueVectors.get(1);
                Vec3 thirdVector = valueVectors.get(2);

                // Multiply Matrix T with each of the 3 Vectors
                Vec4 firstM = matrixT.mul_(new Vec4(firstVector, 1));
                Vec4 secondM = matrixT.mul_(new Vec4(secondVector, 1));
                Vec4 thirdM = matrixT.mul_(new Vec4(thirdVector, 1));

                gl.glBegin(gl.GL_LINE_LOOP);
                gl.glVertex3d(firstM.x / firstM.w, firstM.y / firstM.w, 0);
                gl.glVertex3d(secondM.x / secondM.w, secondM.y / secondM.w, 0);
                gl.glVertex3d(thirdM.x / thirdM.w, thirdM.y / thirdM.w, 0);
                gl.glEnd();

            }
        });    }

    public static void drawCube(LinkedHashMap<String, LinkedHashMap<ABCDEquation, ArrayList<Vec3>>> triangleLineWithABCDValues, GL2 gl, Mat4 matrixT) {

    }


    private static String printMe(String title, Mat4 mat4) {

        return title + " Matrix to be Printed " + "\n"
                + "| " + mat4.m00 + " " + mat4.m01 + " " + mat4.m02 + " " + mat4.m03 + " |\n"
                + "| " + mat4.m10 + " " + mat4.m11 + " " + mat4.m12 + " " + mat4.m13 + " |\n"
                + "| " + mat4.m20 + " " + mat4.m21 + " " + mat4.m22 + " " + mat4.m23 + " |\n"
                + "| " + mat4.m30 + " " + mat4.m31 + " " + mat4.m32 + " " + mat4.m33 + " |\n";
    }

    public static Mat4 TandGs(Vec3 observer, Vec3 gaze) {
        Mat4 t1 = getT1(observer);

        Vec3 gt1 = getGT1(gaze, observer);

        Vec3 gt2 = getGT2(gt1);
        Mat4 t2 = getT2(gaze, observer);

        Vec3 gt3 = getGT3(gt2);
        Mat4 t3 = getT3(gt2);

        Mat4 matrixT = matrixT(t1, t2, t3, getT4(), getT5());
        System.out.println(printMe("T", matrixT));

        // All these values then have to be multiplied by Projection P
        Mat4 getAP = projectionMatrix(observer, gaze);
        getAP.print();

        return getAP.mul_(matrixT);
    }


}
