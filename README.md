## American behavior of election by example of the presidential election 2016

This project uses tools and techniques of the semantic web to determine influences
of the U.S. presidential election 2016. Therefore I explored three different indicators, which
could move people to vote for the republicans.

- number of wounded soldiers by U.S. state (2012) ->
[iCasualties](http://www.icasualties.org/OEF/Fatalities.aspx)
- number of wounded soldiers by U.S. state (2016) ->
[Statista](https://www.statista.com/statistics/303472/us-military-fatalities-in-iraq-and-afghanistan/)

- unemployment rate by U.S. state (2012)->
[U.S. Bureau of Labor Statistics](https://www.bls.gov/lau/lastrk12.htm)
- unemployment rate by U.S. state (2016)->
[Statista](https://www.statista.com/statistics/223675/state-unemployment-rate-in-the-us/)

- number of registered weapons by U.S. state (2012)->
[U.S. States Department of Justice: Bureau of Alcohol, Tobacco, Firearms Explosives](https://www.atf.gov/firearms/docs/report/firearms-commerce-united-states-2011/download)
- number of registered weapons by U.S. state (2016)->
[Statista](https://www.statista.com/statistics/215655/number-of-registered-weapons-in-the-us-by-state/)

I crawled information about each of this indicators from their different web resources and integrated them in my own
data model. Afterwards I extended the [DBpedia ontology](http://mappings.dbpedia.org/server/ontology/classes/) to model this additional information
by using the [Jena](https://jena.apache.org/) Framework. Furthermore Jena's triple store persisted the RDF model as RDF XML.

The results of the presidential election of the years 2012 and 2016 are extracted from [wikipedia](https://en.wikipedia.org/wiki/United_States_presidential_election,_2016)
and also added to the RDF model. This Class [controller.MainController](https://github.com/erohkohl/semantic-web/blob/master/src/main/java/controller/MainController.java) contains
the SPARQL query, which tries to answer the following question:

    In which U.S. states changed the presidential election between 2012 and 2016 from democratic to
    republican majority, which recognized an increase of sold weapons, number of wounded soldiers or
    the unemployment rate.
