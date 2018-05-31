/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Ensemble de produits avec des quantités.
 * @author Rod
 */
@Entity
@Table(name = "COMMANDE")
public class Commande {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "commande")
	private List<QuantiteProduit> produitsCommandes;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "commande")
	private List<QuantiteProduit> produitsRestants;

	@Id
	@Column(name = "COMMANDENO")
	private int id;

	@JoinColumn(name = "ENTREPOT", referencedColumnName = "ENTREPOTNO")
	@ManyToOne(optional = false)
	private Entrepot entrepot;

	@Column(name = "NBCOLIS")
	private int nbColis;

	public Commande() {
		this.produitsCommandes = new ArrayList<>();
		this.produitsRestants = new ArrayList<>();
	}

	/**
	 * Consturctor.
	 * @param id id
	 * @param nbColis Colis count
	 */
	public Commande(int id, int nbColis) {
		this();
		this.id = id;
		this.nbColis = nbColis;
	}

	public void addProduitQuantite(Produit p, Integer q) {
		this.produitsCommandes.add(new QuantiteProduit(p, q));
		this.produitsRestants.add(new QuantiteProduit(p, q));
	}

	/**
	 * Sum weight from every products.
	 * @return total weight
	 */
	public int calculePoidsTotal() {
		int poidsTotal = 0;
		for (QuantiteProduit qp : this.produitsCommandes) {
			poidsTotal += qp.getProduit().getPoids() * qp.getQuantite();
		}
		return poidsTotal;
	}

	/**
	 * Add volume from every products.
	 * @return total volume
	 */
	public int calculeVolumeTotal() {
		int volTotal = 0;
		for (QuantiteProduit qp : this.produitsCommandes) {
			volTotal += qp.getProduit().getVolume() * qp.getQuantite();
		}
		return volTotal;
	}

	public List<QuantiteProduit> getProduitsCommandes() {
		return produitsCommandes;
	}

	public List<QuantiteProduit> getProduitsRestants() {
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

	Integer getId() {
		return this.id;
	}
}
