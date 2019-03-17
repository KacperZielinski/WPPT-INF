package pwr.w11.users;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by Kacper on 2018-01-11.
 */
public class UserManagement
{
	private Configuration cfg;
	private UserType userType = UserType.NOT_EXISTS;

	public UserManagement(String login)
	{
		cfg = new Configuration();
		cfg.configure("hibernate_users.cfg.xml");

		SessionFactory sessionFactory = cfg.buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

//		Admin root = new Admin();
//		root.setLogin("root");
//		session.save(root);
//
//		session.getTransaction().commit();
//		session.close();
//
//		session = sessionFactory.openSession();
//		session.beginTransaction();

		User user = session.get(Admin.class, login);

		if(user == null)
		{
			user = session.get(Pharmacist.class, login);

			if(user == null)
			{
				user = session.get(Supplier.class, login);

				if(user == null)
				{
					userType = UserType.NOT_EXISTS;
				}
				else
				{
					userType = UserType.SUPPLIER;
				}
			}
			else
			{
				userType = UserType.PHARMACIST;
			}
		}
		else
		{
			userType = UserType.ADMIN;
		}

		session.close();
	}

	public UserType getUserType()
	{
		return userType;
	}

}
