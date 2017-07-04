package controller;

import model.State;
import org.apache.jena.query.ResultSet;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MainControllerTest {

    private MainController mainController;

    @Before
    public void initMainControllerTest() {
        mainController = new MainController();
    }

    @Test
    public void testIfStateList2012IsNotNull() {
        assert (mainController.getStateList2012() != null);
    }

    @Test
    public void testIfStateList2016IsNotNull() {
        assert (mainController.getStateList2016() != null);
    }

    @Test
    public void testFirstStateFrom2012() {
        State state = mainController.getStateList2012().get(0);
        assertEquals(state.getName(), "Alabama");
        assertEquals(state.getRegisteredWeapons(), 64027);
        assertEquals(state.getNumberOfMilitaryFatalities(), 73);
        assertTrue(state.getPercentUnemploymentRate() == 8.0);
    }

    @Test
    public void testLastStateFrom2012() {
        State state = mainController.getStateList2012().get(50);
        assertEquals(state.getName(), "Wyoming");
        assertEquals(state.getRegisteredWeapons(), 98904);
        assertEquals(state.getNumberOfMilitaryFatalities(), 14);
        assertTrue(state.getPercentUnemploymentRate() == 5.3);
    }

    @Test
    public void testFirstStateFrom2016() {
        State state = mainController.getStateList2016().get(0);
        assertEquals(state.getName(), "Alabama");
        assertEquals(state.getRegisteredWeapons(), 134687);
        assertEquals(state.getNumberOfMilitaryFatalities(), 105);
        assertTrue(state.getPercentUnemploymentRate() == 6.0);
    }

    @Test
    public void testLastStateFrom2016() {
        State state = mainController.getStateList2016().get(50);
        assertEquals(state.getName(), "Wyoming");
        assertEquals(state.getRegisteredWeapons(), 127787);
        assertEquals(state.getNumberOfMilitaryFatalities(), 22);
        assertTrue(state.getPercentUnemploymentRate() == 5.3);
    }

    @Test
    public void testNumberOfResultSet() {
        ResultSet resultSet = mainController.getResultSet();
        assertEquals(6, resultSet.getRowNumber());
    }

}
