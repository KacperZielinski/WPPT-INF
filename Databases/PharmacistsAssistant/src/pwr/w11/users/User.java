package pwr.w11.users;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Created by Kacper on 2018-01-11.
 */
@MappedSuperclass
public abstract class User
{
	@Enumerated(EnumType.STRING)
	UserType type = UserType.NOT_EXISTS;
	@Id
	String login = "";

	abstract UserType getUserType();
}
