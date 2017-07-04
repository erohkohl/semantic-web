package service;

import model.RdfModel;
import model.State;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erikrohkohl on 22.04.17.
 */
public class SPARQLQueryTest {

    private List<State> stateList;
    private SPARQLQuery sparqlQuery;
    private final String RDF_FILE = "./src/test/resources/test.rdf";

    @Before
    public void initTest() {
        State stateOne = new State("Nebraska", "6,0", "3");
        State stateThree = new State("New_York", "7,0", "3");
        State stateTwo = new State("Alaska", "7,0", "4");

        stateOne.setRegisteredWeapons("123");
        stateThree.setRegisteredWeapons("99");
        stateTwo.setRegisteredWeapons("123");
        /*
        stateOne.setPercentDemocratic("39.0%");
        stateTwo.setPercentDemocratic("42.0%");
        stateThree.setPercentDemocratic("17.0%");

        stateOne.setPercentRepublican("29.0%");
        stateTwo.setPercentRepublican("42.0%");
        stateThree.setPercentRepublican("34.0%");
        */
        stateList = new ArrayList<State>();
        stateList.add(stateOne);
        stateList.add(stateTwo);
        stateList.add(stateThree);

        RdfModel rdfModel = new RdfModel(stateList, stateList);
        sparqlQuery = new SPARQLQuery(RDF_FILE);
    }
    /*
    @Test
    public void testSPARQLQueryFilterUndeployedWeapons(){

        ResultSet resultSet = sparqlQuery.execute(
                "PREFIX dbp: <http://dbpedia.org/ontology/>" +
                "PREFIX stat:  <http://www.statista.com/statistics/>" +
                "SELECT ?state ?number_of_weapons ?undeployed_rate WHERE{" +
                            "?state stat:weapons ?number_of_weapons. FILTER(?number_of_weapons > 100)" +
                            "?state stat:unemployed ?undeployed_rate. FILTER(?undeployed_rate >= 7.0)" +
                        "}");

        assertEquals(1, resultSet.getRowNumber());
    }

    @Test
    public void testSPARQLQueryGetAllStatesWhereDemoWon(){

        ResultSet resultSet = sparqlQuery.execute(
                "PREFIX dbp: <http://dbpedia.org/ontology/>" +
                        "PREFIX stat:  <http://www.statista.com/statistics/>" +
                        "SELECT ?state ?rep_rate ?demo_rate  WHERE{" +
                            "?state ?p dbp:State ." +
                            "?state dbp:Republican_Party_United_States ?rep_rate . " +
                            "?state dbp:Democratic_Party_United_States ?demo_rate. FILTER(?demo_rate > ?rep_rate)" +
                        "}");

        assertEquals(1, resultSet.getRowNumber());
    }

    @Test
    public void testSPARQLQueryGetAllStatesWhereRepWon(){

        ResultSet resultSet = sparqlQuery.execute(
                "PREFIX dbp: <http://dbpedia.org/ontology/>" +
                        "PREFIX stat:  <http://www.statista.com/statistics/>" +
                        "SELECT ?state ?rep_rate ?demo_rate WHERE{" +
                            "?state ?p dbp:State ." +
                            "?state dbp:Republican_Party_United_States ?rep_rate . " +
                            "?state dbp:Democratic_Party_United_States ?demo_rate. FILTER(?demo_rate < ?rep_rate)" +
                        "}");

        assertEquals(1, resultSet.getRowNumber());
    }
    */
}
