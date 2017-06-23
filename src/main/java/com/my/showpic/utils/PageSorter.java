package com.my.showpic.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class PageSorter {
	private Connection con;
	private ResultSet rs;
	private String sqlCondition = "";
	private String queryColumns = "*";
	private String tableName;
	private String orderKey = "";
	private boolean asc = true;
	private boolean outputSql = false;
	private int pageSize;
	private int pages;
	private int targetPage = 1;
	private int resultsAccount;
	private boolean isJoinStatement = false;
	private String[] joinStatement = new String[2];

	private static final Logger LOGGER = LogUtil.getDBLogger(PageSorter.class);

	public PageSorter(int pageSize, int targetPage) {
		this.pageSize = pageSize;
		this.targetPage = targetPage;
	}

	public PageSorter() {
	}

	public ResultSet getResult(int targetPage, int pageSize) throws Exception {
		this.targetPage = targetPage;
		this.pageSize = pageSize;
		ResultSet resultSet = null;
		if (this.isJoinStatement)
			resultSet = getResultByJoin(targetPage, pageSize);
		else {
			resultSet = getResultByCommon(targetPage, pageSize);
		}

		return resultSet;
	}

	private ResultSet getResultByCommon(int targetPage, int pageSize) throws Exception {
		try {
			Statement st = this.con.createStatement();

			if (this.sqlCondition.indexOf("group by") == -1)
				this.rs = st.executeQuery(
						"select count(*) as resultsAccount from " + this.tableName + ("".equals(this.sqlCondition) ? ""
								: new StringBuilder(" where ").append(this.sqlCondition).toString()));
			else {
				this.rs = st
						.executeQuery("select count(*) as resultsAccount from (select count(*) from " + this.tableName
								+ ("".equals(this.sqlCondition) ? ""
										: new StringBuilder(" where ").append(this.sqlCondition).append(") as alias")
												.toString()));
			}
			if (this.rs.next())
				this.resultsAccount = this.rs.getInt("resultsAccount");
			else {
				this.resultsAccount = 0;
			}
			this.pages = ((int) Math.ceil(this.resultsAccount * 1.0D / pageSize));
			if ((targetPage > this.pages) && (this.pages != 0)) {
				this.targetPage = this.pages;
				targetPage = this.pages;
			}

			String sql = "select " + this.queryColumns + " from " + this.tableName
					+ ("".equals(this.sqlCondition) ? ""
							: new StringBuilder(" where ").append(this.sqlCondition).toString())
					+ (this.orderKey.isEmpty() ? ""
							: new StringBuilder(" order by ").append(this.orderKey)
									.append(" " + (this.isAsc() ? "asc" : "desc")).toString())
					+ " limit " + (targetPage - 1) * pageSize + " , " + pageSize;

			if (this.outputSql) {
				System.out.println(sql);
			}
			this.rs = st.executeQuery(sql);
			return this.rs;
		} catch (SQLException e) {
			LOGGER.error("SQLException occurred.", e);
			throw e;
		}
	}

	private ResultSet getResultByJoin(int targetPage, int pageSize) throws Exception {
		try {
			Statement st = this.con.createStatement();
			this.rs = st.executeQuery(this.joinStatement[0]);
			if (this.rs.next())
				this.resultsAccount = this.rs.getInt("resultsAccount");
			else {
				this.resultsAccount = 0;
			}
			this.pages = ((int) Math.ceil(this.resultsAccount * 1.0D / pageSize));
			if ((targetPage > this.pages) && (this.pages != 0)) {
				this.targetPage = this.pages;
				targetPage = this.pages;
			}

			String sql = this.joinStatement[1] + " limit " + (targetPage - 1) * pageSize + " , " + pageSize;

			if (this.outputSql) {
				System.out.println(sql);
			}
			this.rs = st.executeQuery(sql);
			return this.rs;
		} catch (SQLException e) {
			LOGGER.error("SQLException occurred.", e);
			throw e;
		}
	}

	public ResultSet getResult() throws Exception {
		return getResult(this.targetPage, this.pageSize);
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public int getPages() {
		return this.pages;
	}

	public int getTargetPage() {
		return this.targetPage;
	}

	public Connection getCon() {
		return this.con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	public ResultSet getRs() {
		return this.rs;
	}

	public String getSqlCondition() {
		return this.sqlCondition;
	}

	public void setSqlCondition(String sqlCondition) {
		this.sqlCondition = sqlCondition;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int getResultsAccount() {
		return this.resultsAccount;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setTargetPage(int targetPage) {
		this.targetPage = targetPage;
	}

	public String getOrderKey() {
		return orderKey;
	}

	public void setOrderKey(String orderKey) {
		this.orderKey = orderKey;
	}

	public boolean isAsc() {
		return this.asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}

	public boolean isOutputSql() {
		return this.outputSql;
	}

	public void setOutputSql(boolean outputSql) {
		this.outputSql = outputSql;
	}

	public String getQueryColumns() {
		return this.queryColumns;
	}

	public void setQueryColumns(String queryColumns) {
		this.queryColumns = queryColumns;
	}

	public boolean isJoinStatement() {
		return this.isJoinStatement;
	}

	public void setJoinStatement(boolean isJoinStatement) {
		this.isJoinStatement = isJoinStatement;
	}

	public String[] getJoinStatement() {
		return this.joinStatement;
	}

	public void setJoinStatement(String[] joinStatement) {
		this.joinStatement = joinStatement;
	}
}