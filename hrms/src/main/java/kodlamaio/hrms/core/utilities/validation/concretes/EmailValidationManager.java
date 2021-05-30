package kodlamaio.hrms.core.utilities.validation.concretes;

import org.springframework.stereotype.Service;

import kodlamaio.hrms.core.utilities.validation.abstracts.EmailValidationService;

import kodlamaio.hrms.entities.concretes.Users;

@Service
public class EmailValidationManager implements EmailValidationService{

	@Override
	public boolean sendMail(Users user) {

		return true;
	}

	

	

}
