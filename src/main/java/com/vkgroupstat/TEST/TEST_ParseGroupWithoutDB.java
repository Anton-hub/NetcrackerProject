package com.vkgroupstat.TEST;

import com.vkgroupstat.Context;
import com.vkgroupstat.exception.NoDataAccessException;
import com.vkgroupstat.model.Group;
import com.vkgroupstat.vkconnection.GroupCollector;

public class TEST_ParseGroupWithoutDB {
	public static String test(String groupName) throws NoDataAccessException{
		
		Group group = Context.getBean(GroupCollector.class).collect(groupName);		

		return TEST_StringOut.groupToString(group);
	}
}