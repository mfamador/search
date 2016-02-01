package pt.maisis.search.lucene;

import java.util.List;
import java.util.Iterator;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.index.IndexReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Indexer {
    private static Log log = LogFactory.getLog(Indexer.class);
    
    private File indexDir;
    private Directory dir;
    
    public Indexer(final File dir) {
        try {
            this.dir = FSDirectory.getDirectory(dir);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildIndex(List data) {
        if (data == null || data.isEmpty()) {
            log.info("Nothing to index");
            return;
        }


        synchronized (this) {
            IndexWriter writer = null;	    
            try {
                writer = new IndexWriter(this.dir, new StandardAnalyzer(), true);
                writer.setUseCompoundFile(false);

                if (log.isDebugEnabled()) {
                    log.debug("[start indexing]");
                }

                for (Iterator it = data.iterator(); it.hasNext();) {
                    Indexable indexable = (Indexable) it.next();
		    
                    String[] fields = indexable.getFields();
                    Document doc = new Document();		    
                    for (int i = 0; i < fields.length; i++) {
                        doc.add(new Field
                                (fields[i], 
                                 indexable.getValue(fields[i]), 
                                 Field.Store.YES, 
                                 Field.Index.TOKENIZED));
                    }
                    writer.addDocument(doc);

                    if (log.isDebugEnabled()) {
                        log.debug("[indexed]: " + doc);
                    }
                }

                if (log.isDebugEnabled()) {		
                    log.debug("[total indexed]: " + writer.docCount());
                }
                writer.optimize();
	    
            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void add(Indexable indexable) {

        synchronized (this) {
            IndexWriter writer = null;
            try {
                writer = new IndexWriter(this.dir, new StandardAnalyzer(), false);
                writer.setUseCompoundFile(false);
		
                String[] fields = indexable.getFields();
                Document doc = new Document();		    
                for (int i = 0; i < fields.length; i++) {
                    doc.add(new Field(fields[i], 
                                      indexable.getValue(fields[i]), 
                                      Field.Store.YES, 
                                      Field.Index.TOKENIZED));
                }
		
                writer.addDocument(doc);

                if (log.isDebugEnabled()) {
                    log.debug("[added]: " + doc);
                }

                writer.optimize();

            } catch (Exception e) {
                e.printStackTrace();
		
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
			
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void delete(Term term) {
        synchronized (this) {
	    
            IndexReader reader = null;
            try {
                reader = IndexReader.open(this.dir);
                int deleted = reader.deleteDocuments(term);	    

                if (log.isDebugEnabled()) {
                    log.debug("[index]: deleted " + deleted + 
                              " documents containing " + term);
                }


            } catch (Exception e) {
                e.printStackTrace();
		
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
			
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    public void update(Term term, Indexable indexable) {
        synchronized (this) {
            delete(term);
            add(indexable);
        }
    }
}
