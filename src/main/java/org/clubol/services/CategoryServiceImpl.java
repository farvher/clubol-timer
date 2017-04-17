package org.clubol.services;

import java.util.List;

import org.clubol.dao.CategoryRepository;
import org.clubol.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl  implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public void addCategory(Category category) {
		categoryRepository.save(category);		
	}

	@Override
	public List<Category> getCategories() {
		return categoryRepository.findAll();
	} 

}
