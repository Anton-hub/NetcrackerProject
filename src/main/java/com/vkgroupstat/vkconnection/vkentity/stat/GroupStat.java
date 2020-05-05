package com.vkgroupstat.vkconnection.vkentity.stat;

public class GroupStat extends SubscriptionStat{
	
	private Integer memberCount;
	private Integer bannedCount;
	
	public GroupStat(){
		super();
	}
	public GroupStat(Integer memberCount, Integer bannedCount) {
		super();
		this.memberCount = memberCount;
		this.bannedCount = bannedCount;
	}

	public Integer getMemberCount() {
		return memberCount;
	}
	public Integer getBannedCount() {
		return bannedCount;
	}	
}
