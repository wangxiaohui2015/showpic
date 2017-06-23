package com.my.showpic.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.showpic.entity.CategoryItem;
import com.my.showpic.service.ICategoryItemService;
import com.my.showpic.utils.LogUtil;

@Controller
public class CategoryItemController {

	private static final Logger LOGGER = LogUtil.getServerLogger(CategoryItemController.class);

	@Autowired
	private ICategoryItemService categoryItemService;

	@ResponseBody
	@RequestMapping(value = "/updateStar", method = { RequestMethod.POST })
	public String updateStar(HttpServletRequest request) {
		String category = request.getParameter("category");
		String id = request.getParameter("id");
		String star = request.getParameter("star");
		int result = 0;
		try {
			if (!"".equals(category) && !"".equals(id) && !"".equals(star)) {
				result = categoryItemService.updateCategoryItemStar(category, Integer.parseInt(id), star);
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurred while updating star.", e);
		}
		return String.valueOf(result);
	}

	@ResponseBody
	@RequestMapping(value = "/addCategoryItem", method = { RequestMethod.POST })
	public String addCategoryItem(@RequestParam(value = "category") String category,
			@RequestParam(value = "name") String name, @RequestParam(value = "star", required = false) String star,
			@RequestParam(value = "items") String items, @RequestParam(value = "memo", required = false) String memo) {
		int result = 0;
		try {
			CategoryItem categoryItem = new CategoryItem();
			categoryItem.setCategory(category);
			categoryItem.setName(name);
			star = (star == null) ? "0" : star;
			categoryItem.setStar(star);
			categoryItem.setItems(items);
			categoryItem.setAccount(items.split(";").length);
			categoryItem.setMemo(memo);
			categoryItem.setCreateTime(new Date());
			result = categoryItemService.addCategoryItem(categoryItem);
		} catch (Exception e) {
			LOGGER.error("Exception occurred while adding category item.", e);
		}
		return String.valueOf(result);
	}
}
