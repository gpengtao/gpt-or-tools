package com.gpengtao.ortools;

import com.google.ortools.Loader;
import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.CpSolver;
import com.google.ortools.sat.CpSolverStatus;
import com.google.ortools.sat.IntVar;

/**
 * @author gpengtao on 2022/7/5 00:57.
 */
public class SimpleSatProgram {

	public static void main(String[] args) {
		Loader.loadNativeLibraries();
		// Create the model.
		CpModel model = new CpModel();

		// Create the variables.
		int numVals = 3;

		IntVar x = model.newIntVar(0, numVals - 1, "x");
		IntVar y = model.newIntVar(0, numVals - 1, "y");
		IntVar z = model.newIntVar(0, numVals - 1, "z");

		// Create the constraints.
		model.addDifferent(x, y);

		// Create a solver and solve the model.
		CpSolver solver = new CpSolver();
		CpSolverStatus status = solver.solve(model);

		if (status == CpSolverStatus.OPTIMAL || status == CpSolverStatus.FEASIBLE) {
			System.out.println("x = " + solver.value(x));
			System.out.println("y = " + solver.value(y));
			System.out.println("z = " + solver.value(z));
		} else {
			System.out.println("No solution found.");
		}
	}
}
