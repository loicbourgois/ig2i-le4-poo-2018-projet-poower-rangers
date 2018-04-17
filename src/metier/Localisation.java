/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Aurélien Ledieu
 */
public class Localisation {
    private int id;
    private int posX;
    private int posY;
    private Map<Localisation,Integer> distances;

    public Localisation() {
	distances = new HashMap<>();
    }

    public Localisation(int id, int posX, int posY) {
	this();
	this.id = id;
	this.posX = posX;
	this.posY = posY;
    }

    public int getId() {
	return id;
    }

    public int getPosX() {
	return posX;
    }

    public int getPosY() {
	return posY;
    }

    public Map<Localisation, Integer> getDistances() {
	return distances;
    }
    
    public boolean addDistance(Localisation localisation, Integer distance){
	if(distances.containsKey(localisation)) return false;
	return (distances.put(localisation, distance)) == null;
    }

    @Override
    public int hashCode() {
	int hash = 3;
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
	final Localisation other = (Localisation) obj;
	if (this.id != other.id) {
	    return false;
	}
	if (this.posX != other.posX) {
	    return false;
	}
	if (this.posY != other.posY) {
	    return false;
	}
	if (!Objects.equals(this.distances, other.distances)) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "Localisation{" + "id=" + id + ", posX=" + posX + ", posY=" + posY  + '}' + "\n";
    }
}
