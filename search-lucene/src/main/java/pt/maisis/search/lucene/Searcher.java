package pt.maisis.search.lucene;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.Date;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;


public class Searcher {

    public static void search(File indexDir, String q) {
        try {
	    
            Directory fsDir = FSDirectory.getDirectory(indexDir, false);
            IndexSearcher is = new IndexSearcher(fsDir);
	    
            QueryParser parser = new QueryParser("name", new StandardAnalyzer());
            Query query = parser.parse(q);
	    
	    
            long start = new Date().getTime();
            Hits hits = is.search(query);
            long end = new Date().getTime();
            System.err.println("Found " + hits.length() +
                               " document(s) (in " + (end - start) +
                               " milliseconds) that matched query '" +
                               q + "':");
	    
            for (int i = 0; i < hits.length(); i++) {
                Document doc = hits.doc(i);
                System.out.println("[id] " + doc.get("id"));
                System.out.println("[name] " + doc.get("name"));
            }
	    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
