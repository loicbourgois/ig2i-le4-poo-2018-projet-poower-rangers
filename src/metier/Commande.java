/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Rod
 */
@Entity
@Table(name="COMMANDE")
public class Commande {

    //tout doux
	private List<QuantiteProduit> produitsCommandes;
        
    //tout doux
	private List<QuantiteProduit> produitsRestants;
        
        @Id
        @Column(name="COMMANDENO")
	private int id;
        
        @Column(name="NBCOLIS")
	private int nbColis;

	public Commande() {
		this.produitsCommandes = new ArrayList<>();
		this.produitsRestants = new ArrayList<>();
	}

	public Commande(int Integer, int nbColis) {
		this();
		this.id = Integer;
		this.nbColis = nbColis;
	}

	public void addProduitQuantite(Produit p, Integer q) {
		this.produitsCommandes.add(new QuantiteProduit(p, q));
		this.produitsRestants.add(new QuantiteProduit(p, q));
	}

	public int calculePoidsTotal() {
		int poidsTotal = 0;
		for (QuantiteProduit qp : this.produitsCommandes) {
			poidsTotal += qp.getProduit().getPoids() * qp.getQuantite();
		}
		return poidsTotal;
	}

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
}
