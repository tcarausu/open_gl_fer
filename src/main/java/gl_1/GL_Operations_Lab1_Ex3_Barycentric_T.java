package gl_1;

//import com.google.common.util.concurrent.AtomicDouble;


import glm.mat._3.Mat3;
import glm.vec._3.Vec3;

import java.util.Arrays;
import java.util.Scanner;

public class GL_Operations_Lab1_Ex3_Barycentric_T {
    private static Vec3 A = new Vec3(
//            2, 0, 0
    );
    private static Vec3 B = new Vec3(
//            0, 5, 0
    );
    private static Vec3 C = new Vec3(
//            0, 0, 1
    );
    //
    private static Vec3 T = new Vec3(
//            1, 0.125, 0.25
    );
    private static double[] valueToVector = new double[0];

//    private static AtomicDouble x = new AtomicDouble();
//    private static AtomicDouble y = new AtomicDouble();
//    private static AtomicDouble z = new AtomicDouble();
//    private static AtomicDouble res = new AtomicDouble();

    private static float[][] matrix = {};

    public static void main(String[] args) {
        System.out.println("Please type your coordinates:");
        Scanner sc = new Scanner(System.in); // 2,0,0;0,5,0;0,0,1;1,0.125,0.25;
        int chunk = 3; // chunk size to divide
        String line = sc.nextLine();
        String[] lines = line.split(";");
        String[] values = Arrays.toString(lines).split(",");
        for (String val : values) {
            if (val.contains("]")) {
                val = val.replace("]", "");
                valueToVector = addElement(valueToVector, Double.parseDouble(val));
            } else if (val.contains("[")) {
                val = val.replace("[", "");
                valueToVector = addElement(valueToVector, Double.parseDouble(val));
            } else {
                valueToVector = addElement(valueToVector, Double.parseDouble(val));
            }
        }

        sc.close();

        setMatrixVectors(chunk);
        Mat3 matrixToInvert = new Mat3(
                A.x, B.x, C.x,
                A.y, B.y, C.y,
                A.z, B.z, C.z
        );

        Mat3 invertedMatrix = new Mat3();
        matrixToInvert.inverse(invertedMatrix);

        System.out.println(" \nOriginal Matrix is:");
        matrixToInvert.print();
        System.out.println(" \nInverted Matrix is:");
        invertedMatrix.print();

        Vec3 total = invertedMatrix.mul_(T);
        System.out.println(" \na) result is:");
        total.print();
    }

    private static void setMatrixVectors(int chunk) {
        float[][] valuesT = retrieveMatrix(chunk, valueToVector);
        A = new Vec3(valuesT[0][0], valuesT[0][1], valuesT[0][2]);
        A.print();
        System.out.println("VectorA: ");
        B = new Vec3(valuesT[1][0], valuesT[1][1], valuesT[1][2]);
        B.print();
        System.out.println("VectorB: ");
        C = new Vec3(valuesT[2][0], valuesT[2][1], valuesT[2][2]);
        C.print();
        System.out.println("VectorC: ");
        T = new Vec3(valuesT[3][0], valuesT[3][1], valuesT[3][2]);
        T.print();
        System.out.println("VectorT: ");
    }

    private static float[][] retrieveMatrix(int chunk, double[] values) {
        for (int i = 0; i < values.length; i += chunk) {
            double[] arrayV = Arrays.copyOfRange(values, i, Math.min(values.length, i + chunk));
//            x.getAndAdd(arrayV[0]);
//            y.getAndAdd(arrayV[1]);
//            z.getAndAdd(arrayV[2]);
//
//            float[][] row = {
//                    {(float) x.get(), (float) y.get(), (float) z.get()}
//            };
            valueReset();
            if (matrix.length < 5) {
//                matrix = append(matrix, row);
            }
        }
        return matrix;

    }

    private static double[] addElement(double[] a, double e) {
        a = Arrays.copyOf(a, a.length + 1);
        a[a.length - 1] = e;
        return a;
    }

    public static float[][] append(float[][] a, float[][] b) {
        float[][] result = new float[a.length + b.length][];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    private static void valueReset() {
//        x.set(0);
//        y.set(0);
//        z.set(0);
//        res.set(0);
    }
}
