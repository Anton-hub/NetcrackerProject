package com.vkgroupstat.vkconnection.vkentity;

import java.util.LinkedHashMap;

public class SubscriptionStat {
	protected LinkedHashMap<String, Integer> sexStat = null;
	protected LinkedHashMap<String, Integer> cityStat = null;
	protected LinkedHashMap<String, Integer> ageStat = null;
	protected LinkedHashMap<String, Integer> activityStat = null;
	
	public SubscriptionStat() {}

	public LinkedHashMap<String, Integer> getSexStat() { return sexStat; }
	public LinkedHashMap<String, Integer> getCityStat() { return cityStat; }
	public LinkedHashMap<String, Integer> getAgeStat() { return ageStat; }
	public LinkedHashMap<String, Integer> getActivityStat() { return activityStat; }

	public void setSexStat(LinkedHashMap<String, Integer> sexStat) { this.sexStat = sexStat; }
	public void setCityStat(LinkedHashMap<String, Integer> cityStat) { this.cityStat = cityStat; }
	public void setAgeStat(LinkedHashMap<String, Integer> ageStat) { this.ageStat = ageStat; }
	public void setActivityStat(LinkedHashMap<String, Integer> activityStat) { this.activityStat = activityStat; }

	@Override
	public String toString() {
		return "sexStat =  " + sexStat + "<br>cityStat = " + cityStat + "<br>ageStat = " 
					+ ageStat + "<br> activityStat = " + activityStat + "<br>";
	}
}
