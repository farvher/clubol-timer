package org.clubol.controller;

import java.util.List;

import org.clubol.dao.RunnerRepository;
import org.clubol.entity.Runner;
import org.clubol.services.CategoryService;
import org.clubol.services.ChronometerService;
import org.clubol.services.RaceService;
import org.clubol.services.RunnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RunnerController {

	private static final String VIEW_RUNNER ="runners";
	
	@Autowired
	private RunnerService runnerService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private RaceService raceService;
	
	@Autowired
	private ChronometerService chronometerService;
	
	
	@RequestMapping(value = "/runner/new" )
	public String newRunner(Model model){
	
		Runner r = new Runner();
		r.setActive(true);
		model.addAttribute("newRunner",r);
		model.addAttribute("categories",categoryService.getCategories());
		model.addAttribute("distances", chronometerService.findAll());
		return "newRunner";
	}
	
	@RequestMapping(value = "/runner/update/{id}" )
	public String updateRunner(Model model,@PathVariable Long id){
	
		Runner r = runnerService.findById(id);
		model.addAttribute("newRunner",r);
		model.addAttribute("categories",categoryService.getCategories());
		model.addAttribute("distances", chronometerService.findAll());
		return "newRunner";
	}
	
	
	@RequestMapping(value = "/runner/save" , method = RequestMethod.POST)
	public String saveRunner(@ModelAttribute Runner newRunner,Model model){
		
		Runner findFirtRunner = runnerService.findFirstByDocument(newRunner.getDocument());
		if(findFirtRunner!=null){
			newRunner.setId(findFirtRunner.getId());
		}
		runnerService.saveRunner(newRunner);
		model.addAttribute("message", "Corredor "+newRunner.getFirstName() + " guardado.");
		model.addAttribute("runners",runnerService.findAll());
		model.addAttribute("newRunner", new Runner());
		return "redirect:/" +VIEW_RUNNER;
	}
	
	
	@RequestMapping(value ="/runners")
	public String getRunners(Model model){
		
		model.addAttribute("runners",runnerService.findAll());
		
		return VIEW_RUNNER;
	}
	
	
	@RequestMapping(value ="/runners/{key}")
	public String getRunnersByField(Model model , @PathVariable String key){
		model.addAttribute("runners",runnerService.findByDocumentOrFirstNameOrLastName(key, key, key));
		model.addAttribute("newRunner", new Runner());
		return VIEW_RUNNER;
	}
	
	@RequestMapping(value ="/runners/tag/{tag}")
	public String getRunnerByPosition(Model model, @PathVariable Long tag){
		model.addAttribute("runners",runnerService.findByPositionAll(tag));
		return VIEW_RUNNER;
	}

	@RequestMapping(value="/runner/enableDisable/{id}")
	public String disableRunner(Model model,@PathVariable Long id){
		runnerService.disableRunner(id);
		return "redirect:/runners#runner-"+id;
	}
	
	@RequestMapping(value="/runner/delete/{id}")
	private String deleteRunner(Model model,@PathVariable Long id){
		runnerService.delete(id);
		return "redirect:/runners";
	}
	
	@RequestMapping(value="/runner/duplicates")
	@ResponseBody
	public String getDuplicatePosition(){
		
		StringBuilder str = new StringBuilder();
		List<Object[]> listObj = runnerService.findDuplicatePositions();
		
		str.append("tag : veces");
		for(Object [] arrayObject : listObj){
			str.append("<p>");
			str.append(arrayObject[0]);
			str.append(" : ");
			str.append(arrayObject[1]);
			
		}
		return str.toString();
	}
	
}
