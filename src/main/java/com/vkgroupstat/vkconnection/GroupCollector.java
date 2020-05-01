 package com.vkgroupstat.vkconnection;

import java.util.Date;
import java.util.LinkedList;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vk.api.sdk.objects.groups.GroupFull;
import com.vkgroupstat.exception.NoDataAccessException;
import com.vkgroupstat.model.Group;
import com.vkgroupstat.vkconnection.fillers.GroupFiledFiller;
import com.vkgroupstat.vkconnection.fillers.SubscriptionStatFiller;
import com.vkgroupstat.vkconnection.parsers.PostParser;
import com.vkgroupstat.vkconnection.parsers.SubscriberParser;
import com.vkgroupstat.vkconnection.parsers.SubscriptionParser;
import com.vkgroupstat.vkconnection.vkentity.Subscriber;
import com.vkgroupstat.vkconnection.vkentity.Subscription;
import com.vkgroupstat.vkconnection.vkentity.stat.GroupStat;

@Service
public class GroupCollector {
	
	private static final Logger LOG = LogManager.getLogger(GroupCollector.class);
	private final GroupFiledFiller fieldFiller;
	
	@Autowired
	public GroupCollector (GroupFiledFiller fieldField) {
		this.fieldFiller = fieldField;
	}
	public Group collect(String groupName) throws NoDataAccessException{
		long startTime = new Date().getTime();		

		LinkedList<Subscriber> subscriberList = new SubscriberParser(groupName).parse();		
		LinkedList<Subscription> subscriptionList = new SubscriptionParser(subscriberList, groupName).parse();	
//													new TEST_SimpleSubscriptionParser(subscriberList, groupName).parse();
		
		fieldFiller.fillActivityField(subscriberList, new PostParser(groupName).pasre());		
		
		LinkedList<Subscription> slicedSubscriptionList = new LinkedList<Subscription>(subscriptionList
				.stream().limit(20).collect(Collectors.toList()));

		slicedSubscriptionList.stream().forEach(item -> item.countUp());		
		fieldFiller.fillInfoField(slicedSubscriptionList);
		
		GroupFull baseGrInf = ParsingMethodHolder.getGroupInfo(groupName);
		GroupStat groupStat = (GroupStat)new SubscriptionStatFiller(subscriberList).fillStat(
								new GroupStat(baseGrInf.getMembersCount(), fieldFiller.getBannedCount(subscriberList)));		
		
		LOG.info("Download and collect group data completed in " + (new Date().getTime() - startTime) + " miliseconds!");

		return new Group(baseGrInf.getId()
						, groupName
						, baseGrInf.getName()
						, baseGrInf.getDescription()
						, baseGrInf.getPhoto200()
						, groupStat
						, slicedSubscriptionList);
	}
}