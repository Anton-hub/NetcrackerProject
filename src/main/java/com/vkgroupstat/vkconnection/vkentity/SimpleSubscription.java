package com.vkgroupstat.vkconnection.vkentity;

public class SimpleSubscription extends Subscription{
	
	public SimpleSubscription(Integer id) {
		this.id = id;
		targetSubsCount = 1;
	}
	
	public void incSubsCount() {
		targetSubsCount++;
	}
	public void concat(SimpleSubscription subs) {
		targetSubsCount += subs.targetSubsCount;
	}
	public Subscription castDown() {
		return (Subscription)this;
	}
}