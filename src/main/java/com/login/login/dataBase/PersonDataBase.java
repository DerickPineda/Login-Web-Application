package com.login.login.dataBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

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
	
	private Map<UUID, Person> logins = new HashMap<UUID, Person>();
	
	
	
	//Method to add person to hashmap
	public void addPerson(Person person) {
		Person newPerson = new Person(person.getName());
		//Check to see if the person is already in the data base, if not put them in and return true
		//Map the Person object(Key) to the ArrayList(value)
		logins.put(newPerson.getId(),newPerson);
		
	}
	
	//Method to return all the people with logins in data base
	//Will also return their id's with the names
	public String[] getPeopleInDataBase(){
		//Get a set containing all the keys from logins(Person objects)
		Set<UUID> loginsKeys = logins.keySet();
		//Create an array of Person objects from the set
		UUID[] keys = loginsKeys.toArray(new UUID[loginsKeys.size()]);
		
		//Iterate through and get all the names/ids and put them into another array of String
		String[] names = new String[keys.length];
		for(int i = 0; i < names.length; i++) {
			names[i] = logins.get(keys[i]).toString();
					
		}
		
		//return names[] which contains all the names of the users in the database 
		return names;
	}
	
	
	//Method to return the login information for someone 
	public String[] getLoginInformation(UUID id){
		//Get ArrayList of person object logins and store them into an ArrayList
		ArrayList<LoginInformation> personLogins = logins.get(id).getLoginsList();
		//Check if ArrayList is empty, if so then return a String[] = {"empty"}
		if(personLogins.size() == 0){
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
	public void updateLoginInformation(UUID id, LoginInformation login) {
		//Login is defined as an String email, String username, String password 
		//update the person object's private login list
		logins.get(id).updateLogin(login);
		
	}
}
