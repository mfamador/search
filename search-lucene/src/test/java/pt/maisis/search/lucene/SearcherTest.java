package pt.maisis.search.lucene;

import java.io.File;

import java.util.List;
import java.util.ArrayList;

import junit.framework.TestCase;


public class SearcherTest extends TestCase {

    public void testSearch() {
	Searcher s = new Searcher();
	s.search(new File("/home/psousa/dev/cvs/search-1.3/search-lucene/target"), "um dois");
    }
}
