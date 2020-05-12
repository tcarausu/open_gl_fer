package utility;

import glm.vec._3.Vec3;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Cube_Render {


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
//        cubelab5(triangleLineWithABCDValues, gl2);
//        cube(1, gl2);
        gl2.glPopMatrix();
        gl2.glColor3f(0.0f, 0.2f, 1.0f);
        gl2.glPushMatrix();
        gl2.glTranslatef(10.0f, 0.0f, 0.0f);
        gl2.glRotatef(30.0f, 0.0f, 0.0f, 1.0f);
        gl2.glScalef(5.0f, 5.0f, 5.0f);
//        cubelab5(triangleLineWithABCDValues, gl2);

//        cube(1, gl2);

        gl2.glPopMatrix();
    }
//    private static void getVectorValues() throws FileNotFoundException {
//        Scanner sc = new Scanner(new File(Constant.kocka));
//
//        while (sc.hasNext()) {
//            String line = sc.nextLine();
//
//            if (line.startsWith("//") || line.startsWith(" ")
//                    || line.startsWith("#") || line.startsWith("g ")
//                    || line.length() == 0) {
//                continue;
//            }
//
//            if (line.startsWith("v")) {
//                vectorsTopLine = line.split(" ");
//                for (String element : vectorsTopLine) {
//                    if (element.contains("v")) continue;
//                    double elementValue = Double.parseDouble(element);
//                    elementValues.add(elementValue);
//                    if (elementValues.size() % 3 == 0) {
//                        int firstElValueFromV = (int) elementValues.get(counterElements.get()).doubleValue();
//                        int secondElValueFromV = (int) elementValues.get(counterElements.get() + 1).doubleValue();
//                        int thirdElValueFromV = (int) elementValues.get(counterElements.get() + 2).doubleValue();
//                        Vec3 currentVector = new Vec3(firstElValueFromV, secondElValueFromV, thirdElValueFromV);
//                        boolean betweenMin1and1 = true;
//
//                        if (firstElValueFromV < -1 || firstElValueFromV > 1) {
//                            betweenMin1and1 = false;
//                        }
//                        if (secondElValueFromV < -1 || secondElValueFromV > 1) {
//                            betweenMin1and1 = false;
//                        }
//                        if (thirdElValueFromV < -1 || thirdElValueFromV > 1) {
//                            betweenMin1and1 = false;
//                        }
//
//                        vec3s.add(currentVector);
//                        counterElements.getAndAdd(3);
//
//
//                    }
//                }
//
//            }
//        }
//
//        String s = "v";
//
//    }






    public static LinkedList<iPoint2D> getCubeValues(File fileToTest) throws FileNotFoundException {
        Scanner sc = new Scanner(fileToTest);

        while (sc.hasNext()) {
            String line = sc.nextLine();

            if (line.startsWith("//") || line.startsWith(" ")
                    || line.startsWith("#") || line.startsWith("g ")
                    || line.length() == 0) {
                continue;
            }


//            if (line.startsWith("v")) {
//                vectorsTopLine = line.split(" ");
//                for (String element : vectorsTopLine) {
//                    if (element.contains("v")) continue;
//                    double elementValue = Double.parseDouble(element);
//                    elementValues.add(elementValue);
//                    if (elementValues.size() % 3 == 0) {
//                        double firstElValueFromF = elementValues.get(counterElements.get());
//                        double sElValueFromF = elementValues.get(counterElements.get() + 1);
//                        double thElValueFromF = elementValues.get(counterElements.get() + 2);
//                        vec3s.add(new Vec3(firstElValueFromF, sElValueFromF, thElValueFromF));
//                        counterElements.getAndAdd(3);
//                    }
//                }
//            }
        }
//        return points; //2d
        return null;
    }

    static void drawControlPolygon(LinkedList<iPoint2D> points, GL2 gl2) {
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

}
