package org.clubol.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.clubol.entity.Tags;
import org.clubol.services.RunnerService;
import org.clubol.services.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
public class RaceController {

	@Autowired
	private RunnerService runnerService;

	@Autowired
	private TagsService tagService;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private Gson gson = new Gson();

	private static final String raceView = "race";

	@RequestMapping(value = "/race")
	public String getRaceView(Model model) {
		model.addAttribute("tags", tagService.findByNoRunDate(sdf.format(new Date())));
		model.addAttribute("dateRace", sdf.format(new Date()));

		return raceView;
	}

	@RequestMapping(value = "/race/date/{dateText}")
	public String getRaceViewByDate(Model model, @PathVariable String dateText) {

		Date dateRace = getParseDate(dateText);
		model.addAttribute("tags", tagService.findByNoRunDate(dateText));
		model.addAttribute("dateRace", sdf.format(dateRace));

		return raceView;
	}

	@RequestMapping(value = "/race/tag/{tag}")
	public String getRaceViewByTag(Model model, @PathVariable Long tag) {
		model.addAttribute("tags", tagService.findByNoTag(tag));
		model.addAttribute("dateRace", sdf.format(new Date()));

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
	 * Peticiones Ajax para fecha
	 */
	@RequestMapping(value = "/race/ajax/{dateText}")
	@ResponseBody
	public String raceAjaxByDate(Model model, @PathVariable String dateText) {
		List<Tags> tags = tagService.findByNoRunDate(dateText);
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
