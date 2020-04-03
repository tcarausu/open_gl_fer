package system_solver;

public class RouchèCapelliSolver implements LinearSystemSolver {
    private double[][] incompleteMatrix;
    private double[] coefficientMatrix;
    private int rows;
    private int columns;

    public RouchèCapelliSolver(double[][] a, double[] b) {
        if (a != null && b != null) {
            if (a.length != b.length) {
                throw new IllegalArgumentException();
            } else {
                this.incompleteMatrix = a;
                this.coefficientMatrix = b;
                this.rows = a.length;
                this.columns = a[0].length;
            }
        } else {
            throw new NullPointerException();
        }
    }

    public boolean hasSolutions() {
        return this.getIncompleteRank() == this.getCompleteRank();
    }

    public int getSolutionsNumber() {
        if (!this.hasSolutions()) {
            return 0;
        } else {
            return this.getIncompleteRank() == this.columns ? 1 : 2147483647;
        }
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public double[][] getIncompleteMatrix() {
        double[][] incompleteMatrix = new double[this.rows][this.columns];

        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.columns; ++j) {
                incompleteMatrix[i][j] = this.incompleteMatrix[i][j];
            }
        }

        return incompleteMatrix;
    }

    public double[][] getCompleteMatrix() {
        double[][] completeMatrix = new double[this.rows][this.columns + 1];

        int i;
        for (i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.columns; ++j) {
                completeMatrix[i][j] = this.incompleteMatrix[i][j];
            }
        }

        for (i = 0; i < this.rows; ++i) {
            completeMatrix[i][this.columns] = this.coefficientMatrix[i];
        }

        return completeMatrix;
    }

    public double[] getCoefficientMatrix() {
        double[] coefficientMatrix = new double[this.coefficientMatrix.length];

        for (int i = 0; i < coefficientMatrix.length; ++i) {
            coefficientMatrix[i] = this.coefficientMatrix[i];
        }

        return coefficientMatrix;
    }

    public double[] solveSystem(double[][] A, double[] b) {
        double[] x = this.initialize(new double[A[0].length]);

        for (int k = 0; k < 1000; ++k) {
            for (int i = 0; i < A.length; ++i) {
                double x0 = 0.0D;

                for (int j = 0; j < A.length; ++j) {
                    if (i != j) {
                        x0 += A[i][j] * x[j];
                    }
                }

                x[i] = (b[i] - x0) / A[i][i];
            }
        }

        return x;
    }

    private double[] initialize(double[] output) {
        for (int i = 0; i < output.length; ++i) {
            output[i] = 0.0D;
        }

        return output;
    }

    public int getIncompleteRank() {
        double[][] reduced = gaussElimination(this.getIncompleteMatrix());
        int rank = 0;

        for (int i = 0; i < reduced.length; ++i) {
            for (int j = 0; j < reduced[0].length; ++j) {
                if (reduced[i][j] != 0.0D) {
                    ++rank;
                    break;
                }
            }
        }

        return rank;
    }

    public static double[][] gaussElimination(double[][] A) {
        for (int k = 0; k < A[0].length; ++k) {
            finalReduce(A);

            for (int i = k + 1; i < A.length; ++i) {
                double aik = A[i][k] / A[k][k];
                A[i][k] = 0.0D;

                for (int j = k + 1; j < A[0].length; ++j) {
                    A[i][j] -= aik * A[k][j];
                }
            }
        }

        finalReduce(A);
        return A;
    }

    private static void finalReduce(double[][] A) {
        for (int i = 0; i < A.length - 1; ++i) {
            for (int j = 0; j < A[0].length && A[i][j] == 0.0D; ++j) {
                if (j == A[0].length - 1) {
                    for (int k = 0; k < A[0].length; ++k) {
                        A[i][k] = A[i + 1][k];
                        A[i + 1][k] = 0.0D;
                    }
                }
            }
        }

    }

    public int getCompleteRank() {
        double[][] reduced = gaussElimination(this.getCompleteMatrix());
        int rank = 0;

        for (int i = 0; i < reduced.length; ++i) {
            for (int j = 0; j < reduced[0].length; ++j) {
                if (reduced[i][j] != 0.0D) {
                    ++rank;
                    break;
                }
            }
        }

        return rank;
    }

    public double[] solveSystem() {
        if (this.hasSolutions() && this.getSolutionsNumber() != 2147483647) {
            double[][] A = gaussElimination(this.getCompleteMatrix());
            double[] x = new double[this.getCompleteRank()];
            x = this.initialize(x);

            for (int i = A.length - 1; i >= 0; --i) {
                double s = A[i][A[0].length - 1];

                for (int j = i + 1; j < A[0].length; ++j) {
                    if (j < x.length) {
                        s -= A[i][j] * x[j];
                    }
                }

                if (i < x.length) {
                    x[i] = s / A[i][i];
                }
            }

            return x;
        } else {
            throw new IllegalStateException("Impossible system or infinite solutions detected");
        }
    }

    public static void main(String[] args) {
        double[][] A = new double[][]{{4.0D, 1.0D, 2.0D, -3.0D}, {3.0D, -1.0D, 0.0D, 1.0D}, {0.0D, 1.0D, -2.0D, -1.0D}, {3.0D, 0.0D, 1.0D, -1.0D}};
        double[] b = new double[]{0.0D, 1.0D, -4.0D, 0.0D};
        system_solver.RouchèCapelliSolver s = new system_solver.RouchèCapelliSolver(A, b);
        double[][] ridotta = gaussElimination(s.getIncompleteMatrix());

        int i;
        for (i = 0; i < ridotta.length; ++i) {
            for (i = 0; i < ridotta[0].length; ++i) {
                System.out.print(ridotta[i][i] + " ");
            }

            System.out.println();
        }

        System.out.println();
        System.out.println("Incomplete rank: " + s.getIncompleteRank());
        System.out.println("Complete rank: " + s.getCompleteRank());
        double[] x = s.solveSystem();

        for (i = 0; i < x.length; ++i) {
            System.out.print(x[i] + " ");
        }

    }
}
