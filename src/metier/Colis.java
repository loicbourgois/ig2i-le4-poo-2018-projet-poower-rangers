/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metier;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Représente un colis.
 * @author Rod
 */
@Entity
@Table(name = "COLIS")
public class Colis {

	@Id
	@Column(name = "COLISNO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "POIDSRESTANT")
	private int poidsRestant;

	@Column(name = "VOLUMERESTANT")
	private int volumeRestant;

	@JoinColumn(name = "COMMANDE")
	private Commande commande;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "colis")
	private List<QuantiteProduit> produits;

	@JoinColumn(name = "CHARIOT", referencedColumnName = "CHARIOTNO")
	@ManyToOne(optional = false)
	private Chariot chariot;
	private int averageProductId;

	/**
	 * Default constructor.
	 */
	public Colis() {
		this.produits = new ArrayList<>();
		this.averageProductId = -1;
	}

	/**
	 * Secondary constructor.
	 * @param poidsRestant poids restant
	 * @param volumeRestant colume restant
	 * @param commande commande
	 */
	public Colis(int poidsRestant, int volumeRestant, Commande commande) {
		this();
		this.poidsRestant = poidsRestant;
		this.volumeRestant = volumeRestant;
		this.commande = commande;
	}

	/**
	 * Third constructor.
	 * @param id id
	 * @param poidsRestant poids restant
	 * @param volumeRestant columne restant
	 * @param commande commande
	 */
	public Colis(int id, int poidsRestant, int volumeRestant, Commande commande) {
		this();
		this.id = id;
		this.poidsRestant = poidsRestant;
		this.volumeRestant = volumeRestant;
		this.commande = commande;
	}

	/**
	 * Add products to a Colis.
	 * @param p product
	 * @param q quantity
	 * @return 
	 */
	public boolean addProduitQuantite(Produit p, Integer q) {
		int poidsTotal = p.getPoids() * q;
		int volumeTotal = p.getVolume() * q;

		if (this.poidsRestant - poidsTotal < 0) {
			return false;
		}
		if (this.volumeRestant - volumeTotal < 0) {
			return false;
		}

		this.produits.add(new QuantiteProduit(p, q));
		this.poidsRestant -= poidsTotal;
		this.volumeRestant -= volumeTotal;
		return true;
	}

	public int getId() {
		return id;
	}

	public int getPoidsRestant() {
		return poidsRestant;
	}

	public int getVolumeRestant() {
		return volumeRestant;
	}

	public Commande getCommande() {
		return commande;
	}

	public List<QuantiteProduit> getProduits() {
		return produits;
	}

	public Chariot getChariot() {
		return chariot;
	}

	@Override
	public int hashCode() {
		return 7;
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
		final Colis other = (Colis) obj;
		if (this.id != other.id) {
			return false;
		}
		if (this.poidsRestant != other.poidsRestant) {
			return false;
		}
		if (this.volumeRestant != other.volumeRestant) {
			return false;
		}
		if (!Objects.equals(this.commande, other.commande)) {
			return false;
		}
		return Objects.equals(this.produits, other.produits);
	}

	@Override
	public String toString() {
		return "Colis{" 
						+ "id=" + id 
						+ ", poidsRestant=" + poidsRestant 
						+ ", volumeRestant=" + volumeRestant 
						+ ", commande=" + commande 
						+ ", averageProductId=" + averageProductId
						+ '}' + "\n";
	}

	public void setAverageProductId(int id) {
		this.averageProductId = id;
	}

	public int getAverageId() {
		return this.averageProductId;
	}
}
