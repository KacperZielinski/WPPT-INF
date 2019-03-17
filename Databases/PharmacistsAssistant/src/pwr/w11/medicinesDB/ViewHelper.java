package pwr.w11.medicinesDB;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by Kacper on 2018-01-17.
 */
public class ViewHelper
{
	private boolean prescriptionB;
	private Date date;
	private Date expirationDate;
	private Double priceNet;
	private double priceBrutto;
	private int ID;
	private int ingredientID;
	private int medicamentID;
	private int productID;
	private int saleID;
	private Medicament medicament;
	private Medicament replacement;
	private MedicamentProduct medicamentProduct;
	private Sale.DOCUMENT_TYPE documentType;
	private String batchNumber;
	private String content;
	private String document;
	private String name;
	private String prescription;
	private String producer;
	private String replacementName;
	private String seller;
	private Sale sale;

	public Collection<Medicament> getMedicaments() {
		return medicaments;
	}

	public String getReplacementName() {
		return replacementName;
	}

	public void setReplacementName(String replacementName) {
		this.replacementName = replacementName;
	}

	public void setMedicaments(Collection<Medicament> medicaments) {
		this.medicaments = medicaments;
	}

	private Collection<Medicament> medicaments = new ArrayList<>();

	public String getProducer() {
		return producer;
	}

	public Medicament getMedicament() {
		return medicament;
	}

	public int getProductID() {
		return productID;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public int getID() {
		return ID;
	}

	public Medicament getReplacement() {
		return replacement;
	}

	public String getPrescription() {
		return prescription;
	}

	public MedicamentProduct getMedicamentProduct() {
		return medicamentProduct;
	}

	public int getSaleID() {
		return saleID;
	}

	public String getSeller() {
		return seller;
	}

	public Sale.DOCUMENT_TYPE getDocumentType() {
		return documentType;
	}

	public String getDocument() {
		return document;
	}

	public Date getDate() {
		return date;
	}

	public Sale getSale() {
		return sale;
	}

	public int getIngredientID() {
		return ingredientID;
	}

	public int getMedicamentID() {
		return medicamentID;
	}

	public String getName() {
		return name;
	}

	public String getContent() {
		return content;
	}

	public Double getPriceNet() {
		return priceNet;
	}

	public boolean getPrescriptionB() {
		return prescriptionB;
	}

	public double getPriceBrutto() {
		return priceBrutto;
	}

	public void setPriceBrutto(double priceBrutto) {
		this.priceBrutto = priceBrutto;
	}

	public void setIngredientID(int ingredientID) {
		this.ingredientID = ingredientID;
	}

	public void setMedicamentID(int medicamentID) {
		this.medicamentID = medicamentID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setPriceNet(Double priceNet) {
		this.priceNet = priceNet;
	}

	public void setPrescriptionB(boolean prescriptionB) {
		this.prescriptionB = prescriptionB;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public void setMedicament(Medicament medicament) {
		this.medicament = medicament;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public void setReplacement(Medicament replacement) {
		this.replacement = replacement;
	}

	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}

	public void setMedicamentProduct(MedicamentProduct medicamentProduct) {
		this.medicamentProduct = medicamentProduct;
	}

	public void setSaleID(int saleID) {
		this.saleID = saleID;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public void setDocumentType(Sale.DOCUMENT_TYPE documentType) {
		this.documentType = documentType;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}
}
