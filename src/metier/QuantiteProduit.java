/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metier;

import javax.persistence.*;
import java.util.Objects;

/**
 * Association d'un produit et de sa quantité.
 * @author Rod
 */
@Entity
@Table(name = "QUANTITEPRODUIT")
public class QuantiteProduit {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID")
	private Integer id;

	@JoinColumn(name = "PRODUIT")
	private Produit produit;

	@Column(name = "QUANTITE")
	private Integer quantite;

	@JoinColumn(name = "COLIS", referencedColumnName = "COLISNO")
	@ManyToOne(optional = false)
	private Colis colis;

	@JoinColumn(name = "COMMANDE", referencedColumnName = "COMMANDENO")
	@ManyToOne(optional = false)
	private Commande commande;

	public QuantiteProduit() {
	}

	public QuantiteProduit(Produit produit, Integer quantite) {
		this.produit = produit;
		this.quantite = quantite;
	}

	public Produit getProduit() {
		return produit;
	}

	public Integer getQuantite() {
		return quantite;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return 3;
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
		final QuantiteProduit other = (QuantiteProduit) obj;
		if (!Objects.equals(this.produit, other.produit)) {
			return false;
		}
		return Objects.equals(this.quantite, other.quantite);
	}

	@Override
	public String toString() {
		return "QuantiteProduit{" + "produit=" + produit + ", quantite=" + quantite + '}';
	}
}
