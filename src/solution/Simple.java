/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solution;

import java.util.List;
import java.util.Set;
import metier.Chariot;
import metier.Colis;
import metier.Commande;
import metier.Entrepot;
import metier.Instance;
import metier.Localisation;
import metier.Produit;

/**
 *
 * @author antoine
 */
public class Simple {
    private Instance instance;
    
    private List<Colis> colis;

    public Simple(Instance instance) {
        this.instance = instance;
    }
    
    public void solutionSimple() {
        Entrepot entrepot = this.instance.getEntrepot();
        int poidsColisMax = this.instance.getConfig().getPoidsMax();
        int volColisMax = this.instance.getConfig().getValueMax();
        int nbColis = 0;
        int quantite = 0;
        
        Set<Localisation> depots = entrepot.getDepots();
        Set<Commande> commandes = entrepot.getCommandes();
        Set<Produit> produits = entrepot.getProduits();
        Set<Chariot> chariots = entrepot.getChariots();
        
        Set<Colis> colis = null;
        Set<Produit> produitsCommandes = null;
        
        // Affectation du nombre de colis
        for(Commande c: commandes) {
            int poidsMax = c.calculePoidsTotal();
            int volMax = c.calculeVolumeTotal();
            
            if(poidsMax/poidsColisMax > volMax/volColisMax) nbColis = poidsMax/poidsColisMax;
            else nbColis = volMax/volColisMax;
            
            // Vérif si nbColis++ à cause de la division
            // SETTER nbColis
            // c.set...
        }
        
        while(nbColis > 0)
            nbColis = assignerProduit(nbColis, colis, produitsCommandes); // Ajouter QTT
        
    }
    
    private int assignerProduit(int nbColis, Set<Colis> colis, Set<Produit> produits) {
        Produit prod = new Produit();
        // Cas simple (Poids)
        for(Colis c: colis) {
            while(c.getPoidsRestant() > 0)
                prod = chercherProduitLourd(produits);
                c.addProduitQuantite(prod, 1); // Ajouter calcul de QTT * PoidsProduit
            nbColis--;
        }
        // Cas simple (Volume)
        
        // Cas combiné (Poids puis volume)
        return nbColis;
    }
    
    private Produit chercherProduitLourd(Set<Produit> produits) {
        Produit produitLourd = new Produit();
        int poids = 0;
        
        for(Produit p: produits) {
            if(p.getPoids() > poids) {
                poids = p.getPoids();
                produitLourd = p;
            }
        }
        
        return produitLourd;
    }
}
