/**
 * 
 */
package com.talentPool.repository;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shivprasad this is rpiority queue, the events are stroed in the queue
 *         using notify method All events in the queue will be processed one by
 *         one based on priority
 */
public class TPIndexEventQueue {
	private static ArrayList queue;
	private static Thread indexerJob = null;
	static {
		queue = new ArrayList();
	}

	public static synchronized void push(TPIndexEvent tpIndexEvent) {
		tpIndexEvent.setEventTime(new Date().getTime());
		queue.add(tpIndexEvent);
		if(queue.size()==1){
			queue.add(new TPIndexEvent(TPIndexEvent.TYPE_OPTIMIZE_REPOSITORY, "", TPIndexEvent.PRIORITY_LEAST));
		}
		startIndexerJob();
	}

	public static void removeFirst() {
		queue.remove(0);
	}

	/**
	 * sort the queue based on priority
	 */
	private static void sort() {
		if (queue.size() > 1) {
			List ul = Collections.synchronizedList(queue);
			Collections.sort(ul, new Comparator() {
				public int compare(Object o1, Object o2) {
					TPIndexEvent e1 = ((TPIndexEvent) o1);
					TPIndexEvent e2 = ((TPIndexEvent) o2);
					if (e1.getPriority() < e2.getPriority()) {
						return 1;
					} else {
						return -1;
					}
				}
			});
			queue = new ArrayList(ul);
		}
	}

	private static void startIndexerJob() {
		if (queue.size() > 0) {
			// start the indexer Job
			if (indexerJob == null || !indexerJob.isAlive()) {
				indexerJob = new Thread(new TPIndexerJob());
				indexerJob.start();
			}

		}
	}

	public static TPIndexEvent getNext() {
		if (queue.size() > 0) {
			sort();
			return (TPIndexEvent) queue.get(0);
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
//		TPIndexEvent e = new TPIndexEvent(TPIndexEvent.TYPE_ALL, null, TPIndexEvent.PRIORITY_NORMAL);
//		TPIndexEventQueue.push(e);
//		e = new TPIndexEvent(TPIndexEvent.TYPE_ALL, null, TPIndexEvent.PRIORITY_NORMAL);
//		TPIndexEventQueue.push(e);
//		e = new TPIndexEvent(TPIndexEvent.TYPE_ALL, null, TPIndexEvent.PRIORITY_NORMAL);
//		TPIndexEventQueue.push(e);
//		e = new TPIndexEvent(TPIndexEvent.TYPE_ALL, null, TPIndexEvent.PRIORITY_NORMAL);
//		TPIndexEventQueue.push(e);
//		e = new TPIndexEvent(TPIndexEvent.TYPE_SKILLS, "3", TPIndexEvent.PRIORITY_NORMAL);
//		TPIndexEventQueue.push(e);
//		Thread.sleep(1000);
//		e = new TPIndexEvent(TPIndexEvent.TYPE_DEGREE, "2", TPIndexEvent.PRIORITY_NORMAL);
//		TPIndexEventQueue.push(e);
//		e = new TPIndexEvent(TPIndexEvent.TYPE_ADD_APPLICANT, "1", TPIndexEvent.PRIORITY_HIGH);
//		TPIndexEventQueue.push(e);
//		e = new TPIndexEvent(TPIndexEvent.TYPE_DEGREE, "3", TPIndexEvent.PRIORITY_NORMAL);
//		TPIndexEventQueue.push(e);
//		e = new TPIndexEvent(TPIndexEvent.TYPE_DEGREE, "4", TPIndexEvent.PRIORITY_HIGH);
//		TPIndexEventQueue.push(e);
//		e = new TPIndexEvent(TPIndexEvent.TYPE_UPDATE_APPLICANT, "2", TPIndexEvent.PRIORITY_HIGH);
//		TPIndexEventQueue.push(e);
//		e = new TPIndexEvent(TPIndexEvent.TYPE_ALL, null, TPIndexEvent.PRIORITY_NORMAL);
//		TPIndexEventQueue.push(e);
//		

	}

}
