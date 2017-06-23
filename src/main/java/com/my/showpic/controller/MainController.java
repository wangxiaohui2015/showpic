package com.my.showpic.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.my.showpic.entity.Category;
import com.my.showpic.entity.CategoryItem;
import com.my.showpic.service.ICategoryItemService;
import com.my.showpic.service.ICategoryService;
import com.my.showpic.utils.LogUtil;
import com.my.showpic.utils.PageSorter;

@Controller
public class MainController {

	private static final Logger LOGGER = LogUtil.getServerLogger(MainController.class);

	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private ICategoryItemService categoryItemService;

	@RequestMapping(value = "/main", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView showMainPage(HttpServletRequest request) {
		ModelAndView modAndView = new ModelAndView();
		try {

			// Query all categories
			List<Category> categories = categoryService.findAllCategories();
			modAndView.addObject("categories", categories);

			// Process parameters from page side
			String category = request.getParameter("category");
			if (null == category || "".equals(category)) {
				if (!categories.isEmpty()) {
					category = categories.get(0).getItemTableName();
				}
			}
			String star = request.getParameter("star");
			modAndView.addObject("category", category);
			modAndView.addObject("star", star);

			// Target page
			int targetPage = 1;
			String tp = request.getParameter("targetPage");
			if (null != tp && !"".equals(tp)) {
				targetPage = Integer.parseInt(tp);
			}

			// Page size, will save pageSize into application object
			int pageSize = 20;
			String ps = request.getParameter("pageSize");
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
			ServletContext servletContext = webApplicationContext.getServletContext();
			if (null == ps || "".equals(ps)) {
				ps = (String) servletContext.getAttribute("pageSize");
			}
			if (null != ps && !"".equals(ps)) {
				pageSize = Integer.parseInt(ps);
			}
			servletContext.setAttribute("pageSize", String.valueOf(pageSize));

			// Generate PageSorter and query data
			PageSorter pageSorter = new PageSorter(pageSize, targetPage);
			List<CategoryItem> categoryItems = new ArrayList<CategoryItem>();
			if (null != category && !"".equals(category)) {
				categoryItems = categoryItemService.findCategoryItemsByPage(category, star, pageSorter);
			}

			// Store data in modAndView object
			modAndView.addObject("pageSize", pageSize);
			modAndView.addObject("categoryItems", categoryItems);
			modAndView.addObject("pageSorter", pageSorter);

			// Set view name, main.jsp
			modAndView.setViewName("main");
		} catch (Exception e) {
			LOGGER.error("Exception occurred while processing main page.", e);
			modAndView.setViewName("error");
		}
		return modAndView;
	}
}
