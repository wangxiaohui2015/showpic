package com.my.showpic.dao;

import java.util.List;

import com.my.showpic.entity.Category;

public interface ICategoryDao {
	List<Category> findAllCategories() throws Exception;
}
