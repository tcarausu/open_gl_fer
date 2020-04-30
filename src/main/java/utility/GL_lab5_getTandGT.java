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

        Vec4 r1T2 = new Vec4(cosBetaG, sinBetaG, 0, 0);
        Vec4 r2T2 = new Vec4(0, 1, 0, 0);
        Vec4 r3T2 = new Vec4(-sinBetaG, 0, cosBetaG, 0);
        Vec4 r4T2 = new Vec4(0, 0, 0, 1);

        return new Mat4(
                r1T2.x, r1T2.y, r1T2.z, r1T2.w,
                r2T2.x, r2T2.y, r2T2.z, r2T2.w,
                r3T2.x, r3T2.y, r3T2.z, r3T2.w,
                r4T2.x, r4T2.y, r4T2.z, r4T2.w
        );
    }

    public static Vec3 getGT3(Vec3 G2) {
        double xG3 = 0;
        float yG3 = 0;
        double zG3 = Math.sqrt(Math.pow(G2.x, 2) + Math.pow(G2.z, 2));

        return new Vec3(xG3, yG3, zG3);
    }


    public static Mat4 getT4() {
        Vec4 r1T1 = new Vec4(0, -1, 0, 0);
        Vec4 r2T1 = new Vec4(1, 0, 0, 0);
        Vec4 r3T1 = new Vec4(0, 0, 1, 0);
        Vec4 r4T1 = new Vec4(0, 0, 0, 1);

        return new Mat4(
                r1T1.x, r1T1.y, r1T1.z, r1T1.w,
                r2T1.x, r2T1.y, r2T1.z, r2T1.w,
                r3T1.x, r3T1.y, r3T1.z, r3T1.w,
                r4T1.x, r4T1.y, r4T1.z, r4T1.w
        );
    }

    public static Mat4 getT5() {
        Vec4 r1T1 = new Vec4(-1, 0, 0, 0);
        Vec4 r2T1 = new Vec4(0, 1, 0, 0);
        Vec4 r3T1 = new Vec4(0, 0, 1, 0);
        Vec4 r4T1 = new Vec4(0, 0, 1, 0);

        return new Mat4(
                r1T1.x, r1T1.y, r1T1.z, r1T1.w,
                r2T1.x, r2T1.y, r2T1.z, r2T1.w,
                r3T1.x, r3T1.y, r3T1.z, r3T1.w,
                r4T1.x, r4T1.y, r4T1.z, r4T1.w
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

}
