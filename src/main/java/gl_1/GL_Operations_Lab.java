package gl_1;

import glm.mat._3.Mat3;
import glm.vec._3.Vec3;

public class GL_Operations_Lab {
    private static Vec3 i = new Vec3(1, 0, 0);
    private static Vec3 j = new Vec3(0, 1, 0);
    private static Vec3 k = new Vec3(0, 0, 1);
    private static Mat3 identityMatrix = new Mat3();

    public static void main(String[] args) {
        identityMatrix.set(
                i.x, i.y, i.z,
                j.x, j.y, j.z,
                k.x, k.y, k.z
        );

        Vec3 firstNr = new Vec3(2, 3, -4);
        Vec3 secNr = new Vec3(-1, 4, -1);

        Vec3 v1 = additionV(firstNr, secNr);
        int s = dotProd(v1, secNr);

        Vec3 v2 = crossProd(v1, new Vec3(2, 2, 4));

        double magv3 = magnitudeVector(v2); //magnitude of vector
        Vec3 v3 = unitOfVector(v2);

        Vec3 v4 = oppositeVector(v2);

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

    }

    private static Vec3 unitOfVector(Vec3 first) {
        double additionX = (first.x / magnitudeVector(first));
        double additionY = (first.y / magnitudeVector(first));
        double additionZ = (first.z / magnitudeVector(first));

        return new Vec3(additionX, additionY, additionZ);
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

    public static Vec3 oppositeVector(Vec3 unitV) {
        float additionX = unitV.x * -1;
        float additionY = unitV.y * -1;
        float additionZ = unitV.z * -1;

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

    public static Vec3 crossProd(Vec3 first, Vec3 second, Vec3 third) {
        return first.mul(second).mul(third);
    }

    public static Vec3 additionAddV(Vec3 first, Vec3 second) {
        return first.add(first, second);
    }

    public static Vec3 additionV(Vec3 first, Vec3 second, Vec3 third) {
        return first.add_(second).add_(third);
    }

}
