package org.clubol.controller;

import org.clubol.entity.Runner;
import org.clubol.services.CategoryService;
import org.clubol.services.ChronometerService;
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
	
	@Autowired
	private ChronometerService chronometerService;
	
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("/")
	public String index(Model model){
		
		model.addAttribute("races",raceService.findAll());
		model.addAttribute("categories", categoryService.getCategories());
		model.addAttribute("distances",chronometerService.findAll());
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
