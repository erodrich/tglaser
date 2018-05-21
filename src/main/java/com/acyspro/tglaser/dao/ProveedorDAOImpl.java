package com.acyspro.tglaser.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.acyspro.tglaser.entities.Proveedor;

@Repository
public class ProveedorDAOImpl implements ProveedorDAO {

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Proveedor> getProveedores() {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// create a query
		Query<Proveedor> theQuery = currentSession.createQuery("from Proveedor", Proveedor.class);

		// execute query and get result list
		List<Proveedor> proveedores = theQuery.getResultList();

		// return the results
		return proveedores;
	}

}
