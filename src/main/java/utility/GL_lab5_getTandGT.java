package utility;

import glm.mat._4.Mat4;
import glm.vec._3.Vec3;
import glm.vec._4.Vec4;

public class GL_lab5_getTandGT {
    static Vec4 r1T1 = new Vec4(1, 0, 0, 0);
    static Vec4 r2T1 = new Vec4(0, 1, 0, 0);
    static Vec4 r3T1 = new Vec4(0, 0, 1, 0);

    public static Mat4 getT1(Vec3 forthVector) {

        Vec4 r4T1 = new Vec4(-(forthVector.x), -(forthVector.y), -(forthVector.z), 1);

        return new Mat4(
                r1T1.x, r1T1.y, r1T1.z, r1T1.w,
                r2T1.x, r2T1.y, r2T1.z, r2T1.w,
                r3T1.x, r3T1.y, r3T1.z, r3T1.w,
                r4T1.x, r4T1.y, r4T1.z, r4T1.w
        );
    }

    public static Vec3 getGT1(Vec3 original_G, Vec3 original_O) {
        float xG1 = original_G.x - original_O.x;
        float yG1 = original_G.y - original_O.y;
        float zG1 = original_G.z - original_O.z;

        return new Vec3(xG1, yG1, zG1);
    }


    public static Mat4 getT2(Vec3 original_G, Vec3 original_O) {
        Vec3 vec_GT1 = getGT1(original_G, original_O);

        double sinAlphaG = (vec_GT1.y) / (Math.sqrt(Math.pow(vec_GT1.x, 2) + Math.pow(vec_GT1.y, 2)));
        double cosAlphaG = (vec_GT1.x) / (Math.sqrt(Math.pow(vec_GT1.x, 2) + Math.pow(vec_GT1.y, 2)));


        // return Matrix Indentity (if x or y is 0)

        Vec4 r1T2 = new Vec4(cosAlphaG, -sinAlphaG, 0, 0);
        Vec4 r2T2 = new Vec4(sinAlphaG, cosAlphaG, 0, 0);
        Vec4 r3T2 = new Vec4(0, 0, 1, 0);
        Vec4 r4T2 = new Vec4(0, 0, 0, 1);

        return new Mat4(
                r1T2.x, r1T2.y, r1T2.z, r1T2.w,
                r2T2.x, r2T2.y, r2T2.z, r2T2.w,
                r3T2.x, r3T2.y, r3T2.z, r3T2.w,
                r4T2.x, r4T2.y, r4T2.z, r4T2.w
        );
    }

    public static Vec3 getGT2(Vec3 G1) {
        double xG2 = Math.sqrt(Math.pow(G1.x, 2) + Math.pow(G1.y, 2));
        float yG2 = 0;
        float zG2 = G1.z;

        return new Vec3(xG2, yG2, zG2);
    }


    public static Mat4 getT3(Vec3 G2) {
        double sinBetaG = (G2.x) / (Math.sqrt(Math.pow(G2.x, 2) + Math.pow(G2.z, 2)));
        double cosBetaG = (G2.z) / (Math.sqrt(Math.pow(G2.x, 2) + Math.pow(G2.z, 2)));

        Vec4 r1T3 = new Vec4(cosBetaG, sinBetaG, 0, 0);
        Vec4 r2T3 = new Vec4(0, 1, 0, 0);
        Vec4 r3T3 = new Vec4(-sinBetaG, 0, cosBetaG, 0);
        Vec4 r4T3 = new Vec4(0, 0, 0, 1);

        return new Mat4(
                r1T3.x, r1T3.y, r1T3.z, r1T3.w,
                r2T3.x, r2T3.y, r2T3.z, r2T3.w,
                r3T3.x, r3T3.y, r3T3.z, r3T3.w,
                r4T3.x, r4T3.y, r4T3.z, r4T3.w
        );
    }

    public static Vec3 getGT3(Vec3 G2) {
        double xG3 = 0;
        float yG3 = 0;
        double zG3 = Math.sqrt(Math.pow(G2.x, 2) + Math.pow(G2.z, 2));

        return new Vec3(xG3, yG3, zG3);
    }


    public static Mat4 getT4() {
        Vec4 r1T4 = new Vec4(0, -1, 0, 0);
        Vec4 r2T4 = new Vec4(1, 0, 0, 0);
        Vec4 r3T4 = new Vec4(0, 0, 1, 0);
        Vec4 r4T4 = new Vec4(0, 0, 0, 1);

        return new Mat4(
                r1T4.x, r1T4.y, r1T4.z, r1T4.w,
                r2T4.x, r2T4.y, r2T4.z, r2T4.w,
                r3T4.x, r3T4.y, r3T4.z, r3T4.w,
                r4T4.x, r4T4.y, r4T4.z, r4T4.w
        );
    }

    public static Mat4 getT5() {
        Vec4 r1T5 = new Vec4(-1, 0, 0, 0);
        Vec4 r2T5 = new Vec4(0, 1, 0, 0);
        Vec4 r3T5 = new Vec4(0, 0, 1, 0);
        Vec4 r4T5 = new Vec4(0, 0, 1, 0);

        return new Mat4(
                r1T5.x, r1T5.y, r1T5.z, r1T5.w,
                r2T5.x, r2T5.y, r2T5.z, r2T5.w,
                r3T5.x, r3T5.y, r3T5.z, r3T5.w,
                r4T5.x, r4T5.y, r4T5.z, r4T5.w
        );
    }

    public static Mat4 matrixT(Mat4 t1, Mat4 t2, Mat4 t3, Mat4 t4, Mat4 t5) {
        return t1.mul_(t2).mul_(t3).mul_(t4).mul_(t5);
    }

    public static double getHDist(Vec3 original_O, Vec3 original_G) {
        return Math.sqrt(
                (
                        Math.pow((original_O.x - original_G.x), 2) +
                                Math.pow((original_O.y - original_G.y), 2) +
                                Math.pow((original_O.z - original_G.z), 2)
                )
        );
    }

    public static double getXP(Vec3 original_O, double hDist) {
        return (original_O.x / original_O.z) * hDist;
    }

    public static double getYP(Vec3 original_O, double hDist) {
        return (original_O.y / original_O.z) * hDist;
    }

    public static Vec4 getAP(Vec3 original_O, double hDist) {
        double xPStrophe = original_O.x;
        double yPStrophe = original_O.y;
        double zPStrophe = 0;
        double hPStrophe = original_O.z / hDist;


        return new Vec4(xPStrophe, yPStrophe, zPStrophe, hPStrophe);
    }

    public static Mat4 projectionMatrix(Vec3 original_O, Vec3 original_G) {
        Vec4 r1P = new Vec4(1, 0, 0, 0);
        Vec4 r2P = new Vec4(0, 1, 0, 0);
        Vec4 r3P = new Vec4(0, 0, 0, 1 / getHDist(original_O, original_G));
        Vec4 r4P = new Vec4(0, 0, 0, 0);

        return new Mat4(
                r1P.x, r1P.y, r1P.z, r1P.w,
                r2P.x, r2P.y, r2P.z, r2P.w,
                r3P.x, r3P.y, r3P.z, r3P.w,
                r4P.x, r4P.y, r4P.z, r4P.w
        );
    }

}
