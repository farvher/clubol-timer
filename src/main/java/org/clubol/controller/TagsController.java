package org.clubol.controller;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.clubol.EnvioCorreo;
import org.clubol.dto.RaceDto;
import org.clubol.entity.Race;
import org.clubol.entity.Runner;
import org.clubol.entity.Tags;
import org.clubol.services.RaceService;
import org.clubol.services.RunnerService;
import org.clubol.services.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
public class TagsController {

	@Autowired
	private RaceService raceService;

	@Autowired
	private RunnerService runnerService;

	@Autowired
	private TagsService tagService;

	@Autowired
	private VelocityEngine engine;

	private Gson gson = new Gson();

	@RequestMapping("/save/{noTag}/{time}")
	@ResponseBody
	private String newTag(@PathVariable String time, @PathVariable Long noTag) {
		Tags t = new Tags();
		t.setNoTag(noTag);
		t.setNoAerial(1L);
		t.setRunDate("2017-04-23");
		t.setTime(time);
		tagService.saveTag(t);
		return "GUARDADO";
	}

	@RequestMapping(value = "/sendmail/{correo}/{nombre}/{category}/{distance}/{pistolTime}/{chipTime}")
	@ResponseBody
	public String controllerMail(@PathVariable String correo, @PathVariable String nombre,
			@PathVariable String category, @PathVariable String distance, @PathVariable String pistolTime,
			@PathVariable String chipTime) {
		RaceDto r = new RaceDto();
		r.setCategory(category);
		r.setDistance(distance);
		r.setFirstName(nombre);
		r.setBestTime(pistolTime);
		r.setPassTime(chipTime);
		r.setEmail(correo);
		sendMail(r);
		return "enviado";
	}

	@RequestMapping(value = "/massive")
	@ResponseBody
	public String massiveSendMail(Model model) {
		
		List<Runner> runers = runnerService.findAll();
		List<RaceDto> raceDtoList = tagService.findRunnerAndTagsEmail(runers);
		for (RaceDto runner : raceDtoList) {
			RaceDto r = new RaceDto();
			r.setCategory(runner.getCategory());
			r.setDistance(runner.getDistance());
			r.setFirstName(runner.getFirstName() + " - " + runner.getPosition());
			r.setBestTime(runner.getBestTime());
			r.setPassTime(runner.getPassTime());
			r.setEmail(runner.getEmail());
			sendMail(r);
		}
		;

		return "Enviado ";
	}
	
	@RequestMapping(value = "/massive/{noTag}")
	@ResponseBody
	public String massiveSendMailByTag(Model model,@PathVariable Long noTag) {
	
		List<Runner> runers = runnerService.findByPositionAll(noTag);
		List<RaceDto> raceDtoList = tagService.findRunnerAndTagsEmail(runers);
		for (RaceDto runner : raceDtoList) {
			RaceDto r = new RaceDto();
			r.setCategory(runner.getCategory());
			r.setDistance(runner.getDistance());
			r.setFirstName(runner.getFirstName() + " - " + runner.getPosition());
			r.setBestTime(runner.getBestTime());
			r.setPassTime(runner.getPassTime());
			r.setEmail(runner.getEmail());
			sendMail(r);
		}
		;

		return "Enviado ";
	}

	public void sendMail(RaceDto r) {

		Map<String, Object> model = new HashMap<String, Object>();

		Template t = engine.getTemplate("templates/email.vm");
		VelocityContext ctx = new VelocityContext();
		ctx.put("category", r.getCategory());
		ctx.put("distance", r.getDistance());
		ctx.put("pistolTime", r.getBestTime());
		ctx.put("chipTime", r.getPassTime());
		ctx.put("firstName", r.getFirstName());
		final Writer writer = new StringWriter();
		t.merge(ctx, writer);
		try {
			EnvioCorreo.enviarMensaje(writer.toString(), r.getEmail().trim());
		} catch (Exception e) {
			System.out.print(e.getMessage());

		}

	}

}
