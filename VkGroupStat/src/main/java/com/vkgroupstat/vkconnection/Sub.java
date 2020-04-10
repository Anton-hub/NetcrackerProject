package com.vkgroupstat.vkconnection;

import com.google.gson.JsonObject;

class Sub{
	private Boolean isClosed;
	private Integer sex;
	private String age;
	private String city;
	public Sub(JsonObject json) {
		this.isClosed = json.has("is_closed") ? json.get("is_closed").getAsBoolean() : false;
		this.sex = json.has("sex") ? json.get("sex").getAsInt() : null;
		this.age = json.has("bdate") ? json.get("bdate").getAsString() : null;
		this.city = json.has("city") ? json.get("city").getAsJsonObject().get("title").getAsString() : null;
	}
	public Boolean getClosed() {
		return isClosed;
	}
	public Integer getSex() {
		return sex;
	}
	public String getAge() {
		return age;
	}
	public String getCity() {
		return city;
	}
	@Override
	public String toString() {
		return "<br>Sub <br>closed= " + isClosed + "<br>sex= " + sex + "<br>age= " + age + "<br>city= " + city;
	}	
}