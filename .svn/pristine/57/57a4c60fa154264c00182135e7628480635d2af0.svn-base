/**
 * 
 */
package com.talentPool.repository;

import java.io.IOException;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.AlreadyClosedException;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.parser.utils.ParserUtils;

/**
 * @author Dhakane
 * 
 */
public class TPIndexerJob implements Runnable {

	public void run() {
		TPLogger.getLogger().debug("===== Started new Indexer Job ===============");
		TPIndexEvent indexEvent = null;
		IndexWriter idxWriter=null;
		try {
			idxWriter= ParserUtils.getIndexWriter();
			TPLogger.getLogger().error(idxWriter);
			while ((indexEvent = TPIndexEventQueue.getNext()) != null) {
				TPLogger.getLogger().debug("Processing Event ====> " + indexEvent);
				IndexManager.processIndexEvent(indexEvent,idxWriter);
				TPIndexEventQueue.removeFirst();
				// Thread.sleep(1000);
			}
			idxWriter.commit();
		} catch(AlreadyClosedException e){
			TPLogger.getLogger().debug("indexwriter closed recreating it",e);
			ParserUtils.resetIndexWriter();
			run();
		}catch (Exception e) {
			TPLogger.getLogger().error("Error running indexer job",e);
		}
		TPLogger.getLogger().debug("===== End Indexer Job ===============");
	}

}
