/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solution;

import java.util.ArrayList;
import java.util.List;
import metier.Chariot;
import metier.Configuration;
import metier.Entrepot;
import metier.Instance;

/**
 *
 * @author user
 */
public class Main {
	public static void main(String[] args) {
        Instance instance = new Instance("./instances/instance_0116_131950_Z1.txt");
        instance.parse();
        instance.dispatch();
        
        Sample solution = new Sample(instance);
        
        List<Chariot> chariots = new ArrayList<>();
        
        Entrepot entrepot = instance.getEntrepot();
        Configuration config = instance.getConfig();
        
        chariots.addAll(solution.sampleSolution(entrepot, config));
    }
}
