package org.clubol.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.clubol.entity.Race;
import org.clubol.entity.Tags;
import org.clubol.services.RaceService;
import org.clubol.services.RunnerService;
import org.clubol.services.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
public class RaceController {

	@Autowired
	private RunnerService runnerService;

	@Autowired
	private RaceService raceService;

	@Autowired
	private TagsService tagService;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private Gson gson = new Gson();

	private static final String raceView = "race";

	@RequestMapping(value = "/race/new")
	public String newRace(Model model) {
		model.addAttribute("newRace", new Race());
		return "newRace";
	}

	@RequestMapping(value = "/race/save", method = RequestMethod.POST)
	public String saveRace(@ModelAttribute Race race, Model model) {
		if (raceService.findByRaceName(race.getRaceName()).size() == 0) {
			raceService.saveRace(race);
			model.addAttribute("message", "Carrera " + race.getRaceName() + " creada.");
		}else{
			model.addAttribute("error", "Carrera " + race.getRaceName() + " ya existe.");
			return "redirect:/race/new";
		}
		return"redirect:/race/"+race.getRaceName();
	}

	@RequestMapping(value = "/race/{raceName}")
	public String getRaceView(Model model, @PathVariable String raceName) {
		model.addAttribute("tags", tagService.findByNoRunDate(sdf.format(new Date())));
		model.addAttribute("dateRace", sdf.format(new Date()));
		List<Race> races = raceService.findByRaceName(raceName);
		if (races.size() == 0) {
			return "redirect:/race/new";
		}
		model.addAttribute("race", raceService.findByRaceName(raceName).get(0));
		return raceView;
	}

	// @RequestMapping(value = "/race/date/{dateText}")
	// public String getRaceViewByDate(Model model, @PathVariable String
	// dateText) {
	//
	// Date dateRace = getParseDate(dateText);
	// model.addAttribute("tags", tagService.findByNoRunDate(dateText));
	// model.addAttribute("dateRace", sdf.format(dateRace));
	//
	// return raceView;
	// }

	@RequestMapping(value = "/race/{raceName}/tag/{tag}")
	public String getRaceViewByTag(Model model, @PathVariable String raceName, @PathVariable Long tag) {
		model.addAttribute("tags", tagService.findByNoTag(tag));
		model.addAttribute("race", raceService.findByRaceName(raceName).get(0));
		return raceView;
	}

	/**
	 * 
	 * Peticiones Ajax todas
	 */
	@RequestMapping(value = "/race/ajax")
	@ResponseBody
	public String getRaceAjax(Model model) {
		List<Tags> tags = tagService.findAll();
		return gson.toJson(tags);
	}

	/**
	 * 
	 * Peticiones Ajax para tag
	 */
	@RequestMapping(value = "/race/ajax/{tag}")
	@ResponseBody
	public String raceAjaxByTag(Model model, @PathVariable Long tag) {
		List<Tags> tags = tagService.findByNoTag(tag);
		return gson.toJson(tags);
	}

	@RequestMapping(value = "/save/tag")
	@ResponseBody
	public String saveTag(Model model) {
		Tags tag = new Tags();
		tag.setNoAerial(1L);
		tag.setRunDate(sdf.format(new Date()));
		tag.setTime("05:30:22");
		tag.setNoTag(60L);
		tagService.saveTag(tag);
		Tags tag2 = new Tags();
		tag2.setNoAerial(1L);
		tag2.setRunDate(sdf.format(new Date()));
		tag2.setTime("06:30:22");
		tag2.setNoTag(30L);
		tagService.saveTag(tag2);
		return "guardado";
	}

	private static Date getParseDate(String dateText) {
		try {
			return sdf.parse(dateText);
		} catch (Exception e) {

		}
		return new Date();
	}

}
