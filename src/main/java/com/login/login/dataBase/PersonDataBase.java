package com.login.login.dataBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.login.login.model.LoginInformation;
import com.login.login.model.Person;

/*
 * This class will hold the logins/password list for the user 
 * Will hold functions to manipulate the data/list 
 * 
 * @Repository allows us to have multiple implementations/it instantiates the object 
 */
@Repository("personDataBase")
public class PersonDataBase {
	
	private Map<Person, ArrayList<LoginInformation>> logins = new HashMap<Person, ArrayList<LoginInformation>>();
	
	
	
	//Method to add person to hashmap
	public boolean addPerson(Person person) {
		
		//Check to see if the person is already in the data base, if not put them in and return true
		//Map the Person object(Key) to the ArrayList(value)
		logins.putIfAbsent(person,person.getLoginsList());
		return true;
		
	}
	
	//Method to return all the people with logins in data base
	public String[] getPeopleInDataBase(){
		//Get a set containing all the keys from logins(Person objects)
		Set<Person> loginsKeys = logins.keySet();
		//Create an array of Person objects from the set
		Person[] personNames = loginsKeys.toArray(new Person[loginsKeys.size()]);
		
		//Iterate through and get all the names and put them into another array of String
		String[] names = new String[personNames.length];
		for(int i = 0; i < names.length; i++) {
			names[i] = personNames[i].getName();
		}
		
		//return names[] which contains all the names of the users in the database 
		return names;
	}
	
	
	//Method to return the login information for someone 
	public String[] getLoginInformation(Person person){
		//Get ArrayList of person object logins and store them into an ArrayList
		ArrayList<LoginInformation> personLogins = logins.get(person);
		//Check if ArrayList is empty, if so then return a String[] = {"empty"}
		if(personLogins.equals(null)) {
			String[] loginIsEmpty = {"empty"};
			return loginIsEmpty;
		}
		
		//Go through the ArrayList, putting each login into another String[] to return
		String[] loginInfo = new String[personLogins.size()];
		for(int i = 0; i < loginInfo.length; i++){
			loginInfo[i] = personLogins.get(i).toString();
		}
		
		
		//Now that we have all the logins as String inside of loginInfo[], return it
		return loginInfo;
	
	}
	
	
	//Method to add new login information 
	public void updateLoginInformation(Person person, LoginInformation login) {
		//Login is defined as an String email, String username, String password 
		
		//update the person object's private login list
		person.updateLogin(login);

		//then add it to the logins hashmap ArrayList<LoginInformation> by replacing it until
		//I find a better way 
		logins.putIfAbsent(person,person.getLoginsList());
		System.out.println("\n\n\nIn person data base, the new array list is " + person.getLoginsList().toString());
		System.out.println("\n\n\n\nFrom hashmap: " + logins.get(person).toString());
	}
}
