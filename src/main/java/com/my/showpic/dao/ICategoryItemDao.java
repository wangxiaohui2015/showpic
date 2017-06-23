package com.my.showpic.dao;

import java.util.List;

import com.my.showpic.entity.CategoryItem;
import com.my.showpic.utils.PageSorter;

public interface ICategoryItemDao {
	List<CategoryItem> findCategoryItemsByPage(PageSorter pageSorter) throws Exception;

	int updateCategoryItem(CategoryItem categoryItem) throws Exception;
	
	CategoryItem findCategoryItemById(String category, int id) throws Exception;
	
	int addCategoryItem(CategoryItem categoryItem) throws Exception;
}
