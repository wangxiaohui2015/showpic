package com.my.showpic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.my.showpic.dao.ICategoryDao;
import com.my.showpic.entity.Category;

@Component
public class CategoryServiceImple implements ICategoryService {

	@Autowired
	private ICategoryDao categoryDao;

	@Override
	public List<Category> findAllCategories() throws Exception {
		return categoryDao.findAllCategories();
	}
}
