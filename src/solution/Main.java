/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solution;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.Chariot;
import metier.Configuration;
import metier.Entrepot;
import metier.Instance;
import metier.Output;

/**
 *
 * @author Loïc Bourgois
 */
public class Main {

	public static void main(String[] args) {
		String instanceName = "instance_0116_131940_Z2";
		String fileName = "./instances/"+instanceName+".txt";
		Instance instance = new Instance(fileName);
		instance.parse();
		instance.dispatch();
		Sample solution = new Sample(instance);
		solution.populateChariots();
		
		Output output = new Output(fileName, solution.getChariots());
		output.writeToFile();

		//System.out.println(instance.toString());
		//System.out.println(solution.toString());
		//System.out.println(output.toString());
		
		// Move to test folder
		try (PrintWriter out = new PrintWriter("./test/"+instanceName+"_sol.txt")) {
			out.println(output.toString());
			System.out.println("Writing successful");
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Output.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		try {
			System.out.println(execCmd("/bin/bash -c cd ./test ; java -jar CheckerBatchingPicking.jar instance_0116_131940_Z2 ; cd ../"));
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
	
	public static String execCmd(String cmd) throws java.io.IOException {
    java.util.Scanner s = new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");
    return s.hasNext() ? s.next() : "";
	}
}
