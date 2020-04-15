package com.vkgroupstat.vkconnection.vkentity;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonObject;

public class Subscriber{
	private Integer id;
	private Boolean isClosed;	
	private String city;
	private Integer age;
	private String sex;
	private Boolean isBanned = false;
	
	public Subscriber() {}
	public Subscriber(JsonObject json) {
		Pattern pattern = Pattern.compile("\\d\\d\\d\\d");
		Matcher matcher;
		
		if (!json.has("deactivated")) {
			
			id = json.get("id").getAsInt();

			isClosed = json.has("is_closed") ? json.get("is_closed").getAsBoolean() : null;

			sex = json.has("sex") ? json.get("sex").getAsString() : "0";

			city = json.has("city") ? json.get("city").getAsJsonObject().get("title").getAsString() : null;	

			if (json.has("bdate")) {
				matcher = pattern.matcher(json.get("bdate").getAsString());
				if (matcher.find())
					this.age = 1900 + new Date().getYear() - new Integer(matcher.group());
			}
		} else {
			sex = "0";
			isBanned = true;
			isClosed = true;
		}
	}
	
	public Integer getId() {
		return id;
	}
	public Boolean getClosed() {
		return isClosed;
	}	
	public Boolean getIsBanned() {
		return isBanned;
	}
	public String getSex() {
		return sex;
	}
	public Integer getAge() {
		return age;
	}
	public String getCity() {
		return city;
	}
	@Override
	public String toString() {
		return "Subscriber [id=" + id + ", isClosed=" + isClosed + ", sex=" + sex + ", age=" + age + ", city=" + city + "]<br>";
	}	
}