/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Rod
 */
@Entity
@Table(name="COLIS")
public class Colis {
    
    @Id
    @Column(name="COLISNO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name="POIDSRESTANT")
    private int poidsRestant;
    
    @Column(name="VOLUMERESTANT")
    private int volumeRestant;
    
    @Column(name="COMMANDE")
    private Commande commande;
    
    // tout doux
    private Map<Produit,Integer> produits;
    
    @JoinColumn(name = "CHARIOT", referencedColumnName = "CHARIOTNO")
    @ManyToOne(optional = false)
    private Chariot chariot;

    public Colis() {
        this.produits = new HashMap<Produit,Integer>();
    }
    
    public Colis(int poidsRestant, int volumeRestant, Commande commande) {
        this();
        this.poidsRestant = poidsRestant;
        this.volumeRestant = volumeRestant;
        this.commande = commande;
    }

    public Colis(int id, int poidsRestant, int volumeRestant, Commande commande) {
        this();
        this.id = id;
        this.poidsRestant = poidsRestant;
        this.volumeRestant = volumeRestant;
        this.commande = commande;
    }
    
    public boolean addProduitQuantite(Produit p , Integer q){
        int poidsTotal = p.getPoids()*q;
        int volumeTotal = p.getVolume()*q;
        
        if(this.poidsRestant - poidsTotal < 0) return false;
        if(this.volumeRestant - volumeTotal < 0) return false;
        
        this.produits.put(p, q);
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

    public Map<Produit, Integer> getProduits() {
        return produits;
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
        if (!Objects.equals(this.produits, other.produits)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Colis{" + "id=" + id + ", poidsRestant=" + poidsRestant + ", volumeRestant=" + volumeRestant + ", commande=" + commande + '}'+ "\n";
    }
    
    
}
