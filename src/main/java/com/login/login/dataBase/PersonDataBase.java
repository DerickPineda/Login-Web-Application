package com.login.login.dataBase;

import com.login.login.dataBase.App;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	
	private App app = new App();
	
	//Method to add person to database
	public void addPerson(Person person) {
		
		//Create a person object to make sure the id is created
		//This might be a bit redundant
		Person tempPerson = new Person(person.getName(), person.getEmail(), person.getPassword());
		
		String sql = "INSERT INTO person(person_name, person_email, person_id, person_login_list, "
				+ "person_password, person_logins_count) VALUES(?,?,?,?,?,?)"; 
		
		/*
		 * We will take the Person objects Arraylist and turn it into a String[]
		 * which will then be passed into the DB
		 */
		ArrayList<LoginInformation> tempArray = person.getLoginsList();
		String[] newLoginList = new String[tempArray.size()];
		
		for(int i = 0; i < tempArray.size(); i++) {
			newLoginList[i] = tempArray.get(i).toString();
		}
		
		
		//Now we will try to add the person into the DB
		try {
			//Establish a connection with the database
			Connection connect = app.connect();
			//Using the connection, make a prepared statement(used to send postgreSQL query/commands)
			//Send the statement to the database
			PreparedStatement p = connect.prepareStatement(sql);
			
			//add the values into the statement 
			p.setString(1, tempPerson.getName());
			p.setString(2, tempPerson.getEmail());
			p.setObject(3, tempPerson.getId());
			p.setObject(4, newLoginList);
			p.setString(5, tempPerson.getPassword());
			p.setInt(6, tempPerson.getLoginsCount());
			//Execute the query
			p.executeUpdate();
			
			System.out.println("Added " + person.getName() + " into the DB");
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	//Method to return all the people with logins in data base
	//Will also return their id's with the names
	public String[] getPeopleInDataBase(){
		
		String sql = "SELECT person_name FROM person";
		
		try {
			//Establish connection to DB 
			Connection connect = app.connect();
			
			//Create a Statement object to send to the DB
			Statement statement = connect.createStatement();
			
			//Retrieve the result set which should give us the names of everyone in the DB
			ResultSet result = statement.executeQuery(sql);
			
			//If we get this far then we successfully queried the DB
			System.out.println("Successfully called getPeopleInDataBase()");
			
			//Print out the list of people in Database(using Result Set) and return it
			String[] results = printDataFromDB(result, "person_name");
			
			return results;
			
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
		}
		
		return null;
		
	}
	
	
	//Method to return the login information for someone 
	public String[] getLoginInformation(UUID id){
		
		String sql = "SELECT person_login_list FROM person WHERE person_id = '" + id + "'";
		
		try {
			//Connect to the DB
			Connection connect = app.connect();
			
			//Create a statement object to make a DB query
			Statement statement = connect.createStatement();
			
			//Execute the statement
			ResultSet logins = statement.executeQuery(sql);
			System.out.println("\nRetrieved person_login_list");
			
			//We now want to convert the Result Set into a String[] that we can return
			String[] loginsList = printDataFromDB(logins,"person_login_list");
			
			//Return the String[]
			return loginsList;
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
			
		}
		
		//If we made it here there was an error, return null
		return null;
		
	}
	
	
	//Method to add new login information 
	public void updateLoginInformation(UUID id, LoginInformation login) {
	
		try {
			//connect to DB
			Connection connect = app.connect();
			
			/*
			 * Update the person's login count before executing query, this will be to keep
			 * track of the String[] index in the db
			 * 
			 * We first need to find the person in the database, so we will need another prepared statement and statement
			 */
			Statement getCountToUpdate = connect.createStatement();
			String getCountSQL = "SELECT person_logins_count FROM person WHERE person_id = '" + id +"'";
			
			ResultSet count = getCountToUpdate.executeQuery(getCountSQL);
			System.out.println("Obtained person_logins_count");
			
			//We now need to convert the ResultSet into an int variable and then update it
			int updatedCount;
			
			//Check to see if our ResultSet has anything, if it does continue, if it does not
			//Then print out error and exit
			if(count.next()) {
				updatedCount = count.getInt("person_logins_count");
				updatedCount++;
				
				//Now we need to update the logins list in the DB using the new index for the array in DB holding the login information
				String updateCountStatement = "UPDATE person SET person_logins_count = " + updatedCount + " where person_id = '" + id +"'";
				PreparedStatement updateCount = connect.prepareStatement(updateCountStatement);
				
				updateCount.execute();
				System.out.println("\nUpdated persons_logins_count");
				
				
				//Now that we have updated the count, we need to update the logins list with the given Login Information
				String updateLoginsSQL = "UPDATE person SET person_login_list[" + updatedCount + "] = '" + login.toString() + "'"
									   + " WHERE person_id = '" + id + "'";
				PreparedStatement updateLoginsList = connect.prepareStatement(updateLoginsSQL);
				
				//Execute the update
				updateLoginsList.execute();
				System.out.println("\nUpdated persons_login_list");
			
			} else {
				System.out.println("\nCould not find count");
			}
			
			
			
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
		}
		
	}
	
	
	
	
	
	public String[] printDataFromDB(ResultSet result, String columnName) throws SQLException {
		ArrayList<String> peopleInDB = new ArrayList<String>();
		
		//Extract all the person names into an array list from the ResultSet
		while(result.next()) {
			peopleInDB.add(result.getString(columnName));
		}
		
		//Convert the array list into a String[] 
		return peopleInDB.toArray(new String[0]);
		
		
	}
}
