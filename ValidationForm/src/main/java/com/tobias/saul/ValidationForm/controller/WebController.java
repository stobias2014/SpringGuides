package com.tobias.saul.ValidationForm.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tobias.saul.ValidationForm.forms.PersonForm;

@Controller
public class WebController implements WebMvcConfigurer{
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/results").setViewName("results");
	}
	
	@GetMapping("/")
	public String showForm(PersonForm personForm) {
		return "form";
	}
	
	@PostMapping("/")
	public String checkPersonInfo(@Valid PersonForm personForm, BindingResult result) {
		if(result.hasErrors()) {
			return "form";
		} 
		
		return "redirect:/results";
	}

}
