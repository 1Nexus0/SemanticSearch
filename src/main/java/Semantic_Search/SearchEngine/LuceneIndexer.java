/*
 * Copyright (C) 2016
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package Semantic_Search.SearchEngine;


import Semantic_Search.dataAccess.XML_content_cleaner;
import Semantic_Search.ui.UI_Main_Window;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.morphology.russian.RussianAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class LuceneIndexer {


    public static void luceneIndexer(String Files) throws Exception {
        
        Path p = FileSystems.getDefault().getPath(System.getProperty("java.io.tmpdir") + "/"+"LuceneIndexed");

        if (p.toFile().exists()) {
            File dir = p.toFile();
            File[] dircontent = dir.listFiles();
            for (File aDircontent : dircontent) {
                aDircontent.delete();
            }
        }

        Directory indexDir = FSDirectory.open(p);

        File dataDir = new File(Files);
        String suffix = "fb2";

        LuceneIndexer indexer = new LuceneIndexer();

        int numIndex = indexer.index(indexDir, dataDir, suffix);

        System.out.println("Всего проиндексированно: " + numIndex);

    }


    private int index(Directory indexDir, File dataDir, String suffix) throws Exception {
        IndexWriterConfig conf;


        if (UI_Main_Window.AnalyzerRuItem.isSelected()) {
            RussianAnalyzer rusa = new RussianAnalyzer();
            conf = new IndexWriterConfig(rusa).setUseCompoundFile(true);

        } else {
            StandardAnalyzer eng = new StandardAnalyzer();
            conf = new IndexWriterConfig(eng).setUseCompoundFile(true);
        }


        IndexWriter indexWriter = new IndexWriter(indexDir, conf);

        indexDirectory(indexWriter, dataDir, suffix);
        indexWriter.commit();
        int numIndexed = indexWriter.maxDoc();
        indexWriter.close();
        return numIndexed;


    }

    private void indexDirectory(IndexWriter indexWriter, File dataDir,
                                String suffix) throws IOException {

        File[] files = dataDir.listFiles();
        assert files != null;
        for (File f : files) {
            //if (f.isDirectory()) {
               // indexDirectory(indexWriter, f, suffix);
            //} else {
            try {
                indexFileWithIndexWriter(indexWriter, f, suffix);
            } catch (ParserConfigurationException | SAXException e) {
                e.printStackTrace();
            }
            // }
        }

    }

    private void indexFileWithIndexWriter(IndexWriter indexWriter, File f,
                                          String suffix) throws IOException, ParserConfigurationException, SAXException {
        String ret;
        if (f.isHidden() || f.isDirectory() || !f.canRead() || !f.exists()) {
            return;
        }
        if (suffix!=null && !f.getName().endsWith(suffix)) {
            return;
        }
        System.out.println("Индексирую файл: " + f.getCanonicalPath());


        Document doc = new Document();
        Field content = new TextField ("contents", XML_content_cleaner.xml_cleaner(f) , Field.Store.YES);
        Field filename = new StringField ("filename",f.getCanonicalPath(), Field.Store.YES);
        doc.add(content);
        doc.add(filename);

      

        indexWriter.addDocument(doc);

    }

}