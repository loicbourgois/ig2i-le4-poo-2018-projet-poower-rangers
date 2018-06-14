/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metier;

import javax.persistence.*;
import java.util.Objects;

/**
 * Distance from a localisation to another.
 * @author Rod
 */
@Entity
@Table(name = "DISTANCE")
public class Distance {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID")
	private Integer id;

	@JoinColumn(name = "ARRIVEE")
	private Localisation arrivee;

	@Column(name = "DISTANCE")
	private Integer distance;

	@JoinColumn(name = "LOCALISATION", referencedColumnName = "LOCALISATIONNO")
	@ManyToOne(optional = false)
	private Localisation localisation;

	public Distance() {
	}

	public Distance(Localisation arrivee, Integer distance) {
		this.arrivee = arrivee;
		this.distance = distance;
	}

	public Localisation getArrivee() {
		return arrivee;
	}

	public Integer getDistance() {
		return distance;
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
		final Distance other = (Distance) obj;
		if (!Objects.equals(this.arrivee, other.arrivee)) {
			return false;
		}
		return Objects.equals(this.distance, other.distance);
	}

	@Override
	public String toString() {
		return "Distance{" + "arrivee=" + arrivee + ", distance=" + distance + '}';
	}
}
