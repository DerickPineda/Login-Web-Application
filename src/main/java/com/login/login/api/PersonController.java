package com.login.login.api;

import java.util.ArrayList;

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
	
	// POST Method to put in new login information for a person
	@PutMapping(path = "{person}")
	public void updateLoginInformation(@PathVariable("person") Person person, @RequestBody LoginInformation login) {
		System.out.println("in controller, will update for " + person.getName());
		personService.updateLoginInformation(person, login);
	}
	
	//GET method to get a person from the data base 
	@GetMapping
	public String[] getUsers() {
		return personService.getListOfUsers();
	}
	
	// GET Method to retrieve someone's login info
	@GetMapping(path = "{person}")
	public String[] getLoginInformation(@PathVariable("person") Person person) {
		System.out.println("\n\nIn person controller");
		return personService.getLoginInformation(person);
	}
}
