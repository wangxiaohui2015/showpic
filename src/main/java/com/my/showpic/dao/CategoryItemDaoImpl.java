package com.my.showpic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.my.showpic.entity.CategoryItem;
import com.my.showpic.utils.LogUtil;
import com.my.showpic.utils.PageSorter;

@Component
public class CategoryItemDaoImpl implements ICategoryItemDao {

	private static final Logger LOGGER = LogUtil.getDBLogger(CategoryItemDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<CategoryItem> findCategoryItemsByPage(PageSorter pageSorter) throws Exception {
		List<CategoryItem> categoryItems = new ArrayList<CategoryItem>();
		try {
			pageSorter.setCon(jdbcTemplate.getDataSource().getConnection());
			ResultSet rs = pageSorter.getResult();
			while (rs.next()) {
				CategoryItem item = new CategoryItem();
				item.setId(rs.getInt("id"));
				item.setName(rs.getString("name"));
				item.setStar(rs.getString("star"));
				item.setItems(rs.getString("items"));
				item.setAccount(rs.getInt("account"));
				item.setMemo(rs.getString("memo"));
				item.setCreateTime(new Date(rs.getLong("create_time")));
				item.setCategory(pageSorter.getTableName());
				categoryItems.add(item);
			}
		} catch (Exception e) {
			LOGGER.error("Failed to find category items by page.", e);
			throw e;
		} finally {
			if (null != pageSorter.getCon()) {
				pageSorter.getCon().close();
			}
		}
		return categoryItems;
	}

	@Override
	public int updateCategoryItem(CategoryItem categoryItem) throws Exception {
		int result = 0;
		Connection con = null;
		try {
			con = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement pst = con
					.prepareStatement("update " + categoryItem.getCategory() + " set star=? where id=?;");
			pst.setString(1, categoryItem.getStar());
			pst.setInt(2, categoryItem.getId());
			result = pst.executeUpdate();
		} catch (Exception e) {
			LOGGER.error("Failed to update category item.", e);
		} finally {
			if (null != con) {
				con.close();
			}
		}
		return result;
	}

	@Override
	public CategoryItem findCategoryItemById(String category, int id) throws Exception {
		Connection con = null;
		CategoryItem item = null;
		try {
			con = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement pst = con.prepareStatement("select * from " + category + " where id=?;");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				item = new CategoryItem();
				item.setId(rs.getInt("id"));
				item.setName(rs.getString("name"));
				item.setStar(rs.getString("star"));
				item.setItems(rs.getString("items"));
				item.setAccount(rs.getInt("account"));
				item.setMemo(rs.getString("memo"));
				item.setCategory(category);
				item.setCreateTime(new Date(rs.getLong("create_time")));
			}
		} catch (Exception e) {
			LOGGER.error("Failed to find category item by id.", e);
		} finally {
			if (null != con) {
				con.close();
			}
		}
		return item;
	}

	@Override
	public int addCategoryItem(CategoryItem categoryItem) throws Exception {
		int result = 0;
		Connection con = null;
		try {
			con = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement pst = con.prepareStatement("insert into " + categoryItem.getCategory()
					+ " (name, star, items, account, memo, create_time) values (?, ?, ?, ?, ?, ?);");
			pst.setString(1, categoryItem.getName());
			pst.setString(2, categoryItem.getStar());
			pst.setString(3, categoryItem.getItems());
			pst.setInt(4, categoryItem.getAccount());
			pst.setString(5, categoryItem.getMemo());
			pst.setLong(6, categoryItem.getCreateTime().getTime());
			result = pst.execute() ? 1 : 0;
		} catch (Exception e) {
			LOGGER.error("Failed to add category item.", e);
		} finally {
			if (null != con) {
				con.close();
			}
		}
		return result;
	}
}
