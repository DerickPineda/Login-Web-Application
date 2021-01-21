package com.login.login.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.login.login.dataBase.PersonDataBase;
import com.login.login.model.LoginInformation;
import com.login.login.model.Person;


/*
 * This class will be the "middle man" in making PersonDataBase objects
 * and sending instructions?
 * 
 * Using @Autowired for injection
 * 
 * @Qualifier helps in letting you change between different implementations
 * For example, it is currently set to "personDataBase" which is the repository in my PersonDataBase class.
 * If I'm understanding this correctly, assuming I had another class with the repository name "personDataBaseV2"
 * then I could just change the qualifier input to "personDataBaseV2" and this class would then be a service  for that class
 */

@Service
public class PersonService {
	//Class variables
	private PersonDataBase personDataBase;

	//Constructor 
	@Autowired
	public PersonService(@Qualifier("personDataBase") PersonDataBase personDataBase) {
		this.personDataBase = personDataBase;
	}

	public PersonDataBase getPersonDataBase() {
		return personDataBase;
	}
	
	
	
	
	
	//Methods 
	public void insertPerson(Person person) {
		personDataBase.addPerson(person);
	}
	
	public void updateLoginInformation(UUID id, LoginInformation login) {
		personDataBase.updateLoginInformation(id, login);
	}
	
	public String[] getListOfUsers() {
		return personDataBase.getPeopleInDataBase();
	}
	
	
	//Method that will return the login information for a person
	public String[] getLoginInformation(UUID id){
		return personDataBase.getLoginInformation(id);
	}
	
	
	
	
	
}
