package pwr.w11.medicinesDB;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Parameter;
import java.io.Serializable;

/**
 * Prescription model object class
 */
@Entity
public class Prescription implements Serializable
{
	@Id
	private String prescription;

	@OneToOne(cascade = CascadeType.ALL)
	@NotFound(action=NotFoundAction.IGNORE)
	private MedicamentProduct medicamentProduct;

	public String getPrescription() {
		return prescription;
	}

	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}

	public MedicamentProduct getMedicamentProduct() {
		return medicamentProduct;
	}

	public void setMedicamentProduct(MedicamentProduct medicamentProduct) {
		this.medicamentProduct = medicamentProduct;
	}
}
