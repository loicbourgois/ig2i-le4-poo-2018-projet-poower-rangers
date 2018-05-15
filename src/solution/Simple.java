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
    
    public void sampleSolution() {
        // AJOUTER CONDITION POUR NE PAS AJOUTER 2 COMMANDES DANS UN MEME COLIS

        List<Chariot> chariots = new ArrayList<>();
        
        Set<Colis> colis = null;
        
        Entrepot entrepot = this.instance.getEntrepot();
        Configuration config = this.instance.getConfig();
        
        Set<Commande> commandes = entrepot.getCommandes();
        
        Integer indexChariot = 0;
        Integer indexColis = 0;
        
        for(Commande c: commandes) {
            for(Map.Entry<Produit, Integer> entry : c.getProduitCommande().entrySet()){
                
                Produit p = entry.getKey();
                Integer qtt = entry.getValue();

                while(qtt > 0) {
                    if(chariots.isEmpty())
                        chariots.add(new Chariot());
                        
                    for(Chariot ch: chariots) {
                        indexChariot++; indexColis = 0;
                        if(ch.getColis().isEmpty())
                            ch.addColis(new Colis(config.getPoidsMax(), config.getValueMax(), c));
                        if(qtt == 0) break;
                        colis = ch.getColis();

                        for(Colis co: colis){
                            indexColis++;
                            if(qtt == 0) break;
                            addProduitColis(co, p, qtt);
                        }
                    }
                    
                    if(chariots.get(indexChariot-1).getColis().size() < config.getNbBoxesTrolley()) {
                        Colis newColis = new Colis(config.getPoidsMax(), config.getValueMax(), c);
                        chariots.get(indexChariot-1).addColis(newColis);
                        addProduitColis(newColis, p, qtt);
                        qtt--;
                    }
                    else if(chariots.size() == indexChariot){
                        Chariot newChariot = new Chariot();
                        Colis newColis = new Colis(config.getPoidsMax(), config.getValueMax(), c);
                        chariots.add(newChariot);
                        newChariot.addColis(newColis);
                        addProduitColis(newColis, p, qtt);
                        qtt--;
                    }
                }
            }
        }
    }
    
    private void addProduitColis(Colis co, Produit p, Integer qtt) {
        while(co.getPoidsRestant() > p.getPoids() && co.getVolumeRestant() > p.getVolume() && qtt > 0) {
            co.addProduitQuantite(p, 1);
            qtt--;
        }
    }
    
    public static void main(String[] args) {
        List<String> listeNoms = new ArrayList<String>();
        int a = 5, index = 0;
        listeNoms.add("Bob");
        listeNoms.add("Loïc");
        listeNoms.add("Jacqueline");
        
        for(String nom: listeNoms) {
            index++;
            if(index ==  listeNoms.size())
                listeNoms.add("Antoine");
            System.out.println(nom);
        }
        
        
    }
    
//    public void solutionSimple() {
//        Entrepot entrepot = this.instance.getEntrepot();
//        int poidsColisMax = this.instance.getConfig().getPoidsMax();
//        int volColisMax = this.instance.getConfig().getValueMax();
//        int nbColis = 0;
//        int quantite = 0;
//        
//        Set<Localisation> depots = entrepot.getDepots();
//        Set<Commande> commandes = entrepot.getCommandes();
//        Set<Produit> produits = entrepot.getProduits();
//        Set<Chariot> chariots = entrepot.getChariots();
//        
//        Set<Colis> colis = null;
//        Set<Produit> produitsCommandes = null;
//        
//        // Affectation du nombre de colis
//        for(Commande c: commandes) {
//            int poidsMax = c.calculePoidsTotal();
//            int volMax = c.calculeVolumeTotal();
//            
//            if(poidsMax/poidsColisMax > volMax/volColisMax) nbColis = poidsMax/poidsColisMax;
//            else nbColis = volMax/volColisMax;
//            
//            // Vérif si nbColis++ à cause de la division
//            // SETTER nbColis
//            // c.set...
//        }
//        
//        while(nbColis > 0)
//            nbColis = assignerProduit(nbColis, colis, produitsCommandes); // Ajouter QTT
//        
//    }
//    
//    private int assignerProduit(int nbColis, Set<Colis> colis, Set<Produit> produits) {
//        Produit prod = new Produit();
//        // Cas simple (Poids)
//        for(Colis c: colis) {
//            while(c.getPoidsRestant() > 0)
//                prod = chercherProduitLourd(produits);
//                c.addProduitQuantite(prod, 1); // Ajouter calcul de QTT * PoidsProduit
//            nbColis--;
//        }
//        // Cas simple (Volume)
//        
//        // Cas combiné (Poids puis volume)
//        return nbColis;
//    }
//    
//    private Produit chercherProduitLourd(Set<Produit> produits) {
//        Produit produitLourd = new Produit();
//        int poids = 0;
//        
//        for(Produit p: produits) {
//            if(p.getPoids() > poids) {
//                poids = p.getPoids();
//                produitLourd = p;
//            }
//        }
//        
//        return produitLourd;
//    }
}
