package solver.files;

import java.beans.XMLEncoder;
import java.io.OutputStream;

import solver.PlanSolution;

public class XMLSolutionSaver extends SolutionSaver{

	@Override
	public void saveSolution(OutputStream out,PlanSolution s) {

		XMLEncoder xml = new XMLEncoder(out);
		xml.writeObject(s);
		xml.close();
	}

}
