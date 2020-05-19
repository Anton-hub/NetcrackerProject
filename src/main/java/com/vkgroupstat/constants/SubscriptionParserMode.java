package com.vkgroupstat.constants;

public enum SubscriptionParserMode {
	LESS50(1),
	LESS100(2),
	MORE100(10),
	MORE1000(50),
	MORE10000(100),
	MORE50000(200);
	private Integer threadCount;
	private SubscriptionParserMode(Integer threadCount) {
		this.threadCount = threadCount;
	}
	public Integer getThreadCount() {
		return threadCount;
	}
}