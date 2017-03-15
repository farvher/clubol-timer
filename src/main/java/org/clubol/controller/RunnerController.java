package org.clubol.controller;

import org.clubol.entity.Runner;
import org.clubol.services.RunnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RunnerController {

	
	@Autowired
	private RunnerService runnerService;
	
	@RequestMapping(value = "/runner/save" , method = RequestMethod.POST)
	public String saveRunner(@ModelAttribute Runner runner){
		runnerService.saveRunner(runner);
		return "redirect:/greeting";
	}
	
}
