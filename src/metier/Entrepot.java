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
public class Entrepot {

	private int id;
	private Set<Localisation> depots;
	private Set<Produit> produits;
	private Set<Commande> commandes;
	private Set<Chariot> chariots;

	public Entrepot() {
		depots = new HashSet<>();
		produits = new HashSet<>();
		commandes = new HashSet<>();
	}

	public Entrepot(int id) {
		this();
		this.id = id;
	}

	public boolean addDepot(Localisation localisation) {
		if (depots.contains(localisation)) {
			return false;
		}
		return (depots.add(localisation));
	}

	public boolean addProduit(Produit produit) {
		if (produits.contains(produit)) {
			return false;
		}
		return (produits.add(produit));
	}

	public boolean addCommande(Commande commande) {
		if (commandes.contains(commande)) {
			return false;
		}
		return (commandes.add(commande));
	}

	public boolean addChariot(Chariot c) {
		if (this.chariots.contains(c)) {
			return false;
		}
		return (this.chariots.add(c));
	}

	public int getId() {
		return id;
	}

	public Set<Chariot> getChariots() {
		return chariots;
	}

	public Set<Localisation> getDepots() {
		return depots;
	}

	public Set<Produit> getProduits() {
		return produits;
	}

	public Set<Commande> getCommandes() {
		return commandes;
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
		final Entrepot other = (Entrepot) obj;
		if (this.id != other.id) {
			return false;
		}
		if (!Objects.equals(this.depots, other.depots)) {
			return false;
		}
		if (!Objects.equals(this.produits, other.produits)) {
			return false;
		}
		if (!Objects.equals(this.commandes, other.commandes)) {
			return false;
		}
		if (!Objects.equals(this.chariots, other.chariots)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Entrepot{" + "\n"
						+ "\t\t" + "id : " + id + ",\n"
						+ "\t\t" + "depots : " + depots + ",\n"
						//+ "\t\t" + "produits : " + produits + ",\n "
						+ "\t\t" + "commandes : \n" + commandes + ",\n"
						+ "\t\t" + "chariots : " + chariots + ",\n"
						+ "}";
	}
}
