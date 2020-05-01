package com.vkgroupstat.vkconnection.vkentity.stat;

import java.util.LinkedHashMap;

import com.vkgroupstat.vkconnection.fillers.StatItem;

public class SubscriptionStat {
	protected LinkedHashMap<String, StatItem> sexStat = null;
	protected LinkedHashMap<String, StatItem> cityStat = null;
	protected LinkedHashMap<String, StatItem> ageStat = null;
	protected LinkedHashMap<String, StatItem> activityStat = null;
	
	public SubscriptionStat() {}

	public LinkedHashMap<String, StatItem> getSexStat() { return sexStat; }
	public LinkedHashMap<String, StatItem> getCityStat() { return cityStat; }
	public LinkedHashMap<String, StatItem> getAgeStat() { return ageStat; }
	public LinkedHashMap<String, StatItem> getActivityStat() { return activityStat; }

	public void setSexStat(LinkedHashMap<String, StatItem> sexStat) { this.sexStat = sexStat; }
	public void setCityStat(LinkedHashMap<String, StatItem> cityStat) { this.cityStat = cityStat; }
	public void setAgeStat(LinkedHashMap<String, StatItem> ageStat) { this.ageStat = ageStat; }
	public void setActivityStat(LinkedHashMap<String, StatItem> activityStat) { this.activityStat = activityStat; }

	@Override
	public String toString() {
		return "sexStat =  " + sexStat + "<br>cityStat = " + cityStat + "<br>ageStat = " 
					+ ageStat + "<br> activityStat = " + activityStat + "<br>";
	}
}
