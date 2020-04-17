package com.vkgroupstat.vkconnection.vkentity;

import com.google.gson.JsonObject;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Subscriber{
	private Integer id;
	private Boolean isClosed;
	private String sex;
	private Integer age;
	private String city;
	
	public Subscriber() {}
	public Subscriber(JsonObject json) {
		Pattern pattern = Pattern.compile("\\d\\d\\d\\d");
		Matcher matcher;
		
		this.id = json.get("id").getAsInt();
		
		this.isClosed = json.has("is_closed") ? json.get("is_closed").getAsBoolean() : true;
		
		this.sex = json.has("sex") ? json.get("sex").getAsString() : null;
		
		this.city = json.has("city") ? json.get("city").getAsJsonObject().get("title").getAsString() : null;	
				
		if (json.has("bdate")) {
			matcher = pattern.matcher(json.get("bdate").getAsString());
			if (matcher.find())
				this.age = 1900 + new Date().getYear() - new Integer(matcher.group());
		}
	}
	
	public Integer getId() {
		return id;
	}
	public Boolean getClosed() {
		return isClosed;
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