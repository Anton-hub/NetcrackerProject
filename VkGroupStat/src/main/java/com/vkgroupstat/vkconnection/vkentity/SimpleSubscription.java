package com.vkgroupstat.vkconnection.vkentity;

public class SimpleSubscription extends Subscription{
	
	public SimpleSubscription(Integer id) {
		this.id = id;
		subsCount = 1;
	}
	
	public void incSubsCount() {
		subsCount++;
	}
	public void concat(SimpleSubscription subs) {
		subsCount += subs.subsCount;
	}
	public Subscription castDown() {
		return (Subscription)this;
	}
}