package com.talentPool.common;

import com.talentPool.common.Logger.TPLogger;

/**
 * @author SumeetS
 *
 */
public class MyThreadLocal {
	public static final ThreadLocal<ThreadLocalContextObject> userThreadLocal = new ThreadLocal<ThreadLocalContextObject>();
	
	/**
	 * @param context
	 */
	public static void set(ThreadLocalContextObject context){
		userThreadLocal.set(context);
	}
	
	/**
	 *  DONT FORGET TO UNSET THREADLOCAL AFTER USE
	 */
	public static void unset(){
		userThreadLocal.remove();
	}
	
	/**
	 * @return
	 */
	public static ThreadLocalContextObject get(){
		try{
			return (ThreadLocalContextObject) userThreadLocal.get();
		}catch(Exception e){
			TPLogger.getLogger().error("ThreadLocal null");
			return null;
		}
		
	}
}
