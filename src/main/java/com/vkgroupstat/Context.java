package com.vkgroupstat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Context {	
	
	private static ApplicationContext context;
	
    @Autowired
    public Context(ApplicationContext context) {
    	Context.context = context;
    }
    
    public static ApplicationContext getContext() {
        return context;
    }
    
    public static <T> T getBean(Class<T> type) {
    	return context.getBean(type);
    }
}
