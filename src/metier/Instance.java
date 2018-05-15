/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rod
 */
public class Instance {
    private Entrepot entrepot;
    private Configuration config;
    private String pathToFile;
    private int locationCount;
    private int procuctCount;
    private int trolleyCount;
    private int dimensionCount;
    private ArrayList<Integer> boxDimensions;
    private boolean acceptMixedOrders;
    private ArrayList<ArrayList<Integer>> products;
    private int orderCount;
    private ArrayList<ArrayList<Integer>> orders;
    private int verticesIntersectionsCount;
    private int departingDepot;
    private int arrivalDepot;
    private ArrayList<ArrayList<Integer>> arcs;
    private ArrayList<ArrayList<Integer>> shortestPath;
    private ArrayList<ArrayList<String>> locations;

    public Instance() {
    }

    public Instance(String pathToFile) {
            this.pathToFile = pathToFile;
            this.locationCount = -1;
            this.entrepot = new Entrepot();
            this.config = null;
            this.boxDimensions = new ArrayList<Integer>();
    }

    private boolean dispatch() {
            System.out.println("dispatching");
            ArrayList allLocation = new ArrayList<Localisation>();
            ArrayList allProducts = new ArrayList<Produit>();
            ArrayList commandes = new ArrayList<Commande>();
            
            //Location
            for(ArrayList<String> l : locations){
                Localisation loc = new Localisation(Integer.parseInt(l.get(0)), Integer.parseInt(l.get(1)), Integer.parseInt(l.get(2)));
                allLocation.add(loc);
            }
            
            //Distance Location
            for(ArrayList<Integer> a : arcs){
                Localisation lDepart = (Localisation)allLocation.get(a.get(0));
                Localisation lArrivee = (Localisation)allLocation.get(a.get(1));
                lDepart.addDistance(lArrivee, a.get(2));
            }
            
            //Products
            for(ArrayList<Integer> p : products){
                Localisation l = (Localisation)allLocation.get(p.get(1));
                allProducts.add(new Produit(p.get(0),p.get(2),p.get(3),l));
            }
            //System.out.println(allProducts);
            
            //Commande
            for(ArrayList<Integer> order : orders){
                Commande c = new Commande(order.get(0),order.get(2));
                for(int i=3;i<order.size()-1;i+=2){
                    Produit p = (Produit)allProducts.get(order.get(i)-1);
                    c.addProduitQuantite(p, order.get(i+1));
                    //System.out.println(p);
                }
                commandes.add(c);
            //System.out.println(c);
            }
            
            return true;
    }
    
    public Entrepot getEntrepot() {
        return entrepot;
    }

    public Configuration getConfig() {
        return config;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Instance other = (Instance) obj;
        if (!Objects.equals(this.pathToFile, other.pathToFile)) {
            return false;
        }
        if (!Objects.equals(this.entrepot, other.entrepot)) {
            return false;
        }
        if (!Objects.equals(this.config, other.config)) {
            return false;
        }
        return true;
    }

    private boolean parse() {
            System.out.println("parsing");
            try (BufferedReader br = new BufferedReader(new FileReader(this.pathToFile))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                            if(line.startsWith("//NbLocations")) {
                                    line = br.readLine();
                                    this.locationCount = Integer.parseInt(line.replaceAll("\\s+",""));
                            } else if(line.startsWith("//NbProducts")) {
                                    line = br.readLine();
                                    this.procuctCount = Integer.parseInt(line.replaceAll("\\s+",""));
                            } else if(line.startsWith("//K: NbBoxesTrolley")) {
                                    line = br.readLine();
                                    this.trolleyCount = Integer.parseInt(line.replaceAll("\\s+",""));
                            } else if(line.startsWith("//NbDimensionsCapacity")) {
                                    line = br.readLine();
                                    this.dimensionCount = Integer.parseInt(line.replaceAll("\\s+",""));
                            } else if(line.startsWith("//B: CapaBox")) {
                                    line = br.readLine();
                                    String[] dimensions = line.split(" ");
                                    for(int i = 0 ; i < dimensions.length ; i++) {
                                            this.boxDimensions.add(Integer.parseInt(dimensions[i]));
                                    }
                            } else if(line.startsWith("//A box can accept mixed orders(0: no, 1: yes)")) {
                                    line = br.readLine();
                                    if(Integer.parseInt(line.replaceAll("\\s+","")) == 0) {
                                            this.acceptMixedOrders = false;
                                    } else {
                                            this.acceptMixedOrders = true;
                                    }
                            } else if(line.startsWith("//Products")) {
                                    line = br.readLine();
                                    if(line.startsWith("//Idx Location Dim1 Dim2 ...")) {
                                            this.products = new ArrayList<ArrayList<Integer>>();
                                            line = br.readLine();
                                            while(!line.startsWith("//Orders")) {
                                                    String[] numbersStr = line.split(" ");
                                                    ArrayList<Integer> numbers = new ArrayList<Integer>();
                                                    for(int i = 0 ; i < numbersStr.length ; i++) {
                                                            numbers.add(Integer.parseInt(numbersStr[i]));
                                                    }
                                                    if(numbers.size() == this.dimensionCount + 2) {
                                                            this.products.add(numbers);
                                                    }
                                                    line = br.readLine();
                                            }
                                    }
                            } else if(line.startsWith("//NbOrders")) {
                                    line = br.readLine();
                                    this.orderCount = Integer.parseInt(line.replaceAll("\\s+",""));
                            } else if(line.startsWith("//Idx M NbProdInOrder ProdIdx1 Qty1 ProdIdx2 Qty2")) {
                                    this.orders = new ArrayList<ArrayList<Integer>>();
                                    line = br.readLine();
                                    while(!line.startsWith("//Graph (Products are vertices indexe")) {
                                            String[] numbersStr = line.split(" ");
                                            ArrayList<Integer> numbers = new ArrayList<Integer>();
                                            for(int i = 0 ; i < numbersStr.length ; i++) {
                                                    numbers.add(Integer.parseInt(numbersStr[i]));
                                            }
                                            if(numbers.size() > 3 
                                                                            && numbers.size() == numbers.get(2) * this.dimensionCount + 3) {
                                                    this.orders.add(numbers);
                                            }
                                            line = br.readLine();
                                    } 
                            } else if(line.startsWith("//NbVerticesIntersections")) {
                                    line = br.readLine();
                                    this.verticesIntersectionsCount = Integer.parseInt(line.replaceAll("\\s+",""));
                            } else if(line.startsWith("//DepartingDepot")) {
                                    line = br.readLine();
                                    this.departingDepot = Integer.parseInt(line.replaceAll("\\s+",""));
                            } else if(line.startsWith("//ArrivalDepot")) {
                                    line = br.readLine();
                                    this.arrivalDepot = Integer.parseInt(line.replaceAll("\\s+",""));
                            } else if(line.startsWith("//Start End Distance")) {
                                    this.arcs = new ArrayList<ArrayList<Integer>>();
                                    line = br.readLine();
                                    while(!line.startsWith(" ")) {
                                            String[] numbersStr = line.split(" ");
                                            ArrayList<Integer> numbers = new ArrayList<Integer>();
                                            for(int i = 0 ; i < numbersStr.length ; i++) {
                                                    numbers.add(Integer.parseInt(numbersStr[i]));
                                            }
                                            if(numbers.size() == 3) {
                                                    this.arcs.add(numbers);
                                            }
                                            line = br.readLine();
                                    } 
                            } else if(line.startsWith("//LocStart LocEnd ShortestPath")) {
                                    this.shortestPath = new ArrayList<ArrayList<Integer>>();
                                    line = br.readLine();
                                    while(!line.startsWith(" ")) {
                                            String[] numbersStr = line.split(" ");
                                            ArrayList<Integer> numbers = new ArrayList<Integer>();
                                            for(int i = 0 ; i < numbersStr.length ; i++) {
                                                    numbers.add(Integer.parseInt(numbersStr[i]));
                                            }
                                            if(numbers.size() == 3) {
                                                    this.shortestPath.add(numbers);
                                            }
                                            line = br.readLine();
                                    } 
                            } else if(line.startsWith("//Loc x y")) {
                                    this.locations = new ArrayList<ArrayList<String>>();
                                    line = br.readLine();
                                    while(line != null) {
                                            String[] strs = line.split(" ");
                                            ArrayList<String> list = new ArrayList<String>();
                                            for(int i = 0 ; i < strs.length ; i++) {
                                                    list.add(strs[i]);
                                            }
                                            if(list.size() == 4) {
                                                    this.locations.add(list);
                                            }
                                            line = br.readLine();
                                    } 
                            }
                    }
            } catch (FileNotFoundException ex) {
                    Logger.getLogger(Instance.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                    Logger.getLogger(Instance.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
    }
    

    @Override
    public String toString() {
        return "Instance{" + "\n"
            + "\t" + "entrepot : " + entrepot.getId() + ",\n"
            + "\t" + "config : " + config + ",\n"
            + "\t" + "pathToFile : " + pathToFile + ",\n"
            + "\t" + "locationCount : " + locationCount + ",\n"
            + "\t" + "procuctCount : " + procuctCount + ",\n"
            + "\t" + "trolleyCount : " + trolleyCount + ",\n"
            + "\t" + "dimensionCount : " + dimensionCount + ",\n"
            + "\t" + "boxDimensions : " + boxDimensions + ",\n"
            + "\t" + "acceptMixedOrders : " + acceptMixedOrders + ",\n"
            + "\t" + "products : " + products + ",\n"
            + "\t" + "orderCount : " + orderCount + ",\n"
            + "\t" + "orders : " + orders + ",\n"
            + "\t" + "verticesIntersectionsCount : " + verticesIntersectionsCount + ",\n"
            + "\t" + "departingDepot : " + departingDepot + ",\n"
            + "\t" + "arrivalDepot : " + arrivalDepot + ",\n"
            + "\t" + "arcs : " + arcs + ",\n"
            + "\t" + "arcs2 : " + shortestPath + ",\n"
            + "\t" + "locations : " + locations + ",\n"
            + '}';
    }
	
    public static void main(String[] args) {
        ArrayList<String> files = new ArrayList<String>();
        files.add("./instances/instance_0116_131940_Z2.txt");
        files.add("./instances/instance_0116_131950_Z1.txt");
        files.add("./instances/instance_0130_132439_Z2.txt");
        files.add("./instances/instance_0202_132568_Z2.txt");
        files.add("./instances/instance_0203_132623_Z1.txt");
        files.add("./instances/instance_0214_132873_Z2.txt");
        files.add("./instances/instance_0215_132916_Z2.txt");
        files.add("./instances/instance_0606_136170_Z1.txt");
        files.add("./instances/instance_0606_136175_Z1.txt");
        files.add("./instances/instance_0606_136178_Z1.txt");
        for(int i = 0 ; i < files.size() ; i++) {
            //System.out.println("----------------");
            Instance instance = new Instance(files.get(i));
            instance.parse();
            //System.out.println(instance.toString());
            instance.dispatch();
        }
    }
}
