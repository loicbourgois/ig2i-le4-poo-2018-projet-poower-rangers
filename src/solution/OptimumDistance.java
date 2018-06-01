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
 * Solver.
 * @author antoine
 */
public class OptimumDistance {

	private Instance instance;
	public List<Chariot> chariots;

	public OptimumDistance(Instance instance) {
		this.instance = instance;
	}

	/**
	 * Trouve une distance optimale.
	 * @param entrepot entrepot
	 * @param config configuration
	 * @return liste de chariots
	 */
	public List<Chariot> optimumDistanceSolution(Entrepot entrepot, Configuration config) {
		List<Chariot> chariots = new ArrayList<>();
		List<Colis> colis = new ArrayList<>();

		Set<Commande> commandes = entrepot.getCommandes();

		for (Commande c : commandes) {
			colis.addAll(productToColis(c, config));
		}

		chariots.add(new Chariot(config.getNbBoxesTrolley()));
                
                insertionSortColis(colis);
                
		return colisToChariot(colis, chariots, config);
	}

	/**
	 * Assign des produits à un colis.
	 * @param commande commande
	 * @param config config
	 * @return list de colis
	 */
	public List<Colis> productToColis(Commande commande, Configuration config) {
		List<Colis> colis = new ArrayList<>();
		List<Produit> produitsCommandes = new ArrayList<>();

		colis.add(new Colis(config.getPoidsMax(), config.getValueMax(), commande));

		Boolean assigne = false;

		for (QuantiteProduit qp : commande.getProduitsCommandes()) {
			produitsCommandes.add(qp.getProduit());
		}

		insertionSort(produitsCommandes);

		for (Produit p : produitsCommandes) {
			Integer qtt = commande.getQttProduit(p);

			for (Integer i = 0; i < qtt; i++) {
				assigne = false;

				// Parcourt des colis existants
				for (Colis col : colis) {
					if (assigne) {
						break; // Si assigné, on sort
					}
					if (col.getPoidsRestant() >= p.getPoids() 
									&& col.getVolumeRestant() >= p.getVolume()
					) { // S'il passe dans un colis on assigne
						col.addProduitQuantite(p, 1);
						assigne = true;
					}
				}

				// Aucun colis libre, création d'un nouveau
				if (!assigne) {
					Colis newColis = new Colis(
									config.getPoidsMax(), 
									config.getValueMax(), 
									commande
					);
					newColis.addProduitQuantite(p, 1);
					colis.add(newColis);
				}
			}
		}

		return colis;
	}

	/**
	 * Assign a colis to a cart.
	 * @param colis colis
	 * @param chariots cart
	 * @param config configurations
	 * @return list of carts
	 */
	public List<Chariot> colisToChariot(
					List<Colis> colis,
					List<Chariot> chariots,
					Configuration config
	) {
		Boolean assigne = false;

		for (Colis col : colis) {
			assigne = false;
			for (Chariot ch : chariots) {
				if (assigne) {
					break;
				}
				if (ch.getColis().size() < config.getNbBoxesTrolley()) {
					ch.addColis(col);
					assigne = true;
				}
			}

			if (!assigne) {
				Chariot newChariot = new Chariot(config.getNbBoxesTrolley());
				newChariot.addColis(col);
				chariots.add(newChariot);
			}
		}

		return chariots;
	}

	/**
	 * Sort products.
	 * @param produits produits
	 * @return liste de produits
	 */
	public List<Produit> insertionSort(List<Produit> produits) {

		Produit produit = new Produit();
		int i = 1;

		while (i < produits.size()) {
			produit = produits.get(i);
			int j = i - 1;
			int k = i;
			while (j >= 0 && produits.get(j).getId() > produit.getId()) {
				k = j + 1;
				produits.set(k, produits.get(j));
				j--;
			}
			produits.set(k, produit);
			i++;
		}

		return produits;
	}
        
        /**
	 * Sort colis.
	 * @param produits produits
	 * @return liste de produits
	 */
	public List<Colis> insertionSortColis(List<Colis> colis) {

		Colis newColis = new Colis();
		int i = 1;

		while (i < colis.size()) {
			newColis = colis.get(i);
			int j = i - 1;
			int k = i;
//			while (j >= 0 && 
//                                colis.get(j)
//                                        .getProduits()
//                                        .get(0)
//                                        .getProduit()
//                                        .getLocalisation()
//                                        .getId() > 
//                                newColis.getProduits()
//                                        .get(0)
//                                        .getProduit()
//                                        .getLocalisation()
//                                        .getId()) {
//				k = j + 1;
//				colis.set(k, colis.get(j));
//				j--;
//			}
                        while(j >= 0 && averageLocalisation(colis.get(j)) > averageLocalisation(newColis)) {
				k = j + 1;
				colis.set(k, colis.get(j));
				j--;
			}
			colis.set(k, newColis);
			i++;
		}

		return colis;
	}
        
        public int averageLocalisation(Colis colis) {
            int average = 0;
            int compt = 0;
            for(QuantiteProduit p: colis.getProduits()) {
                average += p.getProduit().getLocalisation().getId();
                compt++;
            }
            
            return average / compt;
        }
        
        

	/**
	 * Local main fucntion.
	 * @param args standard arguments
	 */
	public static void main(String[] args) {
		Instance instance = new Instance("./instances/instance_0116_131940_Z2.txt");
		instance.parse();
		instance.dispatch();
		Sample solution = new Sample(instance);
		solution.populateChariots();
		System.out.println(solution.toString());
	}

	public void populateChariots() {
		this.chariots = new ArrayList<>();
		this.chariots.addAll(optimumDistanceSolution(instance.getEntrepot(), instance.getConfig()));
	}

	List<Chariot> getChariots() {
		return this.chariots;
	}
}
