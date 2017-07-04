package model;

public class State implements Comparable<State> {

    private String name;
    private int registeredWeapons;
    private int numberOfMilitaryFatalities;

    private double percentUnemploymentRate;
    private UsElection election;

    public State(String name, String percentUnemploymentRate, String numberOfMilitaryFatalities) {
        this.name = name;
        this.percentUnemploymentRate = Double.valueOf(percentUnemploymentRate.replace(',', '.'));
        this.numberOfMilitaryFatalities = Integer.valueOf(numberOfMilitaryFatalities);
        //this.setRegisteredWeapons(registeredWeapons);
    }

    @Override
    public String toString() {
        return name + "; " + registeredWeapons;
    }

    @Override
    public int compareTo(State o) {
        return Integer.compare(registeredWeapons, o.getRegisteredWeapons());
    }

    public void setRegisteredWeapons(String registeredWeapons) {
        int index = 0;
        if (registeredWeapons.contains(",")) {
            index = registeredWeapons.indexOf(',');
            this.registeredWeapons = Integer.valueOf(registeredWeapons.substring(0, index)
                    + registeredWeapons.substring(index + 1, registeredWeapons.length()));
        } else if (registeredWeapons.contains(".")) {
            index = registeredWeapons.indexOf('.');
            this.registeredWeapons = Integer.valueOf(registeredWeapons.substring(0, index)
                    + registeredWeapons.substring(index + 1, registeredWeapons.length()));
        } else {
            this.registeredWeapons = Integer.valueOf(registeredWeapons);
        }
    }

    public void setElection(int year, String percentDemocratic, String percentRepublican) {
        this.election = new UsElection(year, percentDemocratic, percentRepublican);
    }

    public int getNumberOfMilitaryFatalities() {
        return numberOfMilitaryFatalities;
    }

    public double getPercentUnemploymentRate() {
        return percentUnemploymentRate;
    }

    public UsElection getElection() {
        return election;
    }

    public int getElectionYear() {
        return election.getYear();
    }

    public int getRegisteredWeapons() {
        return registeredWeapons;
    }

    public String getName() {
        return name;
    }
}
