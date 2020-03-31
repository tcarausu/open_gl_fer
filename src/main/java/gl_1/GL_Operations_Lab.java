package gl_1;

import glm.Glm;
import glm.vec._3.Vec3;

public class GL_Operations_Lab {
    private Vec3 i = new Vec3(1, 0, 0);
    private Vec3 j = new Vec3(0, 1, 0);
    private Vec3 k = new Vec3(0, 0, 1);

    public static void main(String[] args) {

//        Vec3 firstNr = new Vec3(1,0,0);
//        Vec3 secNr = new Vec3(0,1,0);
//        Vec3 thirdNr = new Vec3(0,0,1);

//        Vec3 firstNr = new Vec3(1, 1, 0);
//        Vec3 secNr = new Vec3(0, 1, 1);
        Vec3 firstNr = new Vec3(2, 2,1);
        Vec3 secNr = new Vec3(1, -2, 0);
        Vec3 thirdNr = new Vec3(1, 0, 1);

        System.out.println("\nAdditionOfVectors");
        System.out.println(additionV(firstNr,secNr));

        System.out.println("\nDotProdOfVectors");
        System.out.println(dotProd(firstNr,secNr));

        System.out.println("\nCrossProdOfVectors");
        System.out.println(crossProd(firstNr,secNr));

    }


    public static Vec3 additionV(Vec3 first, Vec3 second) {
        float additionX = (first.x + second.x);
        float additionY = (first.y + second.y);
        float additionZ = (first.z + second.z);

        return new Vec3(additionX, additionY, additionZ);
    }
    public static Vec3 additionAddV(Vec3 first, Vec3 second) {
        return first.add(first, second);
    }

    public static Vec3 additionV(Vec3 first, Vec3 second, Vec3 third) {
        return first.add_(second).add_(third);
    }

    public static Vec3 crossProd(Vec3 first, Vec3 second) {
//  u1,u2,u3 x v1,v2,v3
//  u2v3-u3v2,u3v1-u1v3,u1v2-u2v1
        float firstX = (first.y * second.z) - (first.z * second.y);
        float firstY = (first.z * second.x) - (first.x * second.z);
        float firstZ = (first.x * second.y) - (first.y * second.x);
        return new Vec3(firstX, firstY, firstZ);
    }

    public static int dotProd(Vec3 first, Vec3 second) {
        float additionX = (first.x * second.x);
        float additionY = (first.y * second.y);
        float additionZ = (first.z * second.z);
        return (int) (additionX + additionY + additionZ);
    }

    public static Vec3 crossProd(Vec3 first, Vec3 second, Vec3 third) {
        return first.mul(second).mul(third);
    }

}
