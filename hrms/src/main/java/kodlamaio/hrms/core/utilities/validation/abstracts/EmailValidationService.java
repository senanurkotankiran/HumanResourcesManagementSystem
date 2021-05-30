package kodlamaio.hrms.core.utilities.validation.abstracts;

import kodlamaio.hrms.entities.concretes.Users;

public interface EmailValidationService {
	
	public boolean sendMail(Users user);
}
