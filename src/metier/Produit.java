/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metier;

import javax.persistence.*;

/**
 * Product. Has 2 dimensions : weight and volume. 
 * @author Rod
 */
@Entity
@Table(name = "PRODUIT")
public class Produit {

	@Id
	@Column(name = "PRODUITNO")
	private int id;

	@Column(name = "POIDS")
	private int poids;

	@Column(name = "VOLUME")
	private int volume;

	@JoinColumn(name = "LOCALISATION", referencedColumnName = "LOCALISATIONNO")
	private Localisation localisation;

	@JoinColumn(name = "ENTREPOT", referencedColumnName = "ENTREPOTNO")
	@ManyToOne(optional = false)
	private Entrepot entrepot;

	@JoinColumn(name = "QUANTITEPRODUIT", referencedColumnName = "ID")
	@OneToOne
	private QuantiteProduit quantiteProduit;

	@JoinColumn(name = "COLIS", referencedColumnName = "COLISNO")
	@ManyToOne(optional = false)
	private Colis colis;

	/**
	 * Default constructor.
	 */
	public Produit() {
	}

	/**
	 * Real constructor.
	 * @param id id
	 * @param poids weight 
	 * @param volume volume
	 * @param localisation localisation
	 */
	public Produit(int id, int poids, int volume, Localisation localisation) {
		this.id = id;
		if (poids < 0) {
			this.poids = 0;
		} else {
			this.poids = poids;
		}
		if (volume < 0) {
			this.volume = 0;
		} else {
			this.volume = volume;
		}
		if (localisation != null) {
			this.localisation = localisation;
		} else {
			this.localisation = new Localisation();
		}
	}

	@Override
	public int hashCode() {
		return 5;
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
		return this.volume == other.volume;
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
		return "\t\t\tProduit{" 
						+ "id=" + id 
						+ ", poids=" + poids 
						+ ", volume=" + volume 
						+ "Localisation=" + localisation 
						+ '}' + "\n";
	}
}
