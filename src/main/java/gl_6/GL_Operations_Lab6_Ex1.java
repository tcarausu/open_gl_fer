package gl_6;

import com.jogamp.opengl.util.FPSAnimator;
import glm.mat._4.Mat4;
import glm.vec._3.Vec3;
import glm.vec._4.Vec4;
import utility.ABCDEquation;
import utility.Constant;
import utility.iPoint3D;

//import javax.media.opengl.*;
//import javax.media.opengl.awt.GLCanvas;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static gl_4.GL_Operations_Lab4_Ex1.setupVectorsAndTriangles;
import static gl_5.GL_Operations_Lab5_Ex1.TandGs;
import static java.lang.StrictMath.pow;

public class GL_Operations_Lab6_Ex1 implements GLEventListener {
    // Chapter 7.3 brazier - Lab6

    private float incrementalT = 0.05f;

    private static Vec3 v1 = new Vec3(1, 1, 3);
    private static Vec3 v2 = new Vec3(1, 2, 3);
    private static Vec3 v3 = new Vec3(2, 3, 3);
    private static Vec3 original_G = new Vec3(0, 0, 0); // x,y,z (s)
    private static Vec3 cubeO; // x,y,z (s)

    private static LinkedHashMap<String, LinkedHashMap<ABCDEquation, ArrayList<Vec3>>> triangleLineWithABCDValues = new LinkedHashMap<>();
    private static LinkedList<iPoint3D> pointsList;
    private static AtomicInteger currentIPosition = new AtomicInteger();

    //    private static int divs = 20;
    private static int divs = 1000;
    private static iPoint3D p;


    @Override
    public void display(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();

        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();

        viewWithEye(gl, currentIPosition.getAndIncrement());
        //MD: here you need to call a similar function as in 5, for the transformation
        //of the object. However, this time O is not fixed but changes during time,
        //to trigger an animation. For example at t=0 it is equal to the first point in
        //the Bezier curve, this O is then used to calculate the transformation matrix and projection
        //matrix which are then used to transform the object. At the next time point, t=0.05 you do the
        //same but for the next point in the Bezier curve, and so on until you reach the end

    }

    @Override
    public void init(GLAutoDrawable drawable) {

    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }


    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        FPSAnimator animator = new FPSAnimator(drawable, 120); // slower ideally would be 75/80 (not 60)
        animator.start();

        GL2 gl2 = drawable.getGL().getGL2();

        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();

        drawable.getGL().getGL2().glOrtho(-5, 5, -5, 5, -5, 10);
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

    private static LinkedList<iPoint3D> draw_bezier(LinkedList<iPoint3D> points) {
        LinkedList<iPoint3D> point3DS = new LinkedList<>();
        int n = points.size() - 1;
        int[] factors = new int[points.size()];
        double t, b;
        float px, py, pz;
        compute_factors(n, factors);

        //MD: no need to draw this line. Here tou need to collect the points in a list
        //and return that list. These points specify the position of the eye.

        for (int i = 0; i <= divs; i++) {

            t = 1.0 / divs * i;
            px = 0;
            py = 0;
            pz = 0;
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
                pz += b * points.get(j).getZ();

            }
            point3DS.add(new iPoint3D(px, py, pz));
        }

        return point3DS;
    }

    public static void viewWithEye(GL2 gl, int i) {
        //wihtout mouse listner to see if it displays it properly
        //        int i = 0;
        //        cubeO  = i (first elem) mouse it will incremenet (recursion) adjsuting the T
        iPoint3D currentPoint = pointsList.get(i);
        cubeO = new Vec3(currentPoint.getX(), currentPoint.getY(), currentPoint.getZ());

        Mat4 matrixT = TandGs(cubeO, original_G); //G = 0,0,0
        //        clear the screen
        //        redraw
        gl.glColor3f(0.0f, 0.0f, 1.0f);

        triangleLineWithABCDValues.forEach((key, value) -> {
            for (Map.Entry<ABCDEquation, ArrayList<Vec3>> entry : value.entrySet()) {
                ABCDEquation equation = entry.getKey();
                double a = equation.getA();
                double b = equation.getB();
                double c = equation.getC();
                double d = equation.getD();

                double pointX = cubeO.x;
                double pointY = cubeO.y;
                double pointZ = cubeO.z;

                double coeff = pointX * a + pointY * b + pointZ * c +  d;

                if(coeff>0 )
                {
                //    Draw it
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
            }
        });

    }

    public static void main(String[] args) throws FileNotFoundException {
        triangleLineWithABCDValues = setupVectorsAndTriangles(new File(Constant.kocka)); // Cube

        //MD: you need to specify a certain number of points that define
        //the Bezier curve. This curve specifies how the eye will move
        //should have three coordinates
        LinkedList<iPoint3D> point3DS = new LinkedList<>();
        point3DS.add(new iPoint3D(v1.x, v1.y, v1.z));
        point3DS.add(new iPoint3D(v2.x, v2.y, v2.z));
        point3DS.add(new iPoint3D(v3.x, v3.y, v3.z));

        pointsList = draw_bezier(point3DS);


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
