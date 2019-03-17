package pwr.w11.medicinesDB;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

/**
 * MedicamentProduct model object class
 */
@Entity
public class MedicamentProduct implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "medicament")
	private Medicament medicament;
	private int productID;
	private Date expirationDate;
	private String batchNumber;

	public Medicament getMedicament() {
		return medicament;
	}

	public void setMedicament(Medicament medicament) {
		this.medicament = medicament;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}
}
