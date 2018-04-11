/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poowerrangers;

/**
 *
 * @author Rod
 */
public class Instance {
    private Entrepot entrepot;
    private Configuration config;
    private String pathToFile;

    public Instance() {
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
    
    
}
