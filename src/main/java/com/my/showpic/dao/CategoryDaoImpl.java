package com.my.showpic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.my.showpic.entity.Category;
import com.my.showpic.utils.LogUtil;

@Component
public class CategoryDaoImpl implements ICategoryDao {

	private static final Logger LOGGER = LogUtil.getDBLogger(CategoryDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Category> findAllCategories() throws Exception {
		List<Category> categoryies = new ArrayList<Category>();
		Connection con = null;
		try {
			con = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement pst = con.prepareStatement("select * from category;");
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Category category = new Category();
				category.setId(rs.getInt("id"));
				category.setName(rs.getString("name"));
				category.setItemTableName(rs.getString("item_table_name"));
				category.setMemo(rs.getString("memo"));
				categoryies.add(category);
			}
		} catch (Exception e) {
			LOGGER.error("Failed to find all categories.", e);
			throw e;
		} finally {
			if (null != con) {
				con.close();
			}
		}
		return categoryies;
	}
}
