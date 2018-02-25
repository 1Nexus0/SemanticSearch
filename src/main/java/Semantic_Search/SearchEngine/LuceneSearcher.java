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


import java.io.IOException;
import java.io.StringReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;


import Semantic_Search.dataAccess.FB2Reader;
import Semantic_Search.ui.UI_Main_Window;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.analyzing.AnalyzingQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.morphology.russian.RussianAnalyzer;
import org.semanticweb.owlapi.model.OWLOntology;


public class LuceneSearcher {
    public void luceneSearcher(String query, OWLOntology ontobj) throws Exception {

        Path p = FileSystems.getDefault().getPath(System.getProperty("java.io.tmpdir") + "/"+"LuceneIndexed");
        Directory indexDir = FSDirectory.open(p);
        int hits = 100;

        LuceneSearcher searcher = new LuceneSearcher();
        searcher.searchIndex(indexDir, query, hits, ontobj);

    }

    private void searchIndex(Directory indexDir, String queryStr, int maxHits,OWLOntology ontobj)
            throws Exception {
        AnalyzingQueryParser parser;
        StringBuilder sb = new StringBuilder();
        IndexReader reader = DirectoryReader.open(indexDir);
        IndexSearcher searcher = new IndexSearcher(reader);


        if (UI_Main_Window.AnalyzerRuItem.isSelected()) {

            parser = new AnalyzingQueryParser("contents", new RussianAnalyzer());

        } else {

            parser = new AnalyzingQueryParser("contents", new StandardAnalyzer());
        }

        Query query = parser.parse(queryStr);


            TopDocs topDocs = searcher.search(query, maxHits);
            ScoreDoc[] hits = topDocs.scoreDocs;
            for (ScoreDoc hit : hits) {
                int docId = hit.doc;
                Document d = searcher.doc(docId);
                String text = d.get("contents");
                sb.append("<p>" + "<font size=\"4\" face=\"arial\" color=\"black\">" + "Найдено в: " + "</font>")
                        .append(d.get("filename")).append("\n" + "</p>")
                        .append("<p>" + "<font size=\"4\" face=\"arial\" color=\"green\">" + "Совпадения: " + "</font>" + "</p>" + "<p style=\"width:370px\">")
                        .append(HighlightedTerms(query, text)).append("\n" + "</p>");

                sb.append("<p>").append(new FB2Reader().readFB2(d.get("filename"), ontobj)).append("</p>");

            }
            sb.append("Найдено совпадений: ").append(hits.length).append("\n");

            UI_Main_Window.jLabel6.setText("<html>" + sb.toString() + "</html>");
        }



    private String HighlightedTerms(Query query, String fieldContents) throws IOException, InvalidTokenOffsetsException {
        Analyzer analyzer;
        if (UI_Main_Window.AnalyzerRuItem.isSelected()) {
        analyzer = new RussianAnalyzer(); } else {analyzer= new StandardAnalyzer();}
        Formatter formatter = new SimpleHTMLFormatter();
        int maxFragments = 6;
        TokenStream tokens =  analyzer.tokenStream("contents", new StringReader(fieldContents));

        QueryScorer scorer = new QueryScorer(query, "contents");
        Highlighter highlighter = new Highlighter(formatter, scorer);
        highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer));
        String [] fragments = highlighter.getBestFragments(tokens, fieldContents, maxFragments);

     return  Arrays.toString(fragments);
}
    }


