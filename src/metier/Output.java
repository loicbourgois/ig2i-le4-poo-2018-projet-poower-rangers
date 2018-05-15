/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

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

	public Output() {
		this.roundCount = 0;
	}
	
	public String toString() {
		if(!strGenerated) {
			this.generateStr();
		}
		return this.str;
	}

	private void generateStr() {
		this.str = "";
		this.str = this.str.concat("//NbTournees" + "\n");
		this.str = this.str.concat(this.roundCount + "\n");
		int idPackage = 1;
		for(int i = 0 ; i < this.roundCount ; i++) {
			this.str = this.str.concat("//IdTournes NbColis" + "\n");
			this.str = this.str.concat((i+1) + " " + rounds.get(i).size() + "\n");
			this.str = this.str.concat("//IdColis IdCommandeInColis NbProducts IdProd1 QtyProd1 IdProd2 QtyProd2 ..." + "\n");
			for(int j = 0 ; j < rounds.get(i).size() ; j++) {
				this.str = this.str.concat(idPackage + " " + "0" + " " + rounds.get(i).get(j).size()/2);
				for(int k = 0 ; k < rounds.get(i).get(j).size() ; k++) {
					this.str = this.str.concat(" " + Integer.toString(rounds.get(i).get(j).get(k)));
				}
				this.str = this.str.concat("\n");
				idPackage++;
			}
		}
	}
	
	private int getRand(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	
	public static void main(String[] args) {
		System.out.println("Output");
		Output output = new Output();
		output.roundCount = 2;
		output.colisCount = 6;
		output.rounds = new ArrayList<ArrayList<ArrayList<Integer>>>();
		for(int i = 0 ; i < output.roundCount ; i++) {
			output.rounds.add(new ArrayList<ArrayList<Integer>>());
			for(int j = 0 ; j < output.colisCount ; j++) {
				output.rounds.get(i).add(new ArrayList<Integer>());
				int idProd = 1;
				for(int k = 0 ; k < output.getRand(4, 8) ; k++) {
					output.rounds.get(i).get(j).add(idProd);
					output.rounds.get(i).get(j).add(output.getRand(1, 3));	
					idProd += output.getRand(1, 2);
				}
			}
		}
		System.out.println(output.toString());
	}
}
