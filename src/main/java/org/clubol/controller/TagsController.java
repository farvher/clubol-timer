package org.clubol.controller;

import org.clubol.entity.Tags;
import org.clubol.services.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TagsController {

	
	@Autowired
	private TagsService tagService;
	
	
	
	
	@RequestMapping("/save/{noTag}/{time}")
	@ResponseBody
	private String newTag(@PathVariable String time,@PathVariable Long noTag){
		Tags t = new Tags();
		t.setNoTag(noTag);
		t.setNoAerial(1L);
		t.setRunDate("2017-04-23");
		t.setTime(time);
		tagService.saveTag(t);
		return "GUARDADO";
	}
	
}
