package pwr.w11.medicinesDB;

import javax.persistence.*;
import java.io.Serializable;

/**
 * MedicamentReplacement model object class
 */
@Entity
public class MedicamentReplacement implements Serializable
{
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "medicament")
	private Medicament medicament;
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "replacement")
	private Medicament replacement;

	public Medicament getMedicament() {
		return medicament;
	}

	public void setMedicament(Medicament medicament) {
		this.medicament = medicament;
	}

	public Medicament getReplacement() {
		return replacement;
	}

	public void setReplacement(Medicament replacement) {
		this.replacement = replacement;
	}
}
