package org.clubol.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.clubol.dto.RaceDto;
import org.clubol.entity.Race;
import org.clubol.entity.Runner;
import org.clubol.entity.Tags;
import org.clubol.services.CategoryService;
import org.clubol.services.ChronometerService;
import org.clubol.services.RaceService;
import org.clubol.services.RunnerService;
import org.clubol.services.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

	private static final int MAX_RUNNER = 20;

	@Autowired
	private RunnerService runnerService;

	@Autowired
	private RaceService raceService;

	@Autowired
	private TagsService tagService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ChronometerService ChronometerService;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private Gson gson = new Gson();

	private static final String raceView = "race";

	/**
	 * Metodo para retornar el formulario de nueva carrera
	 */
	@RequestMapping(value = "/race/new")
	public String newRace(Model model) {
		model.addAttribute("newRace", new Race());
		return "newRace";
	}

	@RequestMapping(value = "/race/save", method = RequestMethod.POST)
	public String saveRace(@ModelAttribute Race race, Model model) {
		if (raceService.findByRaceName(race.getRaceName()) == null) {
			raceService.saveRace(race);
			model.addAttribute("message", "Carrera " + race.getRaceName() + " creada.");
		} else {
			model.addAttribute("error", "Carrera " + race.getRaceName() + " ya existe.");
			return "redirect:/race/new";
		}
		return "redirect:/race/" + race.getRaceName();
	}

	/**
	 * Metodo para listar los tag que se han leido
	 */
	@RequestMapping(value = "/race/{raceName}")
	public String getRaceView(Model model, @PathVariable String raceName) {
		Race race = raceService.findByRaceName(raceName);
		if (race == null) {
			return "redirect:/race/new";
		}

		model.addAttribute("race", race);
		model.addAttribute("tags", tagService.findByRace(race.getRaceName()));
		// model.addAttribute("tagRunners", tagService.findRunnerAndTags());
		return raceView;
	}

	/**
	 * 
	 * Metodo para listar los corredores y medir sus tiempos
	 */
	@RequestMapping(value = "/raceRunner/{raceName}")
	public String getRaceRunnerView(Model model, @PathVariable String raceName) {
		Race race = raceService.findByRaceName(raceName);
		if (race == null) {
			return "redirect:/race/new";
		}
		model.addAttribute("race", race);
		model.addAttribute("categories",categoryService.getCategories());
		
		Pageable page = new PageRequest(0, MAX_RUNNER);
		List<Runner> runers = runnerService.findAll();
		model.addAttribute("tagRunners", tagService.findRunnerAndTags(runers));
		return "raceTimes";
	}
	
	

	@RequestMapping(value = "/raceRunner/{raceName}/ajax/{cant}")
	@ResponseBody
	public String getRaceRunnerViewAjax(Model model, @PathVariable String raceName,@PathVariable int cant) {
		Race race = raceService.findByRaceName(raceName);
		if (race == null) {
			return gson.toJson(new ArrayList<RaceDto>());
		}
		Pageable page = new PageRequest(0, cant);
		List<Runner> runers = runnerService.findAllPageable(page);
		List<RaceDto> raceDtoList = tagService.findRunnerAndTags(runers);
		return gson.toJson(raceDtoList);
	}

	/**
	 * 
	 * */
	@RequestMapping(value = "/raceRunner/{raceName}/{distance}/{gender}/ajax")
	@ResponseBody
	public String getRaceRunnerViewAjaxByDistanceAndGender(Model model, @PathVariable String raceName,
			@PathVariable String distance, @PathVariable String gender) {
		Race race = raceService.findByRaceName(raceName);
		if (race == null) {
			return gson.toJson(new ArrayList<RaceDto>());
		}
		List<Runner> runers = runnerService.findByDistanceAndGender(distance, gender);
		List<RaceDto> raceDtoList = tagService.findRunnerAndTags(runers);
		return gson.toJson(raceDtoList);
	}

	/**
	 * 
	 * */
	@RequestMapping(value = "/raceRunner/{raceName}/{category}/ajax")
	@ResponseBody
	public String getRaceRunnerViewAjaxByCategoy(Model model, @PathVariable String raceName,
			@PathVariable String category) {
		Race race = raceService.findByRaceName(raceName);
		if (race == null) {
			return gson.toJson(new ArrayList<RaceDto>());
		}
		List<Runner> runers = runnerService.findByCategory(category);
		List<RaceDto> raceDtoList = tagService.findRunnerAndTags(runers);
		return gson.toJson(raceDtoList);
	}

	/**
	 * 
	 * Peticiones Ajax todas
	 */
	@RequestMapping(value = "/race/{raceName}/ajax")
	@ResponseBody
	public String getRaceAjax(Model model, @PathVariable String raceName) {
		Race race = raceService.findByRaceName(raceName);
		if (race == null) {
			return gson.toJson(new ArrayList<RaceDto>());
		}
		List<RaceDto> raceDtoList = tagService.findByRace(race.getRaceName());
		return gson.toJson(raceDtoList);
	}

	@RequestMapping(value = "/race/{raceName}/ajax/{noTag}")
	@ResponseBody
	public String getRaceAjaxByTag(Model model, @PathVariable String raceName, @PathVariable Long noTag) {
		Race race = raceService.findByRaceName(raceName);
		if (race == null) {
			return gson.toJson(new ArrayList<RaceDto>());
		}
		List<RaceDto> raceDtoList = tagService.findByRaceAndTag(race.getRaceName(), noTag);
		return gson.toJson(raceDtoList);
	}

	@RequestMapping("/tag/delete/{id}")

	public String deleteTag(Model model, @PathVariable Long id) {
		tagService.deleteTag(id);
		Race race = raceService.findAll().get(0);
		return "redirect:/race/" + race.getRaceName();
	}

	private static Date getParseDate(String dateText) {
		try {
			return sdf.parse(dateText);
		} catch (Exception e) {

		}
		return new Date();
	}

}
