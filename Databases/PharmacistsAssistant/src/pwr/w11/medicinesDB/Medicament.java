package pwr.w11.medicinesDB;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Medicament model object class
 */
@Entity
public class Medicament
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int medicamentID;
	private String name;
	private String content;
	private Double priceNet;

	@org.hibernate.annotations.Type(type="true_false")
	@NotNull
	private boolean isPrescription;
	private String producer;

	@OneToMany(
			mappedBy = "medicament"
	)
	private Collection<MedicamentProduct> medicamentProduct = new ArrayList<>();


	@OneToMany(
			mappedBy = "medicament"
	)
	private Collection<MedicamentReplacement> medicamentReplacements = new ArrayList<>();


	@ManyToMany
	private Collection<ActiveIngredient> activeIngredients = new ArrayList<>();

	public int getMedicamentID() {
		return medicamentID;
	}

	public void setMedicamentID(int medicamentID) {
		this.medicamentID = medicamentID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Double getPriceNet() {
		return priceNet;
	}

	public void setPriceNet(Double priceNet) {
		this.priceNet = priceNet;
	}

	public boolean isPrescription() {
		return isPrescription;
	}

	public void setPrescription(boolean prescription) {
		isPrescription = prescription;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public Collection<MedicamentProduct> getMedicamentProduct() {
		return medicamentProduct;
	}

	public void setMedicamentProduct(Collection<MedicamentProduct> medicamentProduct) {
		this.medicamentProduct = medicamentProduct;
	}

	public Collection<MedicamentReplacement> getMedicamentReplacements() {
		return medicamentReplacements;
	}

	public void setMedicamentReplacements(Collection<MedicamentReplacement> medicamentReplacements) {
		this.medicamentReplacements = medicamentReplacements;
	}

	public Collection<ActiveIngredient> getActiveIngredients() {
		return activeIngredients;
	}

	public void setActiveIngredients(Collection<ActiveIngredient> activeIngredients) {
		this.activeIngredients = activeIngredients;
	}

	@Override
	public String toString()
	{
		return name + " " + content + " " + producer + " " + isPrescription + " " + priceNet;
	}
}
