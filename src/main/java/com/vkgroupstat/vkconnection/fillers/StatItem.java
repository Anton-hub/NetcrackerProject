package com.vkgroupstat.vkconnection.fillers;

public class StatItem {
	private Integer count;
	private Double percent;
	
	public StatItem() {}
	public StatItem(Integer count, Integer total) {
		this.count = count;
		initPercent(count, total);
	}
	
	public Integer getCount() {	return count; }
	public Double getPercent() { return percent; }
	
	private void initPercent(Integer num, Integer total) {
		Double temp = (num / total.doubleValue()) * 10000d;
		percent = new Long(Math.round(temp)).doubleValue() / 100d;
	}
}
