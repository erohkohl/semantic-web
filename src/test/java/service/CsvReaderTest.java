package service;

import model.State;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by erikrohkohl on 07.04.17.
 */
public class CsvReaderTest {

    private final String fileName = "state_2016.csv";
    private CsvReader csvReader;

    @Before
    public void initTest() {
        csvReader = new CsvReader(fileName, new ArrayList<State>(), true);
    }

    @Test
    public void testExtraction() {
        List<State> states = csvReader.getStateList();
        assertEquals(states.get(0).getName(), "Alabama");
        assertEquals(states.get(0).getRegisteredWeapons(), 134687);
        assertEquals(states.get(0).getNumberOfMilitaryFatalities(), 105);
        assertTrue(states.get(0).getPercentUnemploymentRate() == 6.0);

        assertEquals(states.get(50).getName(), "Wyoming");
        assertEquals(states.get(50).getRegisteredWeapons(), 127787);
        assertEquals(states.get(50).getNumberOfMilitaryFatalities(), 22);
        assertTrue(states.get(50).getPercentUnemploymentRate() == 5.3);
    }

}
