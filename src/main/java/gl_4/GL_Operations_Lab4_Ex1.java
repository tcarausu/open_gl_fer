package gl_4;

import com.jogamp.opengl.util.FPSAnimator;
import glm.vec._3.Vec3;
import utility.ABCDEquation;
import utility.Constant;
import utility.FaceTriangle;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;

public class GL_Operations_Lab4_Ex1 implements GLEventListener {
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static int widthToUse = 1280, heightToUse = 720;
    private static String[] vectorsTopLine, trianglePointersLine;
    private static AtomicInteger counterElements = new AtomicInteger();
    private static AtomicInteger polyElCounter = new AtomicInteger();

    //setupVariables
//    private static HashMap<String, ArrayList<Double>> triangleLineWithABCDValues = new HashMap<>();
    private static HashMap<String, HashMap<ABCDEquation, ArrayList<Vec3>>> triangleLineWithABCDValues = new HashMap<>();
    private static ABCDEquation equation;
    private static List<Vec3> vec3s = new ArrayList<>();
    private static List<Double> elementValues = new ArrayList<>();
    private static List<Double> polyValues = new ArrayList<>();

    private static Vec3 vec3;

    @Override
    public void display(GLAutoDrawable drawable) {

        final GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, 700, 700);
        gl.glMatrixMode(gl.GL_PROJECTION_MATRIX);
        gl.glLoadIdentity();
//        drawable.getGL().getGL2().glOrtho(0, 1000, 0, 1000, 0, 1);
        drawable.getGL().getGL2().glOrtho(-5, 5, -5, 5, -5, 10);
        gl.glMatrixMode(gl.GL_MODELVIEW_MATRIX);

        gl.glClearColor(1, 1, 1, 0);
        gl.glClear(GL_COLOR_BUFFER_BIT);
        //static field
        gl.glColor3f(0, 0, 0);

        gl.glBegin(gl.GL_LINE_LOOP);

        triangleLineWithABCDValues.forEach((key, value) -> {
            for (Map.Entry<ABCDEquation, ArrayList<Vec3>> entry : value.entrySet()) {
                ArrayList<Vec3> valueVectors = entry.getValue();
                ABCDEquation equation = entry.getKey();

                Vec3 firstVector = valueVectors.get(0);
                Vec3 secondVector = valueVectors.get(1);
                Vec3 thirdVector = valueVectors.get(2);

                gl.glVertex3d(firstVector.x, firstVector.y, firstVector.z);
                gl.glVertex3d(secondVector.x, secondVector.y, secondVector.z);
                gl.glVertex3d(thirdVector.x, thirdVector.y, thirdVector.z);

                boolean in;
                if (vec3 != null) {
                    in = isInsideOf(vec3, equation);
                } else {
                    in = isInsideOf(firstVector, equation);
                }
                if (in)
                    System.out.println("Equation with coordinates: ("
                            + equation.getA() + ", " + equation.getB() + ", " + equation.getC() + ", " + equation.getD() + ") "
                            + " with Point V with coordinates: (" + firstVector.x + ", "
                            + firstVector.y + ", " + firstVector.z + ") IS INSIDE OF POLYGON !");
                else
                    System.out.println("Equation: " + equation + " with coordinates: ("
                            + equation.getA() + ", " + equation.getB() + ", " + equation.getC() + ", " + equation.getD() + ") "
                            + " with Point V with coordinates: (" + firstVector.x + ", "
                            + firstVector.y + ", " + firstVector.z + ") IS NOT INSIDE OF POLYGON");
                break;
            }
        });


        gl.glEnd();

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

    }

    public static HashMap<String, HashMap<ABCDEquation, ArrayList<Vec3>>>  setupVectorsAndTriangles() throws FileNotFoundException {
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
                        int firstElValueFromF = (int) elementValues.get(counterElements.get()).doubleValue();
                        int sElValueFromF = (int) elementValues.get(counterElements.get() + 1).doubleValue();
                        int thElValueFromF = (int) elementValues.get(counterElements.get() + 2).doubleValue();
                        vec3s.add(new Vec3(firstElValueFromF, sElValueFromF, thElValueFromF));
                        counterElements.getAndAdd(3);
                    }
                }
            }
            if (line.startsWith("f")) {
                trianglePointersLine = line.split(" ");
                for (String element : trianglePointersLine) {
                    if (element.contains("f")) continue;
                    double polyValue = Double.parseDouble(element);
                    polyValues.add(polyValue);

                    HashMap<ABCDEquation, ArrayList<Vec3>> a_d_eqWithVectors = null;
                    if (polyValues.size() % 3 == 0) {
                        int firstPolyValueFromF = (int) polyValues.get(polyElCounter.get()).doubleValue();
                        int sPolyValueFromF = (int) polyValues.get(polyElCounter.get() + 1).doubleValue();
                        int thPolyValueFromF = (int) polyValues.get(polyElCounter.get() + 2).doubleValue();
                        Vec3 firstEdge = vec3s.get(firstPolyValueFromF - 1);
                        Vec3 secondEdge = vec3s.get(sPolyValueFromF - 1);
                        Vec3 thirdEdge = vec3s.get(thPolyValueFromF - 1);

                        FaceTriangle faceTriangle = new FaceTriangle(firstEdge, secondEdge, thirdEdge);

                        float x_1 = faceTriangle.getFirstEdge().x;
                        float y_1 = faceTriangle.getFirstEdge().y;
                        float z_1 = faceTriangle.getFirstEdge().z;

                        float x_2 = faceTriangle.getSecondEdge().x;
                        float y_2 = faceTriangle.getSecondEdge().y;
                        float z_2 = faceTriangle.getSecondEdge().z;

                        float x_3 = faceTriangle.getThirdEdge().x;
                        float y_3 = faceTriangle.getThirdEdge().y;
                        float z_3 = faceTriangle.getThirdEdge().z;

                        double A = (y_2 - y_1) * (z_3 - z_1) - (z_2 - z_1) * (y_3 - y_1);
                        double B = -(x_2 - x_1) * (z_3 - z_1) + (z_2 - z_1) * (x_3 - x_1);
                        double C = (x_2 - x_1) * (y_3 - y_1) - (y_2 - y_1) * (x_3 - x_1);
                        double D = -(x_1 * A - y_1 * B - z_1 * C);

                        equation = new ABCDEquation(A, B, C, D);

                        a_d_eqWithVectors = new HashMap<>();
                        a_d_eqWithVectors.put(equation, faceTriangle.getEdgeVectors());
                        polyElCounter.getAndAdd(3);
                    }
                    GL_Operations_Lab4_Ex1.triangleLineWithABCDValues.put(line, a_d_eqWithVectors);
                }

            }
        }
        return triangleLineWithABCDValues;
    }

    public boolean isInsideOf(Vec3 vertex, ABCDEquation abcdEquation) {
        double a, b, c, d, r;
        float x = vertex.x;
        float y = vertex.y;
        float z = vertex.z;
        boolean isInside = false;

        a = abcdEquation.getA();
        b = abcdEquation.getB();
        c = abcdEquation.getC();
        d = abcdEquation.getD();

        r = a * x + b * y + c * z + d;

        if (r == 0)
            isInside = true;
//        if (r > 0)
//            return false;
//        if (r < 0)
//            return false;

        return isInside;
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Please introduce Vector's values to test:");
        System.out.println("Do you want to use a manual one?:");

        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();

        if (line.equals("Y") || line.equals("y")) {
            line = sc.next();
            String[] values = line.split(",");
            vec3 = new Vec3(Double.parseDouble(values[0]), Double.parseDouble(values[1]), Double.parseDouble(values[2]));
            System.out.println("Testing Values: " + vec3.x + " " + vec3.y + " " + vec3.z);
            sc.close();
        } else if (line.equals("N") || line.equals("n")) sc.close();

        setupVectorsAndTriangles();
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        GL_Operations_Lab4_Ex1 l = new GL_Operations_Lab4_Ex1();
        glcanvas.requestFocus();
        glcanvas.addGLEventListener(l);
        glcanvas.setSize(700, 700);

        FPSAnimator animator = new FPSAnimator(glcanvas, 60);
        animator.start();

        //creating frame
        final JFrame frame = new JFrame("lab4");
        //adding canvas to frame
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}
