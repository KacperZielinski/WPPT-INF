package pwr.w11.users;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

/**
 * Created by Kacper on 2018-01-11.
 */
@Entity
public class Supplier extends User
{
	@Enumerated(EnumType.STRING)
	private UserType type;
	@Id
	private String login;

	public Supplier()
	{
		type = UserType.SUPPLIER;
	}

	@Override
	public UserType getUserType()
	{
		return type;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
}
