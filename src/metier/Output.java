/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metier;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;


/**
 * Represents a solution and writes it to a file.
 * @author loicbourgois
 */
public class Output {

	private int roundCount;
	private String str;
	private boolean strGenerated = false;
	private ArrayList<ArrayList<ArrayList<Integer>>> rounds;
	private String filename;
	private List<Chariot> chariots;

	/**
	 * Default constructor.
	 */
	public Output() {
		this.roundCount = 0;
	}

	/**
	 * Constructor by arguments.
	 * @param inputFilename file name
	 * @param chariots list of carts
	 */
	public Output(String inputFilename, List<Chariot> chariots) {
		this.roundCount = chariots.size();
		this.chariots = chariots;
		this.filename = inputFilename.replace(".txt", "_sol.txt").replace("instances", "solutions");
		Logger.getLogger(Output.class.getName()).info(this.filename);
	}



	private void generateStr() {
		this.str = "";
		this.str = this.str.concat("//NbTournees" + "\n");
		this.str = this.str.concat(this.roundCount + "\n");
		int idPackage = 1;
		for (int i = 0; i < this.roundCount; i++) {
			Set<Colis> colis = chariots.get(i).getColis();
			this.str = this.str.concat("//IdTournes NbColis" + "\n");
			this.str = this.str.concat((i + 1) + " " + colis.size() + "\n");
			this.str = this.str.concat("//IdColis IdCommandeInColis "
							+ "NbProducts IdProd1 QtyProd1 IdProd2 QtyProd2 ..." + "\n");

			List colisList = colis.stream().collect(toList());

			for (int j = 0; j < colisList.size(); j++) {
				Colis colisUnit = (Colis) colisList.get(j);
				ArrayList<QuantiteProduit> produits = (ArrayList<QuantiteProduit>) colisUnit.getProduits();
				this.str = this.str.concat(
								idPackage 
												+ " " 
												+ colisUnit.getCommande().getId() 
												+ " " 
												+ produits.size());
				for (int k = 0; k < produits.size(); k++) {
					Integer id = produits.get(k).getProduit().getId();
					Integer quantity = produits.get(k).getQuantite();
					this.str = this.str.concat(" " + id + " " + quantity);
				}
				this.str = this.str.concat("\n");
				idPackage++;
			}

		}
	}

	/**
	 * Return the output formatted as a string.
	 * @return output formatted as a string
	 */
	public String toString() {
		if (!strGenerated) {
			this.generateStr();
		}
		return this.str;
	}


	/**
	 * Write the generated output to a file.
	 */
	public void writeToFile() {
		try (PrintWriter out = new PrintWriter(this.filename)) {
			out.println(this.toString());
			// System.out.println("Writing successful");
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Output.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}