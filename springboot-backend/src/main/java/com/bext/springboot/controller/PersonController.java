package com.bext.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bext.springboot.exception.ResourceNotFoundException;
import com.bext.springboot.model.Person;
import com.bext.springboot.repository.PersonRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class PersonController {
   @Autowired
   private PersonRepository personRepository;
   
   //get all persons
   @GetMapping("/persons")
   public List<Person> getAllPerson(){
	   return personRepository.findAll();
   }
   
   @PostMapping("/persons")
   public Person createPerson(@RequestBody Person person) {
	   return personRepository.save(person);
   }
   
   @GetMapping("/persons/{id}")
   public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
	   Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person does not exist with id: " + id));
	   return ResponseEntity.ok(person);
   }
   
   @PutMapping("/persons/{id}")
   public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person personFromTo) {
	   Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person does not existe with id: " + id));
	      
	   person.setFirstName(personFromTo.getFirstName());
	   person.setLastName(personFromTo.getLastName());
	   person.setEmail(personFromTo.getEmail());
	   
	   Person updatedPerson = personRepository.save(person);
	   return ResponseEntity.ok(updatedPerson);
   }
   
   @DeleteMapping("/persons/{id}")
   public ResponseEntity<Person> deletePerson(@PathVariable Long id){
	   Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person does not exist with id: " + id));
	   personRepository.deleteById(id);
	   return ResponseEntity.ok(person);
   }
}
