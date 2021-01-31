package com.login.login.dataBase;

import com.login.login.dataBase.App;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.json.simple.JSONArray;
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
	/*
	 * @Id
	 * 
	 * @SequenceGenerator( name = "login_data_sequence", sequenceName =
	 * "login_data_sequence" )
	 * 
	 * @GeneratedValue( strategy = GenerationType.SEQUENCE, generator =
	 * "login_data_sequence" )
	 * 
	 * @ElementCollection
	 */
	//Am currently implementing adding the people/logins to the actual database
	//Leaving this here for now
	private Map<UUID, Person> logins = new HashMap<UUID, Person>();
	
	private App app = new App();
	
	//Method to add person to database
	public void addPerson(Person person) {
		
		//Create a person object to make sure the id is created
		//This might be a bit redundant
		Person tempPerson = new Person(person.getName(), person.getEmail(), person.getPassword());
		
		String sql = "INSERT INTO person(person_name, person_email, person_id, person_login_list, "
				+ "person_password) VALUES(?,?,?,?,?)"; 
		
		/*
		 * We will take the Person objects Arraylist and turn it into a String[]
		 * which will then be passed into the DB
		 */
		ArrayList<LoginInformation> tempArray = person.getLoginsList();
		String[] newLoginList = new String[tempArray.size()];
		
		for(int i = 0; i < tempArray.size(); i++) {
			newLoginList[i] = tempArray.get(i).toString();
		}
		
		try {
			//Establish a connection with the database
			Connection connect = app.connect();
			//Using the connnection, make a prepared statement(used to send postgreSQL query/commands)
			//Send the statement to the database
			PreparedStatement p = connect.prepareStatement(sql);
			
			//add the values into the statement 
			p.setString(1, tempPerson.getName());
			p.setString(2, tempPerson.getEmail());
			p.setObject(3, tempPerson.getId());
			p.setObject(4, newLoginList);
			p.setString(5, tempPerson.getPassword());
			
			//Execute the query
			p.executeUpdate();
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
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
