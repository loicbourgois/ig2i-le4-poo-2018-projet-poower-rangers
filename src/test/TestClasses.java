/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import metier.Chariot;
import metier.Colis;
import metier.Commande;
import metier.Entrepot;
import metier.Localisation;
import metier.Produit;

/**
 * Classe pour les tests.
 * @author Rod
 */
public class TestClasses {

	/**
	 * Main function to launch tests.
	 * @param args aguments
	 */
	public static void main(String[] args) {
		Localisation l1 = new Localisation(1, 3, 5);
		Localisation l2 = new Localisation(2, 6, 8);
		Localisation l3 = new Localisation(3, 9, 11);
		Localisation l4 = new Localisation(4, 12, 14);
		Produit p1 = new Produit(1, 20, 20, l1);
		Produit p2 = new Produit(2, 2, 50, l2);
		Produit p3 = new Produit(3, 60, 1, l2);
		Produit p4 = new Produit(4, 30, 5, l3);
		Produit p5 = new Produit(5, 18, 7, l3);
		Produit p6 = new Produit(6, 50, 80, l3);
		Produit p7 = new Produit(7, 14, 18, l4);
		Produit p8 = new Produit(8, 39, 45, l4);
		Commande com1 = new Commande(1, 1);
		Commande com2 = new Commande(2, 2000000);
		Commande com3 = new Commande(3, 5);
		com1.addProduitQuantite(p1, 4);
		com1.addProduitQuantite(p2, 7);
		com1.addProduitQuantite(p3, 3);
		com2.addProduitQuantite(p4, 10);
		com3.addProduitQuantite(p5, 1);
		com3.addProduitQuantite(p6, 2);
		com3.addProduitQuantite(p7, 11);
		com3.addProduitQuantite(p8, 99);
		Colis col1 = new Colis(1, 1000, 1000, com1);
		Colis col2 = new Colis(2, 1, 2000, com2);
		Colis col3 = new Colis(3, 2000, 1, com2);
		Colis col4 = new Colis(3, 420, 991, com3);
		Chariot cha1 = new Chariot(1);
		cha1.addColis(col1);
		cha1.addColis(col2);
		cha1.addColis(col3);
		cha1.addColis(col4);
		Entrepot e = new Entrepot(1);
		e.addCommande(com1);
		e.addCommande(com2);
		e.addCommande(com3);
		e.addDepot(l1);
		e.addDepot(l2);
		e.addDepot(l3);
		e.addDepot(l4);
		e.addProduit(p1);
		e.addProduit(p2);
		e.addProduit(p3);
		e.addProduit(p4);
		e.addProduit(p5);
		e.addProduit(p6);
		e.addProduit(p7);
		e.addProduit(p8);

		System.out.println(l1.toString());
		System.out.println(l2.toString());
		System.out.println(l3.toString());
		System.out.println(l4.toString());
		System.out.println(p1.toString());
		System.out.println(p2.toString());
		System.out.println(p3.toString());
		System.out.println(p4.toString());
		System.out.println(p5.toString());
		System.out.println(p6.toString());
		System.out.println(p7.toString());
		System.out.println(p8.toString());
		System.out.println(com1.toString());
		System.out.println(com2.toString());
		System.out.println(com3.toString());
		System.out.println(col1.toString());
		System.out.println(col2.toString());
		System.out.println(col3.toString());
		System.out.println(col4.toString());
		System.out.println(cha1.toString());
		System.out.println(e.toString());
	}
}
