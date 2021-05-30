package kodlamaio.hrms.core.adapters;

import org.springframework.stereotype.Service;

import kodlamaio.hrms.entities.concretes.Candidates;

@Service
public class FakeMernisManager implements FakeMernisService {


	@Override
	public boolean chekIfRealPerson(Candidates candidates) {
		if(candidates.getFirstName().length()<2) {
			
			System.out.println("İsim alani bos veya 2 karakterden az olamaz!");
			return false;
			
		} if(candidates.getLastName().length()<2) {
			
				System.out.println("Soyisim alani bos veya 2 karakterden az olamaz!");
				return false;
				
			} if(candidates.getNationalityId().length() != 11) {
				System.out.println("Tc kimlik 11 karakter olmali");
				return false;
			}
		return true;
	}

}
