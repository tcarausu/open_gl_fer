package gl_1;

import glm.mat._3.Mat3;
import glm.vec._3.Vec3;

public class GL_Operations_Lab1_Ex1 {
    private static Vec3 i = new Vec3(1, 0, 0);
    private static Vec3 j = new Vec3(0, 1, 0);
    private static Vec3 k = new Vec3(0, 0, 1);

    private static Vec3 firstNr = new Vec3(2, 3, -4);
    private static Vec3 secNr = new Vec3(-1, 4, -1);

    private static Mat3 firstMat = new Mat3(
            1, 2, 3,
            2, 1, 3,
            4, 5, 1
    );

    private static Mat3 secondMat = new Mat3(
            -1, 2, -3,
            5, -2, 7,
            -4, -1, 3
    );

    private static Mat3 identityMatrix = new Mat3();

    public static void main(String[] args) {
        //Identity Matrix
        identityMatrix.set(
                i.x, i.y, i.z,
                j.x, j.y, j.z,
                k.x, k.y, k.z
        );
        //MATRIX A multiplied by Vector X => B and from here Getting X => A-1 (Inverse) * B
        //MATRIX A multiplied by Vector X => B and from here Getting X => A-1 (Inverse) * B
        //MATRIX A multiplied by Vector X => B and from here Getting X => A-1 (Inverse) * B
        //MATRIX A multiplied by Vector X => B and from here Getting X => A-1 (Inverse) * B
        //MATRIX A multiplied by Vector X => B and from here Getting X => A-1 (Inverse) * B


        //Vectors
        Vec3 v1 = additionV(firstNr, secNr);
        int s = dotProd(v1, secNr);

        Vec3 v2 = crossProd(v1, new Vec3(2, 2, 4));

        double magv3 = magnitudeVector(v2); //magnitude of vector
        Vec3 v3 = unitOfVector(v2);

        Vec3 v4 = oppositeVector(v2);

        //Matrices
        Mat3 m1 = additionMat(firstMat, secondMat);
        Mat3 trans2 = transposeMatrix(secondMat, new Mat3());
        Mat3 m2 = matrixProduct(firstMat, trans2);

        Mat3 inv2 = inverseMatrix(secondMat, new Mat3());

        Mat3 m3 = matrixProduct(firstMat, inv2);

        System.out.println("\nAdditionOfVectors");
        System.out.println("V1 = " + v1);

        System.out.println("\nDotProdOfVectors");
        System.out.println("s = " + s);

        System.out.println("\nCrossProdOfVectors");
        System.out.println("V2 = " + v2);

        System.out.println("\nMagnitude of Vector");
        System.out.println("Magnitude of V3 = " + magv3);

        System.out.println("\nUnitVector");
        System.out.println("V3 = " + v3);

        System.out.println("\nOpposite Vector");
        System.out.println("V4 = " + v4);

        System.out.println("\nMatrix Addition");
        System.out.println("M1 = " + printMatrix(m1));

        System.out.println("\nMatrix Multiplication with Transpose");
        System.out.println("Transposed2 = " + printMatrix(trans2));
        System.out.println("M2 = " + printMatrix(m2));


        System.out.println("\nMatrix Multiplication with Inverse");
        System.out.println("First = " + printMatrix(firstMat));
        System.out.println("Inverse2 = " + printMatrix(inv2));
        System.out.println("SecondMat = " + printMatrix(secondMat));
        System.out.println("M3 = " + printMatrix(m3));

        System.out.println("Identity = " + printMatrix(matrixProduct(secondMat, inv2)));


    }

    public static Vec3 additionV(Vec3 first, Vec3 second) {
        float additionX = (first.x + second.x);
        float additionY = (first.y + second.y);
        float additionZ = (first.z + second.z);

        return new Vec3(additionX, additionY, additionZ);
    }

    public static double magnitudeVector(Vec3 unitV) {
        double unitVectorX = Math.pow(unitV.x, 2);
        double unitVectorY = Math.pow(unitV.y, 2);
        double unitVectorZ = Math.pow(unitV.z, 2);

        return Math.sqrt(unitVectorX + unitVectorY + unitVectorZ);
    }

    private static Vec3 unitOfVector(Vec3 first) {
        double additionX = (first.x / magnitudeVector(first));
        double additionY = (first.y / magnitudeVector(first));
        double additionZ = (first.z / magnitudeVector(first));

        return new Vec3(additionX, additionY, additionZ);
    }

    public static int dotProd(Vec3 first, Vec3 second) {
        float additionX = (first.x * second.x);
        float additionY = (first.y * second.y);
        float additionZ = (first.z * second.z);
        return (int) (additionX + additionY + additionZ);
    }

    public static Vec3 crossProd(Vec3 first, Vec3 second) {
//  u1,u2,u3 x v1,v2,v3
//  u2v3-u3v2,u3v1-u1v3,u1v2-u2v1
        float firstX = (first.y * second.z) - (first.z * second.y);
        float firstY = (first.z * second.x) - (first.x * second.z);
        float firstZ = (first.x * second.y) - (first.y * second.x);

        return new Vec3(firstX, firstY, firstZ);
    }

    public static Vec3 oppositeVector(Vec3 unitV) {
        float additionX = unitV.x * -1;
        float additionY = unitV.y * -1;
        float additionZ = unitV.z * -1;

        return new Vec3(additionX, additionY, additionZ);
    }

    public static Mat3 additionMat(Mat3 first, Mat3 second) {
        float additionM00 = (first.m00 + second.m00);
        float additionM01 = (first.m01 + second.m01);
        float additionM02 = (first.m02 + second.m02);

        float additionM10 = (first.m10 + second.m10);
        float additionM11 = (first.m11 + second.m11);
        float additionM12 = (first.m12 + second.m12);

        float additionM20 = (first.m20 + second.m20);
        float additionM21 = (first.m21 + second.m21);
        float additionM22 = (first.m22 + second.m22);

        return new Mat3(
                additionM00, additionM01, additionM02,
                additionM10, additionM11, additionM12,
                additionM20, additionM21, additionM22
        );
    }

    private static Mat3 matrixProduct(Mat3 firstMat, Mat3 secondMat) {
        //MATRIX A multiplied by Vector X => B and from here Getting X => A-1 (Inverse) * B
        //MATRIX A multiplied by Vector X => B and from here Getting X => A-1 (Inverse) * B
        //MATRIX A multiplied by Vector X => B and from here Getting X => A-1 (Inverse) * B
        //MATRIX A multiplied by Vector X => B and from here Getting X => A-1 (Inverse) * B
        //MATRIX A multiplied by Vector X => B and from here Getting X => A-1 (Inverse) * B

        float matrixProductM00 = (firstMat.m00 * secondMat.m00) + (firstMat.m01 * secondMat.m10) + (firstMat.m02 * secondMat.m20);
        float matrixProductM01 = (firstMat.m00 * secondMat.m01) + (firstMat.m01 * secondMat.m11) + (firstMat.m02 * secondMat.m21);
        float matrixProductM02 = (firstMat.m00 * secondMat.m02) + (firstMat.m01 * secondMat.m12) + (firstMat.m02 * secondMat.m22);

        float matrixProductM10 = (firstMat.m10 * secondMat.m00) + (firstMat.m11 * secondMat.m10) + (firstMat.m12 * secondMat.m20);
        float matrixProductM11 = (firstMat.m10 * secondMat.m01) + (firstMat.m11 * secondMat.m11) + (firstMat.m12 * secondMat.m21);
        float matrixProductM12 = (firstMat.m10 * secondMat.m02) + (firstMat.m11 * secondMat.m12) + (firstMat.m12 * secondMat.m22);

        float matrixProductM20 = (firstMat.m20 * secondMat.m00) + (firstMat.m21 * secondMat.m10) + (firstMat.m22 * secondMat.m20);
        float matrixProductM21 = (firstMat.m20 * secondMat.m01) + (firstMat.m21 * secondMat.m11) + (firstMat.m22 * secondMat.m21);
        float matrixProductM22 = (firstMat.m20 * secondMat.m02) + (firstMat.m21 * secondMat.m12) + (firstMat.m22 * secondMat.m22);

        return new Mat3(
                matrixProductM00, matrixProductM01, matrixProductM02,
                matrixProductM10, matrixProductM11, matrixProductM12,
                matrixProductM20, matrixProductM21, matrixProductM22
        );
    }

    public static Mat3 transposeMatrix(Mat3 mat, Mat3 dest) {

        return mat.transpose(dest);

//        dest.set(
//                mat.m00, mat.m10, mat.m20,
//                mat.m01, mat.m11, mat.m21,
//                mat.m02, mat.m12, mat.m22);
//        return dest;

    }

    public static Mat3 inverseMatrix(Mat3 mat, Mat3 dest) {
        return mat.inverse_();

//        float s = 1.0f / determinant(mat);
//
//        float matrixProductM00 = (mat.m11 * mat.m22 - mat.m12 * mat.m21)
//                * s
//                ;
//        float matrixProductM01 = (mat.m21 * mat.m02 - mat.m22 * mat.m01)
//                * s
//                ;
//        float matrixProductM02 = (mat.m01 * mat.m12 - mat.m11 * mat.m02)
//                * s
//                ;
//        float matrixProductM10 = (mat.m20 * mat.m12 - mat.m10 * mat.m22)
//                * s
//                ;
//        float matrixProductM11 = (mat.m00 * mat.m22 - mat.m20 * mat.m02)
//                * s
//                ;
//        float matrixProductM12 = (mat.m10 * mat.m02 - mat.m00 * mat.m12)
//                * s
//                ;
//        float matrixProductM20 = (mat.m10 * mat.m21 - mat.m20 * mat.m11)
//                * s
//                ;
//        float matrixProductM21 = (mat.m20 * mat.m01 - mat.m00 * mat.m21)
//                * s
//                ;
//        float matrixProductM22 = (mat.m11 * mat.m22 - mat.m21 * mat.m12)
//                * s
//                ;
//
//        dest.set(
//                matrixProductM00,
//                matrixProductM01,
//                matrixProductM02,
//                matrixProductM10,
//                matrixProductM11,
//                matrixProductM12,
//                matrixProductM20,
//                matrixProductM21,
//                matrixProductM22
//        );

//        (mat.m11 * mat.m22 - mat.m21 * mat.m12) * s,
//                (mat.m21 * mat.m02 - mat.m01 * mat.m22) * s,
//                (mat.m01 * mat.m12 - mat.m11 * mat.m02) * s,
//                (mat.m20 * mat.m12 - mat.m10 * mat.m22) * s,
//                (mat.m00 * mat.m22 - mat.m20 * mat.m02) * s,
//                (mat.m10 * mat.m02 - mat.m00 * mat.m12) * s,
//                (mat.m10 * mat.m21 - mat.m20 * mat.m11) * s,
//                (mat.m20 * mat.m01 - mat.m00 * mat.m21) * s,
//                (mat.m00 * mat.m11 - mat.m10 * mat.m01) * s
//        return dest;
    }

    public static float determinant(Mat3 mat) {
        float a = (mat.m00 * mat.m11 - mat.m01 * mat.m10) * mat.m22;

        float b = (mat.m02 * mat.m10 - mat.m00 * mat.m12) * mat.m21;

        float c = (mat.m01 * mat.m12 - mat.m02 * mat.m11) * mat.m20;

        return
                a + b + c

//                (mat.m00 * mat.m11 - mat.m01 * mat.m10) * mat.m22
//                + (mat.m02 * mat.m10 - mat.m00 * mat.m12) * mat.m21
//                + (mat.m01 * mat.m12 - mat.m02 * mat.m11) * mat.m20
                ;
    }

    public static String printMatrix(Mat3 mat3) {
        return "Matrix to be Printed" + "\n"
                + "| " + mat3.m00 + " " + mat3.m01 + " " + mat3.m02 + " |\n"
                + "| " + mat3.m10 + " " + mat3.m11 + " " + mat3.m12 + " |\n"
                + "| " + mat3.m20 + " " + mat3.m21 + " " + mat3.m22 + " |\n";
    }

}
