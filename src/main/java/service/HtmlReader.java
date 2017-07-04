package service;

import model.State;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

public class HtmlReader {

    private final String URL;
    private List<State> stateList;
    private Document doc;
    private boolean isDemoParty = true;
    private boolean isRepParty = true;
    private String DemoParty, RepParty;
    private int year;

    public HtmlReader(String URL, List<State> stateList, int year) {
        this.URL = URL;
        this.stateList = stateList;
        this.year = year;
        extract();
    }

    private void extract() {
        try {
            doc = Jsoup.connect(URL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Element table : doc.select("tbody")) {
            for (Element row : table.select("tr")) {
                for (State s : stateList) {

                    isDemoParty = true;
                    isRepParty = true;

                    if (!row.select("td").isEmpty() && row.select("td").get(0).text().contains(s.getName())
                            && row.select("td").size() > 1 && row.select("td").get(1).text().contains("WTA")) {


                        for (Element tds : row.select("td")) {

                            if (tds.text().contains("%") && isDemoParty) {
                                DemoParty = tds.text();
                                isDemoParty = false;
                                continue;
                            }

                            if (tds.text().contains("%") && !isDemoParty && isRepParty) {
                                RepParty = tds.text();
                                isRepParty = false;
                                s.setElection(year, DemoParty, RepParty);
                            }

                        }
                    }
                }
            }
        }

    }

    public Document getDocument() {
        return doc;
    }
}
