package gl_5;

import glm.mat._3.Mat3;
import glm.mat._4.Mat4;
import glm.vec._3.Vec3;
import glm.vec._4.Vec4;
import utility.Constant;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import static utility.GL_lab5_getTandGT.*;

public class GL_Operations_Lab5_Ex1 implements GLEventListener {
    private static AtomicInteger counterElements = new AtomicInteger();
    private static AtomicInteger polyElCounter = new AtomicInteger();
    private static String[] vectorsTopLine, trianglePointersLine;

    private static Vec3 original_G = new Vec3(0, 0, 0); // x,y,z (s)
    private static Vec3 original_O = new Vec3(1, 1, 3); // EYE/OPERATOR // x,y,z (0)

    private static Vec3 teddy_G = new Vec3(20, 20, 20); // x,y,z (s)
    private static Vec3 teddy_O = new Vec3(100, 100, 100); // EYE/OPERATOR // x,y,z (0)

    private static Mat3 mat3 = new Mat3();

    private static List<Vec3> vec3s = new ArrayList<>();
    private static List<Double> elementValues = new ArrayList<>();
    private static List<Double> polyValues = new ArrayList<>();

    private static Vec3 vec3;

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        GLU glu = GLU.createGLU(gl);

        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
//        glu.gluLookAt(0.0f, 0.0f, 20.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
//        renderScene(gl);

        TandGs();

    }

    @Override
    public void init(GLAutoDrawable drawable) {

    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
//        GL2 gl = drawable.getGL().getGL2();
//
//        gl.glMatrixMode(GL2.GL_PROJECTION);
//        gl.glLoadIdentity();
//
//        gl.glFrustumf(-1.2f, 1.2f, -1.2f, 1.2f, 1.5f, 30.0f);
//        gl.glMatrixMode(GL2.GL_MODELVIEW);
//
//        //fullscreen
//        gl.glViewport(0, 0, width, height);
    }

    public static void main(String[] args) throws FileNotFoundException {
//        TandGs(true, original_G);
        getVectorValues();

//        final GLProfile profile = GLProfile.get(GLProfile.GL2);
//        GLCapabilities capabilities = new GLCapabilities(profile);
//        // The canvas
//        final GLCanvas glcanvas = new GLCanvas(capabilities);
//        GL_Operations_Lab5_Ex1 l = new GL_Operations_Lab5_Ex1();
//
//        glcanvas.addGLEventListener(l);
//        glcanvas.setSize(700, 700);
//
//        final JFrame frame = new JFrame("lab5");
//        //adding canvas to frame
//        frame.getContentPane().add(glcanvas);
//        frame.setSize(frame.getContentPane().getPreferredSize());
//        frame.setVisible(true);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private static void getVectorValues() throws FileNotFoundException {
        Scanner sc = new Scanner(new File(Constant.kocka));

        while (sc.hasNext()) {
            String line = sc.nextLine();

            if (line.startsWith("//") || line.startsWith(" ")
                    || line.startsWith("#") || line.startsWith("g ")
                    || line.length() == 0) {
                continue;
            }

            if (line.startsWith("v")) {
                vectorsTopLine = line.split(" ");
                for (String element : vectorsTopLine) {
                    if (element.contains("v")) continue;
                    double elementValue = Double.parseDouble(element);
                    elementValues.add(elementValue);
                    if (elementValues.size() % 3 == 0) {
                        int firstElValueFromV = (int) elementValues.get(counterElements.get()).doubleValue();
                        int secondElValueFromV = (int) elementValues.get(counterElements.get() + 1).doubleValue();
                        int thirdElValueFromV = (int) elementValues.get(counterElements.get() + 2).doubleValue();
                        Vec3 currentVector = new Vec3(firstElValueFromV, secondElValueFromV, thirdElValueFromV);
                        boolean betweenMin1and1 = true;

                        if (firstElValueFromV < -1 || firstElValueFromV > 1) {
                            betweenMin1and1 = false;
                        }
                        if (secondElValueFromV < -1 || secondElValueFromV > 1) {
                            betweenMin1and1 = false;
                        }
                        if (thirdElValueFromV < -1 || thirdElValueFromV > 1) {
                            betweenMin1and1 = false;
                        }

                        vec3s.add(currentVector);
                        counterElements.getAndAdd(3);


                    }
                }

            }
        }

        String s = "v";

    }

    public void cubelab5() {
        // For each point in Cube * T ; or vice-versa
        Mat4 matrixT = TandGs();
        // All these values then have to be multiplied by Projection P


        // Chapter 7.3 brazier - Lab6
    }

    public static void cube(float w, GL2 gl2) {
        // top face
        gl2.glBegin(GL.GL_LINE_LOOP);
        gl2.glVertex3f(-w, -w, w);
        gl2.glVertex3f(w, -w, w);
        gl2.glVertex3f(w, w, w);
        gl2.glVertex3f(-w, w, w);
        gl2.glEnd();

        // bottom face
        gl2.glBegin(GL.GL_LINE_LOOP);
        gl2.glVertex3f(-w, w, -w);
        gl2.glVertex3f(w, w, -w);
        gl2.glVertex3f(w, -w, -w);
        gl2.glVertex3f(-w, -w, -w);
        gl2.glEnd();

        // right face
        gl2.glBegin(GL.GL_LINE_LOOP);
        gl2.glVertex3f(w, w, -w);
        gl2.glVertex3f(-w, w, -w);
        gl2.glVertex3f(-w, w, w);
        gl2.glVertex3f(w, w, w);
        gl2.glEnd();

        // left face
        gl2.glBegin(GL.GL_LINE_LOOP);
        gl2.glVertex3f(w, -w, w);
        gl2.glVertex3f(-w, -w, w);
        gl2.glVertex3f(-w, -w, -w);
        gl2.glVertex3f(w, -w, -w);
        gl2.glEnd();

        // front face
        gl2.glBegin(GL.GL_LINE_LOOP);
        gl2.glVertex3f(w, -w, -w);
        gl2.glVertex3f(w, w, -w);
        gl2.glVertex3f(w, w, w);
        gl2.glVertex3f(w, -w, w);
        gl2.glEnd();

        // back face
        gl2.glBegin(GL.GL_LINE_LOOP);
        gl2.glVertex3f(-w, -w, w);
        gl2.glVertex3f(-w, w, w);
        gl2.glVertex3f(-w, w, -w);
        gl2.glVertex3f(-w, -w, -w);
        gl2.glEnd();
    }


    static void renderScene(GL2 gl2) {
        gl2.glColor3f(1.0f, 0.2f, 0.2f);
        gl2.glPushMatrix();
        gl2.glScalef(10.0f, 10.0f, 10.0f);
        cube(1, gl2);
        gl2.glPopMatrix();
        gl2.glColor3f(0.0f, 0.2f, 1.0f);
        gl2.glPushMatrix();
        gl2.glTranslatef(10.0f, 0.0f, 0.0f);
        gl2.glRotatef(30.0f, 0.0f, 0.0f, 1.0f);
        gl2.glScalef(5.0f, 5.0f, 5.0f);
        cube(1, gl2);

        gl2.glPopMatrix();
    }


    private static String printMe(String title, Mat4 mat4) {

        return title + " Matrix to be Printed " + "\n"
                + "| " + mat4.m00 + " " + mat4.m01 + " " + mat4.m02 + " " + mat4.m03 + " |\n"
                + "| " + mat4.m10 + " " + mat4.m11 + " " + mat4.m12 + " " + mat4.m13 + " |\n"
                + "| " + mat4.m20 + " " + mat4.m21 + " " + mat4.m22 + " " + mat4.m23 + " |\n"
                + "| " + mat4.m30 + " " + mat4.m31 + " " + mat4.m32 + " " + mat4.m33 + " |\n";
    }

    private static Mat4 TandGs() {
        Mat4 t1 = getT1(original_O);
        System.out.println(printMe("Matrix T1", t1));

        Vec3 gt1 = getGT1(original_G, original_O);
        System.out.println("Vector GT1 - " + gt1 + "\n");

        Vec3 gt2 = getGT2(gt1);
        Mat4 t2 = getT2(original_G, original_O);

        System.out.println(printMe("Matrix T2", t2));
        System.out.println("Vector GT2 - " + gt2 + "\n");

        Vec3 gt3 = getGT3(gt2);
        Mat4 t3 = getT3(gt2);

        System.out.println(printMe("Matrix T3", t3));
        System.out.println("Vector GT3 - " + gt3 + "\n");

        System.out.println(printMe("Matrix T4", getT4()));
        System.out.println(printMe("Matrix T5", getT5()));

        Mat4 matrixT = matrixT(t1, t2, t3, getT4(), getT5());
        System.out.println(printMe("Matrix T-Total", matrixT));
        System.out.println("Get H - " +
                "distance between projection plane and the eye/observer: " + getHDist(original_O, original_G));
        System.out.println("Get Zg3 - Z coordinate for the 3rd Gaze " + gt3.z);

        Vec4 getAP = getAP(original_O, getHDist(original_O, original_G));
        System.out.println("getAP xStrophe - " + getAP + "\n");

        System.out.println("Get Xp - similarity of 3 triangles " + getXP(original_O, getHDist(original_O, original_G)));
        System.out.println("Get Yp - similarity of 3 triangles " + getYP(original_O, getHDist(original_O, original_G)));
        return matrixT;
    }


}
