/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Aurélien Ledieu
 */
public class Chariot {
    private int id;
    private Set<Colis> colis;

    public Chariot() {
	this.colis = new HashSet<>();
    }

    public Chariot(int id) {
	this();
	this.id = id;
    }

    public int getId() {
	return id;
    }

    public Set<Colis> getColis() {
	return colis;
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
	final Chariot other = (Chariot) obj;
	if (this.id != other.id) {
	    return false;
	}
	if (!Objects.equals(this.colis, other.colis)) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "Chariot{" + "id=" + id + ", colis=" + colis + '}';
    }
    
}
