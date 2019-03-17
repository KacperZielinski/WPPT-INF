package pwr.w11.medicinesDB;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Prescription model object class
 */
@Entity
public class Sale implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int saleID;
	private String seller;
	@OneToMany(
			mappedBy = "sale",
			orphanRemoval = true
	)
	private Collection<SoldProduct> soldProduct = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	@Column(name = "documentType")
	private DOCUMENT_TYPE documentType;
	private String document;
	private Date date;


	public enum DOCUMENT_TYPE
	{
		RECEIPT,
		INVOICE
	}

	public int getSaleID() {
		return saleID;
	}

	public void setSaleID(int saleID) {
		this.saleID = saleID;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public Collection<SoldProduct> getSoldProduct() {
		return soldProduct;
	}

	public void setSoldProduct(Collection<SoldProduct> soldProduct) {
		this.soldProduct = soldProduct;
	}

	public DOCUMENT_TYPE getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DOCUMENT_TYPE documentType) {
		this.documentType = documentType;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
