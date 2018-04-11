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
public class Configuration {
    private int nbBoxesTrolley;
    private int poidsMax;
    private int valueMax;

    public Configuration() {
    }

    public Configuration(int nbBoxesTrolley, int poidsMax, int valueMax) {
        if(nbBoxesTrolley < 0){
            this.nbBoxesTrolley = 0;
        }
        else{
            this.nbBoxesTrolley = nbBoxesTrolley;
        }
        if(poidsMax < 0){
            this.poidsMax = 0;
        }
        else{
            this.poidsMax = poidsMax;
        }
        if(valueMax < 0){
            this.valueMax = 0;
        }
        else{
            this.valueMax = valueMax;
        }
    }

    public int getNbBoxesTrolley() {
        return nbBoxesTrolley;
    }

    public int getPoidsMax() {
        return poidsMax;
    }

    public int getValueMax() {
        return valueMax;
    }
    
}
