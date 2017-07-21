package controller;

import model.*;
import org.apache.jena.query.ResultSet;
import service.CsvReader;
import service.HtmlReader;
import service.PdfReader;
import service.SPARQLQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the services, which extract the data from their original web resources. Furthermore it passes this
 * data to class model.RdfModel, that converts internal data model to rdf and persists as rdf xml. At last MainController
 * executes a SPARQL query to answer the research question, by passing the rdf xml to custom class service.SPARQLQuery.
 */
public class MainController {

    private final String RDF_FILE = "./src/test/resources/test.rdf";

    private List<State> stateList2012;
    private List<State> stateList2016;

    private ResultSet resultSet;

    public MainController() {

        // data extraction from sources into internal data model
        stateList2012 = new ArrayList<State>();
        CsvReader csvReader = new CsvReader(CSV.STATE_2012, stateList2012, false);
        HtmlReader htmlReader = new HtmlReader(URI.WIKI_ELECTION_2012, stateList2012, 2012);
        PdfReader pdfReader = new PdfReader(stateList2012, PDF.urlWeapons, PDF.pdfWeaponFile);

        stateList2016 = new ArrayList<State>();
        csvReader = new CsvReader(CSV.STATE_2016, stateList2016, true);
        htmlReader = new HtmlReader(URI.WIKI_ELECTION_2016, stateList2016, 2016);

        // convert internal data model into rdf model
        RdfModel rdfModel = new RdfModel(stateList2012, stateList2016);

        // prepare rdf model for SPARQL Query
        SPARQLQuery sparqlQuery = new SPARQLQuery(RDF_FILE);

        /**
         * research question modelled as SPARQL query:
         * In which u.s. states changed the presidential election between 2012 and 2016 from democratic to republican
         * majority, which recognized an increase of sold weapons, number of wounded soldiers or unemployment rate.
         */
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("PREFIX dbo: <http://dbpedia.org/ontology/>");
        queryBuilder.append("PREFIX stat: <http://www.statista.com/statistics/>");
        queryBuilder.append("PREFIX gov: <https://www.atf.gov/>");

        queryBuilder.append("SELECT ?state ");
        queryBuilder.append("?number_unemployed_2012 ?number_unemployed_2016 ");
        queryBuilder.append("?number_fatalities_2012 ?number_fatalities_2016 ");
        queryBuilder.append("?number_weapons_2012 ?number_weapons_2016 ?number_rep_2012 ");
        queryBuilder.append("WHERE{ ?state ?p ?cirumstances_2012. ?state ?p ?cirumstances_2016. ");

        queryBuilder.append("{ " + this.getCirumstancesOf2012And2016ByProperty("unemployed", "stat", " < "));
        queryBuilder.append("UNION " + this.getCirumstancesOf2012And2016ByProperty("fatalities", "stat", " < ") + "} ");
        queryBuilder.append(this.getCirumstancesOf2012And2016ByProperty("weapons", "gov", " < "));

        queryBuilder.append("{ SELECT ?state WHERE { ");
        queryBuilder.append(this.getElectionOfRepAndDemoByYear("2012"));
        queryBuilder.append(this.getElectionOfRepAndDemoByYear("2016"));
        queryBuilder.append(" } } }");

        resultSet = sparqlQuery.execute(queryBuilder.toString());
    }

    /**
     * @param p:      weapons, fatalities or fatalities
     * @param prefix: gov or stat
     * @return Returns part of the SPARQL query, which is responsible for determine whether number of reg. weapons,
     * fatalities or unemployment rate increased between 2012 and 2016.
     */
    private String getCirumstancesOf2012And2016ByProperty(String p, String prefix, String ordering) {
        String s =
                "{ " +
                        "?cirumstances_2012 " + prefix + ":" + p + " ?number_" + p + "_2012." +
                        " FILTER(regex(str(?cirumstances_2012), \"2012\", \"i\")) " +

                        "?cirumstances_2016 " + prefix + ":" + p + " ?number_" + p + "_2016." +
                        " FILTER(regex(str(?cirumstances_2016), \"2016\", \"i\")) " +
                        " FILTER(?number_" + p + "_2012 " + ordering + " ?number_" + p + "_2016) " +
                        "} ";
        return s;
    }

    /**
     * @param year: 2012 or 2016
     * @return Returns part of the SPARQL query, which is responsible for determine in which states democratic party was
     * replaced by republican.
     */
    private String getElectionOfRepAndDemoByYear(String year) {
        String greaterOrLess = year.equals("2016") ? " <" : " >";
        String s =
                "?state dbo:United_States_presidential_election ?election_" + year + " ." +
                        "{" +
                        "SELECT ?election_" + year + " " +
                        "WHERE{" +
                        "?election_" + year + " dbo:Republican_Party_United_States ?number_rep_" + year + "." +
                        " FILTER(regex(str(?election_" + year + "), \"" + year + "\", \"i\")) " +

                        "?election_" + year + " dbo:Democratic_Party_United_States ?number_demo_" + year + "." +
                        " FILTER (?number_demo_" + year + greaterOrLess + " ?number_rep_" + year + ") " +
                        "}" +
                        "}";
        return s;
    }

    /**
     * Getter method just for testing
     */
    public List<State> getStateList2012() {
        return stateList2012;
    }

    public List<State> getStateList2016() {
        return stateList2016;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }
}
