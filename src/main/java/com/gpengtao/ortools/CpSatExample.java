package com.gpengtao.ortools;

import com.google.ortools.Loader;
import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.CpSolver;
import com.google.ortools.sat.CpSolverStatus;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearExpr;

import static java.util.Arrays.stream;

/**
 * @author pengtao.geng on 2022/8/1 19:23.
 */
public class CpSatExample {

	public static void main(String[] args) {
		Loader.loadNativeLibraries();

		// Create the model.
		// [START model]
		CpModel model = new CpModel();
		// [END model]

		// Create the variables.
		// [START variables]
		int varUpperBound = stream(new int[] {50, 45, 37}).max().getAsInt();

		IntVar x = model.newIntVar(0, varUpperBound, "x");
		IntVar y = model.newIntVar(0, varUpperBound, "y");
		IntVar z = model.newIntVar(0, varUpperBound, "z");
		// [END variables]

		// Create the constraints.
		// [START constraints]
		model.addLessOrEqual(LinearExpr.weightedSum(new IntVar[] {x, y, z}, new long[] {2, 7, 3}), 50);
		model.addLessOrEqual(LinearExpr.weightedSum(new IntVar[] {x, y, z}, new long[] {3, -5, 7}), 45);
		model.addLessOrEqual(LinearExpr.weightedSum(new IntVar[] {x, y, z}, new long[] {5, 2, -6}), 37);
		// [END constraints]

		// [START objective]
		model.maximize(LinearExpr.weightedSum(new IntVar[] {x, y, z}, new long[] {2, 2, 3}));
		// [END objective]

		// Create a solver and solve the model.
		// [START solve]
		CpSolver solver = new CpSolver();
		CpSolverStatus status = solver.solve(model);
		// [END solve]

		// [START print_solution]
		if (status == CpSolverStatus.OPTIMAL || status == CpSolverStatus.FEASIBLE) {
			System.out.printf("Maximum of objective function: %f%n", solver.objectiveValue());
			System.out.println("x = " + solver.value(x));
			System.out.println("y = " + solver.value(y));
			System.out.println("z = " + solver.value(z));
		} else {
			System.out.println("No solution found.");
		}
		// [END print_solution]

		// Statistics.
		// [START statistics]
		System.out.println("Statistics");
		System.out.printf("  conflicts: %d%n", solver.numConflicts());
		System.out.printf("  branches : %d%n", solver.numBranches());
		System.out.printf("  wall time: %f s%n", solver.wallTime());
		// [END statistics]
	}
}
