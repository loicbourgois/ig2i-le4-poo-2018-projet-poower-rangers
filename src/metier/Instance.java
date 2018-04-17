/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.util.Objects;

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
        return "Instance{" + "entrepot=" + entrepot.getId() + ", config=" + config + ", pathToFile=" + pathToFile + '}'+ "\n";
    }
    
}
