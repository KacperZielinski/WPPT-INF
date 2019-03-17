package pwr.w11.medicinesDB;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * ActiveIngredient model object class
 */
@Entity
public class ActiveIngredient
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ingredientID;

	@Column(name = "activeIngredient", unique = true)
	private String name;

	@ManyToMany(mappedBy = "activeIngredients")
	private Collection<Medicament> medicaments = new ArrayList<>();

	public int getIngredientID() {
		return ingredientID;
	}

	public void setIngredientID(int ingredientID) {
		this.ingredientID = ingredientID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Medicament> getMedicaments() {
		return medicaments;
	}

	public void setMedicaments(Collection<Medicament> medicaments) {
		this.medicaments = medicaments;
	}
}
