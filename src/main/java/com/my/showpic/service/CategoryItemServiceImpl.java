package com.my.showpic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.my.showpic.dao.ICategoryItemDao;
import com.my.showpic.entity.CategoryItem;
import com.my.showpic.utils.PageSorter;

@Component
public class CategoryItemServiceImpl implements ICategoryItemService {

	@Autowired
	private ICategoryItemDao categoryItemDao;

	@Override
	public List<CategoryItem> findCategoryItemsByPage(String category, String star, PageSorter pageSorter)
			throws Exception {
		pageSorter.setTableName(category);
		String condition = "";

		// 100 means query all items
		if (null != star && !"".equals(star) && !"100".equals(star)) {
			condition = "star=" + star;
		}
		pageSorter.setSqlCondition(condition);
		pageSorter.setOrderKey("create_time");
		pageSorter.setAsc(false);
		return categoryItemDao.findCategoryItemsByPage(pageSorter);
	}

	@Override
	public int updateCategoryItemStar(String category, int id, String star) throws Exception {
		CategoryItem categoryItem = findCategoryItemById(category, id);
		if (null != categoryItem) {
			categoryItem.setStar(star);
			return categoryItemDao.updateCategoryItem(categoryItem);
		}
		return 0;
	}

	@Override
	public CategoryItem findCategoryItemById(String category, int id) throws Exception {
		return categoryItemDao.findCategoryItemById(category, id);
	}

	@Override
	public int addCategoryItem(CategoryItem categoryItem) throws Exception {
		return categoryItemDao.addCategoryItem(categoryItem);
	}
}
