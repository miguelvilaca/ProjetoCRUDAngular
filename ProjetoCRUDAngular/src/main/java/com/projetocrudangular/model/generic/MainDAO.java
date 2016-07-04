package com.projetocrudangular.model.generic;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;

/**
 * Classe to control Data Access Generalized
 * 
 * @author mneto
 * 
 **/
public abstract class MainDAO<T, PK> {

	private final EntityManager entityManager = Persistence.createEntityManagerFactory("projetoCRUDPersistenceUnit").createEntityManager();
	private Class<?> clazz= (Class<?>) ((ParameterizedType) this.getClass()
			.getGenericSuperclass()).getActualTypeArguments()[0];
	
	/**
	 * Return specific Entity Objects
	 * 
	 * @param String
	 * 			Query hql
	 * @param Object...
	 * 			Param Query
	 * 
	 * @return Selected Entity Objects
	 * */
	public Object executeQuery(String query, Object... params) {
		Query createdQuery = this.entityManager.createQuery(query);

		for (int i = 0; i < params.length; i++) {
			createdQuery.setParameter(i, params[i]);
		}

		return createdQuery.getResultList();
	}
	
	/**
	 * Return all Entity Objects
	 * 
	 * @return Selected Entity Objects
	 * */
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return this.entityManager.createQuery(("FROM " + this.clazz.getName()))
				.getResultList();
	}
		
	/**
	 * Find Entity Object by primary Key
	 * 
	 * @param PK
	 * @return Object
	 * */
	@SuppressWarnings("unchecked")
	public T findById(Object pk) {
		return (T) this.entityManager.find(this.clazz, pk);
	}
	
	/**
	 * To Save any Entity Object
	 * 
	 * @param Objeto 
	 * @return Boolean
	 * */
	public Boolean save(T entity) {
		try {
			this.beginTransaction();
			this.entityManager.persist(entity);
			this.commit();
			return true;
		} catch (Exception e) {
			this.rollBack();
			return false;
		}
	}

	/**
	 * Metodo To Update Data
	 * 
	 * @param Objeto Content
	 * @return Boolean
	 * */
	public Boolean update(T entity) {
		try {
			this.beginTransaction();
			this.entityManager.merge(entity);
			this.commit();
			return true;
		} catch (Exception e) {
			this.rollBack();
			return false;
		}
	}
	
	/**
	 * To delete data
	 * 
	 * @param Objeto Content
	 * @return Boolean
	 * */
	public Boolean delete(T entity) {
		try {
			this.beginTransaction();
			this.entityManager.remove(entity);
			this.commit();
			return true;
		} catch (Exception e) {
			this.rollBack();
			return false;
		}
	}
	
	/**
	 * To delete by ID
	 * 
	 * @param Objeto 
	 * @return Boolean
	 * */
	public Boolean deleteById(Object id) {
		try {
			this.beginTransaction();
			this.entityManager.remove(this.findById(id));
			this.commit();
			return true;
		} catch (Exception e) {
			this.rollBack();
			return false;
		}
	}
	
	/**
	 * Start the trasaction context
	 * */
	public void beginTransaction(){
		this.entityManager.getTransaction().begin();
	}

	/**
	 * To consolidate trasactions
	 * */
	public void commit(){
		this.entityManager.getTransaction().commit();
	}

	/**
	 * To close trasactions
	 * */
	public void close(){
		this.entityManager.close();
	}
	
	/**
	 * To Stop and RollBack trasactions
	 * */
	public void rollBack(){
		this.entityManager.getTransaction().rollback();
	}

	public EntityManager getEntityManager(){
		return this.entityManager;
	}

}
