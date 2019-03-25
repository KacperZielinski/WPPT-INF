package pwr.w11.medicinesDB;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * SoldProduct model object class
 */
@Entity
public class SoldProduct implements Serializable
{
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sale")
	private Sale sale;

	@Id
	//@OneToOne(cascade = CascadeType.ALL)
	@NotFound(action= NotFoundAction.IGNORE)
	private int medicamentProductID;

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public int getMedicamentProductID() {
		return medicamentProductID;
	}

	public void setMedicamentProductID(int medicamentProductID)
	{
		this.medicamentProductID = medicamentProductID;
	}
}
