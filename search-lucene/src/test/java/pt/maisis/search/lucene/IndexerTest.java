package pt.maisis.search.lucene;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

import org.apache.lucene.index.Term;

import junit.framework.TestCase;


public class IndexerTest extends TestCase {
    
    public void setUp() {
    }

    public void testBuildIndex() {
	List data = new ArrayList();
	data.add(new IndexableBean("1", "um one"));
	data.add(new IndexableBean("2", "dois two"));
	data.add(new IndexableBean("3", "tres tree"));
	Indexer indexer = new Indexer(new File("/home/amador/dev/cvs/search-1.3/search-lucene/target"));
	indexer.buildIndex(data);
    }

    public void testDelete() {	
	List data = new ArrayList();
	data.add(new IndexableBean("1", "um one"));
	data.add(new IndexableBean("2", "dois two"));
	data.add(new IndexableBean("3", "tres tree"));

	Indexer indexer = new Indexer(new File("/home/amador/dev/cvs/search-1.3/search-lucene/target"));
	indexer.delete(new Term("id", "2"));
    }

    public void testAdd() {	
	Indexable indexable = new IndexableBean("1", "um one");
	Indexer indexer = new Indexer(new File("/home/amador/dev/cvs/search-1.3/search-lucene/target"));
	indexer.add(indexable);
    }
}

