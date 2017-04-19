package org.clubol.controller;

import org.clubol.entity.Chronometer;
import org.clubol.services.ChronometerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ChronometerController {

	@Autowired
	private ChronometerService chronometerService;

	private static final String[] DISTANCES = { "10K (Categoria unica)", "21K (Categoria unica)",
			"5K (Categoria unica)", "Vuelta a la isla 32,5K" };

	@RequestMapping("/chronometer/{id}/{timeStart}/{timeEnd}")
	@ResponseBody
	public String saveTimeChronometer(@PathVariable Long id, @PathVariable String timeStart,
			@PathVariable String timeEnd) {

		Chronometer cr = chronometerService.findById(id);
		if (cr != null) {
			if (!"0".equals(timeStart)) {
				cr.setTimeStart(timeStart);
			}
			if (!"0".equals(timeEnd)) {
				cr.setTimeEnd(timeEnd);
			}
			chronometerService.update(cr);
			return "update";
		}
		return "error";
	}

	@RequestMapping(value = "/distance/autosave")
	public String addCategories() {
		if (chronometerService.findAll().isEmpty()) {
			for (String d : DISTANCES) {
				Chronometer cr = new Chronometer();
				cr.setChronometerName(d);
				cr.setTimeEnd("0:00:00");
				cr.setTimeStart("0:00:00");
				chronometerService.save(cr);
			}
		}
		return "redirect:/";
	}

}
