package com.my.showpic.service;

import java.util.List;

import com.my.showpic.entity.CategoryItem;
import com.my.showpic.utils.PageSorter;

public interface ICategoryItemService {
	List<CategoryItem> findCategoryItemsByPage(String category, String star, PageSorter pageSorter) throws Exception;

	int updateCategoryItemStar(String category, int id, String star) throws Exception;

	CategoryItem findCategoryItemById(String category, int id) throws Exception;

	int addCategoryItem(CategoryItem categoryItem) throws Exception;
}
