package com.vkgroupstat.vkconnection.util;

import static com.vkgroupstat.constants.SubscriptionParserMode.*;
import com.vkgroupstat.constants.SubscriptionParserMode;

public class SubscriptionParserConfig {
	private Integer subsCount;
	private SubscriptionParserMode mode;
	
	public SubscriptionParserConfig(Integer subsCount) {
		this.subsCount = subsCount;
		mode = initMode(subsCount);
	}
	
	private static SubscriptionParserMode initMode(Integer count) {
//		if (count > 50000) {
//			return MORE50000;
//		}
		if (count > 10000) {
			return MORE10000;
		}
		if (count > 1000) {
			return MORE1000;
		}
		if (count > 100) {
			return MORE100;
		}
		if (count <= 100) {
			return LESS100;
		}
		return LESS50;
	}
	
	public Integer batchSize() {
		return subsCount / mode.getThreadCount();
	}
	public Integer threadCount() {
		return mode.getThreadCount();
	}
}
