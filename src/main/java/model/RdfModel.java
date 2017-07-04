package model;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.XSD;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class RdfModel {

	private final OntModel ontModel;
	private OntClass statesClass, electionClass, circumstancesClass;

	private final String fileName = "dbpedia_2015-10.owl";

	private Individual electionInd, circumstancesInd;

	public RdfModel(List<State> stateList2012, List<State> stateList2016) {

		ontModel = createOntModel();

		stateList2012.stream().forEach(s -> createIndividual(s, "2012"));
        stateList2016.stream().forEach(s -> createIndividual(s, "2016"));

		setPrefixes();
		saveAsRdfXml("./src/test/resources/test.rdf");
	}

	private void createIndividual(State state, String year){

		// individuals - state
		Individual stateInd =  statesClass.createIndividual(URI.dbpedia + state.getName());

        electionInd = electionClass.createIndividual(URI.election + "/" + state.getName() +"/" + year);
        circumstancesInd = circumstancesClass.createIndividual(URI.circumstances + "/" + state.getName() + "/" + year);

        ontModel.addLiteral(circumstancesInd, ontModel.getDatatypeProperty(URI.unemployed)
                , ResourceFactory.createTypedLiteral(state.getPercentUnemploymentRate()));

        ontModel.addLiteral(circumstancesInd, ontModel.getDatatypeProperty(URI.weapon)
                , ResourceFactory.createTypedLiteral(state.getRegisteredWeapons()));

        ontModel.addLiteral(circumstancesInd, ontModel.getDatatypeProperty(URI.fatalities)
                , ResourceFactory.createTypedLiteral(state.getNumberOfMilitaryFatalities()));

		// individuals - election
        ontModel.addLiteral(electionInd, ontModel.getDatatypeProperty(URI.democratic)
                , ResourceFactory.createTypedLiteral(state.getElection().getPercentDemocratic()));

        ontModel.addLiteral(electionInd, ontModel.getDatatypeProperty(URI.republican)
                , ResourceFactory.createTypedLiteral(state.getElection().getPercentRepublican()));

		stateInd.addProperty(ontModel.getObjectProperty(URI.election), electionInd);
		stateInd.addProperty(ontModel.getObjectProperty(URI.circumstances), circumstancesInd);
	}

	private OntModel createOntModel(){
		//read DBpedia ontology
		OntModel o = ModelFactory.createOntologyModel();
		o.read(fileName, "OWL");

		// onthology classes - state
		statesClass = o.getOntClass(URI.state);
		electionClass = o.createClass(URI.election);
		circumstancesClass = o.createClass(URI.circumstances);

		// properties state
		o.createDatatypeProperty(URI.unemployed).addRange(XSD.integer);
		circumstancesClass.addProperty(o.getDatatypeProperty(URI.unemployed)
				, "State unemployment rate in the U.S. in 2016");

		o.createDatatypeProperty(URI.weapon).addRange(XSD.integer);
		circumstancesClass.addProperty(o.getDatatypeProperty(URI.weapon)
				, "Number of registered weapons in the U.S. in 2016, by state");

		o.createDatatypeProperty(URI.fatalities).addRange(XSD.integer);
		circumstancesClass.addProperty(o.getDatatypeProperty(URI.fatalities)
				, "U.S. military fatalities in Iraq and Afghanistan as of August 2016, by state");

		// properties election
		o.createDatatypeProperty(URI.democratic).addRange(XSD.xdouble);
		electionClass.addProperty(o.getDatatypeProperty(URI.democratic)
				, "votes_Hil");

		o.createDatatypeProperty(URI.republican).addRange(XSD.xdouble);
		electionClass.addProperty(o.getDatatypeProperty(URI.republican)
				, "votes_Trump");


		o.createObjectProperty(URI.election).addRange(electionClass);
		o.createObjectProperty(URI.circumstances).addRange(circumstancesClass);
		statesClass.addProperty(o.getObjectProperty(URI.election), "election");
		statesClass.addProperty(o.getObjectProperty(URI.circumstances), "circumstances");

		return o;
	}

	private void setPrefixes(){
		ontModel.setNsPrefix("dbo", URI.dbpedia);
		ontModel.setNsPrefix("stat", URI.statistics);
		ontModel.setNsPrefix("gov", URI.gov);
	}


	private void saveAsRdfXml(String path){
        FileOutputStream out_file = null;
        try {
            out_file = new FileOutputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ontModel.write(out_file);
    }

	public OntModel getOntModel(){
		return ontModel;
	}
}
