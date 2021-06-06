package kodlamaio.hrms.core.adapters;


import kodlamaio.hrms.entities.concretes.Candidates;


public interface MernisCheckService {

	boolean checkIfRealPerson(Candidates candidate);
}
