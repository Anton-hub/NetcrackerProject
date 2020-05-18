package com.vkgroupstat.constants;

public enum SubscriptionParserMode {
	LESS50(1),
	LESS100(2),
	MORE100(10),
	MORE1000(50),
	MORE10000(200),
	MORE50000(400);
	private Integer threadCount;
	private SubscriptionParserMode(Integer threadCount) {
		this.threadCount = threadCount;
	}
	public Integer getThreadCount() {
		return threadCount;
	}
}