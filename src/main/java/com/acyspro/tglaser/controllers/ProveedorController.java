package com.acyspro.tglaser.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acyspro.tglaser.entities.Proveedor;
import com.acyspro.tglaser.services.ProveedorService;

@Controller
@RequestMapping("/proveedor")
public class ProveedorController {

	// need to inject our customer service
	@Autowired
	private ProveedorService proveedorService;
	
	@GetMapping("/")
	public String listCustomers(Model theModel) {
		
		// get customers from the service
		List<Proveedor> proveedores = proveedorService.getProveedores();
				
		// add the customers to the model
		theModel.addAttribute("proveedores", proveedores);
		
		theModel.addAttribute("titulo", "Proveedores");
		
		return "proveedor/list-proveedor";
	}

}
