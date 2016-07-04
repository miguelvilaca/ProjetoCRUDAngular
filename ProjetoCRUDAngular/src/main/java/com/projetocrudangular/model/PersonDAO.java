package com.projetocrudangular.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.projetocrudangular.model.generic.MainDAO;

/**
 * Classe to manipulate data of the Person
 * 
 * @author mneto
 * */
public class PersonDAO extends MainDAO<Person, Long>{
	
	/**
	 * Create new ID
	 * 
	 * @return new String ID
	 *  		
	 * */
	public String getNextId() {
		List<String> people = this.getIds();
		
	    Comparator<String> cmp = new Comparator<String>() {
	        @Override
	        public int compare(String o1, String o2) {
	            return Integer.valueOf(o1).compareTo(Integer.valueOf(o2));
	        }
	    };
	    String maxID = "";
	    try {
	    	maxID = Collections.max(people, cmp);
		} catch (Exception e) {
			maxID = "0";
		}
		
	    Integer id = Integer.valueOf(maxID) + 1;
	    
		return id.toString();
	}
	
	public List<Person> getPessoasBySearch(String pesquisa){			
		return (List<Person>) this.executeQuery("select p from Person p where lower(p.name) "
					+ "like ?0 or p.cpf like ?0", "%" + pesquisa.toLowerCase() + "%");
	}
	
	public List<String> getIds(){			
		return (List<String>) this.executeQuery("select id from Person");
	}
}
