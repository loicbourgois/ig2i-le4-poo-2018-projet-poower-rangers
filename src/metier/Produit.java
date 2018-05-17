/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

/**
 *
 * @author Rod
 */
public class Produit {
    private int id;
    private int poids;
    private int volume;
    private Localisation localisation;

    public Produit() {
    }

    public Produit(int id, int poids, int volume, Localisation localisation) {
        this.id = id;
        if(poids < 0){
            this.poids = 0;
        }
        else{
            this.poids = poids;
        }
        if(volume < 0){
            this.volume = 0;
        }
        else{
            this.volume = volume;
        }
	if(localisation!=null){
	    this.localisation=localisation;
	}
	else{
	    this.localisation = new Localisation();
	}
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Produit other = (Produit) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.poids != other.poids) {
            return false;
        }
        if (this.volume != other.volume) {
            return false;
        }
        return true;
    }

    public int getId() {
	return id;
    }

    public int getPoids() {
	return poids;
    }

    public int getVolume() {
	return volume;
    }

    public Localisation getLocalisation() {
	return localisation;
    }

    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", poids=" + poids + ", volume=" + volume + "Localisation=" + localisation + '}'+ "\n";
    }
    
    
    
}
