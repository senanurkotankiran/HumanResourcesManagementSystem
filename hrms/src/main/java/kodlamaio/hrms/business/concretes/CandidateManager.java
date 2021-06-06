package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.CandidatesService;
import kodlamaio.hrms.core.adapters.EmailCheckService;
//import kodlamaio.hrms.core.adapters.FakeMernisService;
import kodlamaio.hrms.core.adapters.MernisCheckService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.core.utilities.validation.abstracts.EmailValidationService;
import kodlamaio.hrms.dataAccess.abstracts.CandidatesDao;
import kodlamaio.hrms.entities.concretes.Candidates;


@Service
public class CandidateManager implements CandidatesService{
	
	private CandidatesDao candidatesDao;
	private EmailCheckService emailCheckService;
	private EmailValidationService emailValidationService;
	private MernisCheckService mernisCheckService;

	@Autowired
	public CandidateManager(CandidatesDao candidatesDao, 
			EmailCheckService emailCheckService, EmailValidationService emailValidationService,
			MernisCheckService mernisCheckService) {
		super();
		this.candidatesDao = candidatesDao;
		this.emailCheckService = emailCheckService;
		this.emailValidationService = emailValidationService;
		this.mernisCheckService = mernisCheckService;
	}
	
	

	@Override
	public DataResult<List<Candidates>> getAll() {

		return new SuccessDataResult<List<Candidates>>
		(this.candidatesDao.findAll(),"İs arayanlar listelendi");
	}

	@Override
	public Result add(Candidates candidates) {
		if(		candidates.getFirstName().isBlank()
				|| candidates.getLastName().isBlank()
				|| candidates.getEmail().isBlank()
				|| candidates.getPassword().isBlank()
				|| candidates.getNationalityId().isBlank()
				|| candidates.getBirthDate() == 0) {
			return new ErrorResult("Hicbir alan bos birakilamaz!");
			
		}else if(!mernisCheckService.checkIfRealPerson(candidates)) {
			return new ErrorResult("Mernis bilgileri dogrulanamadi!");
			
		}else if(!emailCheckService.checkIfRealEmail(candidates.getEmail())) {
			return new ErrorResult("Email formati dogrulanamadi!");
			
		}else if(!candidates.getPassword().equals(candidates.getPasswordRepeat())) {
			return new ErrorResult("Sifreler uyumsuz! Tekrar deneyiniz!");
			
		}else if(candidatesDao.findByEmailEquals(candidates.getEmail()) != null) {
			return new ErrorResult("Bu email zaten sisteme kayitli!");
			
		}else if(candidatesDao.findByNationalityIdEquals(candidates.getNationalityId()) != null) {
			return new ErrorResult("Bu Tc No zaten sisteme kayitli!");
			
		}else {
			
			if(emailValidationService.sendMail(candidates)) {
				this.candidatesDao.save(candidates);
				return new SuccessResult("Tum bilgiler dogrulandi! Kayit basarili!");
			}else {
				return new ErrorResult("Eposta dogrulamasi gerceklestirilemedi! Kayit basarisiz! Tekrar deneyin!");
			}
			
			
			
		}
	}






	
}
