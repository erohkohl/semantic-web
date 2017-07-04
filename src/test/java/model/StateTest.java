package model;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class StateTest {

    @Test
    public void StateTestOne() {
        State state = new State("New York", "6,0", "3");
        state.setRegisteredWeapons("123");
        assertEquals("New York", state.getName());
        assertEquals(123, state.getRegisteredWeapons());
        assertEquals(3, state.getNumberOfMilitaryFatalities());
        assertEquals(6.0, state.getPercentUnemploymentRate());
    }

    @Test
    public void StateTestTwo() {
        State state = new State("New York", "6,0", "3");
        state.setRegisteredWeapons("123.456");
        assertEquals("New York", state.getName());
        assertEquals(123456, state.getRegisteredWeapons());
        assertEquals(3, state.getNumberOfMilitaryFatalities());
        assertEquals(6.0, state.getPercentUnemploymentRate());
    }

    @Test
    public void StateTestCompareToOne() {
        State stateOne = new State("New York", "6,0", "3");
        stateOne.setRegisteredWeapons("123.456");
        State stateTwo = new State("New York", "6,0", "3");
        stateTwo.setRegisteredWeapons("123.457");
        assert (stateOne.compareTo(stateTwo) == -1);
    }

    @Test
    public void StateTestCompareToTwo() {
        State stateOne = new State("New York", "6,0", "3");
        stateOne.setRegisteredWeapons("123.457");
        State stateTwo = new State("New York", "6,0", "3");
        stateTwo.setRegisteredWeapons("123.456");
        assert (stateOne.compareTo(stateTwo) == 1);
    }

    @Test
    public void testToString() {
        State stateOne = new State("New York", "6,0", "3");
        stateOne.setRegisteredWeapons("123.456");
        assert (stateOne.toString().equals("New York" + "; " + "123456"));
    }

}
