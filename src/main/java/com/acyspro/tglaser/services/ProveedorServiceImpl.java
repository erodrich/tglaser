package com.acyspro.tglaser.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acyspro.tglaser.dao.ProveedorDAO;
import com.acyspro.tglaser.entities.Proveedor;

@Service
public class ProveedorServiceImpl implements ProveedorService {

	// need to inject customer dao
	@Autowired
	private ProveedorDAO proveedorDAO;

	@Override
	@Transactional
	public List<Proveedor> getProveedores() {

		return proveedorDAO.getProveedores();
	}

}
