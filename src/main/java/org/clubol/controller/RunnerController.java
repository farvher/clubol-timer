package org.clubol.controller;

import org.clubol.entity.Runner;
import org.clubol.services.RunnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RunnerController {

	
	@Autowired
	private RunnerService runnerService;
	
	
	@RequestMapping(value = "/runner/new" )
	public String newRunner(Model model){
	
		model.addAttribute("newRunner", new Runner());
		return "newRunner";
	}
	
	@RequestMapping(value = "/runner/save" , method = RequestMethod.POST)
	public String saveRunner(@ModelAttribute Runner newRunner){
		runnerService.saveRunner(newRunner);
		return "redirect:/runners";
	}
	
	
	@RequestMapping(value ="/runners")
	public String getRunners(Model model){
		
		model.addAttribute("runners",runnerService.findAll());
		model.addAttribute("newRunner", new Runner());
		
		return "runners";
	}
	
	
	@RequestMapping(value ="/runners/{key}")
	public String getRunnersByField(Model model , @PathVariable String key){
		model.addAttribute("runners",runnerService.findByDocumentOrFirstNameOrLastName(key, key, key));
		model.addAttribute("newRunner", new Runner());
		return "runners";
	}
	
	@RequestMapping(value ="/runners/tag/{tag}")
	public String getRunnerByPosition(Model model, @PathVariable Long tag){
		
		model.addAttribute("runners",runnerService.findByPosition(tag));
		model.addAttribute("newRunner", new Runner());
		
		return "runners";
	}
	
}
