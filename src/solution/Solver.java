/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
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
 *
 * @author antoine
 */
public class Solver {

	private Instance instance;
	public List<Chariot> chariots;

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
	public List<Chariot> solverSolution(Entrepot entrepot, Configuration config) {
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
	 *
	 * @param commande commande
	 * @param config configuration
	 * @return liste colis
	 */
	public List<Colis> productToColis(Commande commande, Configuration config) {
		List<Colis> colis = new ArrayList<>();

		colis.add(new Colis(config.getPoidsMax(), config.getValueMax(), commande));
		
		for (QuantiteProduit qp : commande.getProduitsCommandes()) {
			Produit p = qp.getProduit();
			Integer qtt = qp.getQuantite();

			loopQttProd(commande, config, colis, p, qtt);
		}
		return colis;
	}
	
	/**
	 * Loop qtt of a product.
	 *
	 * @param colis colis
	 * @param p produit
	 * @param qtt integer
	 */
	private void loopQttProd(Commande cmd, 
														Configuration config,
														List<Colis> colis,
														Produit p,
														Integer qtt) {
		for (Integer i = 0; i < qtt; i++) {
			Boolean assigne = false;

			loopColis(colis, assigne, p);

			// Aucun colis libre, création d'un nouveau
			if (!assigne) {
				Colis newColis = new Colis(config.getPoidsMax(), config.getValueMax(), cmd);
				newColis.addProduitQuantite(p, 1);
				colis.add(newColis);
			}
		}
	}
	
	/**
	 * Loop colis.
	 *
	 * @param colis colis
	 * @param assigne boolean
	 * @param p produit
	 */
	private void loopColis(List<Colis> colis, Boolean assigne, Produit p) {
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
			//List<QuantiteProduit> p = colis.get(i).getProduits();
			int aveId = 0;
			int minId = 99999999;
			int maxId = 0;
			int aveMaxMin = 0;
			for (int j = 0; j < colis.get(i).getProduits().size(); j++) {
				int a = colis.get(i).getProduits().get(j).getProduit().getLocalisation().getId();
				aveId += a;
				if (a < minId) {
					minId = a;
				}
				if (a > maxId) {
					maxId = a;
				}
			}
			aveId /= colis.get(i).getProduits().size();
			aveMaxMin = (maxId + minId) / 2;
			colis.get(i).setAverageProductId(minId);
		}

		Collections.sort(colis, (Colis a1, Colis a2) -> a1.getAverageId() - a2.getAverageId());

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
		this.chariots.addAll(solverSolution(instance.getEntrepot(), instance.getConfig()));
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
