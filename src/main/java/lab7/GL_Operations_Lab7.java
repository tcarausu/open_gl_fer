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
    private static final Vec3 lightSource = new Vec3(2, 3, 0);
//    private static final Vec3 lightVector = new Vec3(4, 5, 3);//MD not needed

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


//
//        // weak RED ambient
//        float[] ambientLight = {0.1f, 0.f, 0.f, 0f};
//        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0);
//        // multicolor diffuse
//        float[] diffuseLight = {1f, 2f, 1f, 0f};
//        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0);
//
//    /*    // specularLight
//        float[] specularLight = {0f, 0f, 0f, 1f};
//        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specularLight, 0);
//    */
    }

    private void coefficientsSetup() {
        //intensities for ambient/diffuse/reflective (the I coefficient)
//my coefficients are from 0-1 instead of 0-255, but that is just an implementation detail, scale them appropriately
        IaRed = 0.3f;

        IaGreen = IaBlue = 0.1f;

        IdRed = 0.5f;

        IdGreen = 0.15f;

        IdBlue = 0f;

        IrRed = IrGreen = 0.3f;

        IrBlue = 0f;

        //material coefficients for ambient/diffuse/specular (the K coefficient)

        kaRed = kaGreen = kaBlue = 1;

        kdRed = kdGreen = kdBlue = 1;

        krRed = krGreen = krBlue = 0.11f;
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
        cubeO = new Vec3(v3);

        Mat4 matrixT = TandGs(cubeO, original_G); //original_G = 0,0,0

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

                    Vec3 centerOfPolygon = getCentralPoint(firstVector, secondVector, thirdVector);

                    // Multiply Matrix T with each of the 3 Vectors
                    Vec4 firstM = matrixT.mul_(new Vec4(firstVector, 1));
                    Vec4 secondM = matrixT.mul_(new Vec4(secondVector, 1));
                    Vec4 thirdM = matrixT.mul_(new Vec4(thirdVector, 1));


//                    Vec3 normal = new Vec3(equation.getA(), equation.getB(), equation.getC());
//                    calculateIntensity(centerOfPolygon, normal);
                    calculateIntensity(centerOfPolygon, equation.getNormal());

                    gl.glBegin(gl.GL_POLYGON);

                    //TODO here before each vertex call the gl.color method to set the colour for each
                    //vertex. In this case it will be same for each vertex

//                    gl.glColor3f(0.0f, 0.0f, 1.0f);
                    System.out.println("il " + illuminationRed);

//                    gl.glColor3f(illuminationRed, 0, 0);
                    //assuming we use all 3 colors
                    gl.glColor3f(illuminationRed, illuminationGreen, illuminationBlue);

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

        //MD this is ok for ambient
        Igr = IaRed * kaRed;
        Igg = IaGreen * kaGreen;
        Igb = IaBlue * kaBlue;

        //TODO  what is the right one from these 3?
        //MD it should be lightsource.x - centralpoint.x and so for all components
        Vec3 vectorL = new Vec3(lightSource.x - centralPoint.x, lightSource.y - centralPoint.y, lightSource.z - centralPoint.z);
        //MD after this normalise vectorL!
        Vec3 normVecL = vectorL.normalize_();
        Vec3 normNormV = normal.normalize_();

        float cosId = normNormV.x * normVecL.x + normNormV.y * normVecL.y + normNormV.z * normVecL.z;

        //MD: do the dot product: normal.x+vectorL.x + normal.y+vectorL.y + normal.z+vectorL.z
        // it will be then a float number
        //MD also normalise normal vector!
        if (cosId < 0) {
            cosId = 0;
        }

        //TODO how do I get a double/int
        //Diffuse Component?
        //MD it is ok however, here you need to test if prodCosID is <0
        // if yes then you put Idr and all others to 0, if not
        //then they remain as you calculated them here
        Idr = IdRed * kdRed * cosId;
        Idg = IdGreen * kdGreen * cosId;
        Idb = IdBlue * kdBlue * cosId;

        Vec3 reflectedV = reflectedV(vectorL, centralPoint, normNormV);

        //MD: this is ok
        Vec3 eyeV = new Vec3(cubeO.x - centralPoint.x, cubeO.y - centralPoint.y, cubeO.z - centralPoint.z);

        //TODO select some of them
        //MD again do the dot product as for the diffuse.
        //float cosIs = reflectedV.x + eyeV.x + reflectedV.y + eyeV.y + reflectedV.z + eyeV.z;
        float cosIs = reflectedV.x * eyeV.normalize_().x + reflectedV.y * eyeV.normalize_().y + reflectedV.z * eyeV.normalize_().z;

        if (cosIs < 0) {
            cosIs = 0;
        }

        //Specular Component?
        //MD yes, again, just like for diffues if prodCosIs<0
        // then you put the intensity to 0, otherwise it stays like this
        Isr = IrRed * krRed * (float) Math.pow(cosIs, indexOfRoughnessN);
        Isg = IrGreen * krGreen * (float) Math.pow(cosIs, indexOfRoughnessN);
        Isb = IrBlue * krBlue * (float) Math.pow(cosIs, indexOfRoughnessN);

        illuminationRed = Igr + Idr + Isr;
        illuminationGreen = Igg + Idg + Isg;
        illuminationBlue = Igb + Idb + Isb;
    }

    private static Vec3 reflectedV(Vec3 lightV, Vec3 center, Vec3 normal) {

        //MD: use here v1
        Vec3 eye = new Vec3(v1);  //or do I use here v1 as I used for CubeO?

        Vec3 eyeVec = eye.sub_(center); //the vector from the eye to the center of polygon

        eyeVec.normalize_(); // normalise all the vectors!

        //MD: this seems to be ok
        Vec3 dotted = normal.mul(normal.dot(lightV)).mul(2);
        Vec3 r = dotted.sub_(lightV); // r is the reflected vector

        r.normalize_();
        return r;
    }


    static Vec3 getCentralPoint(Vec3 V1, Vec3 V2, Vec3 V3) {
        float Cx, Cy, Cz;

        //MD this is OK
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
