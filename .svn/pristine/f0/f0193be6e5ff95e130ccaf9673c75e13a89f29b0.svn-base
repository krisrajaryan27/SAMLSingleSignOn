package com.talentPool.repository;

import java.io.IOException;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.parser.utils.ParserUtils;

public class MyThead implements Runnable {
	
	private final DocumentQueue queue;
	private TPDocRepository repository;

	public MyThead(TPDocRepository repository ,DocumentQueue queue) {
		this.queue = queue;
		this.repository= repository;
	}

	@Override
	public void run() {
		TPLogger.getLogger().info("Entering " + Thread.currentThread().getName());
		try{
			Thread.sleep(30000);
			TPDocument doc = queue.get();
			int cnt =0;
			int flushCount = Integer.parseInt(TPApplicationProperties.getProperty("index.batch.flush.size"));
			while(queue.continueProducing || doc!=null ){
				if(cnt>=flushCount){
					cnt=0;
					ParserUtils.getIndexWriter().commit();
				}
				if(doc!=null){
					repository.modifyRepository(RepositoryConstants.ACTION_UPDATE_DOC,doc, null, null	);
				}
				doc = queue.get();
				cnt++;
			}
		TPLogger.getLogger().info("Exiting " + Thread.currentThread().getName());
		}catch(InterruptedException e){
			TPLogger.getLogger().error("Indexing thread interrupted",e);
		} catch (IOException e) {
			TPLogger.getLogger().error("exception while committing on indexing thread",e);
		} catch (Exception e) {
			TPLogger.getLogger().error("exception while runnign indexing thread",e);
		}
	}
}
