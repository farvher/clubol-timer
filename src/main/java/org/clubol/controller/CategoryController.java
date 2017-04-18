package org.clubol.controller;

import org.clubol.entity.Category;
import org.clubol.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	private static final String[] CATEGORIES = { "MASTER", "MASTER A", "MASTER B", "MASTER C", "MAYORES", "PLUS",
			"UNICA" ,"ABIERTA" };

	@RequestMapping(value = "/category/autosave")
	public String addCategories() {
		for(String c : CATEGORIES){
			Category category = new Category();
			category.setActive(true);
			category.setName(c);
			category.setDescription("description : "+c.toLowerCase());
			categoryService.addCategory(category);
		}
		return "redirect:/";
	}

}
