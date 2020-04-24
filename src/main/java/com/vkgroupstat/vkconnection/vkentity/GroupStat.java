package com.vkgroupstat.vkconnection.vkentity;

import java.util.LinkedList;

public class GroupStat extends SubscriptionStat{
	
	private Integer memberCount;
	private Integer bannedCount;
	
	public GroupStat(){
		super();
	}
	public GroupStat(LinkedList<Subscriber> subsList, Integer memberCount, Integer bannedCount) {
		super(subsList);
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
