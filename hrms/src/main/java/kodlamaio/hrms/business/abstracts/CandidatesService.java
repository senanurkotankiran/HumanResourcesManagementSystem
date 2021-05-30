package kodlamaio.hrms.business.abstracts;

import java.util.List;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Candidates;

public interface CandidatesService {
	
	DataResult<List<Candidates>> getAll();
	
	Result add(Candidates candidates);
	
	
}
