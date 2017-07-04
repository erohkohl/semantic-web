package model;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RdfModelTest {

    private List<State> stateList2012, stateList2016;
    private OntModel ontModel;


    @Before
    public void initTest() {
        State stateOne = new State("Nebraska", "6,0", "3");
        stateOne.setRegisteredWeapons("123");
        State stateTwo = new State("Alaska", "5,0", "4");
        stateTwo.setRegisteredWeapons("124");

        stateOne.setElection(2012, "66.6%", "39.0%");
        stateTwo.setElection(2012, "42.0%", "45.0%");

        stateList2012 = new ArrayList<State>();
        stateList2012.add(stateOne);
        stateList2012.add(stateTwo);


        stateOne = new State("Nebraska", "6,4", "6");
        stateOne.setRegisteredWeapons("100");
        stateTwo = new State("Alaska", "4,8", "7");
        stateTwo.setRegisteredWeapons("104");

        stateOne.setElection(2016, "60.0%", "29.0%");
        stateTwo.setElection(2016, "22.0%", "35.0%");

        stateList2016 = new ArrayList<State>();
        stateList2016.add(stateOne);
        stateList2016.add(stateTwo);

        RdfModel rdfModel = new RdfModel(stateList2012, stateList2016);
        ontModel = rdfModel.getOntModel();
    }

    @Test
    public void testIfOntModelGotStates() {
        Individual i = ontModel.getIndividual(URI.dbpedia + "Alaska");
        assert (i != null);

        i = ontModel.getIndividual(URI.dbpedia + "Nebraska");
        assert (i != null);

        i = ontModel.getIndividual(URI.dbpedia + "New York");
        assert (i == null);
    }

    @Test
    public void testIfOntModelGotObjects() {
        Individual i = ontModel.getIndividual(URI.circumstances + "/Alaska/2012");
        assertEquals(i.getProperty(ontModel.getDatatypeProperty(URI.weapon)).getLiteral().getLexicalForm(), "124");
        assertEquals(i.getProperty(ontModel.getDatatypeProperty(URI.fatalities)).getLiteral().getLexicalForm(), "4");
        assertEquals(i.getProperty(ontModel.getDatatypeProperty(URI.unemployed)).getLiteral().getLexicalForm(), "5.0");

        i = ontModel.getIndividual(URI.circumstances + "/Nebraska/2012");
        assertEquals(i.getProperty(ontModel.getDatatypeProperty(URI.weapon)).getLiteral().getLexicalForm(), "123");
        assertEquals(i.getProperty(ontModel.getDatatypeProperty(URI.fatalities)).getLiteral().getLexicalForm(), "3");
        assertEquals(i.getProperty(ontModel.getDatatypeProperty(URI.unemployed)).getLiteral().getLexicalForm(), "6.0");
    }

    @Test
    public void testIfOntModelGotPredicates() {
        Individual i = ontModel.getIndividual(URI.dbpedia + "Alaska");
        assertEquals(i.getProperty(ontModel.getObjectProperty(URI.circumstances))
                .getProperty(ontModel.getDatatypeProperty(URI.weapon)).getPredicate().toString(), URI.weapon);

        assertEquals(i.getProperty(ontModel.getObjectProperty(URI.circumstances))
                .getProperty(ontModel.getDatatypeProperty(URI.fatalities)).getPredicate().toString(), URI.fatalities);

        assertEquals(i.getProperty(ontModel.getObjectProperty(URI.circumstances))
                .getProperty(ontModel.getDatatypeProperty(URI.unemployed)).getPredicate().toString(), URI.unemployed);
    }

    @Test
    public void testIfLiteralshaveCorrectDatartype() {
        Individual i = ontModel.getIndividual(URI.dbpedia + "Alaska");
        assertEquals(i.getProperty(ontModel.getObjectProperty(URI.circumstances))
                .getProperty(ontModel.getDatatypeProperty(URI.weapon)).getLiteral().getDatatypeURI(), URI.xsdInteger);

        assertEquals(i.getProperty(ontModel.getObjectProperty(URI.circumstances))
                .getProperty(ontModel.getDatatypeProperty(URI.fatalities)).getLiteral().getDatatypeURI(), URI.xsdInteger);

        assertEquals(i.getProperty(ontModel.getObjectProperty(URI.circumstances))
                .getProperty(ontModel.getDatatypeProperty(URI.unemployed)).getLiteral().getDatatypeURI(), URI.xsdDouble);
    }

    @Test
    public void testPercentagesOfParties() {
        Individual i = ontModel.getIndividual(URI.election + "/Alaska/2012");
        assertEquals(i.getProperty(ontModel.getDatatypeProperty(URI.democratic)).getLiteral().getLexicalForm(), "42.0");
        assertEquals(i.getProperty(ontModel.getDatatypeProperty(URI.republican)).getLiteral().getLexicalForm(), "45.0");

        i = ontModel.getIndividual(URI.election + "/Nebraska/2012");
        assertEquals(i.getProperty(ontModel.getDatatypeProperty(URI.democratic)).getLiteral().getLexicalForm(), "66.6");
        assertEquals(i.getProperty(ontModel.getDatatypeProperty(URI.republican)).getLiteral().getLexicalForm(), "39.0");

    }

}