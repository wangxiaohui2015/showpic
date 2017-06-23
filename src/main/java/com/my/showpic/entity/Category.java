package com.my.showpic.entity;

import net.sf.json.JSONObject;

public class Category {
	private int id;
	private String name;
	private String itemTableName;
	private String memo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getItemTableName() {
		return itemTableName;
	}

	public void setItemTableName(String itemTableName) {
		this.itemTableName = itemTableName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String toString() {
		return convertToJSONObject().toString();
	}

	public JSONObject convertToJSONObject() {
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		obj.put("name", name);
		obj.put("itemTableName", itemTableName);
		obj.put("memo", memo);
		return obj;
	}

	public static void main(String[] args) {
		Category category = new Category();
		category.setId(2);
		category.setItemTableName("abc");
		category.setMemo("memo");
		category.setName("name");
		System.out.println(category.toString());
	}
}
