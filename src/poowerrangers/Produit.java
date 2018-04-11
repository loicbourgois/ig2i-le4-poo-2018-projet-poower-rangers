/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poowerrangers;

/**
 *
 * @author Rod
 */
public class Produit {
    private int id;
    private int poids;
    private int volume;

    public Produit() {
    }

    public Produit(int id, int poids, int volume) {
        this.id = id;
        if(poids < 0){
            this.poids = 0;
        }
        else{
            this.poids = poids;
        }
        if(volume < 0){
            this.volume = 0;
        }
        else{
            this.volume = volume;
        }
    }
    
    
}
