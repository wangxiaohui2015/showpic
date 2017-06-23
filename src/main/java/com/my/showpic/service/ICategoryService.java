package com.my.showpic.service;

import java.util.List;

import com.my.showpic.entity.Category;

public interface ICategoryService {
	List<Category> findAllCategories() throws Exception;
}
