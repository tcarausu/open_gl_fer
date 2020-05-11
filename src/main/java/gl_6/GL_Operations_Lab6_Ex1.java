package gl_6;

import glm.vec._3.Vec3;
import utility.iPoint2D;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.StrictMath.pow;
import static javax.media.opengl.GL.GL_LINE_STRIP;
import static utility.Constant.kocka;

public class GL_Operations_Lab6_Ex1 implements GLEventListener {
    // Chapter 7.3 brazier - Lab6
    // Chapter 7.3 brazier - Lab6
    // Chapter 7.3 brazier - Lab6

    private float t = 1 / 4f;

    //    private float b10 = (T1-T0)*t+T0;
//    private float b11 = (T2-T1)*t+T1;
//    private float b12 = (T3-T2)*t+T2;
//
//    private float b20 = (b11-b10)*t+b10;
//    private float b21 = (b12-b11)*t+b11;
//
//    private float b30 = (b21-b20)*t+b20;
//
//    private float b03 = StrictMath.pow(1-t,3);
//    private float b13 = 3*StrictMath.pow(1-t,2)*t;
//    private float b23 = 3*(1-t)*StrictMath.pow(t,2);
//    private float b33 = StrictMath.pow(t,3);
    private static String[] vectorsTopLine, trianglePointersLine;
    private static AtomicInteger counterElements = new AtomicInteger();
    private static AtomicInteger polyElCounter = new AtomicInteger();

    private static List<Vec3> vec3s = new ArrayList<>();
    private static List<Double> elementValues = new ArrayList<>();
    private static List<Double> polyValues = new ArrayList<>();
    private static LinkedList<iPoint2D> points = new LinkedList<>();
    private static int divs = 50;

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();

        try {
            LinkedList<iPoint2D> point2DS = getCubeValues(new File(kocka));
//            drawControlPolygon(point2DS, gl);
            draw_bezier(point2DS, gl);
            String s = "s";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GLAutoDrawable drawable) {

    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }


    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl2 = drawable.getGL().getGL2();
//        GLU glu = GLU.createGLU(gl2);

        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();

//        glu.gluOrtho2D(0.0f, width, 0.0f, height);
        drawable.getGL().getGL2().glOrtho(-1, 1, -1, 1, -1, 10);
//        drawable.getGL().getGL2().glOrtho(-25, 25, -25, 25, -25, 100);

        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glViewport(0, 0, width, height);
    }


    static void compute_factors(int n, int[] factors) {
        int i, a = 1;
        for (i = 1; i <= n + 1; i++) {
            factors[i - 1] = a;
            a = a * (n - i + 1) / i;
        }
    }

    static void drawControlPolygon(LinkedList<iPoint2D> points, GL2 gl2) {
        gl2.glColor3f(1.0f, 0.0f, 0.0f);
        gl2.glBegin(GL.GL_LINE_STRIP);
        for (int i = 0; i < points.size(); i++) {
            gl2.glVertex2f((float) points.get(i).getX(), (float) points.get(i).getY());
        }
        gl2.glEnd();
        gl2.glColor3f(1.0f, 1.0f, 0.0f);
        for (int i = 0; i < points.size(); i++) {
            gl2.glBegin(GL.GL_LINE_LOOP);
            gl2.glVertex2f((float) points.get(i).getX() - 10, (float) points.get(i).getY() - 10);
            gl2.glVertex2f((float) points.get(i).getX() - 10, (float) points.get(i).getY() + 10);
            gl2.glVertex2f((float) points.get(i).getX() + 10, (float) points.get(i).getY() + 10);
            gl2.glVertex2f((float) points.get(i).getX() + 10, (float) points.get(i).getY() - 10);
            gl2.glEnd();
        }
    }

    private static void draw_bezier(LinkedList<iPoint2D> points, GL2 gl) {
        iPoint2D p = new iPoint2D();
        int n = points.size() - 1;
        int[] factors = new int[points.size()];
        double t, b;
        float px, py;
        compute_factors(n, factors);
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glBegin(GL_LINE_STRIP);
        for (int i = 0; i <= divs; i++) {
            t = 1.0 / divs * i;
            px = 0;
            py = 0;
            for (int j = 0; j <= n; j++) {
                if (j == 0) {
                    b = factors[j] * pow(1 - t, n);
                } else if (j == n) {
                    b = factors[j] * pow(t, n);
                } else {
                    b = factors[j] * pow(t, j) * pow(1 - t, n - j);
                }
                px += b * points.get(j).getX();
                py += b * points.get(j).getY();
                gl.glVertex2f(px, py);

            }
        }
        gl.glEnd();
//        free(factors);
//        Runtime.getRuntime().gc();
    }


    public static LinkedList<iPoint2D> getCubeValues(File fileToTest) throws FileNotFoundException {
        Scanner sc = new Scanner(fileToTest);

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
                        Vec3 vector = new Vec3(firstElValueFromF, sElValueFromF, thElValueFromF);
                        vec3s.add(vector);
                        iPoint2D currentPoint = new iPoint2D(vector.x, vector.y);
                        points.add(currentPoint);
                        counterElements.getAndAdd(3);
                    }
                }
            }
        }
        return points;
    }

    public static void main(String[] args) {
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        GL_Operations_Lab6_Ex1 l = new GL_Operations_Lab6_Ex1();

        glcanvas.addGLEventListener(l);
        glcanvas.setSize(700, 700);

        final JFrame frame = new JFrame("lab6");
        //adding canvas to frame
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
