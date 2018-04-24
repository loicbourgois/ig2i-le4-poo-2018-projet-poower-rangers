/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rod
 */
public class Instance {
    private Entrepot entrepot;
    private Configuration config;
    private String pathToFile;
		private int locationCount;

    public Instance() {
    }
		
		public Instance(String pathToFile) {
			this.pathToFile = pathToFile;
			this.locationCount = -1;
			this.entrepot = new Entrepot();
			this.config = null;
		}

    public Instance(Entrepot entrepot, Configuration config, String pathToFile) {
        this.entrepot = entrepot;
        this.config = config;
        this.pathToFile = pathToFile;
    }

    public Entrepot getEntrepot() {
        return entrepot;
    }

    public Configuration getConfig() {
        return config;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Instance other = (Instance) obj;
        if (!Objects.equals(this.pathToFile, other.pathToFile)) {
            return false;
        }
        if (!Objects.equals(this.entrepot, other.entrepot)) {
            return false;
        }
        if (!Objects.equals(this.config, other.config)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
			return "Instance{" 
							+ "entrepot=" + entrepot.getId() 
							+ ", config=" + config
							+ ", pathToFile=" + pathToFile
							+ ", locationCount=" + locationCount 
							+ '}'+ "\n";
		}
		
		private boolean parse() {
		System.out.println("parsing");
		
		try (BufferedReader br = new BufferedReader(new FileReader(this.pathToFile))) {
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

		return true;
	}
	
		public static void main(String[] args) {
			System.out.println("Start Instance");
			Instance instance = new Instance("./instances/instance_0116_131940_Z2.txt");
			instance.parse();
			System.out.println(instance.toString());
		}
    
}
