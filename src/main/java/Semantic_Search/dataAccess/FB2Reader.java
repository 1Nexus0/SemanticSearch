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

import Semantic_Search.ui.UI_Main_Window;
import org.semanticweb.owlapi.model.OWLOntology;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;



public class FB2Reader {

    public String readFB2(String files,OWLOntology ontobj) throws ParserConfigurationException, IOException, SAXException {
         StringBuilder str = new StringBuilder();
        File file = new File(files);
        String fname=null;
        String mname=null;
        String lname=null;
        String g=null;
         DocumentBuilder  dBuilder1 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    Document doc = dBuilder1.parse(file);
    String output ="<p>" + "<font size=\"4\" face=\"arial\" color=\"black\">" +"Описание: " + "</font>"+"</p>";
        str.append(output);
        String node;
        String node1;
        NodeList tag = doc.getElementsByTagName("title-info");
        for (int count = 0; count < tag.getLength(); count++) {
            Node nod = tag.item(count);
            NodeList nodl = nod.getChildNodes();
            for (int count1 = 0; count1 < nodl.getLength(); count1++) {
                Node finnodes = nodl.item(count1);
                if (finnodes.getNodeType() == Node.ELEMENT_NODE) {

                    node = finnodes.getNodeName();

                    switch (node) {
                        case "author":
                            str.append("<p>" + "<font size=\"4\" face=\"arial\" color=\"red\">" + "Автор: " + "</font>").append("\n" + "</p>");
                            str.append("<p>" + "<font size=\"4\" face=\"arial\" color=\"black\">" + "====================" + "</font>").append("\n" + "</p>");
                            NodeList names = finnodes.getChildNodes();
                            for (int count2 = 0; count2 < names.getLength(); count2++) {
                                Node nameList = names.item(count2);
                                if (nameList.getNodeType() == Node.ELEMENT_NODE) {
                                    node1 = nameList.getNodeName();
                                    switch (node1) {

                                        case "first-name":
                                            if (!nameList.getTextContent().isEmpty()) {
                                                str.append("<p>" + "<font size=\"4\" face=\"arial\" color=\"red\">" + "Имя: " + "</font>").append(nameList.getTextContent()).append("\n" + "</p>");
                                                fname = nameList.getTextContent() + "_";
                                            }
                                            break;
                                        case "middle-name":
                                            if (!nameList.getTextContent().isEmpty()) {
                                                str.append("<p>" + "<font size=\"4\" face=\"arial\" color=\"red\">" + "Отчество: " + "</font>").append(nameList.getTextContent()).append("\n" + "</p>");
                                                mname = nameList.getTextContent() + "_";
                                            }
                                            break;
                                        case "last-name":
                                            if (!nameList.getTextContent().isEmpty()) {
                                                str.append("<p>" + "<font size=\"4\" face=\"arial\" color=\"red\">" + "Фамилия: " + "</font>").append(nameList.getTextContent()).append("\n" + "</p>");
                                                lname = nameList.getTextContent();
                                            }
                                            break;

                                        default:

                                    }
                                }
                            }
                            str.append("<p>" + "<font size=\"4\" face=\"arial\" color=\"black\">" + "====================" + "</font>");
                            break;
                        case "genre":
                            if (!finnodes.getTextContent().isEmpty()) {
                                str.append("<p>" + "<font size=\"4\" face=\"arial\" color=\"red\">" + "Жанр: " + "</font>").append(finnodes.getTextContent()).append("\n" + "</p>");
                                g=finnodes.getTextContent();
                            }
                            break;
                        case "book-title":
                            if (!finnodes.getTextContent().isEmpty()) {
                                str.append("<p>" + "<font size=\"4\" face=\"arial\" color=\"red\">" + "Название: " + "</font>").append(finnodes.getTextContent()).append("\n" + "</p>");
                            }
                            break;
                        case "date":
                            if (!finnodes.getTextContent().isEmpty()){
                                str.append("<p>" + "<font size=\"4\" face=\"arial\" color=\"red\">" + "Дата издания: " + "</font>").append(finnodes.getTextContent()).append("\n" + "</p>");
                    }
                            break;
                        case "lang":
                            if (!finnodes.getTextContent().isEmpty()) {
                                str.append("<p>" + "<font size=\"4\" face=\"arial\" color=\"red\">" + "Язык книги: " + "</font>").append(finnodes.getTextContent()).append("\n" + "</p>");
                            }
                            break;
                        case "annotation":
                            if (!finnodes.getTextContent().isEmpty()) {
                                str.append("<p>" + "<font size=\"4\" face=\"arial\" color=\"red\">" + "Аннотация: " + "</font>" + "</p>" + "<p style=\"width:370px\">").append(finnodes.getTextContent()).append("\n" + "</p>");
                            }
                            break;

                        default:

                    }

                }
            }
        }


            String and = " AND ";
            String or = " OR ";
            String SearchString;
            if (mname !=null ) {
                 SearchString = fname + mname + lname; //+and+ g;
                 fname=null;
                 mname=null;
                lname=null;
            } else {
                 SearchString = fname + lname; //+and+g;
                fname=null;
                mname=null;
                lname=null;
            }

if (!SearchString.isEmpty()) {
    new getont().getont(ontobj, SearchString, UI_Main_Window.jTextField5.getText());
} else {UI_Main_Window.jLabel7.setText("Ontology query is empty");}



        return str.toString();
    }


}


