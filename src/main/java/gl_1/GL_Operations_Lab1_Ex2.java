package gl_1;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class GL_Operations_Lab1_Ex2 {
    private static AtomicInteger x = new AtomicInteger();
    private static AtomicInteger y = new AtomicInteger();
    private static AtomicInteger z = new AtomicInteger();
    private static AtomicInteger res = new AtomicInteger();

    private static float[][] matrix = {};

    public static void main(String[] args) throws FileNotFoundException {
//        Scanner sc = new Scanner(new File("values.txt"));
        Scanner sc = new Scanner(System.in); // 1, 1, 1, 6, -1, -2, 1, -2, 2, 1, 3, 13, set this in the console or use the values.txt
        int chunk = 4; // chunk size to divide
        System.out.println("Introduce Values: ");
        String line = sc.nextLine();
        String[] values = line.split(",");

        //        glu.gluLookAt(3.0f, 4.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);

//        gl.glFrustumf(-0.5f, 0.5f, -0.5f, 0.5f, 1.0f, 100.0f);


        retrieveMatrix(chunk, values);
        // Order of Matrix(n)
        int n = 3, flag;
        // Performing Matrix transformation
        flag = PerformOperation(matrix, n);

        if (flag == 1)
            flag = CheckConsistency(matrix, n);

        // Printing Final Matrix
        System.out.println("\nFinal Augmented Matrix is : ");
        PrintMatrix(matrix, n);

        // Printing Solutions(if exist)
        PrintResult(matrix, n, flag);
        sc.close();

    }

    private static void retrieveMatrix(int chunk, String[] values) {
        System.out.println("Original Augmented Matrix: ");

        for (int i = 0; i < values.length; i += chunk) {
            String[] arrayV = Arrays.copyOfRange(values, i, Math.min(values.length, i + chunk));
            System.out.println(Arrays.toString(arrayV));

            x.getAndAdd(Integer.parseInt(arrayV[0].replace(" ", "")));
            y.getAndAdd(Integer.parseInt(arrayV[1].replace(" ", "")));
            z.getAndAdd(Integer.parseInt(arrayV[2].replace(" ", "")));
            res.getAndAdd(Integer.parseInt(arrayV[3].replace(" ", "")));

            float[][] row = {
                    {x.get(), y.get(), z.get(), res.get()}
            };
            valueReset();

            if (matrix.length < 4) {
                matrix = append(matrix, row);
            }
        }
    }

    public static float[][] append(float[][] a, float[][] b) {
        float[][] result = new float[a.length + b.length][];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    private static void valueReset() {
        x.set(0);
        y.set(0);
        z.set(0);
        res.set(0);
    }

    private static int PerformOperation(float[][] matrixToCalculate, int n) {
        int i, j, k, c, flag = 0;

        // Performing elementary operations
        for (i = 0; i < n; i++) {
            if (matrixToCalculate[i][i] == 0) {
                c = 1;
                while (matrixToCalculate[i + c][i] == 0 && (i + c) < n)
                    c++;
                if ((i + c) == n) {
                    flag = 1;
                    break;
                }
                for (j = i, k = 0; k <= n; k++) {
                    float temp = matrixToCalculate[j][k];
                    matrixToCalculate[j][k] = matrixToCalculate[j + c][k];
                    matrixToCalculate[j + c][k] = temp;
                }
            }

            for (j = 0; j < n; j++) {

                // Excluding all i == j
                if (i != j) {

                    // Converting Matrix to reduced row
                    // echelon form(diagonal matrix)
                    float p = matrixToCalculate[j][i] / matrixToCalculate[i][i];

                    for (k = 0; k <= n; k++)
                        matrixToCalculate[j][k] = matrixToCalculate[j][k] - (matrixToCalculate[i][k]) * p;
                }
            }
        }
        return flag;
    }

    private static void PrintResult(float[][] augmentedMatrix, int n, int flag) {
        System.out.print("Result is : ");

        if (flag == 2)
            System.out.println("Infinite Solutions Exists");
        else if (flag == 3)
            System.out.println("No Solution Exists");


            // Printing the solution by dividing constants by
            // their respective diagonal elements
        else {
            for (int i = 0; i < n; i++)
                System.out.print(augmentedMatrix[i][n] / augmentedMatrix[i][i] + " ");
        }
    }

    private static void PrintMatrix(float[][] matrixToPrint, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= n; j++)
                System.out.print(matrixToPrint[i][j] + " ");
            System.out.println();
        }
        System.out.println("\n");
    }

    private static int CheckConsistency(float[][] matrixToCheck, int n) {
        int i, j;
        float sum;

        // flag == 2 for infinite solution
        // flag == 3 for No solution
        int flag = 3;
        for (i = 0; i < n; i++) {
            sum = 0;
            for (j = 0; j < n; j++)
                sum = sum + matrixToCheck[i][j];
            if (sum == matrixToCheck[i][j])
                flag = 2;
        }
        return flag;
    }
}