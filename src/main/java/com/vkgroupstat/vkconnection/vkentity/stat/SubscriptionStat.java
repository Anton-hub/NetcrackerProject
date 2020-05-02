package com.vkgroupstat.vkconnection.vkentity.stat;

import java.util.LinkedList;

public class SubscriptionStat {
	protected LinkedList<StatItem> sexStat = null;
	protected LinkedList<StatItem> cityStat = null;
	protected LinkedList<StatItem> ageStat = null;
	protected LinkedList< StatItem> activityStat = null;
	
	public SubscriptionStat() {}

	public LinkedList<StatItem> getSexStat() { return sexStat; }
	public LinkedList<StatItem> getCityStat() { return cityStat; }
	public LinkedList<StatItem> getAgeStat() { return ageStat; }
	public LinkedList<StatItem> getActivityStat() { return activityStat; }

	public void setSexStat(LinkedList<StatItem> sexStat) { this.sexStat = sexStat; }
	public void setCityStat(LinkedList<StatItem> cityStat) { this.cityStat = cityStat; }
	public void setAgeStat(LinkedList<StatItem> ageStat) { this.ageStat = ageStat; }
	public void setActivityStat(LinkedList<StatItem> activityStat) { this.activityStat = activityStat; }

	@Override
	public String toString() {
		return "sexStat =  " + sexStat + "<br>cityStat = " + cityStat + "<br>ageStat = " 
					+ ageStat + "<br> activityStat = " + activityStat + "<br>";
	}
}
