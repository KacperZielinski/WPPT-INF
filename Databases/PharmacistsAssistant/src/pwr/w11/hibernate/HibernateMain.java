package pwr.w11.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import pwr.w11.medicinesDB.*;

import java.util.Date;
import java.util.List;

/**
 * Simply Hibernate operations
 */
public class HibernateMain
{
	public static void main(String[] args)
	{
		ActiveIngredient ingredient1 = new ActiveIngredient();
		ingredient1.setName("Paracetamolum");

		ActiveIngredient ingredient2 = new ActiveIngredient();
		ingredient2.setName("Ibuprofenum");

		ActiveIngredient ingredient3 = new ActiveIngredient();
		ingredient3.setName("Acidum acetylsalicylicum");

		Medicament medicament1 = new Medicament();
		medicament1.setName("APAP");
		medicament1.setContent("12 Tabletek");
		medicament1.setPriceNet(4.32);
		medicament1.setPrescription(false);
		medicament1.setProducer("Polpharma");
		medicament1.getActiveIngredients().add(ingredient1);

		Medicament medicament2 = new Medicament();
		medicament2.setName("iBum");
		medicament2.setContent("5 Tabletek");
		medicament2.setPriceNet(9.32);
		medicament2.setPrescription(true);
		medicament2.setProducer("Bayer");
		medicament2.getActiveIngredients().add(ingredient2);

		Medicament medicament3 = new Medicament();
		medicament3.setName("Aspiryna");
		medicament3.setContent("25 Tabletek");
		medicament3.setPriceNet(29.32);
		medicament3.setPrescription(false);
		medicament3.setProducer("Bayer");
		medicament3.getActiveIngredients().add(ingredient3);

		MedicamentReplacement mr = new MedicamentReplacement();
		mr.setMedicament(medicament1);
		mr.setReplacement(medicament2);

		MedicamentProduct medicamentProduct1 = new MedicamentProduct();
		medicamentProduct1.setProductID(12);
		medicamentProduct1.setBatchNumber("333/1AS");
		medicamentProduct1.setExpirationDate(new Date(1, 12, 12));
		medicamentProduct1.setMedicament(medicament1);

		MedicamentProduct medicamentProduct2 = new MedicamentProduct();
		medicamentProduct2.setProductID(512);
		medicamentProduct2.setBatchNumber("12/BC2412");
		medicamentProduct2.setExpirationDate(new Date(1, 1, 2));
		medicamentProduct2.setMedicament(medicament2);

		Sale newSale = new Sale();
		newSale.setSeller("Kaszkiet");
		newSale.setDate(new Date());
		newSale.setDocumentType(Sale.DOCUMENT_TYPE.INVOICE);
		newSale.setDocument("2131251");

		SoldProduct product1 = new SoldProduct();

		product1.setSale(newSale);
		product1.setMedicamentProductID(medicamentProduct1.getID());

		Prescription ibumRx = new Prescription();
		ibumRx.setPrescription("2j13lkj12k");
		ibumRx.setMedicamentProduct(medicamentProduct2);

		newSale.getSoldProduct().add(product1);

		// in try catch?
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(ingredient1);
		session.save(ingredient2);
		session.save(medicament1);
		session.save(medicament2);
		session.save(mr);
		session.save(medicamentProduct1);
		session.save(medicamentProduct2);
		session.save(newSale);
		session.save(product1);
		session.save(ibumRx);


//		Query query1 = session.createQuery("from Medicament");
//		List medicamentList = query1.list();
//
//		Query query2 = session.createQuery("from Medicament where name = 'APAP'");
//		List medicament = query2.list();
//
//		Query query3 = session.createQuery("from Medicament where isPrescription = false");
//		List isPrescriptionF = query3.list();
//
//		Query query4 = session.createQuery("from Medicament where priceNet < 20");
//		List price20 = query4.list();
//
//		Query query5 = session.createQuery("select name from Medicament where producer = 'Bayer'");
//		List active = query5.list();
//
//		Query query6 = session.createQuery("select name from Medicament");
//		List names = query6.list();
//
//		Query query7 = session.createQuery("select name from Medicament where priceNet < 10");
//		List name10 = query7.list();
//
//		Query query8 = session.createQuery("select name from Medicament where priceNet < 15 and isPrescription = false ");
//		List priceF = query8.list();
//
//		Query query9 = session.createQuery("select name, priceNet from Medicament where priceNet < 20 and content = '12 Tabletek'");
//		List namePrice = query9.list();
//
//		Query query10 = session.createQuery("from Medicament where medicamentID = 2");
//		List medicamentID = query10.list();
//
		session.getTransaction().commit();
		session.close();

		//System.out.println( "medicaments =" + medicament.size());

		/* Pobieranie danych z sesji
		session = sessionFactory.openSession();
		session.beginTransaction();
		// 1 is a value of PrimaryKey
		medicament = (Medicament) session.get(Medicament.class, 1);
		System.out.println(medicament.getName());
		*/
	}
}
