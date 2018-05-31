/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Rod
 */
@Entity
@Table(name="QUANTITEPRODUIT")
public class QuantiteProduit {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @JoinColumn(name="PRODUIT")
    private Produit produit;
    
    @Column(name="QUANTITE")
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
        final QuantiteProduit other = (QuantiteProduit) obj;
        if (!Objects.equals(this.produit, other.produit)) {
            return false;
        }
        if (!Objects.equals(this.quantite, other.quantite)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "QuantiteProduit{" + "produit=" + produit + ", quantite=" + quantite + '}';
    }
    
}
