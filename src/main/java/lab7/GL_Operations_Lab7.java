package lab7;

import glm.mat._4.Mat4;
import glm.vec._3.Vec3;
import glm.vec._4.Vec4;
import utility.ABCDEquation;
import utility.Constant;
import utility.iPoint3D;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
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

public class GL_Operations_Lab7 implements GLEventListener {
    private static float IaRed, IaGreen, IaBlue;
    private static float IdRed, IdGreen, IdBlue;
    private static float IrRed, IrGreen, IrBlue;

    private static float kaRed, kaGreen, kaBlue;
    private static float kdRed, kdGreen, kdBlue;
    private static float krRed, krGreen, krBlue;
    private static float ksRed, ksGreen, ksBlue;

    private static float illuminationRed, illuminationGreen, illuminationBlue;

    private static final float indexOfRoughnessN = 96f;

    private static final Vec3 v1 = new Vec3(1, 1, 3);
    private static final Vec3 v2 = new Vec3(1, 2, 3);
    private static final Vec3 v3 = new Vec3(2, 3, 3);
    private static final Vec3 original_G = new Vec3(0, 0, 0); // x,y,z (s)
    private static Vec3 cubeO; // x,y,z (s)
    private static final Vec3 lightSource = new Vec3(3, 3, 3);
    private static final Vec3 lightVector = new Vec3(4, 5, 3);

    private static LinkedHashMap<String, LinkedHashMap<ABCDEquation, ArrayList<Vec3>>> triangleLineWithABCDValues = new LinkedHashMap<>();
    private static final AtomicInteger currentIPosition = new AtomicInteger();

    @Override
    public void display(GLAutoDrawable drawable) {

        coefficientsSetup();

        GL2 gl = drawable.getGL().getGL2();

        gl.glClearColor(1, 1, 1, 1);

//      Clear The Screen And The Depth Buffer
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();


        //MD: This should all be deleted. You need to implement this
        viewWithEye(gl);

        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_NORMALIZE);

        // weak RED ambient
        float[] ambientLight = {0.1f, 0.f, 0.f, 0f};
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0);
        // multicolor diffuse
        float[] diffuseLight = {1f, 2f, 1f, 0f};
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0);

    /*    // specularLight
        float[] specularLight = {0f, 0f, 0f, 1f};
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specularLight, 0);
    */
    }

    private void coefficientsSetup() {
        //intensities for ambient/diffuse/reflective (the I coefficient)
//my coefficients are from 0-1 instead of 0-255, but that is just an implementation detail, scale them appropriately
        IaRed = 0.8f;

        IaGreen = IaBlue = 0.1f;

        IdRed = 0.8f;

        IdGreen = 0.15f;

        IdBlue = 0f;

        IrRed = IrGreen = 0.2f;

        IrBlue = 0f;

        //material coefficients for ambient/diffuse/specular (the K coefficient)

        kaRed = kaGreen = kaBlue = 1;

        kdRed = kdGreen = kdBlue = 1;

        krRed = krGreen = krBlue = 0.01f;
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

    private static LinkedList<iPoint3D> getPolyPoints(LinkedList<iPoint3D> points) {
        LinkedList<iPoint3D> point3DS = new LinkedList<>();
        int n = points.size() - 1;
        int[] factors = new int[points.size()];
        double t, b;
        float px, py, pz;
        compute_factors(n, factors);

        int divs = 1000;
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

    public static void viewWithEye(GL2 gl) {

        // MD: not needed anymore. The cube O should be equal to the O which the user sets, example 1,1,3
        cubeO = new Vec3(v1);
//        iPoint3D currentPoint = pointsList.get(pointsList.size() - 1);
//        cubeO = new Vec3(currentPoint.getX(), currentPoint.getY(), currentPoint.getZ());

        Mat4 matrixT = TandGs(cubeO, original_G); //original_G = 0,0,0
//        gl.glColor3f(0.0f, 0.0f, 1.0f);

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

                double coeff = pointX * a + pointY * b + pointZ * c + d;

                if (coeff > 0) {
                    //    Draw it
                    ArrayList<Vec3> valueVectors = entry.getValue();
                    // Default Values for the Cube (lab4)
                    Vec3 firstVector = valueVectors.get(0);
                    Vec3 secondVector = valueVectors.get(1);
                    Vec3 thirdVector = valueVectors.get(2);

                    //TODO calculate the center of the polygon
                    Vec3 centerOfPolygon = getCentralPoint(firstVector, secondVector, thirdVector);

                    //TODO calculate the value of the ambient component

                    //TODO calculate the value of the diffuse component

                    //TODO the formulas for the calculation are in the lab assignment
                    // or you can find them here https://en.wikipedia.org/wiki/Phong_reflection_model#Description


                    // Multiply Matrix T with each of the 3 Vectors
                    Vec4 firstM = matrixT.mul_(new Vec4(firstVector, 1));
                    Vec4 secondM = matrixT.mul_(new Vec4(secondVector, 1));
                    Vec4 thirdM = matrixT.mul_(new Vec4(thirdVector, 1));

                    calculateIntensity(centerOfPolygon,equation.getNormal());


                    gl.glBegin(gl.GL_LINE_LOOP);

                    gl.glNormal3f((float) a, (float) b, (float) c);

//                    gl.glNormal3f(equation.getNormal().x,equation.getNormal().y,equation.getNormal().z);

                    //TODO here before each vertex call the gl.color method to set the colour for each
                    //vertex. In this case it will be same for each vertex

                    gl.glColor3f(0.0f, 0.0f, 1.0f);
                    gl.glVertex3d(firstM.x / firstM.w, firstM.y / firstM.w, 0);
                    gl.glVertex3d(secondM.x / secondM.w, secondM.y / secondM.w, 0);
                    gl.glVertex3d(thirdM.x / thirdM.w, thirdM.y / thirdM.w, 0);

                    gl.glEnd();

                    gl.glFlush();
                }
            }
        });

    }


    private static void calculateIntensity(Vec3 centralPoint, Vec3 normal) {
        float Igr, Igg, Igb, Idr, Idg, Idb, Isr, Isg, Isb;

        //Ambient Component?
        Igr = IaRed * kaRed;
        Igg = IaGreen * kaGreen;
        Igb = IaBlue * kaBlue;


        //TODO        what is the right one from these 3?
        //3.(3),4,2.(6)
        Vec3 vectorL = new Vec3(lightVector.x - centralPoint.x, lightVector.y - centralPoint.y, lightVector.z - centralPoint.z);
//        Vec3 vectorL = lightVector.sub_(centralPoint); //3.(3),2.(6),0
//        Vec3 vectorL = lightVector.sub(centralPoint); //3.(3),2.(6),3

        Vec3 prodCosId = normal.mul_(vectorL);
        //TODO how do I get a double/int
        //Diffuse Component?
        Idr = IdRed * kdRed * prodCosId;
        Idg = IdGreen * kdGreen * prodCosId;
        Idb = IdBlue * kdBlue * prodCosId;

        Vec3 r = reflectedV(vectorL, centralPoint, normal);
        Vec3 v = new Vec3(cubeO.x - centralPoint.x, cubeO.y - centralPoint.y, cubeO.z - centralPoint.z);
//        Vec3 v = cubeO.sub(centralPoint);

        Vec3 prodCosIs = r.mul_(v);

        //Specular Component?
        Isr = IrRed * ksRed * (float) Math.pow(prodCosIs, indexOfRoughnessN);
        Isg = IrGreen * ksGreen * (float) Math.pow(prodCosIs, indexOfRoughnessN);
        Isb = IrBlue * ksBlue * (float) Math.pow(prodCosIs, indexOfRoughnessN);

        illuminationRed = Igr + Idr + Isr;
        illuminationGreen = Igg + Idg + Isg;
        illuminationBlue = Igb + Idb + Isb;
    }

    private static Vec3 reflectedV(Vec3 lightV, Vec3 center, Vec3 normal) {

        Vec3 eye = new Vec3(3, 4, 1);  //or do I use here v1 as I used for CubeO?
        Vec3 eyeVec = eye.sub_(center); //the vector from the eye to the center of polygon
        eyeVec.normalize_(); // normalise all the vectors!
        Vec3 lightVec = lightV.sub_(center); // the L vector, as denoted previously
        lightVec.normalize_(); //normalise

//        Vec3 r = Vec3.Sub((2 * Vector3.Dot(normal, lightVec)) * normal, lightVec); // r is the reflected vector
        Vec3 dotted = normal.mul(normal.dot(lightV)).mul(2);
        Vec3 r = dotted.sub_(lightVec); // r is the reflected vector
// here you can see the equation for it as well https://www.fabrizioduroni.it/2017/08/25/how-to-calculate-reflection-vector.html

        r.normalize_();
        return r;
    }


    static Vec3 getCentralPoint(Vec3 V1, Vec3 V2, Vec3 V3) {
        float Cx, Cy, Cz;

        Cx = (V1.x + V2.x + V3.x) / 3;
        Cy = (V1.y + V2.y + V3.y) / 3;
        Cz = (V1.z + V2.z + V3.z) / 3;

        return new Vec3(Cx, Cy, Cz);
    }

    public static void main(String[] args) throws FileNotFoundException {
        triangleLineWithABCDValues = setupVectorsAndTriangles(new File(Constant.kocka)); // Cube

        LinkedList<iPoint3D> point3DS = new LinkedList<>();
        point3DS.add(new iPoint3D(v1.x, v1.y, v1.z));
        point3DS.add(new iPoint3D(v2.x, v2.y, v2.z));
        point3DS.add(new iPoint3D(v3.x, v3.y, v3.z));

        LinkedList<iPoint3D> pointsList = getPolyPoints(point3DS);

        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        GL_Operations_Lab7 l = new GL_Operations_Lab7();

        glcanvas.addGLEventListener(l);
        glcanvas.setSize(700, 700);


        final JFrame frame = new JFrame("lab7");
        //adding canvas to frame
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}
