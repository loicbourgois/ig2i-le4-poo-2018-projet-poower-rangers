/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poowerrangers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class Instance {
	
	private String path;
	private int locationCount;

	public Instance(String path) {
		this.path = path;
		this.locationCount = -1;
	}

	@Override
	public String toString() {
		return "Instance{" + "path=" + path + ", locationCount=" + locationCount + '}';
	}
	
	private boolean parse() {
		System.out.println("parsing");
		
		try (BufferedReader br = new BufferedReader(new FileReader(this.path))) {
				String line;
				while ((line = br.readLine()) != null) {
					if(line.startsWith("//NbLocations")) {
						line = br.readLine();
						this.locationCount = Integer.parseInt(line.replaceAll("\\s+",""));
					}
					// System.out.println(line);
				}
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Instance.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(Instance.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		//this.path;
		return true;
	}
	
	public static void main(String[] args) {
		System.out.println("Start Instance");
		Instance instance = new Instance("./instances/instance_0116_131940_Z2.txt");
		instance.parse();
		System.out.println(instance.toString());
	}
}
