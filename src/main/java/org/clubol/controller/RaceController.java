package org.clubol.controller;

import org.clubol.services.RunnerService;
import org.clubol.services.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class RaceController {

	
	@Autowired
	private RunnerService runnerService;
	
	@Autowired
	private TagsService tagService;
	
	private final static String raceView = "race";
	
	public String getRaceView(Model model){
		
		model.addAttribute("tags", tagService.findAll());
		
		return raceView;
	}
	
}
