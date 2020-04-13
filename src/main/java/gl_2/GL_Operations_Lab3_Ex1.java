package gl_2;

import glm.vec._2.Vec2;
import utility.iPoint2D;
import utility.iPolyElement;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import static java.lang.StrictMath.round;
import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;
import static utility.Bresenham_Utility.plotLine;

public class GL_Operations_Lab3_Ex1 implements GLEventListener {
    private static final Vec2 V1 = new Vec2(50, 200);
    private static final Vec2 V2 = new Vec2(150, 350);
    private static final Vec2 V3 = new Vec2(300, 150);
    private static final Vec2 V4 = new Vec2(100, 50);
    private final ArrayList<iPolyElement> iPolyElements = new ArrayList<>();

    private static final Point2D vPoint = new Point(100, 50);

    private float D;

    private final boolean clockwiseOrientation = false;

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, 1000, 1000);
        gl.glMatrixMode(gl.GL_PROJECTION_MATRIX);
        gl.glLoadIdentity();
        drawable.getGL().getGL2().glOrtho(0, 1000, 0, 1000, 0, 1);
        gl.glMatrixMode(gl.GL_MODELVIEW_MATRIX);

        iPolyElements.add(new iPolyElement(new iPoint2D(V1.x, V1.y)));
        iPolyElements.add(new iPolyElement(new iPoint2D(V2.x, V2.y)));
        iPolyElements.add(new iPolyElement(new iPoint2D(V3.x, V3.y)));
        iPolyElements.add(new iPolyElement(new iPoint2D(V4.x, V4.y)));


        gl.glClearColor(1, 1, 1, 0);
        gl.glClear(GL_COLOR_BUFFER_BIT);
        //static field
        gl.glColor3f(0.5f, 0.5f, 0.5f);

        gl.glBegin(gl.GL_POLYGON);

        drawConvexPolygon(iPolyElements, iPolyElements.size(), gl);

        gl.glEnd();
//        gl.glBegin(gl.GL_POLYGON);
//
//        gl.glVertex2f(V1.x, V1.y);
//        gl.glVertex2f(V2.x, V2.y);
//        gl.glVertex2f(V3.x, V3.y);
//        gl.glVertex2f(V4.x, V4.y);
//
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
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        GL_Operations_Lab3_Ex1 l = new GL_Operations_Lab3_Ex1();

        glcanvas.addGLEventListener(l);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        glcanvas.setSize(screenSize.width, screenSize.height);

        //creating frame
        final JFrame frame = new JFrame("lab2");
        //adding canvas to frame
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void drawConvexPolygon(ArrayList<iPolyElement> polyElements, int elemSize, GL2 gl2) {
        int i, i0;
        int xmin, xmax, ymin, ymax;
        i0 = elemSize - 1;

        for (i = 0; i < elemSize; i++) {
            double x = polyElements.get(i0).getTop().getX();
            double y = polyElements.get(i0).getTop().getY();
            gl2.glVertex2f((float) x, (float) y);
            i0 = i;
        }

    }

    private void drawConvexPolygonOri(
            ArrayList<iPolyElement> polyElements,
            int n,
            GL2 gl2
    ) {
        int i, i0;
        i0 = n - 1;
        for (i = 0; i < n; i++) {
            iPolyElement elemi0 = polyElements.get(i0);
            iPolyElement elemi = polyElements.get(i);

            double x0 = polyElements.get(i0).getTop().getX();
            double y0 = polyElements.get(i0).getTop().getY();

            double x1 = polyElements.get(i).getTop().getX();
            double y1 = polyElements.get(i).getTop().getY();

            elemi0.getEdge().setA(elemi0.getTop().getY() - elemi.getTop().getY());
            elemi0.getEdge().setB(-(elemi0.getTop().getX() - elemi.getTop().getX()));
            elemi0.getEdge().setC(elemi0.getTop().getX() * elemi.getTop().getY()
                    - elemi0.getTop().getY() * elemi.getTop().getX());
            if (clockwiseOrientation)
                elemi0.setLeft(elemi0.getTop().getY() < elemi.getTop().getY());
            else
                elemi0.setLeft(elemi0.getTop().getY() > elemi.getTop().getY());

//            double x = polyElements.get(i0).getTop().getX();
//            double y = polyElements.get(i0).getTop().getY();
            gl2.glVertex2f((float) x0, (float) y0);
            i0 = i;
        }
    }

    private void computingCoefficientConvexPolygon(
            ArrayList<iPolyElement> polyElements,
            int n,
            GL2 gl2) {
        int i, i0;
        i0 = n - 1;
        for (i = 0; i < n; i++) {
            polyElements.get(i0).getEdge().setA(polyElements.get(i0).getTop().getY() - polyElements.get(i).getTop().getY());
            polyElements.get(i0).getEdge().setB(-(polyElements.get(i0).getTop().getX() - polyElements.get(i).getTop().getX()));
            polyElements.get(i0).getEdge().setC(polyElements.get(i0).getTop().getX() * polyElements.get(i).getTop().getY()
                    - polyElements.get(i0).getTop().getY() * polyElements.get(i).getTop().getX());
            polyElements.get(i0).setLeft(polyElements.get(i0).getTop().getY() < polyElements.get(i).getTop().getY());
            i0 = i;
        }
    }

    private void fillConvexPolygon(
            ArrayList<iPolyElement> polyElements,
            int n,
            GL2 gl2) {
        int i, i0, y;

        int xmin, xmax, ymin, ymax;
        double L, D, x;
        /* Search for minimum and maximum coordinates      */
        xmin = xmax = (int) polyElements.get(0).getTop().getX();
        ymin = ymax = (int) polyElements.get(0).getTop().getY();
        for (i = 1; i < n; i++) {
            if (xmin > polyElements.get(i).getTop().getX()) xmin = (int) polyElements.get(i).getTop().getX();
            if (xmax < polyElements.get(i).getTop().getX()) xmax = (int) polyElements.get(i).getTop().getX();
            if (ymin > polyElements.get(i).getTop().getY()) ymin = (int) polyElements.get(i).getTop().getY();
            if (ymax < polyElements.get(i).getTop().getY()) ymax = (int) polyElements.get(i).getTop().getY();
        }
        /*Polygon coloring: for every y between ymin and ymax it works ... */
        for (y = ymin; y <= ymax; y++) {
            /* Find the largest Lijevo(Left) least right intersection. . . */
            L = xmin;
            D = xmax;
            i0 = 1 - n;
            /* i0 is the beginning of the hill, and is the end of the hill */
            for (i = 0; i < n; i0 = i++) {
                /* if the edge is horizontal */
                if (polyElements.get(i0).getEdge().getA() == 0) {
                    if (polyElements.get(i0).getTop().getY() == y) {
                        if (polyElements.get(i0).getTop().getX() < polyElements.get(i).getTop().getX()) {
                            L = polyElements.get(i0).getTop().getX();
                            D = polyElements.get(i).getTop().getX();
                        } else {
                            L = polyElements.get(i).getTop().getX();
                            D = polyElements.get(i0).getTop().getX();
                        }
                        break;
                    }
                } else {
                    /* otherwise it's a regular edge, find the intersection */
                    x = (-polyElements.get(i0).getEdge().getB() * y - polyElements.get(i0).getEdge().getC())
                            / polyElements.get(i0).getEdge().getA();
                    if (polyElements.get(i0).isLeft()) {
                        if (L < x) L = x;
                    } else {
                        if (D > x) D = x;
                    }
                }
            }
            plotLine(round(L), y, round(D), y, gl2);
        }
    }

    private void checkIfPolygonIsConvex(ArrayList<iPolyElement> polyElements,
                                        int n,
                                        int[] conv, int[] orij, GL2 gl2) {
        int i, i0, r;
        int above, below, at;
        below = above = at = 0;
        i0 = n - 2;
        for (i = 0; i < n; i++, i0++) {
            if (i0 >= n) i0 = 0;
            r = (int) (polyElements.get(i0).getEdge().getA() * polyElements.get(i).getTop().getX()
                    + polyElements.get(i0).getEdge().getB() * polyElements.get(i).getTop().getY()
                    + polyElements.get(i0).getEdge().getC());
            if (r == 0) at++;
            else if (r > 0) above++;
            else below++;
        }
//        conv = 0;
//        orij = 0;
//        if (below == 0) {
//            conv = 1;
//        } else if (above == 0) {
//            conv = 1;
//            orij = 1;
//        }
    }

}