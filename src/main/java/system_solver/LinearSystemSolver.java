
package system_solver;

public interface LinearSystemSolver {
    boolean hasSolutions();

    int getSolutionsNumber();

    int getRows();

    int getColumns();

    double[][] getIncompleteMatrix();

    double[][] getCompleteMatrix();

    double[] solveSystem();

    int getIncompleteRank();

    int getCompleteRank();

    double[] solveSystem(double[][] var1, double[] var2);
}
