package gl_5;

import glm.mat._3.Mat3;
import glm.mat._4.Mat4;
import glm.vec._3.Vec3;
import glm.vec._4.Vec4;
import utility.ABCDEquation;
import utility.Constant;

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
    private static HashMap<String, HashMap<ABCDEquation, ArrayList<Vec3>>> triangleLineWithABCDValues = new HashMap<>();

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

        //only O and G
        glu.gluLookAt(original_O.x, original_O.y, original_O.z, original_G.x,original_G.y,original_G.z, 0.0f, 1.0f, 0.0f);

        //only O
        glu.gluLookAt(original_O.x, original_O.y, original_O.z, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);

        //default
//        glu.gluLookAt(0.0f, 0.0f, 20.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);

        gl.glColor3f(0, 0, 0);

        gl.glBegin(gl.GL_LINE_LOOP);
        cubeLab5(triangleLineWithABCDValues, gl);
        gl.glEnd();

        gl.glFlush();

//        renderScene(gl);

//        TandGs();

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

        gl.glFrustumf(-1.2f, 1.2f, -1.2f, 1.2f, 1.5f, 30.0f);
        gl.glMatrixMode(GL2.GL_MODELVIEW);

        //fullscreen
        gl.glViewport(0, 0, width, height);
    }

    public static void main(String[] args) throws FileNotFoundException {
        triangleLineWithABCDValues = setupVectorsAndTriangles();

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

    public static void cubeLab5(HashMap<String, HashMap<ABCDEquation, ArrayList<Vec3>>> triangleLineWithABCDValues, GL2 gl) {
        // For each point in Cube * T ; or vice-versa
        Mat4 matrixT = TandGs();
        Vec4 getAP = getAP(original_O, getHDist(original_O, original_G));

        triangleLineWithABCDValues.forEach((key, value) -> {
            for (Map.Entry<ABCDEquation, ArrayList<Vec3>> entry : value.entrySet()) {
                ArrayList<Vec3> valueVectors = entry.getValue();
//              Default Values for the Cube (lab4)
                Vec3 firstVector = valueVectors.get(0);
                Vec3 secondVector = valueVectors.get(1);
                Vec3 thirdVector = valueVectors.get(2);

//                gl.glVertex3d(firstVector.x, firstVector.y, firstVector.z);
//                gl.glVertex3d(secondVector.x, secondVector.y, secondVector.z);
//                gl.glVertex3d(thirdVector.x, thirdVector.y, thirdVector.z);

                String s = "s";
//              Multiply Matrix T with each of the 3 Vectors
                Vec4 firstM = matrixT.mul_(firstVector.toVec4_());
                Vec4 secondM = matrixT.mul_(secondVector.toVec4_());
                Vec4 thirdM = matrixT.mul_(thirdVector.toVec4_());

//
//                gl.glVertex3d(firstM.x, firstM.y, firstM.z);
//                gl.glVertex3d(secondM.x, secondM.y, secondM.z);
//                gl.glVertex3d(thirdM.x, thirdM.y, thirdM.z);

                String s2 = "s";

                // All these values then have to be multiplied by Projection P



                Vec4 getAP1 = firstM.mul_(getAP);
                Vec4 getAP2 = secondM.mul_(getAP);
                Vec4 getAP3 = thirdM.mul_(getAP);

                gl.glVertex3d(getAP1.x, getAP1.y, getAP1.z);
                gl.glVertex3d(getAP2.x, getAP2.y, getAP2.z);
                gl.glVertex3d(getAP3.x, getAP3.y, getAP3.z);

                String s3 = "s";
//
//                Vec4 getAP1 = getAP(firstM.toVec3_(), getHDist(firstM.toVec3_(), original_G));
//                Vec4 getAP2 = getAP(secondM.toVec3_(), getHDist(secondM.toVec3_(), original_G));
//                Vec4 getAP3 = getAP(thirdM.toVec3_(), getHDist(thirdM.toVec3_(), original_G));
//
//                gl.glVertex3d(getAP1.x, getAP1.y, getAP1.z);
//                gl.glVertex3d(getAP2.x, getAP2.y, getAP2.z);
//                gl.glVertex3d(getAP3.x, getAP3.y, getAP3.z);

                String s4 = "s";


            }
        });


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

        Vec3 gt1 = getGT1(original_G, original_O);

        Vec3 gt2 = getGT2(gt1);
        Mat4 t2 = getT2(original_G, original_O);

        Vec3 gt3 = getGT3(gt2);
        Mat4 t3 = getT3(gt2);

        Mat4 matrixT = matrixT(t1, t2, t3, getT4(), getT5());

        Vec4 getAP = getAP(original_O, getHDist(original_O, original_G));
        return matrixT;
    }


}
