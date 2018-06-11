/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metier;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Position dans l'entrepot, trié par id.
 *
 * @author Aurélien Ledieu
 */
@Entity
@Table(name = "LOCALISATION")
public class Localisation {

	@Id
	@Column(name = "LOCALISATIONNO")
	private int id;

	@Column(name = "POSX")
	private int posX;

	@Column(name = "POSY")
	private int posY;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "localisation")
	private List<Distance> distances;

	@JoinColumn(name = "ENTREPOT", referencedColumnName = "ENTREPOTNO")
	@ManyToOne(optional = false)
	private Entrepot entrepot;

	@JoinColumn(name = "DISTANCE", referencedColumnName = "ID")
	@OneToOne
	private Distance distance;

	/**
	 * Default constructor.
	 */
	public Localisation() {
		distances = new ArrayList<>();
	}

	/**
	 * Constructor.
	 * @param id id
	 * @param posX x
	 * @param posY y
	 */
	public Localisation(int id, int posX, int posY) {
		this();
		this.id = id;
		this.posX = posX;
		this.posY = posY;
	}

	public int getId() {
		return id;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public List<Distance> getDistances() {
		return distances;
	}

	public Entrepot getEntrepot() {
		return entrepot;
	}

	/**
	 * Add a localisation if it has not been added yet.
	 * @param localisation localisation to add
	 * @param distance distance
	 * @return 
	 */
	public boolean addDistance(Localisation localisation, Integer distance) {
		if (distances.contains(new Distance(localisation, distance))) {
			return false;
		}
		return (distances.add(new Distance(localisation, distance)));
	}

	@Override
	public int hashCode() {
		return 3;
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
		final Localisation other = (Localisation) obj;
		if (this.id != other.id) {
			return false;
		}
		if (this.posX != other.posX) {
			return false;
		}
		if (this.posY != other.posY) {
			return false;
		}
		return Objects.equals(this.distances, other.distances);
	}

	@Override
	public String toString() {
		return "Localisation{" + "id=" + id + ", posX=" + posX + ", posY=" + posY + '}' + "\n";
	}
}
