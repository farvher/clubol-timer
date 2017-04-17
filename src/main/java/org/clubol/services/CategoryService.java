package org.clubol.services;

import java.util.List;

import org.clubol.entity.Category;

public interface CategoryService {
	
	
	void addCategory(Category category);
	
	List<Category> getCategories();
	
	

}
