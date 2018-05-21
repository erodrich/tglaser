package com.acyspro.tglaser.controllers;

import java.text.DateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
	
	@GetMapping("/test")
	public String index(Model model) {
		String msg = "Hola desde Spring Controller";
		model.addAttribute("serverTime", DateFormat.getInstance().format(new Date()));
		model.addAttribute("msg", msg);
		
		return "test";
	}

}
