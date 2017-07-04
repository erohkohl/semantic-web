package service;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.FileManager;

/**
 * This class represents an additional abstraction layer between Jena and the MainController.
 */
public class SPARQLQuery {

    private Model model;

    public SPARQLQuery(String file) {
        FileManager.get().addLocatorClassLoader(this.getClass().getClassLoader());
        model = FileManager.get().loadModel(file);
    }

    /**
     * MainController calls method to pass it's SPARQL query to Jena. Therefore this method uses Jena's factories to
     * build a triple store based on the rdf model.
     *
     * @param queryString
     * @return result set of SPARQL query
     */
    public ResultSet execute(String queryString) {
        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        ResultSet results = qexec.execSelect();
        ResultSetFormatter.out(results);
        return results;
    }
}
