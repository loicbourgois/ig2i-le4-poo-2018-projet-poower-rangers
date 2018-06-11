/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metier;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Chariot.
 * @author Aurélien Ledieu
 */
@Entity
@Table(name = "CHARIOT")
public class Chariot {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "CHARIOTNO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "CAPAMAX")
	private int capaMax;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "chariot")
	private Set<Colis> colis;

	@JoinColumn(name = "ENTREPOT", referencedColumnName = "ENTREPOTNO")
	@ManyToOne(optional = false)
	private Entrepot entrepot;

	public Chariot() {
	}

	public Chariot(int capaMax) {
		this.capaMax = capaMax;
		this.colis = new HashSet<>();
	}

	public Chariot(int id, int capaMax) {
		this(capaMax);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public Set<Colis> getColis() {
		return colis;
	}

	@Override
	public int hashCode() {
		return 7;
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
		final Chariot other = (Chariot) obj;
		if (this.id != other.id) {
			return false;
		}
		return Objects.equals(this.colis, other.colis);
	}

	/**
	 * Add a colis to a chariot.
	 * @param colis colis to add
	 * @return 
	 */
	public boolean addColis(Colis colis) {
		if (colis == null) {
			return false;
		}
		return (this.colis.add(colis));
	}

	@Override
	public String toString() {
		return "Chariot{" + "id=" + id + ", colis=" + colis + '}' + "\n";
	}
}
