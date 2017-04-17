package org.clubol.controller;

import org.clubol.entity.Runner;
import org.clubol.services.RaceService;
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

	@Autowired
	private RaceService raceService;
	
	@RequestMapping("/")
	public String index(Model model){
		
		model.addAttribute("races",raceService.findAll());
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
