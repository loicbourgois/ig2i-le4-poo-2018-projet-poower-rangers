/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package solution;

import metier.*;

import java.util.*;

/**
 * Solver.
 *
 * @author antoine
 */
public class Solver {

	private Instance instance;
	private List<Chariot> chariots;

	public Solver(Instance instance) {
		this.instance = instance;
	}

	/**
	 * Permet de passer des commandes aux chariots.
	 *
	 * @param entrepot entrepot
	 * @param config configuration
	 * @return liste de chariots
	 */
	public List<Chariot> sampleSolution(Entrepot entrepot, Configuration config) {
		List<Chariot> chariotsList = new ArrayList<>();
		List<Colis> colis = new ArrayList<>();

		Set<Commande> commandes = entrepot.getCommandes();

		for (Commande c : commandes) {
			colis.addAll(productToColis(c, config));
		}

		chariotsList.add(new Chariot(config.getNbBoxesTrolley()));

		return colisToChariot(colis, chariotsList, config);
	}

	/**
	 * Assign a product to a colis.
	 *
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

				// Parcourt des colis
				for (Integer j = colis.size() - 1; j >= 0; j--) {
					if (assigne) {
						break; // Si assigné, on sort
					}
					// S'il passe dans un colis on assigne
					if (colis.get(j).getPoidsRestant() >= p.getPoids()
									&& colis.get(j).getVolumeRestant() >= p.getVolume()) {
						colis.get(j).addProduitQuantite(p, 1);
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
	 *
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

		for (int i = 0; i < colis.size(); i++) {
			int minId = 99999999;
			int maxId = 0;
			for (int j = 0; j < colis.get(i).getProduits().size(); j++) {
				int a = colis.get(i).getProduits().get(j).getProduit().getLocalisation().getId();
				if (a < minId) {
					minId = a;
				}
				if (a > maxId) {
					maxId = a;
				}
			}
			colis.get(i).setAverageProductId(minId);
		}

		Collections.sort(colis, Comparator.comparingInt(Colis::getAverageId));

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

	List<Chariot> getChariots() {
		return this.chariots;
	}
}
