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

package Semantic_Search.dataAccess;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;


public class XML_content_cleaner {

    public static String xml_cleaner(File files) throws ParserConfigurationException, IOException, SAXException {
        String ret = null;

        DocumentBuilder dBuilder1 = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        Document doc = dBuilder1.parse(files);

        
        if (doc.getDocumentElement().getNodeType() == Node.ELEMENT_NODE) {
            ret = doc.getDocumentElement().getTextContent();
            
        }
        return ret;
    }
}