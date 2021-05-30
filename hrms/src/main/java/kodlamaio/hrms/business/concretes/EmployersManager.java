package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.EmployersService;
import kodlamaio.hrms.core.adapters.EmailCheckService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.core.utilities.validation.abstracts.EmailValidationService;
import kodlamaio.hrms.core.utilities.validation.abstracts.EmployeesValidationService;
import kodlamaio.hrms.dataAccess.abstracts.EmployersDao;
import kodlamaio.hrms.entities.concretes.Employers;

@Service
public class EmployersManager implements EmployersService {
	
	private EmployersDao employersDao;
	private EmailCheckService emailCheckService;
	private EmailValidationService emailValidationService;
	private EmployeesValidationService employeesValidationService;
	
	@Autowired
	public EmployersManager(EmployersDao employersDao, EmailCheckService emailCheckService,
			EmailValidationService emailValidationService,EmployeesValidationService employeesValidationService) {
		super();
		this.employersDao = employersDao;
		this.emailCheckService = emailCheckService;
		this.emailValidationService = emailValidationService;
		this.employeesValidationService = employeesValidationService;
	}

	@Override
	public DataResult<List<Employers>> getAll() {
		
		return new SuccessDataResult<List<Employers>>
		(this.employersDao.findAll(),"İs verenler listelendi");
	}

	
	@Override
	public Result add(Employers employers) {
		if(		employers.getCompanyName().isBlank()
				|| employers.getEmail().isBlank()
				|| employers.getPassword().isBlank()
				|| employers.getPhoneNumber().isBlank()
				|| employers.getWebAddress().isBlank()) {
			return new ErrorResult("Hicbir alan bos birakilamaz!");
		
		} else if(!employers.getWebAddress().contains(employers.getEmail().split("@")[0])){
            return new ErrorResult("Domain ayni degil!");
            
		}else if(!emailCheckService.checkIfRealEmail(employers.getEmail())) {
			return new ErrorResult("Email formati dogrulanamadi!");
			
		}else if(employersDao.findByEmailEquals(employers.getEmail()) != null) {
			return new ErrorResult("Bu email zaten sisteme kayitli!");
			
		}else if(!employers.getPassword().equals(employers.getPasswordRepeat())) {
			return new ErrorResult("Sifreler uyumsuz! Tekrar deneyiniz!");
			
		}else {
			
			if(emailValidationService.sendMail(employers) && employeesValidationService.isValid(employers) ) {
				
				this.employersDao.save(employers);
				return new SuccessResult("Tum bilgiler dogrulandi! Kayit basarili!");
				
			}else {
				return new ErrorResult("Dogrulama basarisiz!");
			}
		}

	}

	

}
