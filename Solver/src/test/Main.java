package test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import model.data.files.MyTextLevelLoader;
import model.data.level.Level;
import solver.PlanSolution;
import solver.SokobanSolver;
import solver.files.XMLSolutionSaver;
import strips.Action;

public class Main {

	public static void main(String[] args) {
		MyTextLevelLoader loader = new MyTextLevelLoader();
		SokobanSolver solver = new SokobanSolver();
		try {
			if(args.length < 2){
				System.out.println("Not enough arguments given");
				return;
			}
			Level lvl = loader.loadLevel(new FileInputStream(args[0]));
			PlanSolution solution = new PlanSolution(solver.solve(lvl));
			XMLSolutionSaver xml = new XMLSolutionSaver();
			xml.saveSolution(new FileOutputStream(args[1]), solution);
			System.out.println(solution);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
