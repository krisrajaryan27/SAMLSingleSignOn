/** Class that abstracts the document repository. It allows
 * creation of the repository, addition of TPDocuments to the
 * repository, and searching over them. */

package com.talentPool.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.NativeFSLockFactory;

import com.talentPool.common.Logger.TPLogger;

public class TPDocRepository {
	/*
	 * Mapping doc ids to TPDocuments. Note: the doc id in map is a String while
	 * actual doc id is an int.
	 */
	private IndexSearcher searcher;
	private IndexWriter idxWriter;

	public void setIndexWriter(IndexWriter idxWriter) {
		this.idxWriter = idxWriter;
	}

	protected IndexSearcher getIndexSearcher() {
		try {
			if (searcher == null) {
				IndexReader idxReader = DirectoryReader.open(FSDirectory.open(
						new File(RepositoryConstants.DEFAULT_REPOSITORY_PATH),
						new NativeFSLockFactory()));
				searcher = new IndexSearcher(idxReader);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to open index searcher", e);
		}
		return searcher;
	}

	protected void closeIndexSearcher() {
		try {
			if (searcher != null) {
				searcher.getIndexReader().close();
				searcher = null;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to close index searcher", e);
		}

	}

	/**
	 * This is the only method accesssible publicly to modify repository
	 * 
	 * @param action
	 * @param tpDoc
	 * @param id
	 * @param docs
	 * @throws IOException
	 */
	public synchronized void modifyRepository(String action, TPDocument tpDoc,
			String id, ArrayList<TPDocument> docs) {
		if (action.equals(RepositoryConstants.ACTION_ADD_DOC)) {
			addDoc(tpDoc);
		} else if (action.equals(RepositoryConstants.ACTION_DELETE_DOC)) {
			deleteDoc(id);
		} else if (action.equals(RepositoryConstants.ACTION_UPDATE_DOC)) {
			updateDoc(tpDoc);
		} else if (action.equals(RepositoryConstants.ACTION_BATCH_UPDATE)) {
			batchUpdate(docs);
		} else if (action.equals(RepositoryConstants.ACTION_OPTIMIZE)) {
			// optimizeRepository();
		}
	}

	private void updateDoc(TPDocument tpDoc) {
		try {
			idxWriter.updateDocument(new Term("Id", tpDoc.getId()),
					tpDoc.getDoc());
		} catch (Exception ex) {
			TPLogger.getLogger().error("Unable to update", ex);
		}
	}

	private void addDoc(TPDocument tpDoc) {
		try {
			idxWriter.addDocument(tpDoc.getDoc());
		} catch (Exception ex) {
			TPLogger.getLogger().error("Unable to add", ex);
		}
	}

	private void deleteDoc(String id) {
		try {
			idxWriter.deleteDocuments(new Term("Id", id));
		} catch (Exception ex) {
			TPLogger.getLogger().error("Unable to delete", ex);
		}
	}

	/**
	 * First delete all the documents from repository and then add all. you need
	 * to implicitly optimize the index
	 * 
	 * @param docs
	 */
	private void batchUpdate(ArrayList<TPDocument> docs) {
		for (TPDocument doc : docs) {
			updateDoc(doc);
		}
		try {
			idxWriter.commit();
		} catch (IOException e) {
			TPLogger.getLogger().error("Unable to delete", e);
		}

	}

	// private void optimizeRepository() {
	// try(IndexWriter idxWriter = new IndexWriter(FSDirectory.open(new
	// File(repositoryPath),new NativeFSLockFactory(new
	// File(repositoryPath))),getIndexWriterConfig());) {
	// idxWriter.close();
	// closeIndexSearcher();
	// } catch (Exception e) {
	// TPLogger.getLogger().error("Error while optimizing repository", e);
	// }
	// }

	public IndexSearcher getSearcher() {
		return getIndexSearcher();
	}

}
