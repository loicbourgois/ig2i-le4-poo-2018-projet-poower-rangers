/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metier;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Configuration associated with an Instance.
 * @author Rod
 */
@Entity
@Table(name = "CONFIGURATION")
public class Configuration {

	@Id
	@Column(name = "CONFIGNO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NBBOXTROLLEY")
	private int nbBoxesTrolley;

	@Column(name = "POIDSMAX")
	private int poidsMax;

	@Column(name = "VALUEMAX")
	private int valueMax;

	@JoinColumn(name = "ENTREPOT", referencedColumnName = "ENTREPOTNO")
	@ManyToOne(optional = false)
	private Entrepot entrepot;

	public Configuration() {
	}

	/**
	 * Constructor.
	 * @param nbBoxesTrolley trolley count
	 * @param poidsMax maximum weight
	 * @param valueMax maximum value
	 */
	public Configuration(int nbBoxesTrolley, int poidsMax, int valueMax) {
		if (nbBoxesTrolley < 0) {
			this.nbBoxesTrolley = 0;
		} else {
			this.nbBoxesTrolley = nbBoxesTrolley;
		}
		if (poidsMax < 0) {
			this.poidsMax = 0;
		} else {
			this.poidsMax = poidsMax;
		}
		if (valueMax < 0) {
			this.valueMax = 0;
		} else {
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

	@Override
	public int hashCode() {
		int hash = 5;
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
		final Configuration other = (Configuration) obj;
		if (this.nbBoxesTrolley != other.nbBoxesTrolley) {
			return false;
		}
		if (this.poidsMax != other.poidsMax) {
			return false;
		}
		if (this.valueMax != other.valueMax) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Configuration{" 
						+ "nbBoxesTrolley=" + nbBoxesTrolley 
						+ ", poidsMax=" + poidsMax 
						+ ", valueMax=" + valueMax 
						+ '}' + "\n";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
