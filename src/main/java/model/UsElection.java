package model;

public class UsElection {

    private final int year;
    private double percentDemocratic, percentRepublican;

    public UsElection(int year, String percentDemocratic, String percentRepublican) {
        this.year = year;
        this.setPercentDemocratic(percentDemocratic);
        this.setPercentRepublican(percentRepublican);
    }

    private void setPercentDemocratic(String perc) {
        double percentage = Double.valueOf(perc.replace("%", "0"));
        this.percentDemocratic = percentage;
    }

    private void setPercentRepublican(String perc) {
        double percentage = Double.valueOf(perc.replace("%", "0"));
        this.percentRepublican = percentage;
    }

    public int getYear() {
        return year;
    }

    public double getPercentDemocratic() {
        return percentDemocratic;
    }

    public double getPercentRepublican() {
        return percentRepublican;
    }

}
