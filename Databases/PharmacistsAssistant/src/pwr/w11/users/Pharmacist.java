package pwr.w11.users;

import javax.persistence.*;

/**
 * Created by Kacper on 2018-01-11.
 */
@Entity
public class Pharmacist extends User
{
	@Enumerated(EnumType.STRING)
	private UserType type;
	@Id
	private String login;

	public Pharmacist()
	{
		type = UserType.PHARMACIST;
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
