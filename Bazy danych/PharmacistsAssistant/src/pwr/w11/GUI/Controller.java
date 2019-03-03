package pwr.w11.GUI;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pwr.w11.medicinesDB.*;
import pwr.w11.tools.AdminTools;
import pwr.w11.users.UserType;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Kacper on 2018-01-12.
 */
public class Controller
{
	// Medicament Tab
	public TextField medText1;
	public Button searchButton;
	public ChoiceBox choiceQuery;
	public TextField medText2;
	public TextField medText3;
	public Label medLabel1;
	public Label medLabel2;
	public Label medLabel3;
	public TableView tableView;
	public Tab medicamentTab;
	private int queryID;

	// Sell Tab
	public Tab sellTab;
	public Button endSale;
	public Label price;
	public Button addProductSell;
	public TableView sellTableView;
	private List<Integer> productsID;
	private ObservableList<ViewHelper> data = FXCollections.observableArrayList();
	private boolean isSaleEnded;
	private boolean isInvoice;
	public CheckBox invoiceCheckBox;
	private double taxFullPrice = 0.0;
	private double tax = 1.23;

	// Admin Tab
	public Tab adminTab;
	public Button backup;
	public Button restore;

	// SupplierTab
	public Tab supplierTab;
	public Button add;
	public TextField nameSupplierField;
	public TextField contentSupplierField;
	public TextField priceNetSupplierField;
	public CheckBox prescriptionSupplierField;
	public TextField producerSupplierField;
	public TextField productIDSupplierField;
	public TextField activeIngredientSupplierField;
	public TextField batchNumberSupplierField;
	public TextField expirationDateSupplierField;
	public TextField medID;
	public Button withdraw;


	private SessionFactory sessionFactory;
	private String login;
	private UserType userType = UserType.NOT_EXISTS;

	//TODO uncomment
	public void initialize()
	{
		productsID = new ArrayList<>();
		Platform.runLater(() ->
		{
			System.out.println(userType.name());
			switch (userType)
			{
				case ADMIN:
				{
					break;
				}
				case PHARMACIST:
				{
					adminTab.setDisable(true);
					adminTab.setText("");
					supplierTab.setText("");
					supplierTab.setDisable(true);
					break;
				}
				case SUPPLIER:
				{
					adminTab.setDisable(true);
					adminTab.setText("");
					sellTab.setDisable(true);
					sellTab.setText("");
					medicamentTab.setDisable(true);
					medicamentTab.setText("");
					break;
				}
				case NOT_EXISTS:
				{
					adminTab.setDisable(true);
					adminTab.setText("");
					sellTab.setDisable(true);
					sellTab.setText("");
					supplierTab.setDisable(true);
					supplierTab.setText("");
					medicamentTab.setDisable(true);
					medicamentTab.setText("");
					break;
				}
			}

		});

		choiceQuery.setItems(FXCollections.observableArrayList(
				"Check active ingredient of medicament",
				"Check Medicament Price",
				"Check Replacement",
				"Is Medicament on Prescription?",
				"Is Medicament available?"
		));
		choiceQuery.setValue("Check active ingredient of medicament");

		queryID = 0;
		choiceQueryChanged(queryID);
		choiceQuery.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) ->
		{
			queryID = newValue.intValue();
			choiceQueryChanged(queryID);
		});
	}

	private void choiceQueryChanged(int id)
	{
		switch (id)
		{
			case 0:
			{
				textViewsVisible();
				break;
			}
			case 1:
			{
				textViewsVisible();
				break;
			}
			case 2:
			{
				textViewsVisible();
				break;
			}
			case 3:
			{
				textViewsVisible();
				break;
			}
			case 4:
			{
				textViewsVisible();
				break;
			}
			default:
				break;
		}
	}

	private void textViewsVisible()
	{
		medLabel1.setText("Medicament");
		medLabel2.setVisible(false);
		medLabel3.setVisible(false);
		medText2.setVisible(false);
		medText3.setVisible(false);
	}

	private void checkActiveIngredient(String medicamentName)
	{
		tableView.getColumns().clear();
		tableView.getItems().clear();

		TableColumn name = new TableColumn("Medicament name");
		TableColumn activeSubstance = new TableColumn("Active ingredient");

		ObservableList<ViewHelper> data = FXCollections.observableArrayList();

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from Medicament where name = ?");
		query.setParameter(0, medicamentName);
		List medicamentList = query.list();

		if(medicamentList.isEmpty())
			errorMessage("No medicament in database", "", "Make an order from the warehouse or update database");

		else
		{
			for (Object o: medicamentList)
			{
				Medicament m = (Medicament) o;
				Collection<ActiveIngredient> replacementsList = m.getActiveIngredients();

				for (ActiveIngredient mp: replacementsList)
				{
					ViewHelper tmp = new ViewHelper();
					tmp.setName(m.getName());
					tmp.setContent(mp.getName());

					data.add(tmp);
				}
			}
		}



		session.getTransaction().commit();
		session.close();

		name.setCellValueFactory(
				new PropertyValueFactory<>("name")
		);
		activeSubstance.setCellValueFactory(
				new PropertyValueFactory<>("content")
		);

		tableView.setItems(data);
		tableView.getColumns().addAll(name, activeSubstance);
	}

	private void checkPrice(String medicamentName)
	{
		tableView.getColumns().clear();
		tableView.getItems().clear();

		TableColumn name = new TableColumn("Medicament name");
		TableColumn content = new TableColumn("Content");
		TableColumn medID = new TableColumn("medicamentID");
		TableColumn price = new TableColumn("Price netto");
		TableColumn fullPrice = new TableColumn("Price brutto");

		ObservableList<ViewHelper> data = FXCollections.observableArrayList();

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from Medicament where name = ?");
		query.setParameter(0, medicamentName);
		List medicamentList = query.list();

		if(medicamentList.isEmpty())
			errorMessage("No medicament in database", "", "Make an order from the warehouse or update database");

		else
		{
			for (Object o: medicamentList)
			{
				Medicament med = (Medicament) o;
				ViewHelper tmp = new ViewHelper();
				tmp.setName(med.getName());
				tmp.setID(med.getMedicamentID());
				tmp.setContent(med.getContent());

				Double medPrice = med.getPriceNet();

				tmp.setPriceNet(medPrice);
				tmp.setPriceBrutto(roundTwo(medPrice*tax));

				data.add(tmp);
			}
		}

		session.getTransaction().commit();
		session.close();

		name.setCellValueFactory(
				new PropertyValueFactory<>("name")
		);

		medID.setCellValueFactory(
				new PropertyValueFactory<>("ID")
		);
		content.setCellValueFactory(
				new PropertyValueFactory<>("content")
		);
		price.setCellValueFactory(
				new PropertyValueFactory<>("priceNet")
		);
		fullPrice.setCellValueFactory(
				new PropertyValueFactory<>("priceBrutto")
		);

		tableView.setItems(data);
		tableView.getColumns().addAll(name, content, medID, price, fullPrice);
	}

	// poor but works..
	private void checkReplacement(String medicamentName)
	{
		tableView.getColumns().clear();
		tableView.getItems().clear();

		TableColumn name = new TableColumn("Medicament name");
		TableColumn nameID = new TableColumn("medicamentID");
		TableColumn replacementName = new TableColumn("Medicament replacement");
		TableColumn replacement = new TableColumn("replacementID");

		ObservableList<ViewHelper> data = FXCollections.observableArrayList();

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from Medicament where name = ?");
		query.setParameter(0, medicamentName);
		List medicamentList = query.list();

		if(medicamentList.isEmpty())
			errorMessage("No medicament in database", "", "Make an order from the warehouse or update database");

		else
		{
			Medicament m = (Medicament) medicamentList.get(0);
			Collection<MedicamentReplacement> replacementsList = m.getMedicamentReplacements();

//			Query secondQuery = session.createQuery("from MedicamentReplacement where medicament = ?");
//			secondQuery.setParameter(0, m.getMedicamentID());
//			List secondReplacementsList = query.list();
//
//
//			if(replacementsList.isEmpty())
//				errorMessage("No replacements in database", "", "Make an order from the warehouse or update database");
//
//			else
//			{
//				for (Object obj: secondReplacementsList)
//				{
//					ViewHelper tmp = new ViewHelper();
//
//					Medicament med = (Medicament) obj;
//					tmp.setName(m.getName());
//					tmp.setMedicamentID(m.getMedicamentID());
//					tmp.setReplacementName(med.getName());
//					tmp.setID(med.getMedicamentID());
//
//					data.add(tmp);
//				}
//			}

			for (MedicamentReplacement mp: replacementsList)
			{
				ViewHelper tmp = new ViewHelper();
				tmp.setMedicamentID(m.getMedicamentID());
				tmp.setName(m.getName());
				tmp.setReplacementName(mp.getReplacement().getName());
				tmp.setID(mp.getReplacement().getMedicamentID());

				data.add(tmp);
			}

			session.getTransaction().commit();
			session.close();

			name.setCellValueFactory(new PropertyValueFactory<>("name"));
			nameID.setCellValueFactory(new PropertyValueFactory<>("medicamentID"));
			replacementName.setCellValueFactory(new PropertyValueFactory<>("replacementName"));
			replacement.setCellValueFactory(new PropertyValueFactory<>("ID"));

			tableView.setItems(data);
			tableView.getColumns().addAll(name, nameID, replacementName, replacement);
		}
	}

	private void checkIsOnPrescription(String medicamentName)
	{
		tableView.getColumns().clear();
		tableView.getItems().clear();

		TableColumn name = new TableColumn("Medicament name");
		TableColumn onPrescription = new TableColumn("Prescription?");

		ObservableList<ViewHelper> data = FXCollections.observableArrayList();

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from Medicament where name = ?");
		query.setParameter(0, medicamentName);
		List medicamentList = query.list();

		if(medicamentList.isEmpty())
			errorMessage("No medicament in database", "", "Make an order from the warehouse or update database");

		else
		{
			for (Object o: medicamentList)
			{
				Medicament med = (Medicament) o;
				ViewHelper tmp = new ViewHelper();
				tmp.setName(med.getName() + " - " + med.getContent());
				tmp.setPrescriptionB(med.isPrescription());

				data.add(tmp);
			}
		}

		session.getTransaction().commit();
		session.close();

		name.setCellValueFactory(
				new PropertyValueFactory<>("name")
		);

		onPrescription.setCellValueFactory(
				new PropertyValueFactory<>("prescriptionB")
		);

		tableView.setItems(data);
		tableView.getColumns().addAll(name, onPrescription);
	}

	private void checkIsAvailable(String medicamentName)
	{
		tableView.getColumns().clear();
		tableView.getItems().clear();

		TableColumn name = new TableColumn("Medicament name");
		TableColumn content = new TableColumn("Available?");

		ObservableList<ViewHelper> data = FXCollections.observableArrayList();

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("select COUNT(*) from MedicamentProduct where medicament.name = ?");
		query.setParameter(0, medicamentName);
		List medicamentList = query.list();

		if(medicamentList.isEmpty())
		{
			ViewHelper tmp = new ViewHelper();
			tmp.setName(medicamentName);
			tmp.setContent("NO!, make an order from the warehouse or update database");
			data.add(tmp);
		}

		else
		{
			int ammount = Integer.parseInt(medicamentList.get(0).toString());

			if(ammount > 0)
			{
				ViewHelper tmp = new ViewHelper();
				tmp.setName(medicamentName);
				tmp.setContent("YES! Quantity: " + ammount);
				data.add(tmp);
			}
			else
			{
				ViewHelper tmp = new ViewHelper();
				tmp.setName(medicamentName);
				tmp.setContent("NO!, make an order from the warehouse or update database");
				data.add(tmp);
			}
		}

		session.getTransaction().commit();
		session.close();

		name.setCellValueFactory(
				new PropertyValueFactory<>("name")
		);

		content.setCellValueFactory(
				new PropertyValueFactory<>("content")
		);

		tableView.setItems(data);
		tableView.getColumns().addAll(name, content);
	}

	public void searchButtonAction()
	{
		String medName = medText1.getText();

		if(medName.matches("[\\w]+"))
		{
			switch (queryID)
			{
				case 0:
				{
					checkActiveIngredient(medText1.getText());
					break;
				}
				case 1:
				{
					checkPrice(medText1.getText());
					break;
				}
				case 2:
				{
					checkReplacement(medText1.getText());
					break;
				}
				case 3:
				{
					checkIsOnPrescription(medText1.getText());
					break;
				}
				case 4:
				{
					checkIsAvailable(medText1.getText());
					break;
				}
				default:
					break;
			}
		}
		else
			errorMessage("Wrong medicament name", "", "Please input name excluding spaces etc.!");

	}

	public void onAddProductSell()
	{
		TextInputDialog dialog = new TextInputDialog("0");
		dialog.setTitle("Input medicament");
		dialog.setContentText("Please input MedicamentProduct ID");

		Optional<String> result = dialog.showAndWait();
		result.ifPresent(value -> {
			int productID = 0;
			boolean inTable = false;
			try {
				productID = Integer.parseInt(value);

				for (int h : productsID) {
					if (h == productID) {
						inTable = true;
						errorMessage("Wrong ID", "", "The same productID..");
					}
				}
			} catch (NumberFormatException e) {
				errorMessage("Wrong ID", "", "Input ID again");
			}

			if (!inTable) {
				productsID.add(productID);
				addItemToTable(productID);
			}

		});
	}

	private void addItemToTable(int productID)
	{
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from MedicamentProduct where ID = ?");
		query.setParameter(0, productsID.get(productsID.size()-1));
		List medicamentProductList = query.list();

		if(medicamentProductList.isEmpty())
		{
			productsID.remove(productsID.indexOf(productID));
			errorMessage("No medicament in database", "", "Make an order from the warehouse or update database");
		}
		else
		{
			sellTableView.getColumns().clear();

			TableColumn productIDColumn = new TableColumn("MedicamentProductID");
			TableColumn nameColumn = new TableColumn("Medicament name");
			TableColumn priceNetColumn = new TableColumn("Price netto");
			TableColumn priceBruttoColumn = new TableColumn("Price brutto");
			TableColumn isPrescription = new TableColumn("Prescription required");

			for (Object mp: medicamentProductList) {

				ViewHelper tmp = new ViewHelper();

				MedicamentProduct m = (MedicamentProduct) medicamentProductList.get(0);
				Medicament med = m.getMedicament();

				tmp.setMedicament(med);
				tmp.setMedicamentProduct(m);

				tmp.setName(med.getName());
				tmp.setProductID(m.getProductID());
				tmp.setPriceNet(med.getPriceNet());
				Double taxPrice = roundTwo(med.getPriceNet() * tax);
				tmp.setPriceBrutto(taxPrice);
				tmp.setPrescriptionB(med.isPrescription());
				taxFullPrice += taxPrice;
				price.setText(Double.toString(taxFullPrice) + " zl");

				data.add(tmp);
			}

			session.getTransaction().commit();
			session.close();

			sellTableView.setItems(data);

			productIDColumn.setCellValueFactory(
					new PropertyValueFactory<>("productID")
			);

			nameColumn.setCellValueFactory(
					new PropertyValueFactory<>("name")
			);

			priceNetColumn.setCellValueFactory(
					new PropertyValueFactory<>("priceNet")
			);

			priceBruttoColumn.setCellValueFactory(
					new PropertyValueFactory<>("priceBrutto")
			);

			isPrescription.setCellValueFactory(
					new PropertyValueFactory<>("prescriptionB")
			);

			sellTableView.getColumns().addAll(productIDColumn, nameColumn, priceNetColumn, priceBruttoColumn, isPrescription);
		}
		isSaleEnded = false;
	}

	public void onEndSale()
	{
		isSaleEnded = true;

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Sale newSale = new Sale();
//		newSale.setSaleID(5);
		newSale.setSeller(login);
		newSale.setDate(new Date());

		if(isInvoice)
			newSale.setDocumentType(Sale.DOCUMENT_TYPE.INVOICE);
		else
			newSale.setDocumentType(Sale.DOCUMENT_TYPE.RECEIPT);

		Query query = session.createQuery("select max(document) from Sale");
		List queryList = query.list();

		if(queryList == null)
		{
			errorMessage("No Sale in database", "", "Make an order from the warehouse or update database");
			newSale.setDocument("0");
		}

		else
		{
			for (Object mp : queryList)
			{
				String maxDocument = (String) mp;
				int maxValue = Integer.parseInt(maxDocument);
				maxValue++;
				newSale.setDocument(Integer.toString(maxValue));
			}
		}
		session.save(newSale);

		for (ViewHelper vh: data)
		{
			SoldProduct product = new SoldProduct();
			product.setSale(newSale);
			product.setMedicamentProductID(vh.getMedicamentProduct().getID());
			session.save(product);
		}

		session.getTransaction().commit();

		for (int i: productsID)
		{
			session.getTransaction().begin();
			session.delete(session.load(MedicamentProduct.class, i));
			session.getTransaction().commit();
		}

		session.close();

		sellTableView.getColumns().clear();
		sellTableView.getItems().clear();
		data.clear();
		productsID = new ArrayList<>();
		price.setText("0.00");
		taxFullPrice = 0;
	}

	public void onInvoiceCheckBox()
	{
		isInvoice = invoiceCheckBox.isSelected();
	}

	public void addItemsToDatabase()
	{
		//sprawdz jeszcze regex, moglam cos pominac

		String activeIngredient = activeIngredientSupplierField.getText();
		String medicamentName = nameSupplierField.getText();
		String medicamentContent = contentSupplierField.getText();
		Double medicamentpriceNet=0.0;
		try
		{
			medicamentpriceNet = Double.parseDouble(priceNetSupplierField.getText());
		}
		catch (NumberFormatException e)
		{
			errorMessage("Bad number","", "Insert price value");
		}

		Boolean medicamentIsPrescription = prescriptionSupplierField.isSelected();
		String medicamentProducer = producerSupplierField.getText();

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		if (activeIngredient.matches("([\\w]+[\\s]?)*") && medicamentName.matches("([\\w]+[\\s]?)*") && medicamentContent.matches("([\\w]+[\\s]?)*") && medicamentProducer.matches("([\\w]+[\\s]?)*")){

			Query queryMed = session.createQuery("from Medicament where name = ? and content = ? and priceNet=? and isPrescription=? and producer=?");
			queryMed.setParameter(0, medicamentName);
			queryMed.setParameter(1, medicamentContent);
			queryMed.setParameter(2, medicamentpriceNet);
			queryMed.setParameter(3, medicamentIsPrescription);
			queryMed.setParameter(4, medicamentProducer);
			List medicamentList = queryMed.list();

			Query query = session.createQuery("from ActiveIngredient where name = ?");
			query.setParameter(0, activeIngredient);
			List ingredientList = query.list();

			Medicament medicament;
			ActiveIngredient ingredient;

			if (medicamentList.isEmpty()) {
				if (ingredientList.isEmpty()) {
					ingredient = new ActiveIngredient();
					ingredient.setName(activeIngredient);
					session.save(ingredient);
				} else {
					ingredient = (ActiveIngredient) ingredientList.get(0);
				}

				medicament = new Medicament();
				medicament.setName(medicamentName);
				medicament.setContent(medicamentContent);
				medicament.setPriceNet(medicamentpriceNet);

				medicament.setPrescription(medicamentIsPrescription);
				medicament.setProducer(medicamentProducer);
				medicament.getActiveIngredients().add(ingredient);
				session.save(medicament);
			} else {
				medicament = (Medicament) medicamentList.get(0);
			}


			MedicamentProduct medicamentProduct = new MedicamentProduct();
			medicamentProduct.setMedicament(medicament);

			int prID = 0;
			try {
				prID = Integer.parseInt(productIDSupplierField.getText());
			}
			catch (NumberFormatException e)
			{
				errorMessage("Bad productID","", "Insert price value");
			}
			medicamentProduct.setProductID(prID);

			medicamentProduct.setBatchNumber(batchNumberSupplierField.getText());
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Date result = null;

			try {
				result = df.parse(expirationDateSupplierField.getText());
			} catch (ParseException e) {
				e.printStackTrace();
			}

			medicamentProduct.setExpirationDate(result);
			medicamentProduct.setMedicament(medicament);

			session.save(medicamentProduct);
		}
		else {
			errorMessage("Warning", "Wrong data ", "Please enter correct data.");
		}
		session.getTransaction().commit();
		session.close();

	}

	public void removeMedicamentProduct()
	{
		int productID = 0;

		try
		{
			productID = Integer.parseInt(medID.getText());
		}
		catch (NumberFormatException e)
		{
			errorMessage("Bad productID","", "Insert price value");
		}

		Session session = sessionFactory.openSession();
		session.getTransaction().begin();

		Query query = session.createQuery("from MedicamentProduct where ID=?");
		query.setParameter(0, productID);
		List queryList = query.list();

		if(!queryList.isEmpty())
		{
			for (Object o: queryList)
			{
				MedicamentProduct mp = (MedicamentProduct) o;
				session.delete(session.load(MedicamentProduct.class, mp.getID()));
			}
		}

		session.getTransaction().commit();
		session.close();
	}

	public void createBackup()
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select mysqldump binary file");
		File file = fileChooser.showOpenDialog(new Stage());
		String path = "";

		if (file != null)
			path = file.getAbsolutePath();

		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Input root password");
		dialog.setContentText("Password: ");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			AdminTools.BackupDB(path, login, result.get(), "drugstore");
		}
	}

	public void restoreBackup()
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select mysql binary file");
		File file = fileChooser.showOpenDialog(new Stage());
		String path = "";

		if (file != null)
			path = file.getAbsolutePath();


		FileChooser fileChooser2 = new FileChooser();
		fileChooser2.setTitle("Select .sql backup file to restore");
		File file2 = fileChooser2.showOpenDialog(new Stage());
		String path2 = "";

		if (file2 != null)
			path2 = file2.getAbsolutePath();

		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Input root password");
		dialog.setContentText("Password: ");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			AdminTools.RestoreDB(path, path2, login, result.get(), "drugstore");
		}

	}

	void setUserType(UserType userType)
	{
		this.userType = userType;
	}

	void setLogin(String login) {
		this.login = login;
	}

	void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private void errorMessage(String title, String headerText, String content)
	{
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(content);

		alert.showAndWait();
	}

	private double roundTwo(double value) {
		value = Math.round(value * 100);
		value = value/100;
		return value;

	}
}
