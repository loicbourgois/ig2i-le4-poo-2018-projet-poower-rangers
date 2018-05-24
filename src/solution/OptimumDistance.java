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
}
