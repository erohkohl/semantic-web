package service;

import model.PDF;
import model.State;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by erikrohkohl on 02.05.17.
 */
public class PdfReaderTest {

    private PdfReader pdfReader;
    private List<State> stateList;

    @Before
    public void init() {
        State stateOne = new State("Nebraska", "6,0", "3");
        State stateTwo = new State("Alaska", "5,0", "4");

        stateList = new ArrayList<State>(2);
        stateList.add(stateOne);
        stateList.add(stateTwo);

        pdfReader = new PdfReader(stateList, PDF.urlWeapons, PDF.pdfWeaponFile);
    }

    @Test
    public void testIfPdfWasLoaded() {
        assertTrue(null != pdfReader.getPdDocument());
    }

    @Test
    public void testIfNumbersCorrectParsed() {
        assertEquals(8018, stateList.get(1).getRegisteredWeapons());
        assertEquals(10660, stateList.get(0).getRegisteredWeapons());
    }
}
