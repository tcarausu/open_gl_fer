package gl_1;

import glm.mat._3.Mat3;
import glm.vec._3.Vec3;

public class BarycentricT {
    private static Vec3 A = new Vec3(2, 0, 0);
    private static Vec3 B = new Vec3(0, 5, 0);
    private static Vec3 C = new Vec3(0, 0, 1);

    private static Vec3 T = new Vec3(1, 0.125, 0.25);

    public static void main(String[] args) {

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
}
