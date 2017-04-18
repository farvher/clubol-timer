package org.clubol.controller;


import org.clubol.entity.Distance;
import org.clubol.services.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DistanceController {
	
	
	@Autowired
	DistanceService distanceService;
	
	private static final String[] DISTANCES = { "10K (Categoria unica)", "21K (Categoria unica)",
			"5K (Categoria unica)" , "Vuelta a la isla 32,5K", "Otra", "CategoryFilter" };

	@RequestMapping(value = "/distance/autosave")
	public String addCategories() {
		for(String d : DISTANCES){
			Distance distance = new Distance();
			distance.setDistance(d);
			distance.setDescription("description : "+d.toLowerCase());
			distanceService.addDistance(distance);
		}
		return "redirect:/";
	}
	
	

}
