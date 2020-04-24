package gl_3;

import com.jogamp.opengl.util.FPSAnimator;
import glm.vec._2.Vec2;
import utility.iPoint2D;
import utility.iPolyElement;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;

public class GL_Operations_Lab3_Ex1 implements GLEventListener {
    private static final Vec2 V1 = new Vec2(50, 200);
    private static final Vec2 V2 = new Vec2(150, 350);
    private static final Vec2 V3 = new Vec2(300, 150);
    private static final Vec2 V4 = new Vec2(100, 50);

//    private static final Vec2 V1 = new Vec2(200, 350);
//    private static final Vec2 V2 = new Vec2(300, 500);
//    private static final Vec2 V3 = new Vec2(450, 300);
//    private static final Vec2 V4 = new Vec2(250, 200);
//
//    private static final Vec2 V1 = new Vec2(200, 350);
//    private static final Vec2 V2 = new Vec2(300, 350);
//    private static final Vec2 V3 = new Vec2(400, 100);
//    private static final Vec2 V4 = new Vec2(100, 100);

    private static final ArrayList<iPolyElement> iPolyElements = new ArrayList<>();
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private static final Point2D vPoint = new Point(100, 250);
//    private static final Point2D vPoint = new Point(150, 350);

    private static boolean isClockwise = true;
    private static boolean isConvex = true;
    private static float mouseX;
    private static float mouseY;

    @Override
    public void display(GLAutoDrawable drawable) {

        final GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, 1000, 1000);
        gl.glMatrixMode(gl.GL_PROJECTION_MATRIX);
        gl.glLoadIdentity();
        drawable.getGL().getGL2().glOrtho(0, 1000, 0, 1000, 0, 1);
        gl.glMatrixMode(gl.GL_MODELVIEW_MATRIX);

        gl.glClearColor(1, 1, 1, 0);
        gl.glClear(GL_COLOR_BUFFER_BIT);
        //static field
        gl.glColor3f(0, 0, 0);

        gl.glBegin(gl.GL_LINES);

        computingCoefficientConvexPolygon(gl);
        fillConvexPolygon(gl);

//
//        boolean in;
//        System.out.println("clicked");
//        try {
//            in = checkIsMouseIn(iPolyElements, (int) vPoint.getX(), (int)( screenSize.height - vPoint.getY()));
//        } catch (Exception e1) {
//            System.out.println("Point V with coordinates: (" + vPoint.getX() + ", " + (screenSize.height - vPoint.getY()) + ")" +
//                    "On the edge !");
//
//            return;
//        }
//        if (in)
//            System.out.println("Point V with coordinates: (" + vPoint.getX() + ", " + (screenSize.height - vPoint.getY())
//                    + ") IS INSIDE OF POLYGON !");
//        else
//            System.out.println("Point V with coordinates: (" + vPoint.getX() + ", " + (screenSize.height - vPoint.getY())
//                    + ") IS NOT INSIDE OF POLYGON");

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


    private void computingCoefficientConvexPolygon(GL2 gl2) {
        int i, i0, n;
        n = iPolyElements.size();
        i0 = n - 1;
        for (i = 0; i < n; i++) {
            iPolyElement elemi0 = iPolyElements.get(i0);
            iPolyElement elemi = iPolyElements.get(i);

            double x0 = elemi0.getTop().getX();
            double y0 = elemi0.getTop().getY();

            double x1 = elemi.getTop().getX();
            double y1 = elemi.getTop().getY();

            elemi0.getEdge().setA(y0 - y1);
            elemi0.getEdge().setB(-(x0 - x1));
            elemi0.getEdge().setC(x0 * y1 - y0 * x1);

            if (isClockwise)
                elemi0.setLeft(elemi0.getTop().getY() < elemi.getTop().getY());
            else
                elemi0.setLeft(elemi0.getTop().getY() > elemi.getTop().getY());

            gl2.glVertex2f((float) x0, (float) y0);
            gl2.glVertex2f((float) x1, (float) y1);

            i0 = i;
        }
    }

    private void fillConvexPolygon(GL2 gl2) {
        int i, i0, y;
        int n = iPolyElements.size();
        int xmin, xmax, ymin, ymax;
        double L, D, x;
        /* Search for minimum and maximum coordinates      */
        xmin = xmax = (int) iPolyElements.get(0).getTop().getX();
        ymin = ymax = (int) iPolyElements.get(0).getTop().getY();

        for (i = 1; i < n; i++) {
            iPolyElement elemi = iPolyElements.get(i);

            if (xmin > elemi.getTop().getX()) xmin = (int) elemi.getTop().getX();
            if (xmax < elemi.getTop().getX()) xmax = (int) elemi.getTop().getX();
            if (ymin > elemi.getTop().getY()) ymin = (int) elemi.getTop().getY();
            if (ymax < elemi.getTop().getY()) ymax = (int) elemi.getTop().getY();
        }
        /*Polygon coloring: for every y between ymin and ymax it works ... */
        for (y = ymin; y <= ymax; y++) {
            /* Find the largest Lijevo(Left) least right intersection. . . */
            L = xmin;
            D = xmax;
            i0 = n - 1;
            /* "i0" is the beginning of the hill, and "i" is the end of the hill */
            for (i = 0; i < n; i0 = i++) {
                /* if the edge is horizontal */
                iPolyElement elemi0 = iPolyElements.get(i0);
                iPolyElement elemi = iPolyElements.get(i);

                if (elemi0.getEdge().getA() == 0) {
                    if (elemi0.getTop().getY() == y) {
                        if (elemi0.getTop().getX() < elemi.getTop().getX()) {
                            L = elemi0.getTop().getX();
                            D = elemi.getTop().getX();
                        } else {
                            L = elemi.getTop().getX();
                            D = elemi0.getTop().getX();
                        }
                        break;
                    }
                } else {
                    /* otherwise it's a regular edge, find the intersection */
                    x = (-elemi0.getEdge().getB() * y - elemi0.getEdge().getC())
                            / elemi0.getEdge().getA();
                    if (elemi0.isLeft()) {
                        if (L < x) L = x;
                    } else {
                        if (D > x) D = x;
                    }
                }
            }
//            plotLine(round(L), y, round(D), y, gl2);
            gl2.glVertex2f((float) L, (float) y);
            gl2.glVertex2f((float) D, (float) y);
        }
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

//    private void checkIfPolygonIsConvex() {
//        int i, i0, r, n;
//        n = iPolyElements.size();
//        int above, below, at;
//        below = above = at = 0;
//        i0 = n - 2;
//        iPolyElement elemi0 = iPolyElements.get(i0);
//        iPolyElement elemi = iPolyElements.get(i);
//        for (i = 0; i < n; i++, i0++) {
//
//            if (i0 >= n) i0 = 0;
//            r = (int) (elemi0.getEdge().getA() * elemi.getTop().getX()
//                    + elemi0.getEdge().getB() * elemi.getTop().getY()
//                    + elemi0.getEdge().getC());
//            if (r == 0) at++;
//            else if (r > 0) above++;
//            else below++;
//        }
//
//        isConvex = false;
//        isClockwise = false;
//        if (below == 0) {
//            isConvex = true;
//        } else if (above == 0) {
//            isConvex = true;
//            isClockwise = true;
//        }
//    }

    private static void checkIfPolygonIsConvex() {
        int i, i0, r, n;
        int above, below, at;
        below = above = at = 0;
        n = iPolyElements.size();
        i0 = n - 2;
        for (i = 0; i < n; i++, i0++) {
            if (i0 >= n) i0 = 0;
            r = (int) (iPolyElements.get(i0).getEdge().getA() * iPolyElements.get(i).getTop().getX()
                    + iPolyElements.get(i0).getEdge().getB() * iPolyElements.get(i).getTop().getY()
                    + iPolyElements.get(i0).getEdge().getC());
            if (r == 0) at++;
            else if (r > 0) above++;
            else below++;
        }

        isConvex = false;
        if (below == 0) {
            isConvex = true;
            isClockwise = false;
        } else if (above == 0) {
            isConvex = true;
            isClockwise = true;
        }
        correctOrientation();
    }
        public static void correctOrientation() {
        if (iPolyElements.size() < 2)
            return;
        int i0 = iPolyElements.size() - 1;
        for (int i = 0; i < iPolyElements.size(); i++) {
            iPolyElement elemi0 = iPolyElements.get(i0);
            iPolyElement poleli = iPolyElements.get(i);

            if (isClockwise)
                elemi0.setLeft(elemi0.getTop().getY() < poleli.getTop().getY());
            else
                elemi0.setLeft(elemi0.getTop().getY() > poleli.getTop().getY());
            i0 = i;
        }
    }
    public static boolean checkIsMouseIn( int x, int y) throws Exception {
        checkIfPolygonIsConvex();
        boolean mightBeOnTheEdge = false;
        double r;
        int i, i0, n;
        n = iPolyElements.size();
        i0 = n - 1;
        for (i = 0; i < n; i++) {
            iPolyElement elemi0 = iPolyElements.get(i0);
            iPolyElement elemi = iPolyElements.get(i);

            double a0 = elemi0.getEdge().getA();
            double b0 = elemi0.getEdge().getB();
            double c0 = elemi0.getEdge().getC();

            double a1 = elemi.getEdge().getA();
            double b1 = elemi.getEdge().getB();
            double c1 = elemi.getEdge().getC();

            r = a1 * x + b1 * y + c1;
//            r = a0 * x + b0 * y + c0;
            if (r == 0)
                mightBeOnTheEdge = true;
            if (isClockwise) {
                if (r > 0)
                    return false;
                if (r < 0)
                    return true;
            }
            else {
                if (r > 0)
                    return true;
                if (r < 0)
                    return false;
            }
        }
        if (mightBeOnTheEdge)
            throw new Exception();
        return true;
    }

    public static void main(String[] args) {
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        GL_Operations_Lab3_Ex1 l = new GL_Operations_Lab3_Ex1();
        glcanvas.requestFocus();
        glcanvas.addGLEventListener(l);
        glcanvas.setSize(screenSize.width, screenSize.height);

        FPSAnimator animator = new FPSAnimator(glcanvas, 60);
        animator.start();

        //creating frame
        final JFrame frame = new JFrame("lab2");
        //adding canvas to frame
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        iPolyElements.add(new iPolyElement(new iPoint2D(V1.x, V1.y)));
        iPolyElements.add(new iPolyElement(new iPoint2D(V2.x, V2.y)));
        iPolyElements.add(new iPolyElement(new iPoint2D(V3.x, V3.y)));
        iPolyElements.add(new iPolyElement(new iPoint2D(V4.x, V4.y)));

        System.out.println("please input your coordinates for V: ");

        glcanvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mouseX = e.getX();
                mouseY = screenSize.height - e.getY();
                boolean in;
                System.out.println("clicked");
                try {
                    in = checkIsMouseIn(e.getX(), screenSize.height - e.getY());
                } catch (Exception e1) {
                    System.out.println("Point V with coordinates: (" + e.getX() + ", " + (screenSize.height - e.getY()) + ")" +
                            "On the edge !");

                    return;
                }
                if (in)
                    System.out.println("Point V with coordinates: (" + e.getX() + ", " + (screenSize.height - e.getY()) + ") IS INSIDE OF POLYGON !");
                else
                    System.out.println("Point V with coordinates: (" + e.getX() + ", " + (screenSize.height - e.getY()) + ") IS NOT INSIDE OF POLYGON");

            }
        });

    }

}