package model;

public class URI {

    public static String WIKI_ELECTION_2012
            = "https://en.wikipedia.org/wiki/United_States_presidential_election,_2012";

    public static final String WIKI_ELECTION_2016
            = "https://en.wikipedia.org/wiki/United_States_presidential_election,_2016";

    public static final String dbpedia = "http://dbpedia.org/ontology/";
    public static final String state = dbpedia + "State";
    public static final String statistics = "http://www.statista.com/statistics/";

    public static final String circumstances = dbpedia + "Circumstances";
    public static final String unemployed = statistics + "unemployed";

    public static final String gov = "https://www.atf.gov/";
    public static final String weapon = gov + "weapons";
    public static final String fatalities = statistics + "fatalities";

    public static final String election = dbpedia + "United_States_presidential_election";
    public static final String republican = dbpedia + "Republican_Party_United_States";
    public static final String democratic = dbpedia + "Democratic_Party_United_States";

    public static final String xsdInteger = "http://www.w3.org/2001/XMLSchema#int";
    public static final String xsdDouble = "http://www.w3.org/2001/XMLSchema#double";
}
