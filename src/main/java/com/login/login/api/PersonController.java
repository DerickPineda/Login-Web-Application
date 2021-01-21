package com.login.login.api;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.login.login.model.LoginInformation;
import com.login.login.model.Person;
import com.login.login.service.PersonService;

@RequestMapping("/person")
@RestController
public class PersonController {

	private PersonService personService;
	
	@Autowired
	public PersonController(PersonService personService) {
		this.personService = personService;
	}
	
	//POST method to put in a person into the data base 
	@PostMapping
	public void insertPerson(@RequestBody Person person) {
		personService.insertPerson(person);
	}
	
	//PUT Method to put in new login information for a person
	@PutMapping(path = "{id}")
	public void updateLoginInformation(@PathVariable("id") UUID id, @RequestBody LoginInformation login) {
		personService.updateLoginInformation(id, login);
	}
	
	//GET method to get a person from the data base 
	@GetMapping
	public String[] getUsers() {
		return personService.getListOfUsers();
	}
	
	// GET Method to retrieve someone's login info
	@GetMapping(path = "{id}")
	public String[] getLoginInformation(@PathVariable("id") UUID id) {	
		return personService.getLoginInformation(id);
	}
}
