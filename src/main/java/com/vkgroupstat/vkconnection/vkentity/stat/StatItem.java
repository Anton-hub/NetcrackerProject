package com.vkgroupstat.vkconnection.vkentity.stat;

public class StatItem {
	private String name;
	private Integer count;
	private Double percent;
	
	public StatItem() {}
	public StatItem(String name, Integer count, Integer total) {
		this.name = name;
		this.count = count;
		initPercent(count, total);
	}
	
	public String getName() { return name; }
	public Integer getCount() {	return count; }
	public Double getPercent() { return percent; }
	
	private void initPercent(Integer num, Integer total) {
		Double temp = (num / total.doubleValue()) * 10000d;
		percent = new Long(Math.round(temp)).doubleValue() / 100d;
	}
	
	@Override
	public String toString() {
		return name + " : " + percent + "% (" + count + ")";
	}
}
