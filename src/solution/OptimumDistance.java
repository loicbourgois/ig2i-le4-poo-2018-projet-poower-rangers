/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import metier.Chariot;
import metier.Colis;
import metier.Commande;
import metier.Configuration;
import metier.Entrepot;
import metier.Instance;
import metier.Produit;
import metier.QuantiteProduit;

/**
 *
 * @author antoine
 */
public class OptimumDistance {
    private Instance instance;

    public OptimumDistance(Instance instance) {
        this.instance = instance;
    }
    
    public List<Chariot> optimumDistanceSolution(Entrepot entrepot, Configuration config) {
        List<Chariot> chariots = new ArrayList<>();
        List<Colis> colis = new ArrayList<>();
        
        Set<Commande> commandes = entrepot.getCommandes();
        
        for(Commande c: commandes) {
            colis.addAll(productToColis(c, config));
        }
        
        chariots.add(new Chariot(config.getNbBoxesTrolley()));
        
        return colisToChariot(colis, chariots, config);
    }
    
    public List<Colis> productToColis(Commande c, Configuration config) {
        List<Colis> colis = new ArrayList<>();
        List<Produit> produitsCommandes = new ArrayList<>();
        
        colis.add(new Colis(config.getPoidsMax(), config.getValueMax(), c));
        
        Boolean assigne = false;
        
        for (QuantiteProduit qp : c.getProduitsCommandes()) {
            produitsCommandes.add(qp.getProduit());
        }
        
        for (QuantiteProduit qp : c.getProduitsCommandes()) {
            Produit p = qp.getProduit();
            Integer qtt = qp.getQuantite();

            for (Integer i = 0; i < qtt; i++) {
                assigne = false;

                // Parcourt des colis existants
                for (Colis col : colis) {
                    if (assigne) {
                        break; // Si assigné, on sort
                    }
                    if (col.getPoidsRestant() >= p.getPoids() && col.getVolumeRestant() >= p.getVolume()) { // S'il passe dans un colis on assigne
                        col.addProduitQuantite(p, 1);
                        assigne = true;
                    }
                }

                // Aucun colis libre, création d'un nouveau
                if (!assigne) {
                    Colis newColis = new Colis(config.getPoidsMax(), config.getValueMax(), c);
                    newColis.addProduitQuantite(p, 1);
                    colis.add(newColis);
                }
            }
        }
        
        return colis;
    }
    
    public List<Chariot> colisToChariot(List<Colis> colis, List<Chariot> chariots, Configuration config) {
        Boolean assigne = false;
        
        for(Colis col: colis) {
            assigne = false;
            for(Chariot ch: chariots) {
                if(assigne) break;
                if(ch.getColis().size() < config.getNbBoxesTrolley()) {
                    ch.addColis(col);
                    assigne = true;
                }
            }
            
            if(!assigne) {
                Chariot newChariot = new Chariot(config.getNbBoxesTrolley());
                newChariot.addColis(col);
                chariots.add(newChariot);
            }
        }
        
        return chariots;
    }
    
    public List<Produit> insertionSort(List<Produit> produits) {
        
        Produit produit = new Produit();
        int i = 1;
        
        while(i < produits.size()) {
            produit = produits.get(i);
            int j = i - 1;
            int k = i;
            while(j >= 0 && produits.get(j).getId() > produit.getId()) {
                k = j + 1;
                produits.set(k, produits.get(j));
                j--;
            }
            produits.set(k, produit);
            i++;
        }
        
        return produits;
    }
}
