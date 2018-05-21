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

    public Simple(Instance instance) {
        this.instance = instance;
    }
    
    public List<Chariot> sampleSolution() {
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
                        chariots.add(new Chariot(config.getNbBoxesTrolley()));
                    indexChariot = 0;
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
                    System.out.println("BTEST");
                    System.out.println(chariots.get(indexChariot-1).getColis().size());
                    System.out.println("TEST");
                    if(chariots.get(indexChariot-1).getColis().size() < config.getNbBoxesTrolley()) {
                        Colis newColis = new Colis(config.getPoidsMax(), config.getValueMax(), c);
                        chariots.get(indexChariot-1).addColis(newColis);
                        addProduitColis(newColis, p, qtt);
                        qtt--;
                    }
                    else if(chariots.size() == indexChariot){
                        Chariot newChariot = new Chariot(config.getNbBoxesTrolley());
                        Colis newColis = new Colis(config.getPoidsMax(), config.getValueMax(), c);
                        chariots.add(newChariot);
                        newChariot.addColis(newColis);
                        addProduitColis(newColis, p, qtt);
                        qtt--;
                    }
                }
            }
        }
        return chariots;
    }
    
    private void addProduitColis(Colis co, Produit p, Integer qtt) {
        while(co.getPoidsRestant() > p.getPoids() && co.getVolumeRestant() > p.getVolume() && qtt > 0) {
            co.addProduitQuantite(p, 1);
            qtt--;
        }
    }
    
    public static void main(String[] args) {
        Instance instance = new Instance("./instances/instance_0116_131950_Z1.txt");
        instance.parse();
        //System.out.println(instance.toString());
        instance.dispatch();
        
        
        
        Simple solution = new Simple(instance);
        
        List<Chariot> chariots = solution.sampleSolution();
        
        for(Chariot ch: chariots) {
            System.out.println(ch.toString());
        }
    }
}
