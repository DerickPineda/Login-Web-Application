package com.login.login.model;
	
import java.util.ArrayList;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * Person object's will have id's and passwords to login
 * Each person will have their own set of logins/passwords which will be kept in a list 
 * In the data base 
 */

//Using the @entity annotation creates table for the class in your DB
//You want to give entities names as it's good practice and it makes it easier 
//when you have longer class names 
//The default name in the DB is the class name  
@Entity(name = "Person") 
@Table(name = "Person")
public class Person {
	@Id
	@SequenceGenerator(
		name = "person_sequence",
		sequenceName = "person_sequence",
		allocationSize = 1
	)
	
	/*From my understanding sequences are used to create unique values 
	 * for primary keys, in this case Person objects which allows there to be 
	 * multiple transactions without having them get the same value  
	 * 
	 * In other words it is like an id for each person
	 */
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "person_sequence"	
	)
	
	//@Column allows us to specify the names for the columns(variables) in the DB table
	@Column(
			name = "person_name",
			updatable = false,
			nullable = false,
			columnDefinition = "TEXT"
	)
	private final String name;
	
	@Column(
			name = "person_email",
			updatable = true,
			nullable = false
	)
	private String email;
	
	@Column(
			name = "person_password",
			updatable = true,
			nullable = false
	)
	private String password;
	
	@Column(
			name = "person_id",
			updatable = false,
			nullable = false
	)	
	private UUID id;
	

	@Column(
			name = "person_login_list",
			updatable = true,
			columnDefinition = "text[]"
	)
	private ArrayList<LoginInformation> loginsList;
	
	
	//Constructor
	public Person(@JsonProperty("name") String name,
				  @JsonProperty("email") String email,
				  @JsonProperty("password") String password) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.id = UUID.randomUUID();
		loginsList = new ArrayList<LoginInformation>();
		
	}
	


	public String getName() {
		return name;
	}

	
	
	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public ArrayList<LoginInformation> getLoginsList() {
		return loginsList;
	}
	
	
	//Method to allow person to update their logins
	public void updateLogin(LoginInformation login) {
		loginsList.add(login);
	}
	
	public UUID getId() {
		return id;
	}



	public void setId(UUID id) {
		this.id = id;
	}
	
	
	
	@Override
	public String toString() {
		return name + " " + id;
	}
	
	

}
