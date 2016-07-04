package com.projetocrudangular.control;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.projetocrudangular.model.Person;
import com.projetocrudangular.model.PersonDAO;

/**
 * Classe to control json data of the Person
 * 
 * @author mneto
 * 
 **/
@Path("/people")
public class PersonRS {
	
	private PersonDAO personDAO = new PersonDAO();
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPessoas() {
		List<Person> people = personDAO.findAll();
		
		if (people != null && !people.isEmpty()){
			return Response.ok(people).build();
		} else{
			return Response.noContent().build();
		}
	}
	
	@Path("{id}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPessoa(@PathParam("id") String id) {
		
		Person person = personDAO.findById(id);
		
		if (person != null){
			return Response.ok(person).build();
		} else {
			return Response.noContent().build();
		}
	}
	
	@Path("/search/{search}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPersonBySearch(@PathParam("search") String pesquisa) {
		List<Person> people = personDAO.getPessoasBySearch(pesquisa);
		
		if (people != null && !people.isEmpty()){
			return Response.ok(people).build();
		} else {
			return Response.noContent().build();
		}
	}
	
	@Path("{id}")
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updatePerson(Person person) {
		if (person != null && personDAO.update(person)) {
			return Response.ok().build();
		} else {
			return Response.serverError().build();
		}
	}
	
	@Path("/add")
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response addPerson(Person person) {		
		if (person != null){
			person.setId(personDAO.getNextId());
			if (personDAO.save(person)){ 
				return Response.ok().build();
			} else {
				return Response.serverError().build();
			}
		} else {
			return Response.serverError().build();
		}	 
	}
	
	@Path("{id}")
	@DELETE
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deletePerson(@PathParam("id") String id) {
		if (id != null && personDAO.deleteById(id)) {
			return Response.ok().build();
		} else {
			return Response.serverError().build();
		}
	}


}
