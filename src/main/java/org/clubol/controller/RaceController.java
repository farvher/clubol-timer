package org.clubol.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.clubol.dto.RaceDto;
import org.clubol.entity.Race;
import org.clubol.entity.Tags;
import org.clubol.services.ChronometerService;
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
	
	@Autowired
	private ChronometerService ChronometerService;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private Gson gson = new Gson();

	private static final String raceView = "race";

	
	/**
	 * Metodo para retornar el formulario de nueva carrera
	 * */
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
		}else{
			model.addAttribute("error", "Carrera " + race.getRaceName() + " ya existe.");
			return "redirect:/race/new";
		}
		return"redirect:/race/"+race.getRaceName();
	}

	/**
	 * Metodo para listar los tag que se han leido
	 * */
	@RequestMapping(value = "/race/{raceName}")
	public String getRaceView(Model model, @PathVariable String raceName) {
		Race race = raceService.findByRaceName(raceName);
		if (race==null) {
			return "redirect:/race/new";
		}
	
		model.addAttribute("race",race );
		model.addAttribute("tags", tagService.findByRace(race.getRaceName()));
//		model.addAttribute("tagRunners", tagService.findRunnerAndTags());
		return raceView;
	}
	
	/**
	 * 
	 * Metodo para listar los corredores y medir sus tiempos
	 * */
	@RequestMapping(value = "/raceRunner/{raceName}")
	public String getRaceRunnerView(Model model, @PathVariable String raceName) {
		Race race = raceService.findByRaceName(raceName);
		if (race==null) {
			return "redirect:/race/new";
		}
		model.addAttribute("race",race );
//		model.addAttribute("tags", tagService.findByRace(race.getRaceName()));
		model.addAttribute("tagRunners", tagService.findRunnerAndTags());
		return "raceTimes";
	}
	
	@RequestMapping(value = "/raceRunner/{raceName}/ajax")
	@ResponseBody
	public String getRaceRunnerViewAjax(Model model, @PathVariable String raceName) {
		Race race = raceService.findByRaceName(raceName);
		if (race==null) {
			return gson.toJson(new ArrayList<RaceDto>());
		}
		List<RaceDto> raceDtoList = tagService.findRunnerAndTags();
		return gson.toJson(raceDtoList);
	}



	/**
	 * 
	 * Peticiones Ajax todas
	 */
	@RequestMapping(value = "/race/{raceName}/ajax")
	@ResponseBody
	public String getRaceAjax(Model model,@PathVariable String raceName) {
		Race race = raceService.findByRaceName(raceName);
		if (race==null) {
			return gson.toJson(new ArrayList<RaceDto>());
		}
		List<RaceDto> raceDtoList = tagService.findByRace(race.getRaceName());
		return gson.toJson(raceDtoList);
	}


	@RequestMapping(value = "/save/tag")
	@ResponseBody
	public String saveTag(Model model) {
		Tags tag = new Tags();
		tag.setNoAerial(1L);
		tag.setRunDate("2017-04-23");
		tag.setTime("18:12:22");
		tag.setNoTag(60L);
		tagService.saveTag(tag);
		Tags tag2 = new Tags();
		tag2.setNoAerial(1L);
		tag2.setRunDate("2017-04-23");
		tag2.setTime("19:30:22");
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
