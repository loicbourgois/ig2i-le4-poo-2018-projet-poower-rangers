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
import metier.Output;
import metier.Produit;
import metier.QuantiteProduit;

/**
 * Solver.
 * @author antoine
 */
public class Sample {

	private Instance instance;
	private Output output;
	public List<Chariot> chariots;

	public Sample(Instance instance) {
		this.instance = instance;
	}

	/**
	 * Permet de passer des commandes aux chariots.
	 * @param entrepot entrepot
	 * @param config configuration
	 * @return liste de chariots
	 */
	public List<Chariot> sampleSolution(Entrepot entrepot, Configuration config) {
		List<Chariot> chariots = new ArrayList<>();
		List<Colis> colis = new ArrayList<>();

		Set<Commande> commandes = entrepot.getCommandes();

		for (Commande c : commandes) {
			colis.addAll(productToColis(c, config));
		}

		chariots.add(new Chariot(config.getNbBoxesTrolley()));

		return colisToChariot(colis, chariots, config);
	}

	/**
	 * Assign a product to a colis.
	 * @param commande commande
	 * @param config configuration
	 * @return 
	 */
	public List<Colis> productToColis(Commande commande, Configuration config) {
		List<Colis> colis = new ArrayList<>();

		colis.add(new Colis(config.getPoidsMax(), config.getValueMax(), commande));

		Boolean assigne = false;

		for (QuantiteProduit qp : commande.getProduitsCommandes()) {
			Produit p = qp.getProduit();
			Integer qtt = qp.getQuantite();

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
					Colis newColis = new Colis(config.getPoidsMax(), config.getValueMax(), commande);
					newColis.addProduitQuantite(p, 1);
					colis.add(newColis);
				}
			}
		}
		return colis;
	}

	/**
	 * Assign a colis to a chariot.
	 * @param colis colis
	 * @param chariots chariots
	 * @param config configuration
	 * @return liste de chariots
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

	public void populateChariots() {
		this.chariots = new ArrayList<>();
		this.chariots.addAll(sampleSolution(instance.getEntrepot(), instance.getConfig()));
	}

	@Override
	public String toString() {
		String r = "";
		for (int i = 0; i < chariots.size(); i++) {
			r = r.concat(chariots.get(i).toString());
		}
		return r;
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

	List<Chariot> getChariots() {
		return this.chariots;
	}
}
