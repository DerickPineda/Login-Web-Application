package com.login.login.service;

import java.util.ArrayList;

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
 * @Qualifier helps distinguish the different implementations of PersonService objects?
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
	
	public void updateLoginInformation(Person person, LoginInformation login) {
		System.out.println("in person service, will update for " + person.getName() + "\nlogin update: " +login.toString());
		personDataBase.updateLoginInformation(person, login);
	}
	
	public String[] getListOfUsers() {
		return personDataBase.getPeopleInDataBase();
	}
	
	
	//Method that will return the login information for a person
	public String[] getLoginInformation(Person person){
		System.out.println("in person service for " + person.getName());
		return personDataBase.getLoginInformation(person);
	}
	
	
	
	
	
}
