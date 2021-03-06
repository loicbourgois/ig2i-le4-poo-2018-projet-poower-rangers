/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	public List<Chariot> solverSolution(Entrepot entrepot, Configuration config) {
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

			assigne = loopColis(colis, assigne, p);

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
	 * @return boolean
	 */
	private Boolean loopColis(List<Colis> colis, Boolean assigne, Produit p) {
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
		return assigne;
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
		Integer idSelect = 0;
		Boolean assigne = false;

		for (int i = 0; i < colis.size(); i++) {
			colis.get(i).setAverageProductId(loopProduct(colis, i, idSelect));
		}

		Collections.sort(colis, Comparator.comparingInt(Colis::getAverageId));

		for (Colis col : colis) {			
			assigne = loopChariots(config, false, col);

			if (!assigne) {
				Chariot newChariot = new Chariot(config.getNbBoxesTrolley());
				newChariot.addColis(col);
				chariots.add(newChariot);
			}
		}

		return chariots;
	}
	
	/**
	 * Loop products.
	 *
	 * @param colis colis
	 * @param i integer
	 * @param idSelect integer
	 */
	private Integer loopProduct(List<Colis> colis, Integer i, Integer idSelect) {
		Integer aveId = 0;
		Integer minId = Integer.MAX_VALUE;
		Integer maxId = 0;
		
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
		
		switch (idSelect) {
			case 0 :
				return minId;
			case 1 :
				return maxId;
			case 2 :
				return (aveId / colis.get(i).getProduits().size());
			case 3 :
				return ((maxId + minId) / 2);
			default :
				return 0;
		}
	}
	
	/**
	 * Loop chariots.
	 *
	 * @param config configuration
	 * @param assigne boolean
	 * @param col colis
	 * @return boolean
	 */
	private Boolean loopChariots(Configuration config, Boolean assigne, Colis col) {
		for (Chariot ch : chariots) {
			if (assigne) {
				break;
			}
			if (ch.getColis().size() < config.getNbBoxesTrolley()) {
				ch.addColis(col);
				assigne = true;
			}
		}
		
		return assigne;
	}
	
	/**
	 * Add all chariots.
	 */
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
