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
import org.semanticweb.HermiT.Reasoner;


import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxEditorParser;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;


 
public class Get_OWL_object {


    @SuppressWarnings("javadoc")
    public OWLOntology Get_OWL_object(String files) throws ParserException {
        OWLOntology ontology = null;
        File file = new File(files);
        IRI iri = IRI.create(files);
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

        try {
            // Load an ontology.


            if ((!files.startsWith("http")) & (!files.startsWith("https"))) {


                ontology = manager.loadOntologyFromOntologyDocument(file);
                System.out.println("Загруженна онтология: " + ontology.getOntologyID());


            } else

            {


                ontology = manager.loadOntologyFromOntologyDocument(iri);
                System.out.println("Загруженна онтология: " + ontology.getOntologyID());


            }

        } catch (OWLOntologyCreationException e) {
            System.out.println("Не получилось загрузить онтологию: " + e.getMessage());
        }
        return ontology;
    }
}

class getont {
   public void getont (OWLOntology ontology, String Search, String files) {
      
        
           // We need a reasoner to do our query answering
            OWLReasoner reasoner = new Reasoner.ReasonerFactory().createReasoner(ontology);

             ShortFormProvider shortFormProvider = new SimpleShortFormProvider();
            // Create the DLQueryPrinter helper class. This will manage the
            // parsing of input and printing of results
            DLQueryPrinter dlQueryPrinter = new DLQueryPrinter(
            new DLQueryEngine(reasoner, shortFormProvider), shortFormProvider);

            
            doQuery(dlQueryPrinter, Search, files);
           
            
        } 
    

    private static void doQuery(DLQueryPrinter dlQueryPrinter, String Search,String files)
            throws ParserException {


            dlQueryPrinter.askQuery(Search.trim(), files);
            //System.out.println();

    }



    private static OWLReasoner createReasoner(final OWLOntology rootOntology) {
        // We need to create an instance of OWLReasoner. An OWLReasoner provides
        // the basic query functionality that we need, for example the ability
        // obtain the subclasses of a class etc. To do this we use a reasoner
        // factory.
        // Create a reasoner factory.
        OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
        return reasonerFactory.createReasoner(rootOntology);
    }

    
}



class DLQueryEngine {

    private final OWLReasoner reasoner;
    private final DLQueryParser parser;

    /**
     * Constructs a DLQueryEngine. This will answer "DL queries" using the
     * specified reasoner. A short form provider specifies how entities are
     * rendered.
     * 
     * @param reasoner
     *        The reasoner to be used for answering the queries.
     * @param shortFormProvider
     *        A short form provider.
     */
    public DLQueryEngine(OWLReasoner reasoner,
            ShortFormProvider shortFormProvider) {
        this.reasoner = reasoner;
        OWLOntology rootOntology = reasoner.getRootOntology();
        parser = new DLQueryParser(rootOntology, shortFormProvider);
    }

    /**
     * Gets the superclasses of a class expression parsed from a string.
     * 
     * @param classExpressionString
     *        The string from which the class expression will be parsed.
     * @return The superclasses of the specified class expression If there was a
     *         problem parsing the class expression.
     */
    public Set<OWLClass> getSuperClasses(String classExpressionString) throws ParserException {
        if (classExpressionString.trim().length() == 0) {
            return Collections.emptySet();
        }
        OWLClassExpression classExpression = parser.parseClassExpression(classExpressionString);
        NodeSet<OWLClass> superClasses = reasoner.getSuperClasses(classExpression, true);
        return superClasses.getFlattened();
    }


    /**
     * Gets the equivalent classes of a class expression parsed from a string.
     * 
     * @param classExpressionString
     *        The string from which the class expression will be parsed.
     * @return The equivalent classes of the specified class expression If there
     *         was a problem parsing the class expression.
     */
    public Set<OWLClass> getEquivalentClasses(String classExpressionString) throws ParserException {
        if (classExpressionString.trim().length() == 0) {
            return Collections.emptySet();
        }
        OWLClassExpression classExpression = parser.parseClassExpression(classExpressionString);
        Node<OWLClass> equivalentClasses = reasoner.getEquivalentClasses(classExpression);
        Set<OWLClass> result;
        if (classExpression.isAnonymous()) {
            result = equivalentClasses.getEntities();
        } else {
            result = equivalentClasses.getEntitiesMinus(classExpression.asOWLClass());
        }
        return result;
    }

    /**
     * Gets the subclasses of a class expression parsed from a string.
     * 
     * @param classExpressionString
     *        The string from which the class expression will be parsed.
     * @return The subclasses of the specified class expression If there was a
     *         problem parsing the class expression.
     */
    public Set<OWLClass> getSubClasses(String classExpressionString) throws ParserException {
        if (classExpressionString.trim().length() == 0) {
            return Collections.emptySet();
        }
        OWLClassExpression classExpression = parser.parseClassExpression(classExpressionString);
        NodeSet<OWLClass> subClasses = reasoner.getSubClasses(classExpression,true);
        return subClasses.getFlattened();
    }

    /**
     * Gets the instances of a class expression parsed from a string.
     * 
     * @param classExpressionString
     *        The string from which the class expression will be parsed.
     * @param direct
     *        Specifies whether direct instances should be returned or not.
     * @return The instances of the specified class expression If there was a
     *         problem parsing the class expression.
     */
    public Set<OWLNamedIndividual> getInstances(String classExpressionString,
            boolean direct) throws ParserException {
        if (classExpressionString.trim().length() == 0) {
            return Collections.emptySet();
        }
        OWLClassExpression classExpression = parser.parseClassExpression(classExpressionString);
        NodeSet<OWLNamedIndividual> individuals = reasoner.getInstances(classExpression, direct);
        return individuals.getFlattened();
    }
}

class DLQueryParser {

    private final OWLOntology rootOntology;
    private final BidirectionalShortFormProvider bidiShortFormProvider;

    /**
     * Constructs a DLQueryParser using the specified ontology and short form
     * provider to map entity IRIs to short names.
     * 
     * @param rootOntology
     *        The root ontology. This essentially provides the domain vocabulary
     *        for the query.
     * @param shortFormProvider
     *        A short form provider to be used for mapping back and forth
     *        between entities and their short names (renderings).
     */
    public DLQueryParser(OWLOntology rootOntology,
            ShortFormProvider shortFormProvider) {
        this.rootOntology = rootOntology;
        OWLOntologyManager manager = rootOntology.getOWLOntologyManager();
        Set<OWLOntology> importsClosure = rootOntology.getImportsClosure();
        // Create a bidirectional short form provider to do the actual mapping.
        // It will generate names using the input
        // short form provider.
        bidiShortFormProvider = new BidirectionalShortFormProviderAdapter(manager, importsClosure, shortFormProvider);
    }

    /**
     * Parses a class expression string to obtain a class expression.
     * 
     * @param classExpressionString
     *        The class expression string
     * @return The corresponding class expression if the class expression string
     *         is malformed or contains unknown entity names.
     */
    public OWLClassExpression
            parseClassExpression(String classExpressionString) throws ParserException {
        OWLDataFactory dataFactory = rootOntology.getOWLOntologyManager().getOWLDataFactory();
        // Set up the real parser
        ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(dataFactory, classExpressionString);
        parser.setDefaultOntology(rootOntology);
        // Specify an entity checker that wil be used to check a class
        // expression contains the correct names.
        OWLEntityChecker entityChecker = new ShortFormEntityChecker(
                bidiShortFormProvider);
        parser.setOWLEntityChecker(entityChecker);
        // Do the actual parsing
        return parser.parseClassExpression();
    }
}

class DLQueryPrinter {
private static String cl=null;

    private final DLQueryEngine dlQueryEngine;
    private final ShortFormProvider shortFormProvider;

    /**
     * @param engine
     *        the engine
     * @param shortFormProvider
     *        the short form provider
     */
    public DLQueryPrinter(DLQueryEngine engine,
            ShortFormProvider shortFormProvider) {
        this.shortFormProvider = shortFormProvider;
        dlQueryEngine = engine;
    }

    /**
     * @param classExpression
     *        the class expression to use for interrogation
     */
    public void askQuery(String classExpression,String files) throws ParserException {
       StringBuilder sb = new StringBuilder();

        if (classExpression.length() == 0) {
            System.out.println("Необходимо выражение");
        } else {

            sb.append("<p>"+"Ответ из онтологии: ").append(files).append("\n"+"</p>");

            Set<OWLClass> superClasses = dlQueryEngine.getSuperClasses(
                    classExpression);
            String Jenre = "<p>" + "<font size=\"4\" face=\"blue\" color=\"blue\">" + "Интересующий жанр: " + "</font>" + "</p>";
            printEntities (Jenre, superClasses,sb);
            Set<OWLClass> equivalentClasses = dlQueryEngine
                    .getEquivalentClasses(classExpression);
            for (OWLEntity entity : ((Set<? extends OWLEntity>) superClasses)) {
                cl = shortFormProvider.getShortForm(entity);
            }
            Set<OWLClass>  getSubs = dlQueryEngine.getSubClasses(cl);
            String authors = "<p>" + "<font size=\"4\" face=\"blue\" color=\"blue\">" + "Так же может быть интересно: " + "</font>" + "</p>";
            printEntities(authors,getSubs, sb);
            Set<OWLNamedIndividual> individuals = dlQueryEngine.getInstances(classExpression, true);
            String ind = "<p>" + "<font size=\"4\" face=\"blue\" color=\"blue\">" + "Возможно, вы захотите прочитать: " + "</font>" + "</p>";
            printEntities (ind, individuals,sb);
            UI_Main_Window.jLabel7.setText("<html>" + sb.toString()+ "</html>");
            
        }

    }

    private void printEntities(String name, Set<? extends OWLEntity> entities, StringBuilder sb) {

        sb.append(name);
        int length = 50 - name.length();
        for (int i = 0; i < length; i++) {
            sb.append(".");
        }
        sb.append("\n\n");
        if (!entities.isEmpty()) {
            for (OWLEntity entity : entities) {
                sb.append("\t");
                sb.append("<p>").append(shortFormProvider.getShortForm(entity)).append("</p>");
                sb.append("\n");
            }
        } else {
            sb.append("\t[NONE]\n");
        }
        sb.append("\n");

    }
}