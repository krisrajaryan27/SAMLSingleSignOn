package com.talentPool.repository.test;


import java.io.File;
import java.util.Date;

import junit.framework.TestCase;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.miscellaneous.LimitTokenCountAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;

public class TestSearch extends TestCase {
	IndexReader indexReader;
	private IndexSearcher searcher;

	public void test_I() throws Exception {
		String q = "skprasant@gmail.com";
		Analyzer analyzer = new LimitTokenCountAnalyzer(new StandardAnalyzer(),Integer.MAX_VALUE);
		QueryParser queryParser = new QueryParser("Keywords", analyzer);
		Query query = queryParser.parse(q);
		long start = new Date().getTime();
		TopScoreDocCollector collector = TopScoreDocCollector.create(50, true);
		searcher.search(query,collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		long end = new Date().getTime();
		System.out.println("Found " + hits.length + " document(s) (in " + (end - start) + " milliseconds) that matched query '" + q + "':");
		for (int i = 0; i < hits.length; i++) {
			Document doc = searcher.doc(hits[i].doc);
			System.out.println(doc.get("Id") + " = " + doc.get("Name"));

		}
	}

	protected void setUp() throws Exception {
		File indexDir = new File("D:\\Projects\\Talentpool_repo\\indexrepo");
		indexReader = DirectoryReader.open(FSDirectory.open(indexDir));
		searcher = new IndexSearcher(indexReader);
	}
	public static void main(String[] args) {
		try {
			TestSearch t = new TestSearch();
			t.setUp();t.test_I();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
