/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package solution;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.Instance;
import metier.Output;

/**
 * Classe principale.
 * @author Loïc Bourgois
 */
public class Main {

	/**
	 * Fonction de lancement.
	 * @param args argument standards
	 */
	public static void main(String[] args) {
		ArrayList<String> instances = new ArrayList<>();
		instances.add("instance_0116_131940_Z2");
		instances.add("instance_0116_131950_Z1");
		instances.add("instance_0130_132439_Z2");
		instances.add("instance_0202_132568_Z2");
		instances.add("instance_0203_132623_Z1");
		instances.add("instance_0214_132873_Z2");
		instances.add("instance_0215_132916_Z2");
		instances.add("instance_0606_136170_Z1");
		instances.add("instance_0606_136175_Z1");
		instances.add("instance_0606_136178_Z1");
		
		for (int i = 0; i < instances.size() && i < 1; i++) {
			System.out.println(run(instances.get(i)));
		}
	}
	
	
	/**
	 * Run generate a solution from an instance and launch the checker.
	 * @param instanceName name of the instance
	 * @return length.
	 */
	public static String run(String instanceName) {
		String fileName = "./instances/" + instanceName + ".txt";
		Instance instance = new Instance(fileName);
		instance.parse();
		instance.dispatch();
		Output output = null;
		if(true) {
			System.out.println("\nSolver");
			Solver solver = new Solver(instance);
			solver.populateChariots();
			output = new Output(fileName, solver.getChariots());
		} else {
			Sample sample = new Sample(instance);
			sample.populateChariots();
			output = new Output(fileName, sample.getChariots());
		}
		output.writeToFile();

		// Move to test folder
		try (PrintWriter out = new PrintWriter("./test/" + instanceName + "_sol.txt")) {
			out.println(output.toString());
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Output.class.getName()).log(Level.SEVERE, null, ex);
		}

		// Run checker
		return execCmd("cd ./test ; java -jar CheckerBatchingPicking.jar "
						+ instanceName
						+ " ; cd ../");
	}

	/**
	 * Executes an external command.
	 * @param cmd command to execute
	 */
	public static String execCmd(String cmd) {

		Runtime rt = Runtime.getRuntime();
		String[] commands = {"/bin/bash", "-c", cmd};
		Process proc = null;
		try {
			proc = rt.exec(commands);
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}

		BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

		BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

		// read the output from the command
		// System.out.println("Here is the standard output of the command:\n");
		String s = null;
		String length = "";
		try {
			while ((s = stdInput.readLine()) != null) {
				//System.out.println(s);
				if (s.startsWith("--> Distance totale : ")) {
					length = s;
					length = length.replace("--> Distance totale : ","");
				}
			}
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}

		// read any errors from the attempted command
		// System.out.println("Here is the standard error of the command (if any):\n");
		try {
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
			}
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
		return length;
	}
}
