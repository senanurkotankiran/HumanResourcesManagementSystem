package kodlamaio.hrms.core.utilities.validation.concretes;

import org.springframework.stereotype.Service;

import kodlamaio.hrms.core.utilities.validation.abstracts.EmployeesValidationService;
import kodlamaio.hrms.entities.concretes.Users;

@Service
public class EmployeesValidationManager implements EmployeesValidationService {

	@Override
	public boolean isValid(Users users) {

		return true;
	}

}
