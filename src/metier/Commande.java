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
 * @author Rod
 */
public class Commande {

	private Map<Produit, Integer> produitsCommandes;
	private Map<Produit, Integer> produitsRestants;
	private int id;
	private int nbColis;

	public Commande() {
		this.produitsCommandes = new HashMap<>();
		this.produitsRestants = new HashMap<>();
	}

	public Commande(int Integer, int nbColis) {
		this();
		this.id = Integer;
		this.nbColis = nbColis;
	}

	public void addProduitQuantite(Produit p, Integer q) {
		this.produitsCommandes.put(p, q);
		this.produitsRestants.put(p, q);
	}

	public int calculePoidsTotal() {
		int poidsTotal = 0;
		for (Map.Entry<Produit, Integer> item : this.produitsCommandes.entrySet()) {
			poidsTotal += item.getKey().getPoids() * item.getValue();
		}
		return poidsTotal;
	}

	public int calculeVolumeTotal() {
		int volTotal = 0;
		for (Map.Entry<Produit, Integer> item : this.produitsCommandes.entrySet()) {
			volTotal += item.getKey().getVolume() * item.getValue();
		}
		return volTotal;
	}

	public Map<Produit, Integer> getProduitCommande() {
		return produitsCommandes;
	}

	public Map<Produit, Integer> getProduitRestant() {
		return produitsRestants;
	}

	public int getInteger() {
		return id;
	}

	public int getNbColis() {
		return nbColis;
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
		final Commande other = (Commande) obj;
		if (this.id != other.id) {
			return false;
		}
		if (this.nbColis != other.nbColis) {
			return false;
		}
		if (!Objects.equals(this.produitsCommandes, other.produitsCommandes)) {
			return false;
		}
		if (!Objects.equals(this.produitsRestants, other.produitsRestants)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "";
		/*return "\t\tCommande : {\n"
						+ "\t\t\tid  : " + id + "\n"
						//+ "\t\t\tproduitCommande : " + produitsCommandes  + "\n"
						// + "],\n produitRestant=[" + produitsRestants 
						// + ", nbColis=" + nbColis 
						+ "\t\t}\n";*/
	}
}
