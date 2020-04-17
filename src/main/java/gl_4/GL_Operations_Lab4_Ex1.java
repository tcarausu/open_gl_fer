package gl_4;

import glm.vec._3.Vec3;
import utility.Constant;
import utility.iPolyElement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class GL_Operations_Lab4_Ex1 {
    private static String[] vectorsTopLine, trianglePointersLine;
    private static AtomicInteger counterElements = new AtomicInteger();
    private static AtomicInteger polyElCounter = new AtomicInteger();

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(Constant.tetrahedron));

        HashMap<String, ArrayList<Double>> triangleLineWithABCDValues = new HashMap<>();
        ArrayList<Double> ABDCValues = new ArrayList<>();
        List<Vec3> vec3s = new ArrayList<>();
        List<Vec3> triangleVec3s = new ArrayList<>();
        List<iPolyElement> polyElements = new ArrayList<>();
        List<Double> elementValues = new ArrayList<>();
        List<Double> polyValues = new ArrayList<>();
        while (sc.hasNext()) {
            String line = sc.nextLine();

            if (line.startsWith("//") || line.startsWith(" ")
                    || line.startsWith("#") || line.startsWith("g ")
                    || line.length() == 0) {
                continue;
            }

            if (line.startsWith("v")) {
                vectorsTopLine = line.split(" ");
                for (String element : vectorsTopLine) {
                    if (element.contains("v")) continue;
                    double elementValue = Double.parseDouble(element);
                    elementValues.add(elementValue);
                    if (elementValues.size() % 3 == 0) {
                        int firstElValueFromF = (int) elementValues.get(counterElements.get()).doubleValue();
                        int sElValueFromF = (int) elementValues.get(counterElements.get() + 1).doubleValue();
                        int thElValueFromF = (int) elementValues.get(counterElements.get() + 2).doubleValue();
                        vec3s.add(new Vec3(firstElValueFromF, sElValueFromF, thElValueFromF));
                        counterElements.getAndAdd(3);
                    }
                }
            }
            if (line.startsWith("f")) {
                trianglePointersLine = line.split(" ");
                for (String element : trianglePointersLine) {
                    if (element.contains("f")) continue;
                    double polyValue = Double.parseDouble(element);
                    polyValues.add(polyValue);

                    if (polyValues.size() % 3 == 0) {
                        int firstPolyValueFromF = (int) polyValues.get(polyElCounter.get()).doubleValue();
                        int sPolyValueFromF = (int) polyValues.get(polyElCounter.get() + 1).doubleValue();
                        int thPolyValueFromF = (int) polyValues.get(polyElCounter.get() + 2).doubleValue();
                        Vec3 firstEdge = vec3s.get(firstPolyValueFromF - 1);
                        Vec3 secondEdge = vec3s.get(sPolyValueFromF - 1);
                        Vec3 thirdEdge = vec3s.get(thPolyValueFromF - 1);

                        double A = (secondEdge.y - firstEdge.y) * (thirdEdge.z - firstEdge.z)
                                - (secondEdge.z - firstEdge.z) * (thirdEdge.y - firstEdge.y);

                        double B = -(secondEdge.x - firstEdge.x) * (thirdEdge.z - firstEdge.z)
                                + (secondEdge.z - firstEdge.z) * (thirdEdge.x - firstEdge.x);

                        double C = (secondEdge.x - firstEdge.x) * (thirdEdge.y - firstEdge.y)
                                - (secondEdge.y - firstEdge.y) * (thirdEdge.x - firstEdge.x);

                        double D = -(firstEdge.x * A - firstEdge.y * B - firstEdge.z * C);

                        ABDCValues.add(A);
                        ABDCValues.add(B);
                        ABDCValues.add(C);
                        ABDCValues.add(D);

                        triangleLineWithABCDValues.put(line, ABDCValues);
                        polyElCounter.getAndAdd(3);
                    }

                }

            }
        }
        System.out.println(vec3s.toString());
        System.out.println(triangleLineWithABCDValues.toString());

    }
}
