package com.talentPool.repository;

import java.io.IOException;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

import com.talentPool.common.Logger.TPLogger;

public class MyThreadWorker implements Runnable {

	private TPDocument tpDocument;
	private IndexWriter idxWriter;
	
	public MyThreadWorker(TPDocument document, IndexWriter idxWriter) {
		this.tpDocument = document;
		this.idxWriter = idxWriter;
	}
	
	@Override
	public void run() {
		try {
			TPLogger.getLogger().error("inside subworker  :" + Thread.currentThread().getName()+ "  doc:  "+tpDocument.getId());
			idxWriter.deleteDocuments(new Term("Id", tpDocument.getId()));
			idxWriter.addDocument(tpDocument.getDoc());
		} catch (IOException e) {
			TPLogger.getLogger().error("unable to reindex document "+tpDocument.getId() );
		}
	}
}
