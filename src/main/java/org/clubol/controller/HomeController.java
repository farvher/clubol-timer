package org.clubol.controller;

import org.clubol.entity.Runner;
import org.clubol.services.RunnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	
	@Autowired
	private RunnerService runnerService;

	@RequestMapping("/greeting")
	public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name,
			Model model) {
		
		model.addAttribute("name", name);
		model.addAttribute("runner", new Runner());
		model.addAttribute("runners",runnerService.findAll());
		
		return "greeting";
	}
	
	@RequestMapping("/")
	public String index(){
		
		return "index";
	}
	
	@RequestMapping("/login")
	public String login(){
		
		return "login";
	}
	
	@RequestMapping("/logout")
	public String logout(){
		
		return "";
	}

}
