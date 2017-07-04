package service;

import model.State;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class HtmlReaderTest {

    private HtmlReader htmlReader;
    private List<State> stateList;

    @Test
    public void shouldParsePercCorrect2016() {
        stateList = new ArrayList<State>();
        CsvReader csvReader = new CsvReader("state_2016.csv", stateList, false);
        htmlReader = new HtmlReader("https://en.wikipedia.org/wiki/United_States_presidential_election,_2016"
                , stateList, 2016);

        assertTrue(stateList.get(0).getElection().getPercentDemocratic() == 34.36);
        assertTrue(stateList.get(0).getElection().getPercentRepublican() == 62.08);
        assertTrue(stateList.get(0).getName().equals("Alabama"));

        assertTrue(stateList.get(50).getElection().getPercentDemocratic() == 21.63);
        assertTrue(stateList.get(50).getElection().getPercentRepublican() == 67.40);
        assertTrue(stateList.get(50).getName().equals("Wyoming"));
    }

    @Test
    public void shouldParsePercCorrect2012() {
        stateList = new ArrayList<State>();
        CsvReader csvReader = new CsvReader("state_2012.csv", stateList, true);
        htmlReader = new HtmlReader("https://en.wikipedia.org/wiki/United_States_presidential_election,_2012"
                , stateList, 2012);

        assertTrue(stateList.get(0).getElection().getPercentDemocratic() == 38.36);
        assertTrue(stateList.get(0).getElection().getPercentRepublican() == 60.55);
        assertTrue(stateList.get(0).getName().equals("Alabama"));

        assertTrue(stateList.get(50).getElection().getPercentDemocratic() == 27.82);
        assertTrue(stateList.get(50).getElection().getPercentRepublican() == 68.64);
        assertTrue(stateList.get(50).getName().equals("Wyoming"));
    }
}
