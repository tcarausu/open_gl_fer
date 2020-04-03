package system_solver;

public class Main {

    public Main() {
    }

    public static void main(String[] args) {
        double[][] A = new double[][]{{8.0D, 2.0D}, {2.0D, 7.0D}};
        double[] b = new double[]{6.0D, -2.0D};
        RouchèCapelliSolver rs = new RouchèCapelliSolver(A, b);
        double[] eps = rs.solveSystem(A, b);

        for (int i = 0; i < eps.length; ++i) {
            System.out.println(eps[i]);
        }

    }
}
