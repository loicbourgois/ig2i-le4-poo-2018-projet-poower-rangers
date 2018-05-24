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
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author user
 */
public class Output {

	private int roundCount;
	private int colisCount;
	private String str;
	private boolean strGenerated;
	private ArrayList<ArrayList<ArrayList<Integer>>> rounds;
	private String filename;
	private List<Chariot> chariots;

	public Output() {
		this.roundCount = 0;
	}

    public Output(String inputFilename, List<Chariot> chariots) {
        this.roundCount = chariots.size();
        this.chariots = chariots;
        this.filename = inputFilename.replace(".txt", "_sol.txt").replace("instances", "solutions");
        System.out.println(this.filename);
    }

	private void generateStr() {
		this.str = "";
		this.str = this.str.concat("//NbTournees" + "\n");
		this.str = this.str.concat(this.roundCount + "\n");
		int idPackage = 1;
		for (int i = 0; i < this.roundCount; i++) {
			this.str = this.str.concat("//IdTournes NbColis" + "\n");
			this.str = this.str.concat((i + 1) + " " + rounds.get(i).size() + "\n");
			this.str = this.str.concat("//IdColis IdCommandeInColis NbProducts IdProd1 QtyProd1 IdProd2 QtyProd2 ..." + "\n");
			for (int j = 0; j < rounds.get(i).size(); j++) {
				this.str = this.str.concat(idPackage + " " + "0" + " " + rounds.get(i).get(j).size() / 2);
				for (int k = 0; k < rounds.get(i).get(j).size(); k++) {
					this.str = this.str.concat(" " + Integer.toString(rounds.get(i).get(j).get(k)));
				}
				this.str = this.str.concat("\n");
				idPackage++;
			}
		}
	}

	private void generateStr2() {
		this.str = "";
		this.str = this.str.concat("//NbTournees" + "\n");
		this.str = this.str.concat(this.roundCount + "\n");
		int idPackage = 1;
		for (int i = 0; i < this.roundCount; i++) {
			Set<Colis> colis = chariots.get(i).getColis();
			this.str = this.str.concat("//IdTournes NbColis" + "\n");
			this.str = this.str.concat((i + 1) + " " + colis.size() + "\n");
			this.str = this.str.concat("//IdColis IdCommandeInColis NbProducts IdProd1 QtyProd1 IdProd2 QtyProd2 ..." + "\n");

			List colisList = colis.stream().collect(toList());

			for (int j = 0; j < colisList.size(); j++) {
				Colis colisUnit = (Colis) colisList.get(j);
				Map<Produit, Integer> produits = colisUnit.getProduits();
				this.str = this.str.concat(idPackage + " " + colisUnit.getCommande().getId() + " " + produits.size());
				for (Map.Entry<Produit, Integer> entry : produits.entrySet()) {
					Integer id = entry.getKey().getId();
					Integer quantity = entry.getValue();
					this.str = this.str.concat(" " + id + " " + quantity);
				}
				this.str = this.str.concat("\n");
				idPackage++;
			}

		}
	}

    public String toString() {
        if (!strGenerated) {
            this.generateStr2();
        }
        return this.str;
    }

    private int getRand(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public void writeToFile() {
        try (PrintWriter out = new PrintWriter(this.filename)) {
            out.println(this.toString());
            System.out.println("Writing successful");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Output.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        System.out.println("Output");
        Output output = new Output();
        /*output.roundCount = 2;
		output.colisCount = 6;
		output.rounds = new ArrayList<>();
		for (int i = 0; i < output.roundCount; i++) {
			output.rounds.add(new ArrayList<>());
			for (int j = 0; j < output.colisCount; j++) {
				output.rounds.get(i).add(new ArrayList<>());
				int idProd = 1;
				for (int k = 0; k < output.getRand(4, 8); k++) {
					output.rounds.get(i).get(j).add(idProd);
					output.rounds.get(i).get(j).add(output.getRand(1, 3));
					idProd += output.getRand(1, 2);
				}
			}
		}*/
        System.out.println(output.toString());
        //output.filename = "./solutions/test_sol.txt";
        //output.writeToFile();
    }
}
