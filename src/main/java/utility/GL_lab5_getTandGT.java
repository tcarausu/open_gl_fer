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

    public static Vec3 getGT1(Vec3 original_G, Vec3 original_E) {
        float xG1 = original_G.x - original_E.x;
        float yG1 = original_G.y - original_E.y;
        float zG1 = original_G.z - original_E.z;

        return new Vec3(xG1, yG1, zG1);
    }


    public static Mat4 getT2(Vec3 original_G, Vec3 original_E) {
        Vec3 vec_GT1 = getGT1(original_G, original_E);

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

}
