package service;

import model.State;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Stream;

public class CsvReader {

    private String fileName;
    private List<State> stateList;

    public CsvReader(String fileName, List<State> stateList, boolean loadWeapons) {
        this.fileName = fileName;
        this.stateList = stateList;
        extract(loadWeapons);
    }

    private void extract(boolean loadWeapons) {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)));

        Stream<String> stream = reader.lines();
        stream.forEach((String s) -> {
            int index = s.indexOf(';');
            String[] array = s.split(";");
            if (!array[0].equals("State")) {
                State state = new State(array[0], array[1], array[2]);
                if (loadWeapons) {
                    state.setRegisteredWeapons(array[3]);
                }
                stateList.add(state);
            }
        });
    }

    public List<State> getStateList() {
        return stateList;
    }
}
